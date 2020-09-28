package com.uom.idecide.controller;

import com.uom.idecide.entity.PageResult;
import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;
import com.uom.idecide.pojo.Admin;
import com.uom.idecide.service.AdminService;

import com.uom.idecide.util.JwtUtil;

import com.uom.idecide.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller layer
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * admin user sign up
	 */
	@RequestMapping(method= RequestMethod.POST)
	public Result add(@RequestBody Admin admin){
		try{
			adminService.add(admin);
		}catch(Exception e){
			return new Result(false, StatusCode.REPERROR,e.getMessage());
		}
		String token = jwtUtil.createJWT(admin.getAdminId(),admin.getEmail(),"admin");
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);
		map.put("roles","admin");
		map.put("id",admin.getAdminId());
		return new Result(true, StatusCode.OK,"sign up successful",map);
	}

	/**
	 * admin user login
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(@RequestBody Admin admin){
		admin =adminService.login(admin.getEmail(),admin.getPassword());
		if(admin == null){		//if admin equals to null, its mean that the user dose not exist, or wrong password
			return new Result(false, StatusCode.LOGINERROR,"login fail");
		}
		String token = jwtUtil.createJWT(admin.getAdminId(),admin.getEmail(),"admin");
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);
		map.put("roles","admin");
		map.put("id",admin.getAdminId());
		return new Result(true, StatusCode.OK,"login successful",map);
	}

	/**
	 * query all existing admin user in database
	 */
	@RequestMapping(value = "/adminList", method = RequestMethod.GET)
	public Result adminList(){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		return new Result(true, StatusCode.OK,"operation successful",adminService.findAll());
	}

	/**
	 * find the details of a admin user by admin ID
	 * Require: admin user permission
	 */
	@RequestMapping(value="/{adminId}",method= RequestMethod.GET)
	public Result findById(@PathVariable(value="adminId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		return new Result(true, StatusCode.OK,"fetch successful",adminService.findById(id));
	}

	/**
	 * update the details of a admin user by admin ID
	 * Require: admin user permission
	 */
	@RequestMapping(method= RequestMethod.PUT)
	public Result updateById(@RequestBody Admin admin){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		adminService.updateById(admin);
		return new Result(true, StatusCode.OK,"update successful");
	}

	/**
	 * delete an admin user by admin ID
	 * Require: admin user permission
	 */
	@RequestMapping(value="/{adminId}",method= RequestMethod.DELETE)
	public Result deleteById(@PathVariable(value="adminId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		adminService.deleteById(id);
		return new Result(true, StatusCode.OK,"delete successful");
	}

/*
	@RequestMapping(value = "/adminList/{page}/{size}", method = RequestMethod.GET)
	public Result adminListWithPagination(@PathVariable(value="page") int page, @PathVariable(value="size") int size){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		Page<Admin> pages = adminService.findAllWithPagination(page, size);
		//PageResult中第一个是返回记录条数，第二个是对应的adminList
		return new Result(true, StatusCode.OK,"operation successful", new PageResult<Admin>(page,pages.getTotalElements(),pages.getTotalPages(),pages.getContent()));
	}

	@RequestMapping(value = "/jwt", method = RequestMethod.POST)
	public Result testJwt(HttpServletRequest req){
		String token = (String) req.getAttribute("claims_admin");
		if(token!=null){
			System.out.println(token);
		}else{
			System.out.println("没有token");
		}
		return new Result(true, StatusCode.OK,"test end");
	}*/
	
}
