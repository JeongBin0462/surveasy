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

	List<MainSurveyObj> listByNewPage(List<MainSurveyObj> list, int newPage);

	int getMaxPage(List<MainSurveyObj> list);

	int getNewPage(List<MainSurveyObj> list, int currentPage, String dir);

	
}
