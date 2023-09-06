package com.surveasy.survey.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.surveasy.security.UserSecurityServiceImpl;
import com.surveasy.survey.mapper.SurveyMapper;
import com.surveasy.survey.model.Answers;
import com.surveasy.survey.model.SurveyOption;
import com.surveasy.survey.model.SurveyPaper;
import com.surveasy.survey.model.SurveyQuestion;
import com.surveasy.survey.model.SurveyRequire;
import com.surveasy.user.model.Employees;
import com.surveasy.user.model.Student;
import com.surveasy.user.model.User;
import com.surveasy.user.model.UserDTO;

@Service
public class SurveyServiceImpl implements SurveyService {
	@Autowired
	SurveyMapper surveyMapper;

	@Autowired
	UserSecurityServiceImpl userSecurityServiceImpl;

	// 3-1에 사용되는 전체 설문 목록
	@Override
	public List<SurveyPaper> getSurveyPaperList(String subject, String sortOption, String search, int currentPage) {
		final int PAGE_SIZE = 10; // 한 페이지에 표시될 항목 수
		List<SurveyPaper> list = surveyMapper.getSurveyList();
		LocalDateTime now = LocalDateTime.now();

		List<SurveyPaper> removeList = new ArrayList<>();

		for (SurveyPaper surveyPaper : list) {
			LocalDateTime deadline = surveyPaper.getDeadline();

			if (deadline == null || now.isAfter(deadline)) {
				removeList.add(surveyPaper);
			}
		}
		list.removeAll(removeList);
		System.out.println(list.toString());

		removeList = new ArrayList<>();
		List<Integer> removeListPrivate = new ArrayList<>();
		for (SurveyPaper surveyPaper : list) {
			int surveyno = surveyPaper.getSurveyno();
			removeListPrivate.add(surveyMapper.getSurveyOptionIsPublic(surveyno));
		}

		for (SurveyPaper surveyPaper : list) {
			if (removeListPrivate == null) {
				break;
			}
			for (Integer numbers : removeListPrivate) {
				if (surveyPaper.getSurveyno() == numbers) {
					removeList.add(surveyPaper);
				}
			}
		}
		list.removeAll(removeList);
		
		if (search != null && !search.trim().isEmpty()) {
			list = list.stream()
	                 .filter(paper -> paper.getSurveytitle().contains(search))
	                 .collect(Collectors.toList());
	    }

		// 필터링: 주제선택
		if (subject != null && !subject.isEmpty()) {
			String parseSubject = parseSubject(subject);
			List<Integer> keepSurveynoList = surveyMapper.getSurveynoBySubject(parseSubject);
			Iterator<SurveyPaper> iterator = list.iterator();
			while (iterator.hasNext()) {
				SurveyPaper paper = iterator.next();
				if (!keepSurveynoList.contains(paper.getSurveyno())) {
					iterator.remove();
				}
			}
		}

		// 정렬: sortOption에 따라 리스트를 정렬
		if ("남은기간".equals(sortOption)) {
			list.sort(Comparator.comparing(SurveyPaper::getDeadline));
		} else if ("최신순".equals(sortOption)) {
			list.sort(Comparator.comparing(SurveyPaper::getRegidate));
		} else if ("참여순".equals(sortOption)) {
			list.sort(Comparator.comparing(SurveyPaper::getParticipants).reversed());
		}
		
		// 페이징처리
	    int startItem = (currentPage - 1) * PAGE_SIZE;
	    int endItem = startItem + PAGE_SIZE;
	    if (endItem > list.size()) {
	        endItem = list.size();
	    }
	    return list.subList(startItem, endItem);
	}
	
