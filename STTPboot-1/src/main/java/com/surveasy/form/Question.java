package com.surveasy.form;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.surveasy.user.model.UserDTO;

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
    private List<String> answers;
}

