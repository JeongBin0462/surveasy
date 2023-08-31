package com.surveasy.model.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerRadio {
	private Integer user_survey_no;
	private Integer questionno;
	private Integer useranswer;
}
