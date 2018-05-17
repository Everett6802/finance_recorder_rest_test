package com.price.finance_recorder_rest.persistence;

import java.util.List;

import com.price.finance_recorder_rest.service.UserDTO;

public interface MySQLDAO {
    void openConnection();
    void closeConnection();
    UserDTO getUserByUserName(String userName);
    UserDTO saveUser(UserDTO user);
    UserDTO getUser(String id);
    List<UserDTO> getUsers(int start, int limit);
    void updateUser(UserDTO userProfile);
    void deleteUser(UserDTO userProfile);
    UserDTO getUserByEmailToken(String token);
}
