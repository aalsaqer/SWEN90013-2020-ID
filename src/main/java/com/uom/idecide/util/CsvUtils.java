package com.uom.idecide.util;

import com.uom.idecide.pojo.Answer;
import com.uom.idecide.pojo.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class CsvUtils {
    public static String exportData(String csvName, List<Answer> resultList){
        try {
            File file = new File("/usr/local/files/"+csvName+".csv");
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            ow.write("userId");
            ow.write(",");
            ow.write("surveyId");
            ow.write(",");
            ow.write("sectionId");
            ow.write(",");
            ow.write("completedTime");
            ow.write(",");
            ow.write("questionId");
            ow.write(",");
            ow.write("questionType");
            ow.write(",");
            ow.write("questionText");
            ow.write("\r\n");
            for(Answer result:resultList){
                if(result == null) continue;
                String userId = result.getUserId();
                if(userId != null){
                    ow.write(result.getUserId());
                }else{
                    ow.write("Anonymous User");
                }
                ow.write(",");
                ow.write(result.getSurveyId());
                ow.write(",");
                ow.write(result.getSectionId());
                ow.write(",");
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH/mm");
                ow.write(format.format(Long.parseLong(result.getCompletedTime())));
                ow.write(",");
                boolean flag = true;
                for (Question question:result.getQuestions()){
                    if(question == null) continue;
                    if(flag) flag = false;
                    else{
                        ow.write("");
                        ow.write(",");

                        ow.write("");
                        ow.write(",");

                        ow.write("");
                        ow.write(",");

                        ow.write("");
                        ow.write(",");
                    }
                    ow.write(question.getQuestionId());
                    ow.write(",");
                    ow.write(question.getQuestionType());
                    ow.write(",");
                    ow.write(question.getQuestionText());
                    ow.write(",");
                    ow.write("\r\n");
                }
            }
            ow.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "8.210.28.169/files/"+csvName+".csv";

    }
}
