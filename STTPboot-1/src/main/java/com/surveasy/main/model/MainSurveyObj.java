package com.surveasy.main.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainSurveyObj {
	private Integer surveyno;
	private String surveytitle;
	private String regidate;
	private String deadline;
	private int participants;
	private String link;
	private int bookmark;
	private String subject;
	private String target;
	private boolean is_public_survey;
}



