package com.survey.sttp.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.survey.sttp.model.user.EmailDTO;
import com.survey.sttp.model.user.PhoneNumberDTO;
import com.survey.sttp.model.user.User;
import com.survey.sttp.model.user.UserDTO;
import com.survey.sttp.model.user.UsernameDTO;
import com.survey.sttp.service.user.UserRepository;
import com.survey.sttp.service.user.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/surveasy/user")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;


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
	public ResponseEntity<Map<String, Object>> checkUsername(@Valid UsernameDTO usernameDTO, BindingResult result) {
	    
	    Map<String, Object> response = new HashMap<>();

	    if (result.hasErrors()) {
	        response.put("isDuplicateUsername", true);
	        response.put("message", result.getFieldError().getDefaultMessage());
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    try {
	        int userCount = userService.checkUsername(usernameDTO.getUsername());
	        if (userCount > 0) {
	            response.put("isDuplicateUsername", true);
	            response.put("message", "이미 사용중인 아이디입니다.");
	        } else {
	            response.put("isDuplicateUsername", false);
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
	public ResponseEntity<Map<String, Object>> checkEmail(@Valid EmailDTO emailDTO, BindingResult result) {
		
        Map<String, Object> response = new HashMap<>();
        
        if (result.hasErrors()) {
        	response.put("isDuplicateEmail", true);
	        response.put("message", result.getFieldError().getDefaultMessage());
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {
            int userCount = userService.checkEmail(emailDTO.getEmail());
            if (userCount > 0) {
                response.put("isDuplicateEmail", true);
                response.put("message", "이미 사용중인 이메일입니다.");
            } else {
                response.put("isDuplicateEmail", false);
                response.put("message", "사용 가능한 이메일입니다.");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "서버 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping(value = "/join", params = "phonenumber")
	public ResponseEntity<Map<String, Object>> checkPhonenumber(@Valid PhoneNumberDTO phoneNumberDTO, BindingResult result) {
		
        Map<String, Object> response = new HashMap<>();
        
        if (result.hasErrors()) {
        	response.put("isDuplicatePhonenumber", true);
	        response.put("message", result.getFieldError().getDefaultMessage());
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {
            int userCount = userService.checkPhonenumber(phoneNumberDTO.getPhonenumber());
            if (userCount > 0) {
                response.put("isDuplicatePhonenumber", true);
                response.put("message", "이미 사용중인 전화번호입니다.");
            } else {
                response.put("isDuplicatePhonenumber", false);
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
			response.put("redirectUrl", "/surveasy/main");
		} else {
			response.put("success", false);
			response.put("redirectUrl", "/surveasy/user/join");
		}

		return response;
	}
	
	@GetMapping(value = "/login")
    public String login() {
        return "/7.3login";
    }
	
	@PostMapping(value = "/login")
	public String loginCheckUsername(@RequestParam("username") String username) {
		Optional<User> user = userRepository.findByusername(username);
		
		if(user.isEmpty()) {
			System.out.println("회원없음");
		} else {
			System.out.println("로그인성공");
		}
		
		return "/1.main";
	}
}
