package com.surveasy.user.model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameDTO {
	@Pattern(regexp = "^[가-힣A-Za-z0-9]{5,20}$"
			, message = "사용자 이름은 한글, 영어, 숫자를 이용하여 5~20자로 구성되어야 합니다.")
	private String username;
}
