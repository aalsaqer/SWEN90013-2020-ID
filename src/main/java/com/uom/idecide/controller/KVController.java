package com.uom.idecide.controller;

import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;
import com.uom.idecide.pojo.Admin;
import com.uom.idecide.pojo.KeyValue;
import com.uom.idecide.service.AdminService;
import com.uom.idecide.service.KVService;
import com.uom.idecide.util.JwtUtil;
import com.uom.idecide.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Controller layer
 */
@RestController
@CrossOrigin
@RequestMapping("/kv")
public class KVController {

	@Autowired
	private KVService kVService;

	@Autowired
	private HttpServletRequest request;

	/**
	 * for storage, put the key-value pair into database
	 */
	@RequestMapping(method= {RequestMethod.POST, RequestMethod.PUT})
	public Result add(@RequestBody KeyValue kv){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		kVService.add(kv);
		return new Result(true, StatusCode.OK,"insert successful");
	}

	/**
	 * find the details of a admin user by admin ID
	 * Require: admin user permission
	 */
	@RequestMapping(value="/{adminId}",method= RequestMethod.GET)
	public Result findById(@PathVariable(value="adminId") String id){
/*		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}*/
		try{
			return new Result(true, StatusCode.OK,"fetch successful",kVService.findById(id));
		}catch (NoSuchElementException e){
			throw new NoSuchElementException();
		}
	}

	/**
	 * for delete
	 */
	@RequestMapping(value="/{adminId}",method= RequestMethod.DELETE)
	public Result deleteById(@PathVariable(value="adminId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		kVService.deleteById(id);
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
