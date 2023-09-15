package com.surveasy.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserbookmarkMapper {

	// 3-2 즐겨찾기 되어있는지
	@Select("SELECT bookmark_no FROM user_bookmark WHERE userno=#{userno} AND surveyno=#{surveyno}")
	Integer CheckBookmark(@Param("userno") int userno, @Param("surveyno") int surveyno);

	// 3-2 즐겨찾기 카운트
	@Select("SELECT count(*) FROM user_bookmark WHERE surveyno=#{surveyno}")
	int CountBookmark(@Param("surveyno") int surveyno);

	// 3-2 즐겨찾기 추가
	@Insert("INSERT INTO user_bookmark (userno, surveyno) VALUES (#{userno}, #{surveyno})")
	int InsertBookmark(@Param("userno") int userno, @Param("surveyno") int surveyno);

	// 3-2 즐겨찾기 제거
	@Delete("DELETE FROM user_bookmark WHERE userno=#{userno}")
	int DeleteBookmark(@Param("userno") int userno);
}
