package com.surveasy.main.service;

import java.util.List;

import com.surveasy.main.model.MainSurveyObj;

public interface MainService {

	List<MainSurveyObj> generateMainList();

	List<MainSurveyObj> sortByRemainTime(List<MainSurveyObj> mainSurveyList);

	List<MainSurveyObj> sortByLatest(List<MainSurveyObj> mainSurveyList);

	List<MainSurveyObj> sortByParticipants(List<MainSurveyObj> mainSurveyList);

	List<MainSurveyObj> sortBySubject(List<MainSurveyObj> mainSurveyList, String subject);

	List<MainSurveyObj> sortByBookmark(List<MainSurveyObj> mainSurveyList);

	int getCurrentPage(List<MainSurveyObj> list, int pageNum);

	List<MainSurveyObj> listByPage(List<MainSurveyObj> list, int currentPage);

}
