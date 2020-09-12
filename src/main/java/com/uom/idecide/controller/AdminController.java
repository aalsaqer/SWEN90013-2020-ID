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
 * 控制器层
 * @author Administrator
 *
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
	 * add an admin account
	 */
	@RequestMapping(method= RequestMethod.POST)
	public Result add(@RequestBody Admin admin){
		adminService.add(admin);
		return new Result(true, StatusCode.OK,"Inserting successfully");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(@RequestBody Admin admin){
		admin =adminService.login(admin.getEmail(),admin.getPassword());
		if(admin == null){		//为null的原因之一是密码错误
			return new Result(false, StatusCode.LOGINERROR,"login fail");
		}
		String token = jwtUtil.createJWT(admin.getAdminId(),admin.getEmail(),"admin");
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);		//把token返回给前端
		map.put("roles","admin");	//告诉前端role是admin
		return new Result(true, StatusCode.OK,"login successful",map);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.PUT)
	public Result logout(@RequestBody Admin admin){
		//TODO 登出只需要在前端销毁token即可
		return new Result(true, StatusCode.OK,"logout successful");
	}

	@RequestMapping(value = "/adminList", method = RequestMethod.GET)
	public Result adminList(){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		return new Result(true, StatusCode.OK,"operation successful",adminService.findAll());
	}

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

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{adminId}",method= RequestMethod.GET)
	public Result findById(@PathVariable(value="adminId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		return new Result(true, StatusCode.OK,"fetching successfully",adminService.findById(id));
	}

	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(method= RequestMethod.PUT)
	public Result updateById(@RequestBody Admin admin){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		adminService.updateById(admin);
		return new Result(true, StatusCode.OK,"updating successfully");
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{adminId}",method= RequestMethod.DELETE)
	public Result deleteById(@PathVariable(value="adminId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		adminService.deleteById(id);
		return new Result(true, StatusCode.OK,"deleting successfully");
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
	}
	
}
