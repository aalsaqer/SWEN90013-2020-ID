package com.uom.idecide.controller;

import com.uom.idecide.entity.PageResult;
import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;
import com.uom.idecide.pojo.Researcher;
import com.uom.idecide.service.ResearcherService;

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
@RequestMapping("/researcher")
public class ResearcherController {

	@Autowired
	private ResearcherService researcherService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * researcher user sign up
	 */
	@RequestMapping(method= RequestMethod.POST)
	public Result add(@RequestBody Researcher researcher){
		try {
			researcherService.add(researcher);
		} catch (Exception e) {
			return new Result(false, StatusCode.REPERROR,e.getMessage());
		}
		String token = jwtUtil.createJWT(researcher.getResearcherId(),researcher.getEmail(),"researcher");
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);
		map.put("roles","researcher");
		return new Result(true, StatusCode.OK,"sign up successful",map);
	}

	/**
	 * researcher user login
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(@RequestBody Researcher researcher){
		researcher =researcherService.login(researcher.getEmail(),researcher.getPassword());
		if(researcher == null){		//if research equals to null, its mean that the user dose not exist, or wrong password
			return new Result(false, StatusCode.LOGINERROR,"login fail");
		}

		String token = jwtUtil.createJWT(researcher.getResearcherId(),researcher.getEmail(),"researcher");
		System.out.println(token);
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);
		map.put("roles","researcher");
		return new Result(true, StatusCode.OK,"login successful",map);
	}

	/**
	 * query all existing researcher user in database
	 */
	@RequestMapping(value = "/researcherList", method = RequestMethod.GET)
	public Result researcherList(){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		return new Result(true, StatusCode.OK,"operation successful",researcherService.findAll());
	}

	/**
	 * find the details of a researcher user by researcher ID
	 * Require: admin user permission
	 */
	@RequestMapping(value="/{researcherId}",method= RequestMethod.GET)
	public Result findById(@PathVariable(value="researcherId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		return new Result(true, StatusCode.OK,"fetch successful",researcherService.findById(id));
	}

	/**
	 * update the details of a researcher user by admin ID
	 * Require: researcher user permission
	 */
	@RequestMapping(value="/{researcherId}",method= RequestMethod.PUT)
	public Result updateById(@RequestBody Researcher researcher){
		try{
			PrivilegeUtil.checkResearcher(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		researcherService.updateById(researcher);
		return new Result(true, StatusCode.OK,"update successful");
	}

	/**
	 * delete an researcher user by admin ID
	 * Require: admin user permission
	 */
	@RequestMapping(value="/{researcherId}",method= RequestMethod.DELETE)
	public Result deleteById(@PathVariable(value="researcherId") String id){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		researcherService.deleteById(id);
		return new Result(true, StatusCode.OK,"delete successful");
	}


/*	@RequestMapping(value = "/jwt", method = RequestMethod.POST)
	public Result testJwt(HttpServletRequest req){
		String token = (String) req.getAttribute("claims_researcher");
		if(token!=null){
			System.out.println(token);
		}else{
			System.out.println("没有token");
		}
		return new Result(true, StatusCode.OK,"test end");
	}

	@RequestMapping(value = "/researcherList/{page}/{size}", method = RequestMethod.GET)
	public Result researcherListWithPagination(@PathVariable(value="page") int page, @PathVariable(value="size") int size){
		try{
			PrivilegeUtil.checkAdmin(request);
		}catch (Exception e){
			return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
		}
		Page<Researcher> pages = researcherService.findAllWithPagination(page, size);
		//PageResult中第一个是返回记录条数，第二个是对应的researcherList
		return new Result(true, StatusCode.OK,"operation successful", new PageResult<Researcher>(page,pages.getTotalElements(),pages.getTotalPages(),pages.getContent()));
	}*/
	
}
