package com.surveasy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.survey.model.SurveyQuestion;

@Mapper
public interface QuestionMapper {
	
	// question 테이블을 채움
		@Insert({
			"<script>",
			"INSERT INTO question (surveyno, answer_min, answer_max",
		    ", question_contents",
		    ", answer_types",
		    ", mandatory",
		    ") VALUES (",
		    "#{surveyno}, #{answer_min}, #{answer_max}", 
		    ", #{question_contents}",
		    ", #{answer_types}",
		    ", #{mandatory}",
		    ")",
		    "ON DUPLICATE KEY UPDATE",
		    " answer_min = VALUES(answer_min),",
		    " answer_max = VALUES(answer_max),",
		    " question_contents = VALUES(question_contents),",
		    " answer_types = VALUES(answer_types),",
		    " mandatory = VALUES(mandatory)",
			"</script>"
		})
		int insertSurveyQuestion(SurveyQuestion surveyQuestion);
		
		// questionno 가져옴
		@Select("SELECT questionno FROM question WHERE surveyno = #{surveyno}")
		List<Integer> selectQuestionNo(int surveyno);
		
		// 3-3 설문지 생성을 위한 설문지 문항정보 리스트
		@Select("SELECT * FROM question WHERE surveyno=#{surveyno}")
		List<SurveyQuestion> getQuestion(@Param("surveyno") int surveyno);
}
