package com.surveasy.main.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	public static final Map<String, String> SUBJECT_MAP;
	
	static {
		Map<String, String> tempMap = new HashMap<>();
		tempMap.put("정치", "politics");
		tempMap.put("경제", "economy");
		tempMap.put("사회", "society");
		tempMap.put("문화", "culture");
		tempMap.put("과학", "science");
		tempMap.put("철학", "philosophy");
		SUBJECT_MAP = Collections.unmodifiableMap(tempMap);
	}
	
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
		System.out.println("mainSurveyList" + mainSurveyList);
		return mainSurveyList;
	}

	@Override
	public List<MainSurveyObj> sortByRemainTime(List<MainSurveyObj> mainSurveyList) {
		RemainTimeComparator comp = new RemainTimeComparator();
		Collections.sort(mainSurveyList, comp);
		System.out.println("mainSurveyList 정렬" + mainSurveyList);
		return mainSurveyList;
	}
	
	@Override
	public List<MainSurveyObj> sortByLatest(List<MainSurveyObj> mainSurveyList) {
		RegidateComparator comp = new RegidateComparator();
		Collections.sort(mainSurveyList, comp);
		System.out.println("mainSurveyList 정렬" + mainSurveyList);
		return mainSurveyList;
	}
	
	@Override
	public List<MainSurveyObj> sortByParticipants(List<MainSurveyObj> mainSurveyList) {
		ParticipantsComparator comp = new ParticipantsComparator();
		Collections.sort(mainSurveyList, comp);
		System.out.println("mainSurveyList 정렬" + mainSurveyList);
		return mainSurveyList;
	}
	
	@Override
	public List<MainSurveyObj> sortByBookmark(List<MainSurveyObj> mainSurveyList) {
		BookmarkComparator comp = new BookmarkComparator();
		Collections.sort(mainSurveyList, comp);
		System.out.println("mainSurveyList 정렬" + mainSurveyList);
		return mainSurveyList;
	}
	
	@Override
	public List<MainSurveyObj> sortBySubject(List<MainSurveyObj> mainSurveyList, String subject) {
		
		String sortSubject = SUBJECT_MAP.get(subject);
		
		List<MainSurveyObj> sortedList = new ArrayList<>();
		Iterator<MainSurveyObj> iter = mainSurveyList.iterator();
		while(iter.hasNext()) {
			MainSurveyObj elem = iter.next();
			if (elem.getSubject().equals(sortSubject)) {
				sortedList.add(elem);
			}
		}
		System.out.println("subject 정렬" + sortedList);
		return sortedList;
	}
}

class RemainTimeComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		if (o1 == null || o2 == null) return 0;
		return o1.getDeadline().compareTo(o2.getDeadline());
	}
}

class RegidateComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		if (o1 == null || o2 == null) return 0;
		return o2.getRegidate().compareTo(o1.getRegidate());
	}
	
}

class ParticipantsComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		if (o1 == null || o2 == null) return 0;
		return o2.getParticipants()-o1.getParticipants();
	}
	
}

class BookmarkComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		if (o1 == null || o2 == null) return 0;
		return o2.getBookmark()-o1.getBookmark();
	}
	
}


