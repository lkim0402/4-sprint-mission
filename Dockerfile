# ====== build args는 반드시 FROM보다 위에 선언 ======
ARG BUILDER_IMAGE=gradle:7.6.0-jdk17
ARG RUNTIME_IMAGE=amazoncorretto:17.0.7-alpine

# ============ (1) Builder ============
FROM ${BUILDER_IMAGE} AS builder
ENV GRADLE_USER_HOME=/home/gradle/.gradle

USER root
WORKDIR /app
RUN mkdir -p $GRADLE_USER_HOME && chown -R gradle:gradle /home/gradle /app
USER gradle

# enabling the gradle wrapper
COPY --chown=gradle:gradle gradlew ./
COPY --chown=gradle:gradle gradle ./gradle
COPY --chown=gradle:gradle build.gradle settings.gradle ./
RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon --refresh-dependencies dependencies || true

COPY --chown=gradle:gradle src ./src
RUN ./gradlew clean build --no-daemon --no-parallel -x test

RUN ls -l /app/build/libs


# ============ (2) Runtime ============

FROM ${RUNTIME_IMAGE}

# ENV should come after FROM
ENV PROJECT_NAME=discodeit
ENV PROJECT_VERSION=1.2-M8
ENV SPRING_PROFILES_ACTIVE=prod
ENV JVM_OPS=''

WORKDIR /app

COPY --from=builder /app/build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar app.jar
EXPOSE 80
ENTRYPOINT ["sh", "-c", "java $JVM_OPTS -jar app.jar"]
