package com.surveasy.model.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerEssay {
	private Integer user_survey_no;
	private Integer questionno;
	private String useranswer;
}
