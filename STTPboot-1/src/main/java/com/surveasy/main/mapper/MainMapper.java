package com.surveasy.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.surveasy.survey.model.SurveyPaper;

@Mapper
public interface MainMapper {
	
	@Select("SELECT * FROM surveypaper WHERE deadline > CURRENT_TIMESTAMP()")
	List<SurveyPaper> getSurveyListByTime();
	
}