	// 전체 리스트
	public int getTotalSurveyCount(String subject, String sort, String search) {
		List<SurveyPaper> list = surveyMapper.getSurveyList();
		LocalDateTime now = LocalDateTime.now();

		List<SurveyPaper> removeList = new ArrayList<>();

		for (SurveyPaper surveyPaper : list) {
			LocalDateTime deadline = surveyPaper.getDeadline();

			if (deadline == null || now.isAfter(deadline)) {
				removeList.add(surveyPaper);
			}
		}
		list.removeAll(removeList);
		System.out.println(list.toString());

		removeList = new ArrayList<>();
		List<Integer> removeListPrivate = new ArrayList<>();
		for (SurveyPaper surveyPaper : list) {
			int surveyno = surveyPaper.getSurveyno();
			removeListPrivate.add(surveyMapper.getSurveyOptionIsPublic(surveyno));
		}

		for (SurveyPaper surveyPaper : list) {
			if (removeListPrivate == null) {
				break;
			}
			for (Integer numbers : removeListPrivate) {
				if (surveyPaper.getSurveyno() == numbers) {
					removeList.add(surveyPaper);
				}
			}
		}
		list.removeAll(removeList);
		
		if (search != null && !search.trim().isEmpty()) {
			list = list.stream()
	                 .filter(paper -> paper.getSurveytitle().contains(search))
	                 .collect(Collectors.toList());
	    }

		// 필터링: 주제선택
		if (subject != null && !subject.isEmpty()) {
			String parseSubject = parseSubject(subject);
			List<Integer> keepSurveynoList = surveyMapper.getSurveynoBySubject(parseSubject);
			Iterator<SurveyPaper> iterator = list.iterator();
			while (iterator.hasNext()) {
				SurveyPaper paper = iterator.next();
				if (!keepSurveynoList.contains(paper.getSurveyno())) {
					iterator.remove();
				}
			}
		}

		// 정렬: sortOption에 따라 리스트를 정렬
		if ("남은기간".equals(sort)) {
			list.sort(Comparator.comparing(SurveyPaper::getDeadline));
		} else if ("최신순".equals(sort)) {
			list.sort(Comparator.comparing(SurveyPaper::getRegidate));
		} else if ("참여순".equals(sort)) {
			list.sort(Comparator.comparing(SurveyPaper::getParticipants).reversed());
		}
	    return list.size();
	}

	// 주제 parse
	private String parseSubject(String subject) {
		switch (subject) {
		case "정치":
			return "politics";
		case "경제":
			return "economy";
		case "사회":
			return "society";
		case "문화":
			return "culture";
		case "과학":
			return "science";
		case "철학":
			return "philosophy";
		default:
			return "";
		}
	}

	@Override
	public SurveyOption getSurveyOption(int surveyno) {
		SurveyOption surveyOption = surveyMapper.getSurveyOption(surveyno);
		System.out.println(surveyOption.toString());
		System.out.println("--------------------");
		return surveyOption;
	}

	@Override
	public SurveyRequire getSurveyRequire(int surveyno) {
		SurveyRequire surveyRequire = surveyMapper.getSurveyRequire(surveyno);
		System.out.println(surveyRequire.toString());
		System.out.println("--------------------");
		return surveyRequire;
	}

	@Override
	public List<SurveyQuestion> getSurveyQuestion(int surveyno) {
		List<SurveyQuestion> surveyQuestion = surveyMapper.getQuestion(surveyno);
		System.out.println(surveyQuestion.toString());
		System.out.println("--------------------");
		return surveyQuestion;
	}

	@Override
	public List<Answers> getAnswers(List<SurveyQuestion> list) {
		Answers answers;
		List<Answers> answersList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String type = list.get(i).getAnswer_types();
			int questionno = list.get(i).getQuestionno();
			System.out.println(type);

			if (!type.equals("서술형")) {
				answers = surveyMapper.getAnswer(questionno);

				System.out.println(answers);
				answersList.add(answers);
			}
		}
		System.out.println(answersList.toString());
		return answersList;
	}

	// 링크를 통한 설문지 정보 가져오기
	@Override
	public SurveyPaper getSurveyPaperByLink(String link) {
		return surveyMapper.getSurveyByLink(link);
	}

	// 설문 중복 참여 방지를 위한 설문 참여 정보
	@Override
	public boolean getUserSurvey(int surveyno) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		// 현재 userno
		Integer userno = userSecurityServiceImpl.getUserno(username);
		Integer userSurveyno = surveyMapper.getUserSurveyBySurveyno(userno, surveyno);

		System.out.println("surveyno: " + surveyno);
		System.out.println("userno: " + userno);
		System.out.println("userSurveyNo : " + userSurveyno);
		if (userSurveyno != null) {
			return false;
		}
		return true;
	}

	// 기본 데이터 미리 입력을 위해 회원가입 시 입력된 정보 불러오기
	@Override
	public UserDTO getUserInfo() {
		// 시큐리티를 통한 유저아이디 불러오기
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		// 유저 정보 중 필요한 정보만 객체로 생성
		UserDTO userInfo = new UserDTO();
		User user = surveyMapper.getUserInfo(username);
		BeanUtils.copyProperties(user, userInfo);
		int userno = user.getUserno();
		if (user.getJob() != null) {
			if (user.getJob().equals("학생")) {
				Student student = surveyMapper.getStudentInfo(userno);
				BeanUtils.copyProperties(student, userInfo);
			}
			if (user.getJob().equals("직장인")) {
				Employees employees = surveyMapper.getEmployeesInfo(userno);
				BeanUtils.copyProperties(employees, userInfo);
			}
		}
		return userInfo;
	}
}
