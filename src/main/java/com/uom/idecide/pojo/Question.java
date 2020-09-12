package com.uom.idecide.pojo;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private String questionId;
    private String questionType;
    private String questionText;
    private List<String> questionAnswer;

    @Override
    public String toString() {
        return "Question{" +
                "questionId='" + questionId + '\'' +
                ", questionType='" + questionType + '\'' +
                ", questionText='" + questionText + '\'' +
                ", questionAnswer=" + questionAnswer +
                '}';
    }

    public List<String> getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(List<String> questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

}
