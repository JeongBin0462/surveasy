package com.surveasy.form.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;

@Mapper
public interface FormMapper {

	@Insert({
		"<script>",
        "INSERT INTO surveypaper (userno",
        "<if test='map.surveytitle != null'>, surveytitle</if>",
        "<if test='map.surveycontent != null'>, surveycontent</if>",
        "<if test='map.regidate != null'>, regidate</if>",
        "<if test='map.deadline != null'>, deadline</if>",
        "<if test='map.link != null'>, link</if>",
        ") VALUES (#{map.userno}",
        "<if test='map.surveytitle != null'>, #{map.surveytitle}</if>",
        "<if test='map.surveycontent != null'>, #{map.surveycontent}</if>",
        "<if test='map.regidate != null'>, #{map.regidate}</if>",
        "<if test='map.deadline != null'>, #{map.deadline}</if>",
        "<if test='map.link != null'>, #{map.link}</if>",
        ")",
        "</script>"
    })
	@Options(useGeneratedKeys = true, keyProperty = "map.surveyno", keyColumn = "surveyno")
	int insertSurveyPaperTemp(@Param("map") Map<String, Object> params);
	
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

	
	@Select({
		"<script>",
		"SELECT COUNT(*) AS cnt FROM surveypaper WHERE userno = #{userno}",
		" AND regidate IS NULL",
		"</script>"
	})
	int countTempSaveNum(@Param("userno") Integer userno);
	
	@Select("SELECT link FROM surveypaper WHERE surveyno = #{surveyno}")
	String getLink(@Param("surveyno") Integer surveyno);
}
