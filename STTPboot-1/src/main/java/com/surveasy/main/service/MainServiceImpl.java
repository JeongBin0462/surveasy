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
	
	private final int TOTAL_PAGE_NUM = 3; // 총 페이지 개수. 3개 > 0, 1, 2

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
	public List<MainSurveyObj> listByNewPage(List<MainSurveyObj> list, int newPage) {
		List<MainSurveyObj> mainSurveyList = new ArrayList<>();
		for (int i = 5 * newPage; i < list.size(); i++) {
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

	@Override
	public int getMaxPage(List<MainSurveyObj> list) {
		if (list.size() > TOTAL_PAGE_NUM * 5) {
			return TOTAL_PAGE_NUM - 1;
		}
		if (list.size() == 0) {
			return 0;
		}
		return (list.size() - 1) / 5;
	}

	@Override
	public int getNewPage(List<MainSurveyObj> list, int currentPage, String dir) {

		int maxPage = getMaxPage(list);
		
		if (dir.equals("prev")) {
			if (maxPage < currentPage) {
				return 0;
			}
			
			return (currentPage - 1 + (maxPage + 1)) % (maxPage + 1);
		}

		if (dir.equals("next")) {
			if (maxPage <= currentPage) {
				return 0;
			}
			return (currentPage + 1 + (maxPage + 1)) % (maxPage + 1);
		}

		return 0;
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
