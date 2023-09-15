package com.surveasy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.surveasy.submit.model.UserAnswers;

@Mapper
public interface UseranswersMapper {

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
