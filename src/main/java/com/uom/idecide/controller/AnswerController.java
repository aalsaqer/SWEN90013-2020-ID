package com.uom.idecide.controller;

import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;
import com.uom.idecide.pojo.Answer;

import com.uom.idecide.service.AnswerService;

import com.uom.idecide.util.IdWorker;
import com.uom.idecide.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller layer for survey result
 */
@RestController
@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.POST, RequestMethod.GET}, origins="*")
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IdWorker idWorker;

    /**
     * add new survey result to database & return the corresponding Action Plan
     * Require: user permission (anonymous user is also a user)
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Answer answer) {
        String userId;
        try{
            userId = PrivilegeUtil.checkUser(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        String answerId = String.valueOf(idWorker.nextId());
        answer.setAnswerId(answerId);
        answer.setUserId(userId);   //find the user id from JWT
        answerService.add(answer);
        return new Result(true, StatusCode.OK, "insert successful","ActionPlan XXXX");
    }

    @RequestMapping(value = "/getUnfinishedSurveyByUserIdInJwt",method = RequestMethod.GET)
    public Result findUnfinishedSurvey() {
        String userId;
        try{
            userId = PrivilegeUtil.checkUser(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        return new Result(true, StatusCode.OK,"fetch successful",answerService.getUnfinishedResultByUserId(userId));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody Answer answer){
        String userId;
        try{
            userId = PrivilegeUtil.checkUser(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        answer.setUserId(userId);   //find the user id from JWT
        answerService.update(answer);
        return new Result(true, StatusCode.OK, "update successful","ActionPlan XXXX");
    }

    /**
     * update the details of a survey according to survey ID
     * Require: user permission or admin permission or researcher user permission
     */
    @RequestMapping(value = "/getResult/{userId}",method= RequestMethod.GET)
    public Result getAllResultByUserId(@PathVariable("userId") String userId) {
        try{
            PrivilegeUtil.checkUserOrResearcherOrAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        return new Result(true, StatusCode.OK,"fetch successful",answerService.getAllResultByUserId(userId));
    }

    /**
     * get all survey details
     * Require: admin permission or researcher permission
     */
    @RequestMapping(value = "/getResult",method= RequestMethod.GET)
    public Result getAllResult() {
        try{
            PrivilegeUtil.checkResearcherOrAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        return new Result(true, StatusCode.OK,"fetch successful",answerService.getAllResult());
    }

    /**
     * get the specific survey details done by specific user
     * Require: admin permission or researcher permission
     */
    @RequestMapping(value = "/getResult/{userId}/{surveyId}", method= RequestMethod.GET)
    public Result getAllResultByUserIdAndSurveyId(@PathVariable("userId") String userId, @PathVariable("surveyId") String surveyId) {
        try{
            PrivilegeUtil.checkResearcherOrAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        return new Result(true, StatusCode.OK,"fetch successful",answerService.getAllResultByUserIdAndSurveyId(userId,surveyId));
    }

    /**
     * download all result into a .csv
     * Require: admin permission or researcher permission
     */
    @RequestMapping(value = "/downloadResult",method= RequestMethod.GET)
    public Result downloadAllResult() {
        try{
            PrivilegeUtil.checkResearcherOrAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        return new Result(true, StatusCode.OK,"fetch successful",answerService.downloadAllResult());
    }

    /**
     * download the result done by a specific user
     * Require: admin permission or researcher permission
     */
    @RequestMapping(value = "/downloadResult/byUserId/{userId}",method= RequestMethod.GET)
    public Result downloadAllResultByUserId(@PathVariable("userId") String userId) {
        try{
            PrivilegeUtil.checkResearcherOrAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        return new Result(true, StatusCode.OK,"fetch successful",answerService.downloadResultByUserId(userId));
    }

    /**
     * download the specific survey's result
     */
    @RequestMapping(value = "/downloadResult/bySurveyId/{surveyId}",method= RequestMethod.GET)
    public Result downloadAllResultBySurveyId(@PathVariable("surveyId") String surveyId) {
        try{
            PrivilegeUtil.checkResearcherOrAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        return new Result(true, StatusCode.OK,"fetch successful",answerService.downloadResultBySurveyId(surveyId));
    }

    /**
     * download the specific survey's result done by a specific user
     * Require: admin permission or researcher permission
     */
    @RequestMapping(value = "/downloadResult/{userId}/{surveyId}",method= RequestMethod.GET)
    public Result downloadAllResultByUserIdAndSurveyId(@PathVariable("userId") String userId, @PathVariable("surveyId") String surveyId) {
        try{
            PrivilegeUtil.checkResearcherOrAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        return new Result(true, StatusCode.OK,"fetch successful",answerService.downloadResultByUserIdAndSurveyId(userId,surveyId));
    }

    /*    @RequestMapping(value = "/getResult/{userId}/{surveyId}/{sectionId}",method= RequestMethod.GET)
    public Result getAllResultByUserIdAndSurveyIdAndSectionId(@PathVariable("userId") String userId, @PathVariable("surveyId") String surveyId, @PathVariable("sectionId") String sectionId) {
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }
        return new Result(true, StatusCode.OK,"fetch successful",answerService.getAllResultByUserIdAndSurveyIdAndSectionId(userId,surveyId,sectionId));
    }*/

}

