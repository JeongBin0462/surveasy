package com.surveasy.form.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;

@Mapper
public interface FormMapper {

//	 임시저장(regidate, deadline 제외) surveypaper 테이블
//	@Insert({
//	    "<script>",
//	    "INSERT INTO surveypaper (userno, surveytitle, surveycontent, link)",
//	    " VALUES (#{userno}, #{surveytitle}, #{surveycontent}, #{link})",
//	    " ON DUPLICATE KEY UPDATE",
//	    " surveytitle = VALUES(surveytitle),",
//	    " surveycontent = VALUES(surveycontent),",
//	    " link = VALUES(link)",
//	    "</script>"
//	})
//	int insertSurveyPaperTemp(@Param("userno") Integer userno, @Param("surveytitle") String surveytitle,
//	                          @Param("surveycontent") String surveycontent, @Param("link") String link);

//	 임시저장(regidate, deadline 제외) surveypaper 테이블
	@Insert({
	    "<script>",
	    "INSERT INTO surveypaper (userno, surveytitle, surveycontent, link)",
	    " VALUES (#{userno}, #{surveytitle}, #{surveycontent}, #{link})",
	    "</script>"
	})
	int insertSurveyPaperTemp(@Param("userno") Integer userno, @Param("surveytitle") String surveytitle,
	                          @Param("surveycontent") String surveycontent, @Param("link") String link);

	// 제출(regidate, deadline 포함) surveypaper 테이블
	@Insert({
	    "<script>",
	    "INSERT INTO surveypaper (userno, surveytitle, surveycontent, regidate, deadline, link)",
	    " VALUES (#{userno}",
	    ", #{surveytitle}",
	    ", #{surveycontent}",
	    ", #{regidate, jdbcType=TIMESTAMP}",
	    ", #{deadline}",
	    ", #{link}",
	    ")",
	    "ON DUPLICATE KEY UPDATE",
	    " surveytitle = VALUES(surveytitle),",
	    " surveycontent = VALUES(surveycontent),",
	    " regidate = VALUES(regidate),",
	    " deadline = VALUES(deadline),",
	    " link = VALUES(link)",
	    "</script>"
	})
	int insertSurveyPaperSubmit(@Param("userno") Integer userno, @Param("surveytitle") String surveytitle,
	                            @Param("surveycontent") String surveycontent, @Param("regidate") LocalDateTime regidate,
	                            @Param("deadline") LocalDateTime deadline, @Param("link") String link);
	
	// 저장한 surveypaper의 surveyno를 가져옴
	@Select("SELECT surveyno FROM surveypaper WHERE userno = #{userno}")
	Integer getSurveyPaper(@Param("userno") Integer userno);
	
	// surveyrequire 테이블에 넣음
	@Insert({
		    "<script>",
		    "INSERT INTO surveyrequire (surveyno, period",
		    ", subject",
		    ", email_option, phone_option, age_option, gender_option",
		    ", target",
		    ", department_option, position_option, grade_option, college_option, region_option, finaledu_option, incomelevel_option",
		    ") VALUES (#{surveyno}, #{period}",
		    ", #{subject}",
		    ", #{email_option}, #{phone_option}, #{age_option}, #{gender_option}",
		    ", #{target}",
		    ", #{department_option}, #{position_option}, #{grade_option}, #{college_option}, #{region_option}, #{finaledu_option}, #{incomelevel_option}",
		    ")",
		    "ON DUPLICATE KEY UPDATE",
		    "period = VALUES(period),",
		    "subject = VALUES(subject),",
		    "email_option = VALUES(email_option),",
		    "phone_option = VALUES(phone_option),",
		    "age_option = VALUES(age_option),",
		    "gender_option = VALUES(gender_option),",
		    "target = VALUES(target),",
		    "department_option = VALUES(department_option),",
		    "position_option = VALUES(position_option),",
		    "grade_option = VALUES(grade_option),",
		    "college_option = VALUES(college_option),",
		    "region_option = VALUES(region_option),",
		    "finaledu_option = VALUES(finaledu_option),",
		    "incomelevel_option = VALUES(incomelevel_option)",
		    "</script>"
	})
	int insertSurveyRequire(SurveyRequire surveyRequire);

	// survey_option 테이블 입력
	@Insert({
	    "<script>",
	    "INSERT INTO survey_option (surveyno",
	    ", is_public_survey",
	    ", require_login",
	    ", is_public_result",
	    ", show_progress",
	    ") VALUES (",
	    "#{surveyno}",
	    ", #{is_public_survey}",
	    ", #{require_login}",
	    ", #{is_public_result}",
	    ", #{show_progress}",
	    ")",
	    "ON DUPLICATE KEY UPDATE",
	    " is_public_survey = VALUES(is_public_survey),",
	    " require_login = VALUES(require_login),",
	    " is_public_result = VALUES(is_public_result),",
	    " show_progress = VALUES(show_progress)",
	    "</script>"
	})
	int insertSurveyOption(SurveyOption surveyOption);

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

}
