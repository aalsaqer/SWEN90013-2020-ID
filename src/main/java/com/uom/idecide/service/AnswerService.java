package com.uom.idecide.service;


import com.uom.idecide.dao.AnswerDao;
import com.uom.idecide.pojo.Answer;

import com.uom.idecide.pojo.Survey;
import com.uom.idecide.util.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerDao AnswerDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void add(Answer answer) {
        AnswerDao.save(answer);
    }

    public void update(Answer answer) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(answer.getAnswerId()));
        Update update = new Update();
        if(answer.getCompletedTime()!=null)   update.set("completedTime",answer.getCompletedTime());
        if(answer.getAttemptCompleted()!=null)   update.set("attemptCompleted",answer.getAttemptCompleted());
        if(answer.getQuestions()!=null)   update.set("questions",answer.getQuestions());
        //only update the fields are not null
        mongoTemplate.findAndModify(query,update,Answer.class);
    }

    public List<Answer> getUnfinishedResultByUserId(String id) {
        return AnswerDao.findAllByUserIdAndAttemptCompletedIsFalse(id);
    }

    public List<Answer> getAllResultByUserId(String id) {
        return AnswerDao.findAllByUserId(id);
    }

    public List<Answer> getAllResult() {
        return AnswerDao.findAll();
    }

    public List<Answer> getAllResultBySurveyId(String surveyId){
        return AnswerDao.findAllBySurveyId(surveyId);
    }

    public List<Answer> getAllResultByUserIdAndSurveyId(String userId, String surveyId){
        return AnswerDao.findAllByUserIdAndSurveyId(userId,surveyId);
    }

    public List<Answer> getAllResultByUserIdAndSurveyIdAndSectionId(String userId, String surveyId, String sectionId){
        return AnswerDao.findAllByUserIdAndSurveyIdAndSectionId(userId,surveyId,sectionId);
    }

    public String downloadAllResult() {
        List<Answer> resultList = getAllResult();
        return CsvUtils.exportData("totalResult",resultList);
    }

    public String downloadResultByUserId(String userId) {
        List<Answer> resultList = getAllResultByUserId(userId);
        return CsvUtils.exportData("uid_"+userId,resultList);
    }

    public String downloadResultBySurveyId(String surveyId) {
        List<Answer> resultList = getAllResultBySurveyId(surveyId);
        return CsvUtils.exportData("sid_"+surveyId,resultList);
    }
    public String downloadResultByUserIdAndSurveyId(String userId,String surveyId) {
        List<Answer> resultList = getAllResultByUserIdAndSurveyId(userId,surveyId);
        return CsvUtils.exportData("uid_"+userId+"_sid_"+surveyId,resultList);
    }

}
