package com.surveasy.survey.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyOption {

	private Integer surveyno;
	private boolean is_public_survey;
	private boolean require_login;
	private boolean is_public_result;
	private boolean show_progress;
}
