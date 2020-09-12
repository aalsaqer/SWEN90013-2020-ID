package com.uom.idecide.dao;

import com.uom.idecide.pojo.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnswerDao extends MongoRepository<Answer,String> {

        public List<Answer> findAllByUserId(String userId);
        public List<Answer> findAllBySurveyId(String surveyId);
        public List<Answer> findAllByUserIdAndSurveyId(String userId, String surveyId);
        public List<Answer> findAllByUserIdAndSurveyIdAndSectionId(String userId, String surveyId, String sectionId);
}
