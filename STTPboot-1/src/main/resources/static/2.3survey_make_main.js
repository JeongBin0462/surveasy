let newSurveyBtns = document.getElementsByClassName("newSurveyBtn");
let newSurveyArea = document.getElementById("survey-question");

let formCount = 0;
for (let i = 0; i < newSurveyBtns.length; i++) {
  newSurveyBtns[i].addEventListener("click", function (e) {
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

    // 답변 유형
    let answerTypeLabel = document.createElement("label");
    answerTypeLabel.setAttribute("for", "checkbox" + formCount);
    answerTypeLabel.innerText = "답변 유형: ";

    // (답변 유형[체크박스 / 라디오 / 서술형])
    // 체크박스 라디오 버튼 생성
    let checkboxRadio = document.createElement("input");
    checkboxRadio.setAttribute("type", "radio");
    checkboxRadio.setAttribute("id", "checkboxRadio" + formCount);
    checkboxRadio.setAttribute("name", "answerType" + formCount);
    checkboxRadio.setAttribute("value", "체크박스" + formCount);

    let checkboxLabel = document.createElement("label");
    checkboxLabel.setAttribute("for", "checkboxRadio" + formCount);
    checkboxLabel.innerText = "체크박스";

    // 일반 라디오 버튼 생성
    let radioRadio = document.createElement("input");
    radioRadio.setAttribute("type", "radio");
    radioRadio.setAttribute("id", "radioRadio" + formCount);
    radioRadio.setAttribute("name", "answerType" + formCount);
    radioRadio.setAttribute("value", "라디오" + formCount);

    let radioLabel = document.createElement("label");
    radioLabel.setAttribute("for", "radioRadio" + formCount);
    radioLabel.innerText = "라디오";

    // 서술형 라디오 버튼 생성
    let descriptiveRadio = document.createElement("input");
    descriptiveRadio.setAttribute("type", "radio");
    descriptiveRadio.setAttribute("id", "descriptiveRadio" + formCount);
    descriptiveRadio.setAttribute("name", "answerType" + formCount);
    descriptiveRadio.setAttribute("value", "서술형" + formCount);

    let descriptiveLabel = document.createElement("label");
    descriptiveLabel.setAttribute("for", "descriptiveRadio" + formCount);
    descriptiveLabel.innerText = "서술형";

    // 삭제 버튼 생성
    let deleteButton = document.createElement("button");
    deleteButton.innerText = "삭제";
    // 삭제 버튼에 추가될 이벤트 리스너
    deleteButton.addEventListener("click", function(event) {
        event.stopPropagation();
        event.preventDefault();

        // 해당 form 요소 삭제
        newForm.remove();

        // 이후의 모든 form 요소들의 이름 및 아이디 갱신
        for(let i = formCount; i > newForm.dataset.formNumber; i--) {
            const formToUpdate = document.querySelector(`[data-form-number='${i}']`);
            if (formToUpdate) {
                // 이름 및 아이디 갱신
                formToUpdate.setAttribute('data-form-number', i - 1);
                
                const questionLabel = formToUpdate.querySelector(`[for='input${i}']`);
                if(questionLabel) {
                    questionLabel.setAttribute("for", "input" + (i - 1));
                    questionLabel.innerText = "질문 " + (i - 1);
                }

                const questionContent = formToUpdate.querySelector(`#input${i}`);
                if(questionContent) {
                    questionContent.setAttribute("id", "input" + (i - 1));
                    questionContent.setAttribute("name", "input" + (i - 1));
                }

                // 다른 요소들도 비슷한 방법으로 갱신 (라디오 버튼, 라벨 등)
            }
        }

        // formCount 값 감소
        formCount--;
    });
    
    // 체크박스 라디오 버튼의 이벤트 리스너
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

    // 라디오 라디오 버튼의 이벤트 리스너
    radioRadio.addEventListener("change", function() {
        if (radioRadio.checked) {
            // 1. 각 라디오
            // 2점척도(예/아니오)

            // 5점척도

            // 7점척도

            // 개수선택(7개이하)

            // 2. 라디오가 선택되면 

        }
    });

    // 서술형 라디오 버튼의 이벤트 리스너
    descriptiveRadio.addEventListener("change", function() {
        if (descriptiveRadio.checked) {
            // 입력칸 1개 
        }
    });

    // form에 label과 input 요소 추가
    newForm.appendChild(questionLabel);
    newForm.appendChild(questionContent);
    newForm.appendChild(answerTypeLabel);

    newForm.appendChild(checkboxRadio);
    newForm.appendChild(checkboxLabel);
    newForm.appendChild(radioRadio);
    newForm.appendChild(radioLabel);
    newForm.appendChild(descriptiveRadio);
    newForm.appendChild(descriptiveLabel);

    // newForm에 삭제 버튼 추가
    newForm.appendChild(deleteButton);

    newSurveyArea.append(newForm);

    // 새로 생성된 form 요소로 스크롤 이동
    newForm.scrollIntoView({ behavior: "smooth" });
  });
}


// 리모컨 로직
document.addEventListener("DOMContentLoaded", initializeValues);

function initializeValues() {
  let answerType = sessionStorage.getItem('answerTypeSelect') || '선택안함';
  let inputRequirement = sessionStorage.getItem('inputRequirementSelect') || '필수입력';

  document.getElementById('answerTypeSelect').value = answerType;
  document.getElementById('inputRequirementSelect').value = inputRequirement;
}

function updateSessionStorage(selectElement) {
  let key = selectElement.id;
  let value = selectElement.value;

  sessionStorage.setItem(key, value);
  console.log('SessionStorage updated with key:', key, 'and value:', value);
}


// 설정화면 로직
function showScreen(screenId) {
    const screens = document.querySelectorAll('.screen');
    screens.forEach(screen => {
        screen.classList.remove('active');
    });

    const selectedScreen = document.getElementById(screenId);
    selectedScreen.classList.add('active');
}