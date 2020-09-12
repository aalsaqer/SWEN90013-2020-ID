package com.uom.idecide.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Survey implements Serializable {
    @Id
    private String surveyId;
    private String surveyTitle;
    private String surveyVersion;
    private String surveyIntroduction;
    private String surveyImageName;

    //all of the JSON string in request will be stored in jsonStr
    private String jsonStr;


    public Survey() {
    }

    public Survey(String surveyId, String surveyTitle, String surveyVersion, String surveyIntroduction, String surveyImageName, String jsonStr) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.surveyVersion = surveyVersion;
        this.surveyIntroduction = surveyIntroduction;
        this.surveyImageName = surveyImageName;
        this.jsonStr = jsonStr;
    }

    public Survey(String surveyId, String surveyTitle, String surveyVersion, String surveyIntroduction, String surveyImageName) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.surveyVersion = surveyVersion;
        this.surveyIntroduction = surveyIntroduction;
        this.surveyImageName = surveyImageName;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "surveyId='" + surveyId + '\'' +
                ", surveyTitle='" + surveyTitle + '\'' +
                ", surveyVersion='" + surveyVersion + '\'' +
                ", surveyIntroduction='" + surveyIntroduction + '\'' +
                ", surveyImageName='" + surveyImageName + '\'' +
                ", jsonStr='" + jsonStr + '\'' +
                '}';
    }

    public String getSurveyImageName() {
        return surveyImageName;
    }

    public void setSurveyImageName(String surveyImageName) {
        this.surveyImageName = surveyImageName;
    }

    public String getSurveyVersion() {
        return surveyVersion;
    }

    public void setSurveyVersion(String surveyVersion) {
        this.surveyVersion = surveyVersion;
    }

    public String getSurveyIntroduction() {
        return surveyIntroduction;
    }

    public void setSurveyIntroduction(String surveyIntroduction) {
        this.surveyIntroduction = surveyIntroduction;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

}
