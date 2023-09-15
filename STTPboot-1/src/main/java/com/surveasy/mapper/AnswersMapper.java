package com.surveasy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.survey.model.Answers;

@Mapper
public interface AnswersMapper {

	// answers 테이블을 채움(surveyno, questionno)
		@Insert({
			"<script>",
			"INSERT INTO answers",
			" (surveyno, questionno,",
			" answer1, answer2, answer3, answer4, answer5,",
			" answer6, answer7, answer8, answer9, answer10,",
			" answer11, answer12, answer13, answer14, answer15,",
			" answer16, answer17, answer18, answer19, answer20)",
			"VALUES (",
			"#{surveyno}, #{questionno}, ",
			"#{answer1}, #{answer2}, #{answer3}, #{answer4}, #{answer5}, ",
			"#{answer6}, #{answer7}, #{answer8}, #{answer9}, #{answer10}, ",
			"#{answer11}, #{answer12}, #{answer13}, #{answer14}, #{answer15}, ",
			"#{answer16}, #{answer17}, #{answer18}, #{answer19}, #{answer20}", 
			")",
		    "ON DUPLICATE KEY UPDATE",
		    "answer1 = VALUES(answer1), answer2 = VALUES(answer2), answer3 = VALUES(answer3),",
		    "answer4 = VALUES(answer4), answer5 = VALUES(answer5), answer6 = VALUES(answer6),",
		    "answer7 = VALUES(answer7), answer8 = VALUES(answer8), answer9 = VALUES(answer9),",
		    "answer10 = VALUES(answer10), answer11 = VALUES(answer11), answer12 = VALUES(answer12),",
		    "answer13 = VALUES(answer13), answer14 = VALUES(answer14), answer15 = VALUES(answer15),",
		    "answer16 = VALUES(answer16), answer17 = VALUES(answer17), answer18 = VALUES(answer18),",
		    "answer19 = VALUES(answer19), answer20 = VALUES(answer20)",
			"</script>"
		})
		int insertAnswers(Answers answers);
		
		// 3-3 설문지 생성을 위한 설문지 문항들
		@Select("SELECT * FROM answers WHERE questionno=#{questionno}")
		Answers getAnswer(@Param("questionno") int questionno);
		
		// 3-3 전체 문항 수
		@Select("SELECT count(*) FROM answers WHERE surveyno=#{surveyno}")
		Integer getCountAnswers(@Param("surveyno") int surveyno);
}
