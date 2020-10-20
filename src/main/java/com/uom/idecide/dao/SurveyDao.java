package com.uom.idecide.dao;

import com.uom.idecide.pojo.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SurveyDao extends MongoRepository<Survey,String> {

    /**
     * 返回所有的启用状态的module，并且不包含section信息
     */
    @Query(fields="{ 'surveyTitle': 1,'surveyId':1 , 'surveyIntroduction':1,'surveyDisplayOrder':1, 'surveyVersion':1, 'surveyImageName':1 , 'surveyIntroductionHtmlB64':1}")
    public List<Survey> findAllByJsonStrNotNull();

    public Boolean existsSurveyBySurveyId(String surveyId);
}
