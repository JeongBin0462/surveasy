package com.surveasy.main.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.surveasy.main.mapper.MainMapper;
import com.surveasy.main.model.MainSurveyObj;
import com.surveasy.survey.mapper.SurveyMapper;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyRequire;

@Service
public class MainServiceImpl implements MainService {

	@Autowired
	SurveyMapper surveyMapper;

	@Autowired
	MainMapper mainMapper;

	@Transactional
	@Override
	public List<MainSurveyObj> generateMainList() {
		List<MainSurveyObj> mainSurveyList = new ArrayList<>();
		List<SurveyPaper> surveyPaperList = mainMapper.getSurveyListByTime();

		for (int i = 0; i < surveyPaperList.size(); i++) {
			SurveyPaper surveyPaper = surveyPaperList.get(i);
			SurveyRequire surveyRequire = surveyMapper.getSurveyRequire(surveyPaper.getSurveyno());

			SurveyOption surveyOption = surveyMapper.getSurveyOption(surveyPaper.getSurveyno());

			MainSurveyObj mainSurvey = MainSurveyObj.builder().surveyno(surveyPaper.getSurveyno()).surveytitle(surveyPaper.getSurveytitle())
					.regidate(surveyPaper.getRegidate()).deadline(surveyPaper.getDeadline())
					.participants(surveyPaper.getParticipants()).link(surveyPaper.getLink())
					.bookmark(surveyPaper.getBookmark()).subject(surveyRequire.getSubject())
					.target(surveyRequire.getTarget()).is_public_survey(surveyOption.is_public_survey()).build();
			
			mainSurveyList.add(mainSurvey);
		}
		return mainSurveyList;
	}

	public List<MainSurveyObj> sortByRemainTime(List<MainSurveyObj> mainSurveyList) {
		mainSurveyList.sort(new RemainTimeComparator());
		return mainSurveyList;
	}
	
	public List<MainSurveyObj> sortByLatest(List<MainSurveyObj> mainSurveyList) {
		mainSurveyList.sort(new RegidateComparator());
		return mainSurveyList;
	}
	
	public List<MainSurveyObj> sortByParticipants(List<MainSurveyObj> mainSurveyList) {
		mainSurveyList.sort(new ParticipantsComparator());
		return mainSurveyList;
	}
	
	public List<MainSurveyObj> sortBySubject(List<MainSurveyObj> mainSurveyList, String subject) {
		Iterator<MainSurveyObj> iter = mainSurveyList.iterator();
		while(iter.hasNext()) {
			MainSurveyObj elem = iter.next();
			if (elem.getSubject().equals(subject)) {
				iter.remove();
			}
		}
		return mainSurveyList;
	}
}

class RemainTimeComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		return o1.getDeadline().compareTo(o2.getDeadline());
	}
}

class RegidateComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		return o2.getRegidate().compareTo(o1.getRegidate());
	}
	
}

class ParticipantsComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		return o1.getParticipants()-o2.getParticipants();
	}
	
}


