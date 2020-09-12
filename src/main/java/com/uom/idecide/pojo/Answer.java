package com.uom.idecide.pojo;

import java.io.Serializable;
import java.util.List;

public class Answer implements Serializable {

    private String userId;
    private String surveyId;
    private String sectionId;
    private String completedTime;
    private List<Question> questions;
    private String resultCalculation;

    public Answer() {}

    public Answer(String userId, String surveyId, String sectionId, String completedTime, List<Question> questions, String resultCalculation) {
        this.userId = userId;
        this.surveyId = surveyId;
        this.sectionId = sectionId;
        this.completedTime = completedTime;
        this.questions = questions;
        this.resultCalculation = resultCalculation;
    }

    public String getResultCalculation() {
        return resultCalculation;
    }

    public void setResultCalculation(String resultCalculation) {
        this.resultCalculation = resultCalculation;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
