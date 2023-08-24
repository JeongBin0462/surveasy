package com.survey.sttp.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.survey.sttp.model.user.User;
import com.survey.sttp.model.user.UserDTO;
import com.survey.sttp.service.user.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/STTP/user")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;


	@GetMapping(value = "/joinagree")
	public String joinagree() {

		return "/7.1join_agree";
	}

	@GetMapping(value = "/join")
	public String join(Model model) {
		model.addAttribute("user", new User());
		return "/7.2join";
	}
	
	@GetMapping(value = "/join", params = "username")
    public ResponseEntity<Map<String, Object>> checkUsername(@RequestParam("username") String username) {
        Map<String, Object> response = new HashMap<>();
        System.out.println("여기오니?");
        System.out.println("eee" + username);
        try {
        	System.out.println("오겠지?");
            int userCount = userService.checkUsername(username);
            System.out.println("여기까지만와줘");
            if (userCount > 0) {
                response.put("isDuplicate", true);
                response.put("message", "이미 사용중인 아이디입니다.");
            } else {
                response.put("isDuplicate", false);
                response.put("message", "사용 가능한 아이디입니다.");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            response.put("message", "서버 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping(value = "/join", params = "email")
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam("email") String email) {
        Map<String, Object> response = new HashMap<>();
        try {
            int userCount = userService.checkEmail(email);
            if (userCount > 0) {
                response.put("isDuplicate", true);
                response.put("message", "이미 사용중인 이메일입니다.");
            } else {
                response.put("isDuplicate", false);
                response.put("message", "사용 가능한 이메일입니다.");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "서버 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping(value = "/join", params = "phonenumber")
    public ResponseEntity<Map<String, Object>> checkPhonenumber(@RequestParam("phonenumber") String phonenumber) {
        Map<String, Object> response = new HashMap<>();
        try {
            int userCount = userService.checkPhonenumber(phonenumber);
            if (userCount > 0) {
                response.put("isDuplicate", true);
                response.put("message", "이미 사용중인 전화번호입니다.");
            } else {
                response.put("isDuplicate", false);
                response.put("message", "사용 가능한 전화번호입니다.");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "서버 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@ResponseBody
	@PostMapping(value = "/join", consumes = "application/json")
	public Map<String, Object> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		List<String> errors = new ArrayList<>();

		if (!userDTO.getPassword().equals(userDTO.getPasswordCheck())) {
			errors.add("입력한 비밀번호가 서로 다릅니다");
		}

		if (result.hasErrors()) {
			errors.addAll(
					result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList()));
		}

		if (!errors.isEmpty()) {
			response.put("success", false);
			response.put("errors", errors);
			return response;
		}

		if (userService.insertUser(userDTO)) {
			response.put("success", true);
			response.put("redirectUrl", "/STTP/main");
		} else {
			response.put("success", false);
			response.put("redirectUrl", "/STTP/user/join");
		}

		return response;
	}
}
