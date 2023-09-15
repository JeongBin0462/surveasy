package com.surveasy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.surveasy.survey.model.SurveyPaper;

@Mapper
public interface SurveypaperMapper {

	@Insert({ "<script>", "INSERT INTO surveypaper (userno", "<if test='map.surveytitle != null'>, surveytitle</if>",
			"<if test='map.surveycontent != null'>, surveycontent</if>",
			"<if test='map.regidate != null'>, regidate</if>", "<if test='map.deadline != null'>, deadline</if>",
			"<if test='map.link != null'>, link</if>", ") VALUES (#{map.userno}",
			"<if test='map.surveytitle != null'>, #{map.surveytitle}</if>",
			"<if test='map.surveycontent != null'>, #{map.surveycontent}</if>",
			"<if test='map.regidate != null'>, #{map.regidate}</if>",
			"<if test='map.deadline != null'>, #{map.deadline}</if>", "<if test='map.link != null'>, #{map.link}</if>",
			")", "</script>" })
	@Options(useGeneratedKeys = true, keyProperty = "map.surveyno", keyColumn = "surveyno")
	int insertSurveyPaperTemp(@Param("map") Map<String, Object> params);

	@Select({ "<script>", "SELECT COUNT(*) AS cnt FROM surveypaper WHERE userno = #{userno}", " AND regidate IS NULL",
			"</script>" })
	int countTempSaveNum(@Param("userno") Integer userno);

	@Select("SELECT link FROM surveypaper WHERE surveyno = #{surveyno}")
	String getLink(@Param("surveyno") Integer surveyno);

	@Select("SELECT * FROM surveypaper WHERE deadline > CURRENT_TIMESTAMP()")
	List<SurveyPaper> getSurveyListByTime();

	// 3-1 설문지 목록
	@Select("SELECT * FROM surveypaper WHERE deadline > now()")
	List<SurveyPaper> getSurveyList();

	// 3-2, 3-3 링크를 통해 접근 -> 어떤 설문지인지
	@Select("SELECT * FROM surveypaper WHERE link=#{link}")
	SurveyPaper getSurveyByLink(@Param("link") String link);

	// 3-3 본인 설문 참여 방지
	@Select("SELECT surveyno FROM surveypaper WHERE userno=#{userno} AND surveyno=#{surveyno}")
	Integer getSurveyByUserno(@Param("userno") int userno, @Param("surveyno") int surveyno);
	
	// SurveyPaper리스트
	@Select("SELECT * FROM surveypaper WHERE deadline > NOW() \r\n"
			+ "AND surveyno NOT IN (SELECT surveyno FROM user_survey WHERE userno =#{userno}) "
			+ "AND surveyno=#{surveyno}")
	SurveyPaper getSurveyPaperBySurveyno(@Param("userno") int userno, @Param("surveyno") int surveyno);

}
