package com.surveasy.mapper.form;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.model.survey.SurveyOption;
import com.surveasy.model.survey.SurveyRequire;

@Mapper
public interface FormMapper {

	// 임시저장(regidate, deadline 제외) surveypaper 테이블
	@Insert({
	    "<script>",
	    "INSERT INTO surveypaper (userno",
	    "<if test='surveytitle != null'>, surveytitle</if>",
	    "<if test='surveycontent != null'>, surveycontent</if>",
	    "<if test='link != null'>, link</if>",
	    ") VALUES (#{userno}",
	    "<if test='surveytitle != null'>, #{surveytitle}</if>",
	    "<if test='surveycontent != null'>, #{surveycontent}</if>",
	    "<if test='link != null'>, #{link}</if>",
	    ")",
	    "ON DUPLICATE KEY UPDATE",
	    "<if test='surveytitle != null'> surveytitle = VALUES(surveytitle),</if>",
	    "<if test='surveycontent != null'> surveycontent = VALUES(surveycontent),</if>",
	    "<if test='link != null'> link = VALUES(link)</if>",
	    "</script>"
	})
	int insertSurveyPaperTemp(@Param("userno") Integer userno, @Param("surveytitle") String surveytitle,
	                          @Param("surveycontent") String surveycontent, @Param("link") String link);

	// 제출(regidate, deadline 포함) surveypaper 테이블
	@Insert({
	    "<script>",
	    "INSERT INTO surveypaper (userno",
	    "<if test='surveytitle != null'>, surveytitle</if>",
	    "<if test='surveycontent != null'>, surveycontent</if>",
	    "<if test='regidate != null'>, regidate</if>",
	    "<if test='deadline != null'>, deadline</if>",
	    "<if test='link != null'>, link</if>",
	    ") VALUES (#{userno}",
	    "<if test='surveytitle != null'>, #{surveytitle}</if>",
	    "<if test='surveycontent != null'>, #{surveycontent}</if>",
	    "<if test='regidate != null'>, #{regidate}</if>",
	    "<if test='deadline != null'>, #{deadline}</if>",
	    "<if test='link != null'>, #{link}</if>",
	    ")",
	    "ON DUPLICATE KEY UPDATE",
	    "<if test='surveytitle != null'> surveytitle = VALUES(surveytitle),</if>",
	    "<if test='surveycontent != null'> surveycontent = VALUES(surveycontent),</if>",
	    "<if test='regidate != null'> regidate = VALUES(regidate),</if>",
	    "<if test='deadline != null'> deadline = VALUES(deadline),</if>",
	    "<if test='link != null'> link = VALUES(link)</if>",
	    "</script>"
	})
	int insertSurveyPaperSubmit(@Param("userno") Integer userno, @Param("surveytitle") String surveytitle,
	                            @Param("surveycontent") String surveycontent, @Param("regidate") LocalDateTime regidate,
	                            @Param("deadline") LocalDateTime deadline, @Param("link") String link);
	
	// 저장한 surveypaper의 surveyno를 가져옴
	@Select("SELECT * FROM surveypaper WHERE userno = #{userno}")
	int getSurveyPaper(@Param("userno") Integer userno);
	
	// surveyrequire 테이블에 넣음
	@Insert({
		    "<script>",
		    "INSERT INTO surveyrequire (surveyno, period",
		    "<if test='subject != null'>, subject</if>",
		    ", email_option, phone_option, age_option, gender_option",
		    "<if test='target != null'>, target</if>",
		    ", department_option, position_option, grade_option, college_option, region_option, finaledu_option, incomelevel_option",
		    ") VALUES (#{surveyno}, #{period}",
		    "<if test='subject != null'>, #{subject}</if>",
		    ", #{email_option}, #{phone_option}, #{age_option}, #{gender_option}",
		    "<if test='target != null'>, #{target}</if>",
		    ", #{department_option}, #{position_option}, #{grade_option}, #{college_option}, #{region_option}, #{finaledu_option}, #{incomelevel_option}",
		    ")",
		    "ON DUPLICATE KEY UPDATE",
		    "period = VALUES(period),",
		    "<if test='subject != null'> subject = VALUES(subject),</if>",
		    "email_option = VALUES(email_option),",
		    "phone_option = VALUES(phone_option),",
		    "age_option = VALUES(age_option),",
		    "gender_option = VALUES(gender_option),",
		    "<if test='target != null'> target = VALUES(target),</if>",
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
	    "<if test='is_public_survey != null'>, is_public_survey</if>",
	    "<if test='require_login != null'>, require_login</if>",
	    "<if test='is_public_result != null'>, is_public_result</if>",
	    "<if test='show_progress != null'>, show_progress</if>",
	    ") VALUES (",
	    "#{surveyno}",
	    "<if test='is_public_survey != null'>, #{is_public_survey}</if>",
	    "<if test='require_login != null'>, #{require_login}</if>",
	    "<if test='is_public_result != null'>, #{is_public_result}</if>",
	    "<if test='show_progress != null'>, #{show_progress}</if>",
	    ")",
	    "ON DUPLICATE KEY UPDATE",
	    "<if test='is_public_survey != null'> is_public_survey = VALUES(is_public_survey),</if>",
	    "<if test='require_login != null'> require_login = VALUES(require_login),</if>",
	    "<if test='is_public_result != null'> is_public_result = VALUES(is_public_result),</if>",
	    "<if test='show_progress != null'> show_progress = VALUES(show_progress)</if>",
	    "</script>"
	})
	int insertSurveyOption(SurveyOption surveyOption);

	// question 테이블을 채움

	// answers 테이블을 채움(surveyno, questionno)

}
