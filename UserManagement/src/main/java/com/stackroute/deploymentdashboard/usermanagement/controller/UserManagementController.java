package com.stackroute.deploymentdashboard.usermanagement.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.deploymentdashboard.usermanagement.Exceptions.*;
import com.stackroute.deploymentdashboard.usermanagement.domains.UserModel;
import com.stackroute.deploymentdashboard.usermanagement.services.UserManagementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v1.0/continousdelivery")
@Api(value = "project", description = "Operations pertaining to Users")
@CrossOrigin(origins = "*")
public class UserManagementController {

	@Autowired
	private UserManagementService userService;

	/*@Autowired
	private KafkaProducerService kafkaProd;*/

	@ApiOperation(value = "View a list of available projects", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	/*
	 * request handler for getting list of all projects
	 */
	@GetMapping(value = "/user", produces = { "application/json" })
	public ResponseEntity<?> list() {

		try {
			List<UserModel> users = this.userService.read();
			return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);
		} catch (UserNotFoundException exp) {
			return new ResponseEntity<String>(exp.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * request handler for adding a project
	 */

	@PostMapping(value = "/user/add", consumes = { "application/json" })
	@ApiOperation(value = "Add a project")
	public ResponseEntity<String> postJson(RequestEntity<UserModel> newData) {
		try {
			if ((newData.getBody().getUserName() == null) || (newData.getBody().getEmailId() == null)) {
				return new ResponseEntity<String>("Username or EmailId cannot be EMPTY", HttpStatus.PARTIAL_CONTENT);
			} else if (!emailValidation(newData.getBody().getEmailId())) {
				return new ResponseEntity<String>("EmailId not valid", HttpStatus.PARTIAL_CONTENT);
			} else {
				
				this.userService.addUser(newData.getBody());
				return new ResponseEntity<String>("User Added Successfully", HttpStatus.CREATED);
			}
		} catch (UserAlreadyExistsException exp) {
			return new ResponseEntity<String>(exp.getMessage(), HttpStatus.CONFLICT);

		}
	}

	/*
	 * request handler for updating a project
	 */
	@PutMapping(value = "/user/update", consumes = { "application/json" })
	@ApiOperation(value = "Update a project")
	public ResponseEntity<String> putJson(RequestEntity<UserModel> updateData) {
		try {
			if ((updateData.getBody().getUserName() == null) || (updateData.getBody().getEmailId() == null)) {
				return new ResponseEntity<String>("Username or EmailId cannot be EMPTY", HttpStatus.PARTIAL_CONTENT);
			} else if (!emailValidation(updateData.getBody().getEmailId())) {
				return new ResponseEntity<String>("EmailId not valid", HttpStatus.PARTIAL_CONTENT);
			} else {
				//this.kafkaProd.produce(updateData.getBody());
				this.userService.update(updateData.getBody());
				return new ResponseEntity<String>("User Updated Successfully", HttpStatus.OK);
			}
		} catch (UserNotFoundException exp) {
			return new ResponseEntity<String>(exp.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	/*
	 * request handler for showing user by id
	 */
	@GetMapping(value = "/user/{userId}", produces = { "application/json" })
	@ApiOperation(value = "Search  user with an ID", response = UserModel.class)
	public ResponseEntity<?> getone(@PathVariable("userId") String userId) {

		try {
			UserModel user = this.userService.readById(userId);
			return new ResponseEntity<UserModel>(user, HttpStatus.OK);
		} catch (UserNotFoundException exp) {
			return new ResponseEntity<String>(exp.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * request handler for showing user by userName
	 */
	/*
	 * @GetMapping(value = "/user/{userName}", produces = { "application/json" })
	 * 
	 * @ApiOperation(value = "Search  User with userName", response =
	 * UserModel.class) public ResponseEntity<?>
	 * getByUserName(@PathVariable("userName") String userName) {
	 * 
	 * try { UserModel user = this.userService.readByUserName(userName); return new
	 * ResponseEntity<UserModel>(user, HttpStatus.OK); } catch
	 * (UserNotFoundException exp) { return new
	 * ResponseEntity<String>(exp.getMessage(), HttpStatus.BAD_REQUEST); } }
	 */

	/*
	 * request handler for deleting a project by id
	 */

	@ApiOperation(value = "Delete a project")
	@DeleteMapping(value = "/user/delete/{userId}")
	public ResponseEntity<String> delete(@PathVariable("userId") String userId) {
		try {
			this.userService.deleteById(userId);
			return new ResponseEntity<String>("User with Id " + userId + " Deleted Succesfully", HttpStatus.OK);
		} catch (UserNotFoundException exp) {
			return new ResponseEntity<String>(exp.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * DEFAULT MAPPING
	 */
	@RequestMapping()
	public ResponseEntity<String> defaultMap() {
		return new ResponseEntity<String>("Request Not Found, Please Enter Proper Url", HttpStatus.NOT_FOUND);
	}

	/*
	 * Function to validate emailId
	 */
	private static boolean emailValidation(String emailId) {
		String regex = ".*?@[a-z]*?.[a-z]*?";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(emailId);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

}
