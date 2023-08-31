function trimTextarea(textareaElement) {
	textareaElement.value = textareaElement.value.trim();
}

// 체크박스 제한
function enforceCheckboxLimit(questionDiv) {
	const checkboxes = questionDiv.querySelectorAll('input[type="checkbox"]');
	const max = parseInt(questionDiv.querySelector('.check-min-max-div div:nth-child(4)').textContent, 10);

	const checkedCount = [...checkboxes].filter(cb => cb.checked).length;

	if (checkedCount >= max) {
		checkboxes.forEach(checkbox => {
			if (!checkbox.checked) {
				checkbox.disabled = true;
			}
		});
	} else {
		checkboxes.forEach(checkbox => {
			checkbox.disabled = false;
		});
	}
}

document.addEventListener('DOMContentLoaded', (event) => {
	// 입력 항목의 변화를 감지
	const inputs = document.querySelectorAll('.question-div input, .question-div textarea');

	inputs.forEach(input => {
		input.addEventListener('change', function() {
			validateField(this);
			enforceCheckboxLimit(this.closest('.question-div'));
		});

		if (input.tagName.toLowerCase() === 'textarea') {
			input.addEventListener('input', function() {
				validateField(this);
				trimTextarea(this);
			});
		}
	});

	// 페이지 로딩 시 유효성 검사
	const allQuestions = document.querySelectorAll('.question-div .survey-center-container div');
	const mandatoryQuestions = [...allQuestions].filter(div => div.textContent.trim() === '필수');
	mandatoryQuestions.forEach(questionDiv => {
		const inputElement = questionDiv.closest('.question-div').querySelector('input, textarea');
		validateField(inputElement);
	});
});

function validateField(inputElement) {
	const questionDiv = inputElement.closest('.question-div');
	const isMandatory = questionDiv.querySelector('.survey-center-container div').textContent.trim() === '필수';

	const checkboxes = questionDiv.querySelectorAll('input[type="checkbox"]');
	const targetHighlightElement = questionDiv.querySelector('.question-content-text');
	targetHighlightElement.classList.remove('error-highlight');

	if (checkboxes.length) {
		const checkedCount = [...checkboxes].filter(cb => cb.checked).length;

		const min = parseInt(questionDiv.querySelector('.check-min-max-div div:nth-child(2)').textContent, 10);
		const max = parseInt(questionDiv.querySelector('.check-min-max-div div:nth-child(4)').textContent, 10);

		if (isMandatory) {
			if (checkedCount == 0 || !(checkedCount >= min && checkedCount <= max)) {
				targetHighlightElement.classList.add('error-highlight');
			}
		} else {
			if (!(checkedCount == 0 || (checkedCount >= min && checkedCount <= max))) {
				targetHighlightElement.classList.add('error-highlight');
			}
		}
	} else if (inputElement.type === 'radio' && !inputElement.checked) {
		targetHighlightElement.classList.add('error-highlight');
	} else if (inputElement.tagName.toLowerCase() === 'textarea') {
		if (!inputElement.value.trim() && isMandatory) {
			targetHighlightElement.classList.add('error-highlight');
		} else {
			targetHighlightElement.classList.remove('error-highlight');
		}
	}
}

function showError(questionNo) {
	alert(`${questionNo}번 질문에 답변을 하여야 합니다.`);
}

function formToJSON(form) {
    const formData = new FormData(form);
    const surveyData = [];
    const obj = {
        surveyno: formData.get('surveyno'),
        questions: {}
    };

    formData.forEach((value, key) => {
        if (key === 'surveyno') return;

        if (key.startsWith('question')) {
            if (!obj.questions[key]) {
                obj.questions[key] = {
                    type: null,
                    answerMap: {}
                };
            }

            const questionElement = form.querySelector(`[name="${key}"]`);
            if (questionElement) {
                const questionDiv = questionElement.closest('.question-div');
                obj.questions[key].type = questionDiv.getAttribute('data-type');
            }

            const answerKey = 'answer' + (Object.keys(obj.questions[key].answerMap).length + 1);
            obj.questions[key].answerMap[answerKey] = value;
        }
    });

    for (let [questionNo, data] of Object.entries(obj.questions)) {
        surveyData.push({
            surveyno: obj.surveyno,
            questionno: parseInt(questionNo.replace('question', '')),
            type: data.type,
            answerMap: data.answerMap
        });
    }

    return JSON.stringify(surveyData);
}

function submitData(event) {
    event.preventDefault();

    // 필수 항목 검증
    const mandatoryFieldsValid = [...document.querySelectorAll('.question-div')]
        .every(questionDiv => !questionDiv.querySelector('p span:first-child').classList.contains('error-highlight'));

    if (!mandatoryFieldsValid) {
        return;
    }

    const errorElements = document.querySelectorAll('.error-highlight');

    if (errorElements.length > 0) {
        const firstErrorElement = errorElements[0];
        const questionNo = firstErrorElement.closest('.question-div').dataset.questionNo;
        showError(questionNo);
        return;
    }

    const formElement = document.querySelector('form');
    const json = formToJSON(formElement);

    console.log(json);

    fetch('/surveasy/survey/show', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: json
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}
