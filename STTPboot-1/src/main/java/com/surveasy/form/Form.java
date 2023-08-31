package com.surveasy.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Form {
	private String subject;
    private String period;
    private boolean email;
    private boolean phonenumber;
    private boolean age;
    private boolean gender;
    private String target;
    private boolean department;
    private boolean position;
    private boolean grade;
    private boolean college;
    private boolean region;
    private boolean finaledu;
    private boolean incomelevel;
}
