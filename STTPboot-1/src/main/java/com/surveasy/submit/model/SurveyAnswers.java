package com.surveasy.submit.model;


import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswers {
    private int questionno;
    private String type;
    private Map<String, String> answerMap;
}
