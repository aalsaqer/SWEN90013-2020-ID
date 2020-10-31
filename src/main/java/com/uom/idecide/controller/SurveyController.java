package com.uom.idecide.controller;


import com.alibaba.fastjson.JSONObject;
import com.uom.idecide.entity.Result;
import com.uom.idecide.entity.StatusCode;
import com.uom.idecide.pojo.Survey;
import com.uom.idecide.service.SurveyService;
import com.uom.idecide.util.IdWorker;

import com.uom.idecide.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller layer of the survey, which provides the interface for the front-end.
 * Handling HTTP/HTTPS request.
 */
@RestController
@CrossOrigin
@RequestMapping("/survey")
@PropertySource(value = "classpath:application.properties")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private HttpServletRequest request;

    /**
     * find all the details of a survey according to survey ID
     */
    @RequestMapping(value="/{surveyId}",method= RequestMethod.GET)
    public Result findById(@PathVariable("surveyId") String id){
        return new Result(true, StatusCode.OK,"fetch successful",surveyService.findById(id));
    }

    /**
     * fetch all of the surveys without survey details
     */
    @RequestMapping(value = "/allSurveyId",method= RequestMethod.GET)
    public Result getAllSurveyId(){
        return new Result(true, StatusCode.OK,"fetching successful",surveyService.findAllByJsonStrNotNull());
    }

    /**
     * add new survey
     * Require: admin user permission
     */
/*
    @RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result add(@RequestBody JSONObject jsonParam) {
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }

        //String surveyId = String.valueOf(idWorker.nextId());
        String surveyId = (String) jsonParam.get("surveyId");
        if(surveyService.existsSurveyBySurveyId(surveyId)){
            //check whether the id exist
            return new Result(true, StatusCode.REPERROR,"survey id already exist, please use another id");
        }


        String surveyTitle = (String) jsonParam.get("surveyTitle");
        String surveyVersion = (String) jsonParam.get("surveyVersion");
        String surveyIntroduction = (String) jsonParam.get("surveyIntroduction");
        Integer surveyDisplayOrder = (Integer) jsonParam.get("surveyDisplayOrder");
        String surveyImageName = (String) jsonParam.get("surveyImageName");
        String surveyIntroductionHtmlB64 = (String) jsonParam.get("surveyIntroductionHtmlB64");
        Object surveySections = jsonParam.get("surveySections");

        //remove these redundant fields
        if(surveyId!=null) jsonParam.remove("surveyId");
        if(surveyTitle!=null)   jsonParam.remove("surveyTitle");
        if(surveyVersion!=null)   jsonParam.remove("surveyVersion");
        if(surveyIntroduction!=null)   jsonParam.remove("surveyIntroduction");
        if(surveyImageName!=null)   jsonParam.remove("surveyImageName");
        if(surveyIntroductionHtmlB64!=null) jsonParam.remove("surveyIntroductionHtmlB64");

        Survey survey;
        if(surveySections==null){
            //if surveySections not exist, no need to make a json str
            survey = new Survey(surveyId, surveyTitle,surveyVersion,surveyIntroduction,surveyDisplayOrder,surveyImageName,surveyIntroductionHtmlB64);
        }else{
            survey = new Survey(surveyId, surveyTitle,surveyVersion,surveyIntroduction,surveyDisplayOrder,surveyImageName,surveyIntroductionHtmlB64,jsonParam.toJSONString());
        }

       // return the inserted survey's surveyId to frontend
        return new Result(true, StatusCode.OK,"Insert successful", surveyService.save(survey));
    }
*/

    /**
     * update the details of a survey according to survey ID
     * Require: admin user permission
     */
/*
    @RequestMapping(method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public Result updateById(@RequestBody JSONObject jsonParam) {
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }

        String surveyId = (String) jsonParam.get("surveyId");
        String surveyTitle = (String) jsonParam.get("surveyTitle");
        String surveyVersion = (String) jsonParam.get("surveyVersion");
        String surveyIntroduction = (String) jsonParam.get("surveyIntroduction");
        Integer surveyDisplayOrder = (Integer) jsonParam.get("surveyDisplayOrder");
        String surveyImageName = (String) jsonParam.get("surveyImageName");
        String surveyIntroductionHtmlB64 = (String) jsonParam.get("surveyIntroductionHtmlB64");
        Object surveySections = jsonParam.get("surveySections");

        if(surveyId==null) return new Result(true, StatusCode.ERROR,"update fail, please contain the correct survey Id");

        //remove these redundant fields
        if(surveyId!=null) jsonParam.remove("surveyId");
        if(surveyTitle!=null)   jsonParam.remove("surveyTitle");
        if(surveyVersion!=null)   jsonParam.remove("surveyVersion");
        if(surveyIntroduction!=null)   jsonParam.remove("surveyIntroduction");
        if(surveyImageName!=null)   jsonParam.remove("surveyImageName");
        if(surveyIntroductionHtmlB64!=null) jsonParam.remove("surveyIntroductionHtmlB64");

        Survey survey;
        if(surveySections==null){
            //No sections, do not need to update section
            survey = new Survey(surveyId, surveyTitle,surveyVersion,surveyIntroduction,surveyDisplayOrder,surveyImageName,surveyIntroductionHtmlB64);
        }else{
            survey = new Survey(surveyId, surveyTitle,surveyVersion,surveyIntroduction,surveyDisplayOrder,surveyImageName,surveyIntroductionHtmlB64,jsonParam.toJSONString());
        }
        surveyService.update(survey);

 */
