window.onload = function () {
  document
    .getElementById("input-job")
    .addEventListener("change", function () {
      let selectedJob = this.value;
      
      let employeesBox = document.getElementById("input-employees-box");
      let studentBox = document.getElementById("input-student-box");
      
      employeesBox.classList.add('hidden');
      studentBox.classList.add('hidden');

      if (selectedJob === "직장인") {
        employeesBox.classList.remove('hidden');
      } else if (selectedJob === "학생") {
        studentBox.classList.remove('hidden');
      }
    });
};

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
        alert('회원가입 실패: ' + data.message);
    }
})
.catch(error => {
    console.error('Error:', error);
    alert('회원가입 중 오류 발생!');
});
}

document.getElementById('registrationForm').addEventListener('submit', function(event) {
    event.preventDefault();

    submitForm();
});