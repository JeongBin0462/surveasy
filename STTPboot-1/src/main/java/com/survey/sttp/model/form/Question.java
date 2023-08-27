package com.survey.sttp.model.form;

import java.util.List;

public class Question {
	private String question_contents;
	private String answer_types;
	private boolean mandatory;

	private int answer_min;
	private int answer_max;

	private List<String> answer;
}
