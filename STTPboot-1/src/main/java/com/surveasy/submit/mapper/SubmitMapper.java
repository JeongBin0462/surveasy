package com.surveasy.submit.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.surveasy.submit.model.InputInfoTable;
import com.surveasy.submit.model.UserAnswers;
import com.surveasy.submit.model.UserSurvey;

@Mapper
public interface SubmitMapper {
	
	// user_survey 테이블
	@Insert({ "<script>"
			, "INSERT INTO user_survey (userno, surveyno) VALUES (#{userno}, #{surveyno})"
			, "</script>" })
	@Options(useGeneratedKeys = true, keyProperty = "user_survey_no")
	int insertUserSurvey(UserSurvey userSurvey);
	
	
	// input_info 테이블
	@Insert({
	    "<script>",
	    "INSERT INTO input_info (user_survey_no",
	    
	    "<if test='email != null'>", ", email_input", "</if>",
	    "<if test='phonenumber != null'>", ", phone_input", "</if>",
	    "<if test='birth != null'>", ", birth_input", "</if>",
	    "<if test='gender != null'>", ", gender_input", "</if>",
	    "<if test='department != null'>", ", department_input", "</if>",
	    "<if test='position != null'>", ", position_input", "</if>",
	    "<if test='grade != null'>", ", grade_input", "</if>",
	    "<if test='college != null'>", ", college_input", "</if>",
	    "<if test='region != null'>", ", region_input", "</if>",
	    "<if test='finaledu != null'>", ", finaledu_input", "</if>",
	    "<if test='incomelevel != null'>", ", incomelevel_input", "</if>",
	    
	    ") VALUES (#{user_survey_no}",
	    
	    "<if test='email != null'>", ", #{email}", "</if>",
	    "<if test='phonenumber != null'>", ", #{phonenumber}", "</if>",
	    "<if test='birth != null'>", ", #{birth}", "</if>",
	    "<if test='gender != null'>", ", #{gender}", "</if>",
	    "<if test='department != null'>", ", #{department}", "</if>",
	    "<if test='position != null'>", ", #{position}", "</if>",
	    "<if test='grade != null'>", ", #{grade}", "</if>",
	    "<if test='college != null'>", ", #{college}", "</if>",
	    "<if test='region != null'>", ", #{region}", "</if>",
	    "<if test='finaledu != null'>", ", #{finaledu}", "</if>",
	    "<if test='incomelevel != null'>", ", #{incomelevel}", "</if>",
	    
	    ")",
	    "</script>"
	})
    int insertInputInfo(InputInfoTable inputInfo);
	
	// user_answers 테이블
	@Insert({
	    "<script>",
	    "INSERT INTO `project_survey`.`user_answers`",
	    "(user_survey_no, questionno",
	    "<if test='useranswer1 != null'>, useranswer1</if>",
	    "<if test='useranswer2 != null'>, useranswer2</if>",
	    "<if test='useranswer3 != null'>, useranswer3</if>",
	    "<if test='useranswer4 != null'>, useranswer4</if>",
	    "<if test='useranswer5 != null'>, useranswer5</if>",
	    "<if test='useranswer6 != null'>, useranswer6</if>",
	    "<if test='useranswer7 != null'>, useranswer7</if>",
	    "<if test='useranswer8 != null'>, useranswer8</if>",
	    "<if test='useranswer9 != null'>, useranswer9</if>",
	    "<if test='useranswer10 != null'>, useranswer10</if>",
	    "<if test='useranswer11 != null'>, useranswer11</if>",
	    "<if test='useranswer12 != null'>, useranswer12</if>",
	    "<if test='useranswer13 != null'>, useranswer13</if>",
	    "<if test='useranswer14 != null'>, useranswer14</if>",
	    "<if test='useranswer15 != null'>, useranswer15</if>",
	    "<if test='useranswer16 != null'>, useranswer16</if>",
	    "<if test='useranswer17 != null'>, useranswer17</if>",
	    "<if test='useranswer18 != null'>, useranswer18</if>",
	    "<if test='useranswer19 != null'>, useranswer19</if>",
	    "<if test='useranswer20 != null'>, useranswer20</if>",
	    ") VALUES (#{user_survey_no}, #{questionno}",
	    "<if test='useranswer1 != null'>, #{useranswer1}</if>",
	    "<if test='useranswer2 != null'>, #{useranswer2}</if>",
	    "<if test='useranswer3 != null'>, #{useranswer3}</if>",
	    "<if test='useranswer4 != null'>, #{useranswer4}</if>",
	    "<if test='useranswer5 != null'>, #{useranswer5}</if>",
	    "<if test='useranswer6 != null'>, #{useranswer6}</if>",
	    "<if test='useranswer7 != null'>, #{useranswer7}</if>",
	    "<if test='useranswer8 != null'>, #{useranswer8}</if>",
	    "<if test='useranswer9 != null'>, #{useranswer9}</if>",
	    "<if test='useranswer10 != null'>, #{useranswer10}</if>",
	    "<if test='useranswer11 != null'>, #{useranswer11}</if>",
	    "<if test='useranswer12 != null'>, #{useranswer12}</if>",
	    "<if test='useranswer13 != null'>, #{useranswer13}</if>",
	    "<if test='useranswer14 != null'>, #{useranswer14}</if>",
	    "<if test='useranswer15 != null'>, #{useranswer15}</if>",
	    "<if test='useranswer16 != null'>, #{useranswer16}</if>",
	    "<if test='useranswer17 != null'>, #{useranswer17}</if>",
	    "<if test='useranswer18 != null'>, #{useranswer18}</if>",
	    "<if test='useranswer19 != null'>, #{useranswer19}</if>",
	    "<if test='useranswer20 != null'>, #{useranswer20}</if>",
	    ")",
	    "</script>"
	})
	int insertUserAnswer(UserAnswers userAnswers);
}