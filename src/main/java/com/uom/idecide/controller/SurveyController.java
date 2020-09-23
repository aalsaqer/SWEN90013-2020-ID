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

    @RequestMapping(value = "/allSurveyId",method= RequestMethod.GET)
    public Result getAllSurveyId(){
        return new Result(true, StatusCode.OK,"fetching successfully",surveyService.findAllByJsonStrNotNull());
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result add(@RequestBody JSONObject jsonParam) {
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }

        String surveyId = String.valueOf(idWorker.nextId());
        String surveyTitle = (String) jsonParam.get("surveyTitle");
        String surveyVersion = (String) jsonParam.get("surveyVersion");
        String surveyIntroduction = (String) jsonParam.get("surveyIntroduction");
        String surveyImageName = (String) jsonParam.get("surveyImageName");
        Object surveySections = jsonParam.get("surveySections");

        //remove these redundant fields
        if(surveyTitle!=null)   jsonParam.remove("surveyTitle");
        if(surveyVersion!=null)   jsonParam.remove("surveyVersion");
        if(surveyIntroduction!=null)   jsonParam.remove("surveyIntroduction");
        if(surveyImageName!=null)   jsonParam.remove("surveyImageName");

        Survey survey;
        if(surveySections==null){
            //if surveySections not exist, no need to make a json str
            survey = new Survey(surveyId, surveyTitle,surveyVersion,surveyIntroduction,surveyImageName);
        }else{
            survey = new Survey(surveyId, surveyTitle,surveyVersion,surveyIntroduction,surveyImageName,jsonParam.toJSONString());
        }

       // return the inserted survey's surveyId to frontend
        return new Result(true, StatusCode.OK,"Inserting successfully", surveyService.save(survey));
    }

    //the url for uploading image
    @Value("${system.config.imageSavePath}")
    private String imageSavePath;

    //the url for viewing the image
    @Value("${system.config.imageLoadPath}")
    private String imageLoadPath;

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public Result uploadImg(@RequestParam("img") MultipartFile file, HttpServletRequest request){

        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }

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


    @RequestMapping(value="/{surveyId}",method= RequestMethod.GET)
    public Result findById(@PathVariable("surveyId") String id){
        return new Result(true, StatusCode.OK,"fetching successfully",surveyService.findById(id));
    }


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
        String surveyImageName = (String) jsonParam.get("surveyImageName");
        Object surveySections = jsonParam.get("surveySections");

        if(surveyId==null) return new Result(true, StatusCode.ERROR,"update fail, please contain the correct survey Id");

        //remove these redundant fields
        jsonParam.remove("surveyId");
        if(surveyTitle!=null)   jsonParam.remove("surveyTitle");
        if(surveyVersion!=null)   jsonParam.remove("surveyVersion");
        if(surveyIntroduction!=null)   jsonParam.remove("surveyIntroduction");
        if(surveyImageName!=null)   jsonParam.remove("surveyImageName");

        Survey survey;
        if(surveySections==null){
            //No sections, do not need to update section
            survey = new Survey(surveyId, surveyTitle,surveyVersion,surveyIntroduction,surveyImageName);
        }else{
            survey = new Survey(surveyId, surveyTitle,surveyVersion,surveyIntroduction,surveyImageName,jsonParam.toJSONString());
        }
        surveyService.update(survey);

        return new Result(true, StatusCode.OK,"updating successfully");
    }

    @RequestMapping(value="/{surveyId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("surveyId") String id){
        try{
            PrivilegeUtil.checkAdmin(request);
        }catch (Exception e){
            return new Result(false, StatusCode.ACCESSERROR,e.getMessage());
        }

        surveyService.deleteById(id);
        return new Result(true, StatusCode.OK,"delete the survey successfully");
    }

}

