package com.surveasy.submit.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveySubmitDTO {
	private Require require;
	private List<SurveyAnswers> surveySubmits;
}
