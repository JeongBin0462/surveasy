package com.surveasy.user;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberDTO {
	@Pattern(regexp = "^010[0-9]{8}$", message = "유효한 전화번호 형식이 아닙니다.")
    private String phonenumber;
}
