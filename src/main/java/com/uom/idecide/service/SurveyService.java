package com.uom.idecide.service;


import com.uom.idecide.dao.SurveyDao;
import com.uom.idecide.pojo.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyDao surveyDao;


    public String save(Survey survey) {
        Survey savedSurvey = surveyDao.save(survey);
        return savedSurvey.getSurveyId();
    }

    public List<Survey> findAllByJsonStrNotNull(){
        return surveyDao.findAllByJsonStrNotNull();
    }

    public Survey findById(String id) {
        return surveyDao.findById(id).get();
    }

    public boolean existsSurveyBySurveyId(String id){
        return surveyDao.existsSurveyBySurveyId(id);
    }

    public void deleteById(String id) {
        surveyDao.deleteById(id);
    }


    @Autowired
    private MongoTemplate mongoTemplate;

    public void update(Survey survey) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(survey.getSurveyId()));
        Update update = new Update();
        if(survey.getSurveyTitle()!=null)   update.set("surveyTitle",survey.getSurveyTitle() );
        if(survey.getSurveyVersion()!=null)   update.set("surveyVersion",survey.getSurveyVersion() );
        if(survey.getSurveyIntroduction()!=null)   update.set("surveyIntroduction",survey.getSurveyIntroduction() );
        if(survey.getSurveyDisplayOrder()!=null)   update.set("surveyDisplayOrder",survey.getSurveyDisplayOrder() );

        if(survey.getSurveyImageName()!=null)   update.set("surveyImageName",survey.getSurveyImageName() );
        if(survey.getSurveyIntroductionHtmlB64()!=null)   update.set("surveyIntroductionHtmlB64",survey.getSurveyIntroductionHtmlB64());
        if(survey.getJsonStr()!=null)   update.set("jsonStr",survey.getJsonStr() );
        //only update the fields are not null
        mongoTemplate.findAndModify(query,update,Survey.class);
    }
/*
    public List<ActionPlan> getAllActionsBySurveyId(String id) {
        return actionPlanDao.findAllBySurveyId(id);
    }

    public ActionPlan getBySurveyIdAndActionSeverity(String surveyId, int actionSeverity){
        return actionPlanDao.findBySurveyIdAndActionSeverity(surveyId,actionSeverity);
    }

    public void saveActionPlan(ActionPlan actionPlan){
        actionPlanDao.save(actionPlan);
    }*/
}
