package com.surveasy.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyQuestion {
	private Integer questionno;
	private Integer surveyno;
	private String question_contents;
    private String answer_types;
    private boolean mandatory;
    private String answer_min;
    private String answer_max;
}