/*       if(request.getAttribute("token_ttl")!=null){
            return new Result(true, StatusCode.OK,"update successful",null,(Long) request.getAttribute("token_ttl"));
        }*//*


        return new Result(true, StatusCode.OK,"update successful");
    }
*/

    /**
     * update the details of a survey according to survey ID
     * Require: admin user permission
     */
    @RequestMapping(method = {RequestMethod.PUT,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public Result saveOrUpdateById(@RequestBody JSONObject jsonParam) {
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }

        String surveyId = (String) jsonParam.get("surveyId");
        String surveyTitle = (String) jsonParam.get("surveyTitle");
        String surveyVersion = (String) jsonParam.get("surveyVersion");
        String surveyIntroduction = (String) jsonParam.get("surveyIntroduction");
        Integer surveyDisplayOrder = (Integer) jsonParam.get("surveyDisplayOrder");
        String surveyImageName = (String) jsonParam.get("surveyImageName");
        String surveyIntroductionHtmlB64 = (String) jsonParam.get("surveyIntroductionHtmlB64");
        Object surveySections = jsonParam.get("surveySections");

        if(surveyId==null) return new Result(true, StatusCode.ERROR,"Please contain the correct survey Id");

        //remove these redundant fields
        if(surveyId!=null) jsonParam.remove("surveyId");
        if(surveyTitle!=null)   jsonParam.remove("surveyTitle");
        if(surveyVersion!=null)   jsonParam.remove("surveyVersion");
        if(surveyIntroduction!=null)   jsonParam.remove("surveyIntroduction");
        if(surveyImageName!=null)   jsonParam.remove("surveyImageName");
        if(surveyIntroductionHtmlB64!=null) jsonParam.remove("surveyIntroductionHtmlB64");

        Survey survey;
        if(surveySections==null){
            //No sections, do not need to update section
            survey = new Survey(surveyId, surveyTitle,surveyVersion,surveyIntroduction,surveyDisplayOrder,surveyImageName,surveyIntroductionHtmlB64);
        }else{
            survey = new Survey(surveyId, surveyTitle,surveyVersion,surveyIntroduction,surveyDisplayOrder,surveyImageName,surveyIntroductionHtmlB64,jsonParam.toJSONString());
        }

        if("POST".equals(request.getMethod())){
            return new Result(true, StatusCode.OK,"Insert successful", surveyService.save(survey));
        }else{
            surveyService.update(survey);
            return new Result(true, StatusCode.OK,"update successful");
        }
    }


    //the url for uploading image
    @Value("${system.config.imageSavePath}")
    private String imageSavePath;

    //the url for viewing the image
    @Value("${system.config.imageLoadPath}")
    private String imageLoadPath;

    /**
     * handling the image uploading function of survey
     * Require: admin user permission
     */
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public Result uploadImg(@RequestParam("img") MultipartFile file, HttpServletRequest request){

/*        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }*/

        System.out.println(imageSavePath);
        String surveyId = request.getParameter("surveyId");
        System.out.println("survey id is: " + surveyId);

        //get file name
        String fileName = file.getOriginalFilename();
        System.out.println("file name is: "+ fileName);

        //get suffix
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("suffix is: "+ suffixName);

        //path for image
        fileName = surveyId + suffixName;
        File dest = new File(imageSavePath + fileName);

        try {
            file.transferTo(dest);

            //update the image info into db
            Survey survey = new Survey();
            survey.setSurveyId(surveyId);
            survey.setSurveyImageName(fileName);
            surveyService.update(survey);

            return new Result(true, StatusCode.OK,"upload successful", imageLoadPath+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Result(true, StatusCode.OK,"upload failed",fileName);
    }

    //Delete Images from server
    @RequestMapping(value="/delImg",method= RequestMethod.DELETE)
    public Result delFile(@RequestBody JSONObject jsonParam) {
        String path = (String) jsonParam.get("path");

        String resultInfo = null;
        int lastIndexOf = path.lastIndexOf("/");
        String img_path = path.substring(lastIndexOf + 1, path.length());

        img_path = imageSavePath + img_path;
        File file = new File(img_path);
        if (file.exists()) {
            if (file.delete()) {
                resultInfo =  "delete successful";
            } else {
                resultInfo =  "delete fail";
            }
        } else {
            resultInfo = "file doesn't exist";
        }
        return new Result(true, StatusCode.OK,resultInfo);
    }

    /**
     * fetch all of the surveys without survey details
     */
    @RequestMapping(value = "/allImgId",method= RequestMethod.GET)
    public Result getAllImgId(){
        ArrayList<String> imgIdList = new ArrayList<>();

        File fileNew = new File(imageSavePath);
        //fetch all the images under dir
        File[] files = fileNew.listFiles();
        for (File file : files) {
            // fetch a image from dir
            imgIdList.add(file.getName());
        }

        return new Result(true, StatusCode.OK,"fetching successful",imgIdList);
    }

    /**
     * delete a survey according to survey ID
     * Require: admin user permission
     */
    @RequestMapping(value="/{surveyId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("surveyId") String id){
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }

        surveyService.deleteById(id);
        return new Result(true, StatusCode.OK,"delete successful");
    }

}

