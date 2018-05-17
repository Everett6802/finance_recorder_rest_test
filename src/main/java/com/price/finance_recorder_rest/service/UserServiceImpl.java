package com.price.finance_recorder_rest.service;

import java.util.List;

import com.price.finance_recorder_rest.common.UserProfileUtils;
import com.price.finance_recorder_rest.exception.CouldNotCreateRecordException;
import com.price.finance_recorder_rest.exception.ExceptionType;
import com.price.finance_recorder_rest.exception.NoRecordFoundException;
import com.price.finance_recorder_rest.persistence.MySQLDAO;
import com.price.finance_recorder_rest.persistence.MySQLDAOImpl;

public class UserServiceImpl implements UserService {

    MySQLDAO database;
    UserProfileUtils userProfileUtils = new UserProfileUtils();

    public UserServiceImpl() {
        this.database = new MySQLDAOImpl();
    }
	
	@Override
	public UserDTO createUser(UserDTO user) {
        UserDTO returnValue = null;

        // Validate the required fields
        user.validateRequiredFields();

        // Check if user already exists
        UserDTO existingUser = this.getUserByUserName(user.getEmail());
        if (existingUser != null) {
            throw new CouldNotCreateRecordException(ExceptionType.RECORD_ALREADY_EXISTS.name());
        }

        // Generate secure public user id 
        String userId = userProfileUtils.generateUserId(30);
        user.setUserId(userId);

        // Generate salt 
        String salt = userProfileUtils.getSalt(30);
        // Generate secure password 
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setEncryptedPassword(encryptedPassword);
        user.setEmailVerificationStatus(false);
        user.setEmailVerificationToken(userProfileUtils.generateEmailverificationToken(30));

        // Record data into a database 
        returnValue = this.saveUser(user);

        return returnValue;
	}

	@Override
    public UserDTO getUser(String id) {
        UserDTO returnValue = null;
        try {
            this.database.openConnection();
            returnValue = this.database.getUser(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NoRecordFoundException(ExceptionType.NO_RECORD_FOUND.getExceptionMessage());
        } finally {
            this.database.closeConnection();
        }
        return returnValue;
    }

    @Override
    public UserDTO getUserByUserName(String userName) {
        UserDTO userDto = null;

        if (userName == null || userName.isEmpty()) {
            return userDto;
        }

        // Connect to database 
        try 
        {
            this.database.openConnection();
            userDto = this.database.getUserByUserName(userName);
        }
        catch (Exception e)
        {
        	System.err.println("Exception occurs while connecting to database, due to: " + e);
        } 
        finally 
        {
            this.database.closeConnection();
        }

        return userDto;
    }

    private UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;
        // Connect to database 
        try {
            this.database.openConnection();
            returnValue = this.database.saveUser(user);
        } finally {
            this.database.closeConnection();
        }

        return returnValue;
    }

    @Override
    public List<UserDTO> getUsers(int start, int limit) {

        List<UserDTO> users = null;

        // Get users from database
        try {
            this.database.openConnection();
            users = this.database.getUsers(start, limit);
        } finally {
            this.database.closeConnection();
        }

        return users;
    }
}
