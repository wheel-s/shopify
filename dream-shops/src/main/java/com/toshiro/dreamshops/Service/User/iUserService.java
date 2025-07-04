package com.toshiro.dreamshops.Service.User;

import com.toshiro.dreamshops.Model.User;
import com.toshiro.dreamshops.Request.CreateUserRequest;
import com.toshiro.dreamshops.Request.UserUpdateRequest;
import com.toshiro.dreamshops.dto.UserDto;

public interface iUserService {
    User getUserById(Long userId);
    User createUser (CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);


    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
