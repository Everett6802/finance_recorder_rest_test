package com.price.finance_recorder_rest.service;

import com.price.finance_recorder_rest.exception.AuthenticationException;

public interface AuthenticationService {
    UserDTO authenticate(String userName, String password) throws AuthenticationException;
    String issueAccessToken(UserDTO userProfile) throws AuthenticationException;
    public void resetSecurityCridentials(String password, UserDTO userProfile);
}
