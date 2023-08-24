window.onload = function () {
  
  let employeesBox = document.getElementById("input-employees-box");
  let studentBox = document.getElementById("input-student-box");
  
  employeesBox.classList.add('hidden');
  studentBox.classList.add('hidden');

  document
    .getElementById("input-job")
    .addEventListener("change", function () {
      let selectedJob = this.value;
      
      employeesBox.classList.add('hidden');
      studentBox.classList.add('hidden');

      if (selectedJob === "직장인") {
        employeesBox.classList.remove('hidden');
      } else if (selectedJob === "학생") {
        studentBox.classList.remove('hidden');
      }
    });
};

document.getElementById("checkUsernameButton").addEventListener("click", function(event) {
	event.preventDefault();
	document.querySelector('#usernameError').textContent = "";
		document.querySelector('#passwordError').textContent = "";
		document.querySelector('#emailError').textContent = "";
		document.querySelector('#phonenumberError').textContent = "";
    const username = document.getElementById("usernameInput").value;

    fetch(`././join?username=${username}`, {
        method: "GET",
    })
    .then(response => {
		return response.json();
	})
    .then(data => {
        if (data.isDuplicateUsername) {
            document.getElementById("usernameError").textContent = data.message;
        } else {
            document.getElementById("usernameError").textContent = data.message;
        }
    })
});

document.getElementById("checkEmailButton").addEventListener("click", function(event) {
	event.preventDefault();
	document.querySelector('#usernameError').textContent = "";
		document.querySelector('#passwordError').textContent = "";
		document.querySelector('#emailError').textContent = "";
		document.querySelector('#phonenumberError').textContent = "";
    const email = document.getElementById("emailInput").value;

    fetch(`././join?email=${email}`, {
        method: "GET"
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        if (data.isDuplicateEmail) {
            document.getElementById("emailError").textContent = data.message;
        } else {
            document.getElementById("emailError").textContent = data.message;
        }
    })
});


document.getElementById("checkPhonenumberButton").addEventListener("click", function(event) {
	event.preventDefault();
	document.querySelector('#usernameError').textContent = "";
		document.querySelector('#passwordError').textContent = "";
		document.querySelector('#emailError').textContent = "";
		document.querySelector('#phonenumberError').textContent = "";
    const phonenumber = document.getElementById("phonenumberInput").value;

    fetch(`././join?phonenumber=${phonenumber}`, {
        method: "GET",
    })
    .then(response => {
		return response.json();
	})
    .then(data => {
        if (data.isDuplicatePhonenumber) {
            document.getElementById("phonenumberError").textContent = data.message;
        } else {
            document.getElementById("phonenumberError").textContent = data.message;
        }
    })
});

document.getElementById('checkPasswordSame').addEventListener('click', function(event) {
    event.preventDefault();
    document.querySelector('#usernameError').textContent = "";
		document.querySelector('#passwordError').textContent = "";
		document.querySelector('#emailError').textContent = "";
		document.querySelector('#phonenumberError').textContent = "";

    const password = document.querySelector('input[name="password"]').value;
    const passwordCheck = document.querySelector('input[name="passwordCheck"]').value;

    const passwordError = document.getElementById('passwordError');

    if (password === passwordCheck) {
        passwordError.textContent = "확인되었습니다";
    } else {
        passwordError.textContent = "서로 다른 비밀번호를 입력하였습니다";
    }
});


function getValueOrDefault(selector, defaultValue = null) {
    const value = document.querySelector(selector).value;
    return value === '선택' ? defaultValue : value;
}

function submitForm() {
	let genderValue = document.querySelector('[name="gender"]').value;
	let result;

	if (genderValue == '선택') {
   	 result = null;
	} else if (genderValue === '남자') {
    	result = true;
	} else if (genderValue === '여자') {
	    result = false;
	} else {
    	result = null;
	}
	
    const formData = {
        username: document.querySelector('[name="username"]').value,
        password: document.querySelector('[name="password"]').value,
        passwordCheck: document.querySelector('[name="passwordCheck"]').value,
        email: document.querySelector('[name="email"]').value,
        phonenumber: document.querySelector('[name="phonenumber"]').value,
        age: document.querySelector('[name="age"]').value,
        gender: result,
        finaledu: getValueOrDefault('[name="finaledu"]'),
        job: getValueOrDefault('[name="job"]'),
        department: getValueOrDefault('[name="department"]'),
        position: getValueOrDefault('[name="position"]'),
        grade: getValueOrDefault('[name="grade"]'),
        college: getValueOrDefault('[name="college"]'),
        region: getValueOrDefault('[name="region"]'),
        incomelevel: getValueOrDefault('[name="incomelevel"]')
    };

    fetch('/STTP/user/join', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(formData)
})
.then(response => {
    return response.json();
})
.then(data => {
    if (data.success) {
        alert('회원가입 성공!');
        window.location.href = data.redirectUrl;
    } else {
        document.querySelector('#usernameError').textContent = "";
		document.querySelector('#passwordError').textContent = "";
		document.querySelector('#emailError').textContent = "";
		document.querySelector('#phonenumberError').textContent = "";

if (data.errors && data.errors.length > 0) {
    data.errors.forEach(error => {
        if (error.includes("사용자 이름은 한글, 영어, 숫자를 이용하여 5~20자로 구성되어야 합니다.")) {
            document.querySelector('#usernameError').textContent = error;
        }
        if (error.includes("비밀번호는 5~15자의 길이를 가져야 하며, 특수문자, 숫자, 소문자, 대문자를 모두 포함해야 합니다.")) {
            document.querySelector('#passwordError').textContent = error;
        } else if (error.includes("입력한 비밀번호가 서로 다릅니다")) {
			 document.querySelector('#passwordError').textContent = error;
		}
        if (error.includes("유효한 이메일 형식이 아닙니다.")) {
            document.querySelector('#emailError').textContent = error;
        }
        if (error.includes("유효한 전화번호 형식이 아닙니다.")) {
            document.querySelector('#phonenumberError').textContent = error;
        }
    });
} else {
            alert('회원가입 실패: 중복확인을 부탁드립니다.');
        }
    }
})
}

document.getElementById('registrationForm').addEventListener('submit', function(event) {
    event.preventDefault();

    submitForm();
});