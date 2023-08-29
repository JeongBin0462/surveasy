package com.survey.sttp.model.form;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyOptionTable {
	private int surveyno;
	private boolean is_public_survey;
	private boolean require_login;
	private boolean is_public_result;
	private boolean show_progress;
}
