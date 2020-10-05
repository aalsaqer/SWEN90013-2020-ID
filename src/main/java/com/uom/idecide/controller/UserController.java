package com.uom.idecide.controller;

import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;
import com.uom.idecide.pojo.User;
import com.uom.idecide.service.UserService;

import com.uom.idecide.util.IdWorker;
import com.uom.idecide.util.JwtUtil;

import com.uom.idecide.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller layer
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private IdWorker idWorker;

	private String ANONYMOUS_NAME = "tempUser";

	/**
	 * user sign up
	 */
	@RequestMapping(method= RequestMethod.POST)
	public Result add(@RequestBody User user){
		String userId = PrivilegeUtil.getUserId(request);
		if(userId!=null){
			//It means that this user completed survey and want sign up
			user.setUserId(userId);	//set user id from JWT
		}
		try{
			userService.add(user);
		}catch(Exception e){
			return new Result(false, StatusCode.REPERROR,e.getMessage());
		}

		String token = jwtUtil.createJWT(user.getUserId(),user.getUsername(),"user");
		System.out.println("sign up token --->: " + token);

		Map<String,Object> map = new HashMap<>();
		map.put("token",token);		//return the JWT to frontend
		map.put("roles","user");	//tell the frontend the role is user
		map.put("id",user.getUserId());
		return new Result(true, StatusCode.OK,"sign up successfully",map);
	}

	/**
	 * user login
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(@RequestBody User user){
		try {
			user =userService.login(user.getUsername(),user.getPassword());
		} catch (Exception e) {
			return new Result(false, StatusCode.LOGINERROR,e.getMessage());
		}

		String token = jwtUtil.createJWT(user.getUserId(),user.getUsername(),"user");
		System.out.println("login token --->: " + token);
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);		//return the JWT to frontend
		map.put("roles","user");	//tell the frontend the role is user
		map.put("id",user.getUserId());
		return new Result(true, StatusCode.OK,"login successfully",map);
	}

	/**
	 * find the details of a user by user ID
	 * Require: admin user permission
	 */
	@RequestMapping(value="/{userId}",method= RequestMethod.GET)
	public Result findById(@PathVariable(value="userId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch(Exception e){
			return new Result(false,StatusCode.ACCESSERROR,e.getMessage());
		}
        User user = userService.findById(id);
		return new Result(true, StatusCode.OK,"queried successfully",user);
	}

	/**
	 * Update the details of an user
	 * Require: current user permission
	 */
	@RequestMapping(method= RequestMethod.PUT)
	public Result updateById(@RequestBody User user){
		try{
			PrivilegeUtil.checkIsThisUser(request,user.getUserId());
		}catch(Exception e){
			return new Result(false,StatusCode.ACCESSERROR,e.getMessage());
		}
		userService.updateById(user);
		return new Result(true, StatusCode.OK,"updated successfully");
	}

	/**
	 * delete by ID
	 * Require: admin user permission
	 */
	@RequestMapping(value="/{userId}",method= RequestMethod.DELETE)
	public Result deleteById(@PathVariable(value="userId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);	//check whether this user is an admin user
		}catch(Exception e){
			return new Result(false,StatusCode.ACCESSERROR,e.getMessage());
		}
		userService.deleteById(id);
		return new Result(true, StatusCode.OK,"deleted successfully");
	}

	/**
	 * anonymous user login
	 * provide the JWT to the frontend in case this user want to sign up later
	 */
	@RequestMapping(value = "/anonymousLogin")
	public Result anonymousLogin(){
		String userId = idWorker.nextId()+"";

		String tempUsername = ANONYMOUS_NAME;		//provide a fake email in case of Null Pointer Exception when fetching email
		String token = jwtUtil.createJWT(userId,tempUsername,"user");
		System.out.println("anonymous login token --->: " + token);
		System.out.println("anonymous user id --->: " + userId);

		Map<String,Object> map = new HashMap<>();
		map.put("token",token);		//return the JWT to frontend
		map.put("roles","user");	//tell the frontend the role is user
		map.put("id",userId);
		return new Result(true, StatusCode.OK,"anonymous login successfully",map);
	}


	/**
	 * check all the users
	 * Require: admin user permission
	 */
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public Result userList(){
		List<User> userList;
		try{
			PrivilegeUtil.checkAdmin(request);	//check whether this user is an admin user
		}catch(Exception e){
			return new Result(false,StatusCode.ACCESSERROR,e.getMessage());
		}
		userList = userService.findAll();
		return new Result(true, StatusCode.OK,"operation successful",userList);
	}

	/**
	 * 查询用户列表含分页
	 * admin权限
	 */
/*	@RequestMapping(value = "/userList/{page}/{size}", method = RequestMethod.GET)
	public Result userListWithPagination(@PathVariable(value="page") int page,
										 @PathVariable(value="size") int size){
		Page<User> pages;
		try{
			PrivilegeUtil.checkAdmin(request);	//鉴权是否为admin用户
		}catch(Exception e){
			return new Result(false,StatusCode.ACCESSERROR,e.getMessage());
		}
		pages = userService.findAllWithPagination(page, size);

		//PageResult中第一个是返回记录条数，第二个是对应的userList
		return new Result(true, StatusCode.OK,"Operation Successful", new PageResult<User>(page,pages.getTotalElements(),pages.getTotalPages(),pages.getContent()));
	}


	@RequestMapping(value = "/jwt", method = RequestMethod.POST)
	public Result testJwt(HttpServletRequest req){
		String token = (String) req.getAttribute("claims_user");
		if(token!=null){
			System.out.println(token);
		}else{
			System.out.println("No Token");
		}
		return new Result(true, StatusCode.OK,"test end");
	}*/
	
}
