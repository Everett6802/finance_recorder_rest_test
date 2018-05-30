package com.price.finance_recorder_rest.entrypoint;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;

import com.price.finance_recorder_rest.namebinding.Secured;
import com.price.finance_recorder_rest.service.UserDTO;
import com.price.finance_recorder_rest.service.UserService;
import com.price.finance_recorder_rest.service.UserServiceImpl;


@Path("/users")
public class UserEntryPoint 
{
	@POST
    @Consumes(MediaType.APPLICATION_JSON) // Input format
    @Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML} ) // Output format
    public UserRsp createUser(UserReq requestObject) {
		UserRsp returnValue = new UserRsp();

        // Prepare UserDTO
        UserDTO userDto = new UserDTO();
// Bean object, copy from requestObject to userDto
// Only firstName, lastName, email, password variables are copied;
        BeanUtils.copyProperties(requestObject, userDto);
        
// Pass into service layer
        // Create new user 
        UserService userService = new UserServiceImpl();
// Return a user transfer object read by database
        UserDTO createdUserProfile = userService.createUser(userDto);
 
        //Prepare response
// Only firstName, lastName, email, password, href variables are copied;
// Should NOT contain any sensitive database data 
        BeanUtils.copyProperties(createdUserProfile, returnValue);

		return returnValue;
	}

    @Secured
    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML} )
    public UserRsp getUser(@PathParam("id") String id)
    {
        UserRsp returnValue = null;
        
        UserService userService = new UserServiceImpl();
        UserDTO userProfile = userService.getUser(id);
                
        returnValue = new UserRsp();
        BeanUtils.copyProperties(userProfile, returnValue);
        
        return returnValue;
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserRsp> getUsers(@DefaultValue("0") @QueryParam("start") int start, 
            @DefaultValue("50") @QueryParam("limit") int limit) {
  
        UserService userService = new UserServiceImpl();
        List<UserDTO> users = userService.getUsers(start, limit);
        
        // Prepare return value 
        List<UserRsp> returnValue = new ArrayList<UserRsp>();
        for (UserDTO userDto : users) {
            UserRsp userModel = new UserRsp();
            BeanUtils.copyProperties(userDto, userModel);
            userModel.setHref("/users/" + userDto.getUserId());
            returnValue.add(userModel);
        }
        
        return returnValue;
    }
    
//    @PUT
//    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public UserRsp updateUserDetails(@PathParam("id") String id,
//            UpdateUserRequestModel userDetails) {
//        
//        UserService userService = new UserServiceImpl();
//        UserDTO storedUserDetails = userService.getUser(id);
//        
//         // Set only those fields you would like to be updated with this request
//        if(userDetails.getFirstName() !=null && !userDetails.getFirstName().isEmpty())
//        {
//            storedUserDetails.setFirstName(userDetails.getFirstName());  
//        }
//        storedUserDetails.setLastName(userDetails.getLastName());
//        
//        // Update User Details
//        userService.updateUserDetails(storedUserDetails);
//        
//        // Prepare return value 
//        UserRsp returnValue = new UserRsp();
//        BeanUtils.copyProperties(storedUserDetails, returnValue);
//
//
//        return returnValue;
// }

////    @Secured
//    @DELETE
//    @Path("/{id}")
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public DeleteUserProfileResponseModel deleteUserProfile(@PathParam("id") String id) {
//        DeleteUserProfileResponseModel returnValue = new DeleteUserProfileResponseModel();
//        returnValue.setRequestOperation(RequestOperation.DELETE);
//        
//        UsersService userService = new UsersServiceImpl();
//        UserDTO storedUserDetails = userService.getUser(id);
// 
//        userService.deleteUser(storedUserDetails);
//
//        returnValue.setResponseStatus(ResponseStatus.SUCCESS);
// 
//        return returnValue;
//    }

}
