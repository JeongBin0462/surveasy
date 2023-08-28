package com.survey.sttp.model.form;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.survey.sttp.model.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
	
	private String question_contents;
    private String answer_types;
    private boolean mandatory;
    private String answer_min;
    private String answer_max;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;
}
