package com.price.finance_recorder_rest.entrypoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.price.finance_recorder_rest.service.AuthenticationService;
import com.price.finance_recorder_rest.service.AuthenticationServiceImpl;
import com.price.finance_recorder_rest.service.UserDTO;


@Path("/authentication")
public class AuthenticationEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public AuthenticationRsp userLogin(AuthenticationReq loginCredentials)
    {
        AuthenticationRsp returnValue = new AuthenticationRsp();
        
        AuthenticationService authenticationService = new AuthenticationServiceImpl();
        UserDTO authenticatedUser = authenticationService.authenticate(loginCredentials.getUserName(), loginCredentials.getUserPassword());

       // Reset Access Token
        authenticationService.resetSecurityCridentials(loginCredentials.getUserPassword(), 
                 authenticatedUser);
        
        String accessToken = authenticationService.issueAccessToken(authenticatedUser);
        
        returnValue.setId(authenticatedUser.getUserId());
        returnValue.setToken(accessToken);
        
        return returnValue;
    }
}
