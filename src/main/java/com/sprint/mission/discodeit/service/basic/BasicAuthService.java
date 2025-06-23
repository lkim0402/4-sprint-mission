package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.AuthService.UserLoginRequestDto;
import com.sprint.mission.discodeit.dto.AuthService.UserLoginResponseDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.Mapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final Mapper mapper;

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {

        // checking if username exists
        User user = userRepository.findByUsername(userLoginRequestDto.username())
                .orElseThrow(() -> new NoSuchElementException("Invalid username - username not found!"));

        // checking if pw equals to the pw in the repo
        if (!user.getPassword().equals(userLoginRequestDto.password())) {
            throw new IllegalArgumentException("Invalid password!");
        }

        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
            .orElseThrow(() -> new NoSuchElementException("User Status with user id " +  user.getId() + " not found!"));


        return mapper.toUserLoginResponseDto(user, userStatus);
    }
}
