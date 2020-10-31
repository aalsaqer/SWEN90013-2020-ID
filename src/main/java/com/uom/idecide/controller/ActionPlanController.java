package com.uom.idecide.controller;

import com.alibaba.fastjson.JSONArray;
import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;
import com.uom.idecide.pojo.ActionPlan;
import com.uom.idecide.pojo.ActionStrategyRule;
import com.uom.idecide.service.ActionPlanService;
import com.uom.idecide.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller layer of the action plan, which provides the interface for the front-end.
 * Handling HTTP/HTTPS request.
 */
@RestController
@CrossOrigin
@RequestMapping("/actionplan")
public class ActionPlanController {

    @Autowired
    private ActionPlanService actionPlanService;

    @Autowired
    private HttpServletRequest request;

    /**
     * add new action plan
     * Require: admin user permission
     */
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

    /**
     * delete an existing action plan
     * Require: admin user permission
     */
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

    /**
     * update an existing action plan
     * Require: admin user permission
     */
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

    /**
     * find a strategy details by strategy name
     */
    @RequestMapping(value="/{strategyName}",method= RequestMethod.GET)
    public Result findByName(@PathVariable("strategyName") String strategyName){
        return new Result(true, StatusCode.OK,"fetch successful",actionPlanService.findByStrategyName(strategyName));
    }

    /**
     * find all strategies details
     */
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"fetch successful",actionPlanService.findAll());
    }

    /**
     * add a new strategy rule
     * Require: admin user permission
     */
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

    /**
     * get the action strategy rule
     */
    @RequestMapping(value="/actionStrategyRule", method = RequestMethod.GET)
    public Result getActionStrategyRule() {
        return new Result(true, StatusCode.OK,"insert successfully",actionPlanService.getActionStrategyRule());
    }

    /**
     * update the action strategy rule
     * Require: admin user permission
     */
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
