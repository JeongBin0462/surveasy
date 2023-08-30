package com.surveasy.model.survey;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveySubmitDTO {
	private String surveyno;
    private Map<String, Object> questions;
}
