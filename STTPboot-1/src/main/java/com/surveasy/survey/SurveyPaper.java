package com.surveasy.survey;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyPaper {
	private Integer userno;
	private String surveytitle;
	private String surveycontent;
	private LocalDateTime regidate;
	private LocalDateTime deadline;
	private Integer participants;
	private String link;
	private Integer bookmark;
}