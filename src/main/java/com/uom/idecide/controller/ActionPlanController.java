package com.uom.idecide.controller;


import com.alibaba.fastjson.JSONArray;
import com.uom.idecide.dao.ActionStrategyRuleDao;
import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;
import com.uom.idecide.pojo.ActionPlan;
import com.uom.idecide.pojo.ActionStrategyRule;
import com.uom.idecide.service.ActionPlanService;
import com.uom.idecide.util.JwtUtil;
import com.uom.idecide.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/actionplan")
public class ActionPlanController {

    @Autowired
    private ActionPlanService actionPlanService;

    @Autowired
    private HttpServletRequest request;


    @RequestMapping(method= RequestMethod.POST)
    public Result add(@RequestBody ActionPlan actionPlan){
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        actionPlanService.add(actionPlan);
        return new Result(true, StatusCode.OK,"insert successfully");
    }

    @RequestMapping(value="/{strategyName}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable("strategyName") String strategyName){
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        actionPlanService.delete(strategyName);
        return new Result(true, StatusCode.OK,"delete successfully");
    }

    @RequestMapping(method= RequestMethod.PUT)
    public Result update(@RequestBody ActionPlan actionPlan){
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        actionPlanService.update(actionPlan);
        return new Result(true, StatusCode.OK,"update successfully");
    }

    @RequestMapping(value="/{strategyName}",method= RequestMethod.GET)
    public Result findByName(@PathVariable("strategyName") String strategyName){
        return new Result(true, StatusCode.OK,"fetch successfully",actionPlanService.findByStrategyName(strategyName));
    }

    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"fetch successfully",actionPlanService.findAll());
    }


    @RequestMapping(value="/actionStrategyRule", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result addActionStrategyRule(@RequestBody JSONArray jsonArray) {
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        actionPlanService.saveActionStrategyRule(new ActionStrategyRule(jsonArray.toJSONString()));
        return new Result(true, StatusCode.OK,"insert successfully");
    }


    @RequestMapping(value="/actionStrategyRule", method = RequestMethod.GET)
    public Result getActionStrategyRule() {
        return new Result(true, StatusCode.OK,"insert successfully",actionPlanService.getActionStrategyRule());
    }

    @RequestMapping(value="/actionStrategyRule", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public Result updateActionStrategyRule(@RequestBody JSONArray jsonArray) {
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        actionPlanService.saveActionStrategyRule(new ActionStrategyRule(jsonArray.toJSONString()));
        return new Result(true, StatusCode.OK,"update successfully");
    }

}
