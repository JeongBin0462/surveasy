package com.surveasy.model.form;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyPaperTable {
	private Integer userno;
	private String surveytitle;
	private String surveycontent;
	private String surveysubject;
	private String surveytarget;
	private LocalDateTime writetime;
	private LocalDateTime regidate;
	private LocalDateTime deadline;
	private Integer participants;
	private String link;
	private Integer bookmark;
}
