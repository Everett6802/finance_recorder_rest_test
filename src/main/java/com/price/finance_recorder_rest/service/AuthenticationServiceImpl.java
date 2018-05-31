package com.price.finance_recorder_rest.service;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.price.finance_recorder_rest.common.UserProfileUtils;
import com.price.finance_recorder_rest.exception.AuthenticationException;
import com.price.finance_recorder_rest.exception.EmailVerificationException;
import com.price.finance_recorder_rest.exception.ExceptionType;
import com.price.finance_recorder_rest.persistence.MySQLDAO;
import com.price.finance_recorder_rest.persistence.MySQLDAOImpl;


public class AuthenticationServiceImpl implements AuthenticationService {

	MySQLDAO database;

    private void updateUserProfile(UserDTO userProfile) {
        this.database = new MySQLDAOImpl();
        try {
            database.openConnection();
            database.updateUser(userProfile);
        } finally {
            database.closeConnection();
        }
    }

	@Override
	public UserDTO authenticate(String userName, String password) throws AuthenticationException {
        UserService UserService = new UserServiceImpl();
        UserDTO storedUser = UserService.getUserByUserName(userName); // User name must be unique in our system

        if (storedUser == null) {
            throw new AuthenticationException(ExceptionType.AUTHENTICATION_FAILED.getExceptionMessage());
        }
        
// For email verification, used for preventing user from login until they verify email        
//        if(!storedUser.getEmailVerificationStatus())
//        {
//            throw new EmailVerificationException(ExceptionType.EMAIL_ADDRESS_NOT_VERIFIED.getExceptionMessage());
//        }

        String encryptedPassword = null;

        encryptedPassword = new UserProfileUtils().
                generateSecurePassword(password, storedUser.getSalt());

        boolean authenticated = false;
        if (encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())) {
            if (userName != null && userName.equalsIgnoreCase(storedUser.getEmail())) {
                authenticated = true;
            }
        }

        if (!authenticated) {
            throw new AuthenticationException(ExceptionType.AUTHENTICATION_FAILED.getExceptionMessage());
        }

        return storedUser;
	}

	@Override
    public String issueAccessToken(UserDTO userProfile) throws AuthenticationException {
        String returnValue = null;

        String newSaltAsPostfix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;

        byte[] encryptedAccessToken = null;
        try {
            encryptedAccessToken = new UserProfileUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Faled to issue secure access token");
        }

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

        // Split token into equal parts
        int tokenLength = encryptedAccessTokenBase64Encoded.length();

        String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

        userProfile.setToken(tokenToSaveToDatabase);
        // Update the token into database
        updateUserProfile(userProfile);

        return returnValue;
    }

	@Override
    public void resetSecurityCridentials(String password, UserDTO userProfile) {
        // Generate a new salt
        UserProfileUtils userUtils = new UserProfileUtils();
        String salt = userUtils.getSalt(30);
        
        // Generate a new password 
        String securePassword = userUtils.generateSecurePassword(password, salt);
        userProfile.setSalt(salt);
        userProfile.setEncryptedPassword(securePassword);
        
        // Update user profile 
        updateUserProfile(userProfile);
 
    }

}
