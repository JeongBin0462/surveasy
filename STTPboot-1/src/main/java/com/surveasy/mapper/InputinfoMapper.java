package com.surveasy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.surveasy.submit.model.InputInfoTable;

@Mapper
public interface InputinfoMapper {

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
		
		
}
