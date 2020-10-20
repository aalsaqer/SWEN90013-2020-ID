package com.uom.idecide.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Survey implements Serializable {
    @Id
    private String surveyId;
    private String surveyTitle;
    private String surveyVersion;
    private String surveyIntroduction;
    private Integer surveyDisplayOrder;
    private String surveyImageName;
    private String surveyIntroductionHtmlB64;

    //all of the JSON string in request will be stored in jsonStr
    private String jsonStr;


    public Survey() {
    }

    public Survey(String surveyId, String surveyTitle, String surveyVersion, String surveyIntroduction, Integer surveyDisplayOrder, String surveyImageName, String surveyIntroductionHtmlB64) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.surveyVersion = surveyVersion;
        this.surveyIntroduction = surveyIntroduction;
        this.surveyDisplayOrder = surveyDisplayOrder;
        this.surveyImageName = surveyImageName;
        this.surveyIntroductionHtmlB64 = surveyIntroductionHtmlB64;
    }

    public Survey(String surveyId, String surveyTitle, String surveyVersion, String surveyIntroduction, Integer surveyDisplayOrder, String surveyImageName, String surveyIntroductionHtmlB64, String jsonStr) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.surveyVersion = surveyVersion;
        this.surveyIntroduction = surveyIntroduction;
        this.surveyDisplayOrder = surveyDisplayOrder;
        this.surveyImageName = surveyImageName;
        this.surveyIntroductionHtmlB64 = surveyIntroductionHtmlB64;
        this.jsonStr = jsonStr;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "surveyId='" + surveyId + '\'' +
                ", surveyTitle='" + surveyTitle + '\'' +
                ", surveyVersion='" + surveyVersion + '\'' +
                ", surveyIntroduction='" + surveyIntroduction + '\'' +
                ", surveyDisplayOrder=" + surveyDisplayOrder +
                ", surveyImageName='" + surveyImageName + '\'' +
                ", surveyIntroductionHtmlB64='" + surveyIntroductionHtmlB64 + '\'' +
                ", jsonStr='" + jsonStr + '\'' +
                '}';
    }

    public Integer getSurveyDisplayOrder() {
        return surveyDisplayOrder;
    }

    public void setSurveyDisplayOrder(Integer surveyDisplayOrder) {
        this.surveyDisplayOrder = surveyDisplayOrder;
    }

    public String getSurveyIntroductionHtmlB64() {
        return surveyIntroductionHtmlB64;
    }

    public void setSurveyIntroductionHtmlB64(String surveyIntroductionHtmlB64) {
        this.surveyIntroductionHtmlB64 = surveyIntroductionHtmlB64;
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
