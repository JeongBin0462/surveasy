package com.surveasy.mapper.form;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.model.survey.SurveyPaper;

@Mapper
public interface FormMapper {
	
	// 임시저장(regidate, deadline 제외) surveypaper 테이블
	@Insert("INSERT INTO surveypaper (surveytitle, surveycontent, link) VALUES (#{surveytitle}, #{surveycontent}, #{link})")
	int insertSurvey(@Param("surveytitle") String surveytitle, @Param("surveycontent") String surveycontent, @Param("link") String link);

	// 저장한 surveypaper의 surveyno를 가져옴
	@Select("SELECT * FROM surveypaper WHERE surveytitle = #{surveytitle} and surveycontent = #{surveycontent} and link = #{link}")
	SurveyPaper getSameSurvey(@Param("surveytitle") String surveytitle, @Param("surveycontent") String surveycontent, @Param("link") String link);
	
//	@Insert
}
