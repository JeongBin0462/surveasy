document.addEventListener('DOMContentLoaded', function() {
	const targetSelection = document.getElementById('targetSelection');
	const employeesInputDiv = document.getElementById('employeesInputDiv');
	const studentInputDiv = document.getElementById('studentInputDiv');

	function clearCheckboxes(element) {
		const checkboxes = element.querySelectorAll('input[type="checkbox"]');
		checkboxes.forEach(checkbox => {
			checkbox.checked = false;
		});
	}

	function hideElement(element) {
		element.style.visibility = 'hidden';
		clearCheckboxes(element);
	}

	function showElement(element) {
		element.style.visibility = 'visible';
	}

	hideElement(employeesInputDiv);
	hideElement(studentInputDiv);

	targetSelection.addEventListener('change', function() {
		const selectedValue = this.value;

		hideElement(employeesInputDiv);
		hideElement(studentInputDiv);

		if (selectedValue === 'employees') {
			showElement(employeesInputDiv);
		} else if (selectedValue === 'student') {
			showElement(studentInputDiv);
		}
	});

	targetSelection.dispatchEvent(new Event('change'));
});

document.addEventListener('DOMContentLoaded', function() {
	const form = document.querySelector('form');

	form.addEventListener('submit', function(event) {
		event.preventDefault();

		// 폼 데이터 수집
		const formData = new FormData(form);
		let dataObject = {};
		formData.forEach((value, key) => {
			dataObject[key] = value;
		});

		// JSON 문자열 변환 후 세션에 저장
		const jsonString = JSON.stringify(dataObject);
		sessionStorage.setItem("formData", jsonString);

		// 원하는 주소로 리디렉션
		window.location.href = "/STTP/makesurvey";
	});
});

function toggleCheckbox(divElement) {
	let checkbox = divElement.querySelector('.checkBox');
	if (checkbox) {
		checkbox.checked = !checkbox.checked;
	}
}