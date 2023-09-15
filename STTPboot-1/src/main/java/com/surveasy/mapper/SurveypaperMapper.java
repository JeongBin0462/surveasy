package com.surveasy.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SurveypaperMapper {
	
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
