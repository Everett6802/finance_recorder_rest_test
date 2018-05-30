package com.price.finance_recorder_rest.service;

import java.util.List;

public interface UserService 
{
    UserDTO createUser(UserDTO user);
    UserDTO getUser(String id);
    UserDTO getUserByUserName(String userName);
    List<UserDTO> getUsers(int start, int limit);
    void updateUserDetails(UserDTO userDetails);
    void deleteUser(UserDTO userDto);
    boolean verifyEmail(String token);
}
