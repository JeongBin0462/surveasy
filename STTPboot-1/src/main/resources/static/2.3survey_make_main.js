let newSurveyBtns = document.getElementsByClassName("newSurveyBtn");
let newSurveyArea = document.getElementById("survey-question");

function createForm() {
    let newForm = document.createElement("form");
    formCount++;
    newForm.setAttribute("data-form-number", formCount);
    newForm.id = "answerForm";

		// 질문 n:
		let questionLabel = document.createElement("label");
		questionLabel.setAttribute("for", "input" + formCount);
		questionLabel.innerText = "질문 " + formCount;

		// (질문 내용 text)
		let questionContent = document.createElement("input"); // 이름을 questionContent으로 변경
		questionContent.setAttribute("type", "text");
		questionContent.setAttribute("id", "input" + formCount);
		questionContent.setAttribute("name", "input" + formCount);
        newForm.appendChild(questionLabel);
		newForm.appendChild(questionContent);
		newForm.appendChild(answerTypeLabel);

    return newForm;
}

function createCheckboxRadioListener(checkboxRadio) {
    checkboxRadio.addEventListener("change", function() {
        if (checkboxRadio.checked) {
            // 개수 선택(20개 이하)
				// input type number

				// 최소 응답 개수
				// input type number

				// 최대 응답 개수
				// input type number
        }
    });
}

function createRadioRadioListener(radioRadio) {
    radioRadio.addEventListener("change", function() {
        if (radioRadio.checked) {
            // 1. 각 라디오
				// 2점척도(예/아니오)

				// 5점척도

				// 7점척도

				// 개수선택(7개이하)
        }
    });
}

function createDescriptiveRadioListener(descriptiveRadio) {
    descriptiveRadio.addEventListener("change", function() {
        if (descriptiveRadio.checked) {
            // Your logic here ...
        }
    });
}

let formCount = 0;
for (let i = 0; i < newSurveyBtns.length; i++) {
    newSurveyBtns[i].addEventListener("click", function(e) {
        let newForm = createForm();

        // Creating listeners for radio buttons:
        let checkboxRadio = newForm.querySelector(`#checkboxRadio${formCount}`);
        let radioRadio = newForm.querySelector(`#radioRadio${formCount}`);
        let descriptiveRadio = newForm.querySelector(`#descriptiveRadio${formCount}`);

        createCheckboxRadioListener(checkboxRadio);
        createRadioRadioListener(radioRadio);
        createDescriptiveRadioListener(descriptiveRadio);

        createDeleteButton(newForm);

        newSurveyArea.append(newForm);
        newForm.scrollIntoView({ behavior: "smooth" });
    });
}

// 리모컨 로직
document.addEventListener("DOMContentLoaded", initializeValues);

function initializeValues() {
	let answerType = sessionStorage.getItem("answerTypeSelect") || "선택안함";
	let inputRequirement =
		sessionStorage.getItem("inputRequirementSelect") || "필수입력";

	document.getElementById("answerTypeSelect").value = answerType;
	document.getElementById("inputRequirementSelect").value = inputRequirement;
}

function updateSessionStorage(selectElement) {
	let key = selectElement.id;
	let value = selectElement.value;

	sessionStorage.setItem(key, value);
	console.log("SessionStorage updated with key:", key, "and value:", value);
}

// 설정화면 로직
function showScreen(screenId) {
	// 모든 화면을 숨김
	const screens = document.querySelectorAll(".screen");
	screens.forEach((screen) => {
		screen.classList.remove("active");
	});

	// 선택한 화면만 표시
	const selectedScreen = document.getElementById(screenId);
	selectedScreen.classList.add("active");
}
