package com.sprint.mission.discodeit.validator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * https://stackoverflow.com/questions/37320870/is-there-a-uuid-validator-annotation
 */

@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "Not a valid UUID")
public @interface ValidUUID {

  String message() default "{invalid.uuid}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

