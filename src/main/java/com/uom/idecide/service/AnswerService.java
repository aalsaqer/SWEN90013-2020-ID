package com.uom.idecide.service;


import com.uom.idecide.dao.AnswerDao;
import com.uom.idecide.pojo.Answer;

import com.uom.idecide.util.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerDao AnswerDao;

    public void add(Answer answer) {
        AnswerDao.save(answer);
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
