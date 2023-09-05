// 필수데이터 검사
function validateBasicData(section) {
    const basicDataFields = [
        'surveyno',
        'email',
        'phonenumber',
        'birth',
        'gender',
        'department',
        'position',
        'grade',
        'college',
        'region',
        'finaledu',
        'incomelevel'
    ];

    basicDataFields.forEach(fieldName => {
        const inputElement = section.querySelector(`[name="${fieldName}"]`);
        const parentDiv = inputElement ? inputElement.closest('div') : null;

        if (inputElement && (inputElement.value === '선택' || !inputElement.value.trim())) {
            parentDiv.classList.add('error-highlight');
        } else if (parentDiv) {
            parentDiv.classList.remove('error-highlight');
        }
    });
}

document.addEventListener('DOMContentLoaded', (event) => {
    const basicDataSection = document.querySelector('#question-require-div'); 
    if (basicDataSection) {
        validateBasicData(basicDataSection);

        const basicDataInputs = basicDataSection.querySelectorAll('input, select');
        basicDataInputs.forEach(input => {
            input.addEventListener('change', function() {
                validateBasicData(basicDataSection);
            });
        });
    }
});

function trimTextarea(textareaElement) {
	textareaElement.value = textareaElement.value.trim();
}

// 체크박스 제한
function enforceCheckboxLimit(questionDiv) {
	const checkboxes = questionDiv.querySelectorAll('input[type="checkbox"]');
	const maxElement = questionDiv.querySelector('.check-min-max-div div:nth-child(4)');

    if (!maxElement) {
        return;
    }
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
    if (!inputElement) {
        return;
    }

    const questionDiv = inputElement.closest('.question-div');
    if (!questionDiv) {
        return;
    }

    const isMandatory = questionDiv.querySelector('.survey-center-container div').textContent.trim() === '필수';

    const checkboxes = questionDiv.querySelectorAll('input[type="checkbox"]');
    const targetHighlightElement = questionDiv.querySelector('.question-content-text');
    if (!targetHighlightElement) {
        return;
    }

    targetHighlightElement.classList.remove('error-highlight');
	
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
    if (typeof questionNo === 'undefined' || questionNo === null) {
        alert('기본데이터를 입력하셔야 합니다.');
    } else {
        alert(`${questionNo}번 질문에 답변을 하여야 합니다.`);
    }
}

function formToJSON(form) {
    const formData = new FormData(form);
    
    let genderElement = document.querySelector('[name="gender"]');
    let gradeElement = document.querySelector('[name="grade"]');
    let genderValue = genderElement ? genderElement.value : null;
    let gradeValue = gradeElement ? gradeElement.value : null;
	
    let genderParse = null;
    let gradeParse = null;
	
    if (genderValue) {
        if (genderValue === '선택') {
            genderParse = null;
        } else if (genderValue === '남자') {
            genderParse = 0;
        } else if (genderValue === '여자') {
            genderParse = 1;
        }
    }
	
    if (gradeValue) {
        if (gradeValue === '1학년') {
            gradeParse = 1;
        } else if (gradeValue === '2학년') {
            gradeParse = 2;
        } else if (gradeValue === '3학년') {
            gradeParse = 3;
        } else if (gradeValue === '4학년') {
            gradeParse = 4;
        } else {
            gradeParse = null;
        }
    }
    
    const basicData = {
        surveyno: formData.get('surveyno'),
        email: formData.get('email'),
        phonenumber: formData.get('phonenumber'),
        birth: formData.get('birth'),
        gender: genderParse,
        department: formData.get('department') === '선택' ? null : formData.get('department'),
        position: formData.get('position') === '선택' ? null : formData.get('position'),
        grade: gradeParse,
        college: formData.get('college') === '선택' ? null : formData.get('college'),
        region: formData.get('region') === '선택' ? null : formData.get('region'),
        finaledu: formData.get('finaledu') === '선택' ? null : formData.get('finaledu'),
        incomelevel: formData.get('incomelevel') === '선택' ? null : formData.get('incomelevel')
    };

    const questionsData = {
        questions: {}
    };

    formData.forEach((value, key) => {
        if (key === 'surveyno') return;

        if (key.startsWith('question')) {
            if (!questionsData.questions[key]) {
                questionsData.questions[key] = {
                    type: null,
                    answerMap: {}
                };
            }

            const questionElement = form.querySelector(`[name="${key}"]`);
            if (questionElement) {
                const questionDiv = questionElement.closest('.question-div');
                questionsData.questions[key].type = questionDiv.getAttribute('data-type');
            }

            const answerKey = 'answer' + (Object.keys(questionsData.questions[key].answerMap).length + 1);
            questionsData.questions[key].answerMap[answerKey] = value;
        }
    });

    const surveyData = [];

    for (let [questionNo, data] of Object.entries(questionsData.questions)) {
        surveyData.push({
            questionno: parseInt(questionNo.replace('question', '')),
            type: data.type,
            answerMap: data.answerMap
        });
    }

    return JSON.stringify({
        require: basicData,
        surveySubmits: surveyData
    });
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

    // hidden input에서 값 가져오기
    const subjectValue = document.querySelector('input[name="subject"]').value;
    const surveynoValue = document.querySelector('input[name="surveyno"]').value;
    const urlValue = 'http://localhost:8080/surveasy/survey/' + document.querySelector('input[name="url"]').value + '?no=' + surveynoValue;

    // 임시 폼을 생성
    const form = document.createElement('form');
    document.body.appendChild(form);
    form.method = 'post';
    form.action = '/surveasy/survey/success';

    // subject 폼에 추가
    const subjectInput = document.createElement('input');
    subjectInput.type = 'hidden';
    subjectInput.name = 'subject';
    subjectInput.value = subjectValue; 
    form.appendChild(subjectInput);

    // URL 폼에 추가
    const urlInput = document.createElement('input');
    urlInput.type = 'hidden';
    urlInput.name = 'url';
    urlInput.value = urlValue;
    form.appendChild(urlInput);
    
    // surveyno 폼에 추가
    const surveynoInput = document.createElement('input');
    surveynoInput.type = 'hidden';
    surveynoInput.name = 'surveyno';
    surveynoInput.value = surveynoValue;
    form.appendChild(surveynoInput);

    // 폼을 제출하여 데이터 전송
    form.submit();
})
    .catch((error) => {
        console.error('Error:', error);
    });
}
