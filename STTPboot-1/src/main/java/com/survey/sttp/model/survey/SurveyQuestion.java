package com.survey.sttp.model.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyQuestion {
	private int questionno;
	private String question_contents;
    private String answer_types;
    private boolean mandatory;
    private String answer_min;
    private String answer_max;
}
