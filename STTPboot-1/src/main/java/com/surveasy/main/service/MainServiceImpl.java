package com.surveasy.main.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.surveasy.main.model.MainSurveyObj;
import com.surveasy.mapper.SurveyoptionMapper;
import com.surveasy.mapper.SurveypaperMapper;
import com.surveasy.mapper.SurveyrequireMapper;
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
	SurveypaperMapper surveypaperMapper;
	
	@Autowired
	SurveyrequireMapper surveyrequireMapper;
	
	@Autowired
	SurveyoptionMapper surveyoptionMapper;

	@Transactional
	@Override
	public List<MainSurveyObj> generateMainList() {
		List<MainSurveyObj> mainSurveyList = new ArrayList<>();
		List<SurveyPaper> surveyPaperList = surveypaperMapper.getSurveyListByTime();

		for (int i = 0; i < surveyPaperList.size(); i++) {
			SurveyPaper surveyPaper = surveyPaperList.get(i);

			SurveyRequire surveyRequire = surveyrequireMapper.getSurveyRequire(surveyPaper.getSurveyno());

			SurveyOption surveyOption = surveyoptionMapper.getSurveyOption(surveyPaper.getSurveyno());
			
			if (surveyPaper == null || surveyRequire == null || surveyOption == null) {
				continue;
			}
			
			if (!surveyOption.is_public_survey()) {
				continue;
			}
			MainSurveyObj mainSurvey = MainSurveyObj.builder().surveyno(surveyPaper.getSurveyno())
					.surveytitle(surveyPaper.getSurveytitle()).regidate(formatDateTime(surveyPaper.getRegidate()))
					.deadline(formatDateTime(surveyPaper.getDeadline())).participants(surveyPaper.getParticipants())
					.link(surveyPaper.getLink()).bookmark(surveyPaper.getBookmark()).subject(surveyRequire.getSubject())
					.target(surveyRequire.getTarget()).is_public_survey(surveyOption.is_public_survey()).build();

			mainSurveyList.add(mainSurvey);
		}
		return mainSurveyList;
	}

	@Override
	public List<MainSurveyObj> sortByRemainTime(List<MainSurveyObj> mainSurveyList) {
		RemainTimeComparator comp = new RemainTimeComparator();
		Collections.sort(mainSurveyList, comp);
		return mainSurveyList;
	}

	@Override
	public List<MainSurveyObj> sortByLatest(List<MainSurveyObj> mainSurveyList) {
		RegidateComparator comp = new RegidateComparator();
		Collections.sort(mainSurveyList, comp);
		return mainSurveyList;
	}

	@Override
	public List<MainSurveyObj> sortByParticipants(List<MainSurveyObj> mainSurveyList) {
		ParticipantsComparator comp = new ParticipantsComparator();
		Collections.sort(mainSurveyList, comp);
		return mainSurveyList;
	}

	@Override
	public List<MainSurveyObj> sortByBookmark(List<MainSurveyObj> mainSurveyList) {
		BookmarkComparator comp = new BookmarkComparator();
		Collections.sort(mainSurveyList, comp);
		return mainSurveyList;
	}

	@Override
	public List<MainSurveyObj> sortBySubject(List<MainSurveyObj> mainSurveyList, String subject) {
		String sortSubject = SUBJECT_MAP.get(subject);

		List<MainSurveyObj> sortedList = new ArrayList<>();
		Iterator<MainSurveyObj> iter = mainSurveyList.iterator();
		while (iter.hasNext()) {
			MainSurveyObj elem = iter.next();
			if (elem.getSubject().equals(sortSubject)) {
				sortedList.add(elem);
			}
		}
		return sortedList;
	}

	@Override
	public int getCurrentPage(List<MainSurveyObj> list, int pageNum) {
		if (list.size() > 15) {
			return pageNum;
		}
		
		if ((list.size() != 0) && (((list.size() - 1) / 5)) > pageNum) {
			return pageNum;
			
			
			// 사이즈가 9일때, 오른쪽 버튼을 누르면 1에서 더이상 증가하지 않음.
			// 사이즈가 9이고 pageNum이 1일 떄 버튼을 누르면 0이 되어야함.
			
			// ex) 사이즈가 14이고 pageNum이 2일 때 버튼을 누르면 0
			// 사이즈가 4이고 pageNum이 0일 때 버튼을 누르면 0
			
		} else if ((list.size() != 0) && ((list.size() - 1) / 5) == pageNum) {
			return 0;
		
		
	} else {
			return (list.size() / 5);
		}
	}

	@Override
	public List<MainSurveyObj> listByPage(List<MainSurveyObj> list, int currentPage) {
		List<MainSurveyObj> mainSurveyList = new ArrayList<>();
		for (int i = 5 * currentPage; i < list.size(); i++) {
			mainSurveyList.add(list.get(i));
			if (mainSurveyList.size() == 5) {
				break;
			}
		}
		return mainSurveyList;
	}

	private String formatDateTime(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd hh:mm");
		return dateTime.format(formatter);
	}

}

class RemainTimeComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		if (o1 == null || o2 == null)
			return 0;
		return o1.getDeadline().compareTo(o2.getDeadline());
	}
}

class RegidateComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		if (o1 == null || o2 == null)
			return 0;
		return o2.getRegidate().compareTo(o1.getRegidate());
	}

}

class ParticipantsComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		if (o1 == null || o2 == null)
			return 0;
		return o2.getParticipants() - o1.getParticipants();
	}

}

class BookmarkComparator implements Comparator<MainSurveyObj> {
	@Override
	public int compare(MainSurveyObj o1, MainSurveyObj o2) {
		if (o1 == null || o2 == null)
			return 0;
		return o2.getBookmark() - o1.getBookmark();
	}

}
