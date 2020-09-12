package com.uom.idecide.controller;

import com.uom.idecide.entity.PageResult;
import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;
import com.uom.idecide.pojo.User;
import com.uom.idecide.service.UserService;

import com.uom.idecide.util.JwtUtil;

import com.uom.idecide.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器层
 * @author Administrator
 *
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

	/**
	 * add new user
	 */
	@RequestMapping(method= RequestMethod.POST)
	public Result add(@RequestBody User user){
		try{
			userService.add(user);
		}catch(Exception e){
			return new Result(false, StatusCode.REPERROR,e.getMessage());
		}
		return new Result(true, StatusCode.OK,"Inserted Successfully");
	}

	/**
	 * Query by ID
	 * Require: admin user permission
	 */
	@RequestMapping(value="/{userId}",method= RequestMethod.GET)
	public Result findById(@PathVariable(value="userId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);//鉴权是否为admin用户
		}catch(Exception e){
			return new Result(false,StatusCode.ACCESSERROR,e.getMessage());
		}
        User user = userService.findById(id);
		return new Result(true, StatusCode.OK,"queried successfully",user);
	}

	/**
	 * Update by ID
	 * Require: current user permission
	 */
	@RequestMapping(value="/{userId}",method= RequestMethod.PUT)
	public Result updateById(@RequestBody User user,
							 @PathVariable(value="userId") String id){
		try{
			PrivilegeUtil.checkIsThisUser(request,user.getUserId());
		}catch(Exception e){
			return new Result(false,StatusCode.ACCESSERROR,e.getMessage());
		}
		return new Result(true, StatusCode.OK,"updated successfully");
	}

	/**
	 * delete by ID
	 * Require: admin user permission
	 */
	@RequestMapping(value="/{userId}",method= RequestMethod.DELETE)
	public Result deleteById(@PathVariable(value="userId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);	//鉴权是否为admin用户
		}catch(Exception e){
			return new Result(false,StatusCode.ACCESSERROR,e.getMessage());
		}
		userService.deleteById(id);
		return new Result(true, StatusCode.OK,"deleted successfully");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(@RequestBody User user){
		try {
			user =userService.login(user.getEmail(),user.getPassword());
		} catch (Exception e) {
			return new Result(false, StatusCode.LOGINERROR,e.getMessage());
		}

		String token = jwtUtil.createJWT(user.getUserId(),user.getEmail(),"user");
		//把token打印出来看看
		System.out.println(token);
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);		//把token返回给前端
		map.put("roles","user");	//告诉前端role是user
		return new Result(true, StatusCode.OK,"login successfully",map);
	}

	/**
	 * 登出
	 * user权限
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.PUT)
	public Result logout(@RequestBody User user){
		//TODO 登出只需要在前端销毁token即可
		return new Result(true, StatusCode.OK,"logout successfully");
	}

	/**
	 * 查询用户列表
	 * admin权限
	 */
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public Result userList(){
		List<User> userList;
		try{
			PrivilegeUtil.checkAdmin(request);	//鉴权是否为admin用户
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
	@RequestMapping(value = "/userList/{page}/{size}", method = RequestMethod.GET)
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
	}
	
}
