// 답변 개수 확인 함수
function checkAnswerCount(type, newAnswerBtn) {
  if (currentAnswerCounts[type] > maxAnswerCounts[type]) {
    newAnswerBtn.style.display = "none";
  } else {
    newAnswerBtn.style.display = "block";
  }
}

// 현재 답변 개수를 저장하는 객체
const currentAnswerCounts = {
  체크박스: 0,
  라디오버튼: 0,
  서술형: 0,
};

let maxAnswerCounts = {
  체크박스: 20,
  라디오버튼: 7,
  서술형: 1,
};

let newSurveyBtns = document.getElementsByClassName("newSurveyBtn");

let newSurveyArea = document.getElementById("survey-question");

// 질문 번호 카운트용
let formCount1 = 0;

// 설문타입변경리스너
function surveyTypeChange(event) {
  let selectElem = event.currentTarget;
  let topDiv = selectElem.parentElement;

  // 모든 답변을 제거
  let newAnswerBtn = topDiv.querySelector(".btn_addAnswer");
  let answers = topDiv.querySelectorAll(".answerContainer");
  answers.forEach((answer) => topDiv.removeChild(answer));

  // 새로운 유형에 따라 답변 개수 확인
  let selectedText = selectElem.options[selectElem.selectedIndex].text;
  
  // currentAnswerCounts의 해당 타입의 개수를 0으로 초기화
  let currentAnswerCounts = JSON.parse(topDiv.dataset.answerCounts); 
  currentAnswerCounts[selectedText] = 0;
  
  // 변경된 currentAnswerCounts를 topDiv의 data-answerCounts 속성에 저장
  topDiv.dataset.answerCounts = JSON.stringify(currentAnswerCounts);

  // 버튼의 표시 상태를 초기화
  newAnswerBtn.style.display = "block";
  checkAnswerCount(selectedText, newAnswerBtn);

  // 요소들이 topDiv에 이미 존재하는지 확인
  let minLabelExists = topDiv.querySelector("label.minLabel");
  let maxLabelExists = topDiv.querySelector("label.maxLabel");
  let minRequiredExists = topDiv.querySelector("input.input_minRequired");
  let maxRequiredExists = topDiv.querySelector("input.input_maxRequired");

  if (selectedText != "체크박스") {
    if (minLabelExists) topDiv.removeChild(minLabelExists);
    if (maxLabelExists) topDiv.removeChild(maxLabelExists);
    if (minRequiredExists) topDiv.removeChild(minRequiredExists);
    if (maxRequiredExists) topDiv.removeChild(maxRequiredExists);
    // if (answerDiv) answerDiv.remove();
  } else {
    // 요소들이 이미 존재하지 않는 경우에만 추가
    if (!minLabelExists) {
      var minLabel = document.createElement("label");
      minLabel.textContent = "최소 선택 가능 개수: ";
      minLabel.className = "minLabel";
      topDiv.appendChild(minLabel);
    }

    if (!minRequiredExists) {
      var minRequired = document.createElement("input");
      minRequired.type = "number";
      minRequired.className = "input_minRequired";
      minRequired.min = "1";
      minRequired.max = "20";
      topDiv.appendChild(minRequired);
    }

    if (!maxLabelExists) {
      var maxLabel = document.createElement("label");
      maxLabel.textContent = "최대 선택 가능 개수: ";
      maxLabel.className = "maxLabel";
      topDiv.appendChild(maxLabel);
    }

    if (!maxRequiredExists) {
      var maxRequired = document.createElement("input");
      maxRequired.type = "number";
      maxRequired.className = "input_maxRequired";
      maxRequired.min = "1";
      maxRequired.max = "20";
      topDiv.appendChild(maxRequired);
    }
  }
}

// 답변추가리스너
function addAnswer(event) {
  let topDiv = event.currentTarget.parentElement;
  let currentAnswerCounts = JSON.parse(topDiv.dataset.answerCounts);  // 가져오기
  let selectElem = topDiv.querySelector(".answerTypeCombo");

  // 현재의 답변 유형 텍스트 추출
  let selectedIndex = selectElem.selectedIndex;
  let selectedText = selectElem.options[selectedIndex].text;

  currentAnswerCounts[selectedText]++;  // 수정하기
  console.log("answerCounts 상태: " + currentAnswerCounts[selectedText]);

  topDiv.dataset.answerCounts = JSON.stringify(currentAnswerCounts);  // 저장하기
  checkAnswerCount(selectedText, event.currentTarget);

  // 답변 생성 함수 호출
  createAnswer(
    topDiv,
    formCount1,
    selectedText,  // 수정된 부분
    selectElem,
    event.currentTarget
  );
}

// newSurveyBtns는 여러개 있음(본문 1개, 리모컨 1개)
for (let i = 0; i < newSurveyBtns.length; i++) {
  // 질문 추가 리스너
  newSurveyBtns[i].addEventListener("click", function (e) {
    formCount1++;

    // 리모컨 요소
    let answerTypeSelectValue = document.getElementById("answerTypeSelect").value;

    // 설문 질문 컨테이너 생성
    var topDiv = document.createElement("div");
    topDiv.className = "topDiv";

    var topDivAnswerCounts = {
      체크박스: 0,
      라디오버튼: 0,
      서술형: 0,
    };
    
    topDiv.dataset.answerCounts = JSON.stringify(topDivAnswerCounts);

    // 질문 레이블 생성
    let newQuestionLabel = document.createElement("p");
    newQuestionLabel.innerText = "Q" + formCount1 + ".";
    newQuestionLabel.className = "input_questionText";

    // 질문 입력 필드 생성
    let inputQuestion = document.createElement("input");
    inputQuestion.type = "text";
    inputQuestion.className = "inputQuestion";

    // 답변 추가 버튼 생성
    let newAnswerBtn = document.createElement("button");
    newAnswerBtn.type = "button";
    newAnswerBtn.innerText = "+ 답변 추가";
    newAnswerBtn.className = "btn_addAnswer";
    newAnswerBtn.setAttribute("data-question-num", formCount1);

    // 질문 번호
    // let questionNum = newAnswerBtn.getAttribute("data-question-num");

    // 새로운 select 요소 생성(답변유형 콤보박스)
    let selectElem = document.createElement("select");
    selectElem.id = "selectElem" + formCount1;
    selectElem.className = "answerTypeCombo";

    // option 0
    let option0 = document.createElement("option");
    option0.text = "답변유형 선택";
    selectElem.appendChild(option0);

    // option 1
    let option1 = document.createElement("option");
    option1.value = "check";
    option1.text = "체크박스";
    selectElem.appendChild(option1);

    // option 2
    let option2 = document.createElement("option");
    option2.value = "rad";
    option2.text = "라디오버튼";
    selectElem.appendChild(option2);

    // option 3
    let option3 = document.createElement("option");
    option3.value = "desc";
    option3.text = "서술형";
    selectElem.appendChild(option3);

    switch (answerTypeSelectValue) {
      case "체크박스":
        selectElem.value = "check";
        break;
      case "라디오버튼":
        selectElem.value = "rad";
        break;
      case "서술형":
        selectElem.value = "desc";
        break;
      default:
        break;
    }

    // topDiv에 요소들 추가
    topDiv.appendChild(newQuestionLabel);
    topDiv.appendChild(inputQuestion);
    topDiv.appendChild(selectElem);
    topDiv.appendChild(newAnswerBtn);

    // topDiv와 bottomDiv를 감싸는 totalDiv 생성
    let totalDiv = document.createElement("div");
    totalDiv.className = "questionContainer";

    // bottomDiv 생성
    let inputRequirement = document.getElementById("inputRequireSelect").value;
    let isMandatory = inputRequirement === "필수입력" ? true : false;
    let bottomDiv = createBottomDiv(totalDiv, isMandatory);

    // totalDiv에 topDiv, bottomDiv 추가
    totalDiv.appendChild(topDiv);
    totalDiv.appendChild(bottomDiv);

    // newSurveyArea에 totalDiv 추가
    newSurveyArea.append(totalDiv);

    // topDiv 생성시 스크롤 포커싱
    totalDiv.scrollIntoView({ behavior: "smooth" });

    // 답변유형 콤보박스 리스너
    selectElem.addEventListener("change", surveyTypeChange);

    // 답변 추가 버튼 리스너
    newAnswerBtn.addEventListener("click", addAnswer);
  });
}

// 답변 생성 함수
function createAnswer(
  topDiv,
  questionNum,
  selectedText,
  selectElem,
  newAnswerBtn
) {
  let currentAnswerCounts = JSON.parse(topDiv.dataset.answerCounts);

  if (currentAnswerCounts[selectedText] > maxAnswerCounts[selectedText]) {
    console.log("최대 답변 개수에 도달했습니다.");
    return;
  }

  let answerDiv = document.createElement("div");
  answerDiv.className = "answerContainer";

  switch (selectedText) {
    case "체크박스":
      // checkbox 요소 생성
      var checkboxElement = document.createElement("input");
      checkboxElement.type = "checkbox";
      checkboxElement.name = "check" + questionNum;
      checkboxElement.className = "answerCheckbox";
      answerDiv.appendChild(checkboxElement);
      defaultAnswerContent(selectElem, topDiv, answerDiv, newAnswerBtn);
      break;

    case "라디오버튼":
      var radioElement = document.createElement("input");
      radioElement.type = "radio";
      radioElement.name = "rad" + questionNum;
      radioElement.className = "answerRadio";
      answerDiv.appendChild(radioElement);
      defaultAnswerContent(selectElem, topDiv, answerDiv, newAnswerBtn);
      break;

    case "서술형":
      defaultAnswerContent(selectElem, topDiv, answerDiv, newAnswerBtn);
      break;
  }

  currentAnswerCounts[selectedText]++;
}


// 답변 요소 공통 생성 메소드
function defaultAnswerContent(selectElem, topDiv, answerDiv, newAnswerBtn) {
  // input type="text" 요소 생성
  var inputElement = document.createElement("input");
  inputElement.type = "text";
  inputElement.className = "answerInput";

  // button 요소 생성
  var buttonElement = document.createElement("button");
  buttonElement.type = "button";
  buttonElement.innerText = "X";
  buttonElement.className = "btn_answerDelete";

  // 답변 삭제 버튼 리스너
  buttonElement.addEventListener("click", function () {
    console.log("Delete button clicked"); // 로그 추가
    console.log("topDiv:", topDiv); // 로그 추가
    console.log("answerDiv:", answerDiv); // 로그 추가

    let selectedIndex = selectElem.selectedIndex;
    let selectedText = selectElem.options[selectedIndex].text;
    currentAnswerCounts[selectedText]--;
    checkAnswerCount(selectedText, newAnswerBtn);
    console.log("answerCounts 상태: " + answerCounts[selectedText]);
    topDiv.removeChild(answerDiv);
  });

  // 요소들을 div에 추가
  answerDiv.appendChild(inputElement);
  answerDiv.appendChild(buttonElement);

  topDiv.appendChild(answerDiv);
}

// let count = 0;
// bottomDiv 생성 함수
function createBottomDiv(totalDiv, isMandatory) {
  // count++;

  // bottomDiv 선언
  let bottomDiv = document.createElement("div");
  bottomDiv.className = "bottomDiv";

  // 필수여부 체크박스
  let mandatoryCheck = document.createElement("input");
  mandatoryCheck.className = "ckeck_mandatoryQuestion";
  mandatoryCheck.type = "checkbox";
  mandatoryCheck.name = "mandatoryCheck";
  mandatoryCheck.value = "mandatoryCheck";
  mandatoryCheck.innerText = "필수여부";
  mandatoryCheck.id = "ckeck_mandatoryQuestion_id" + formCount1;

  let mandatoryLabel = document.createElement("label");
  mandatoryLabel.htmlFor = "ckeck_mandatoryQuestion_id" + formCount1; // 위에서 지정한 id값을 사용
  mandatoryLabel.innerText = "필수여부";

  // 필수여부 체크박스 상태 설정
  mandatoryCheck.checked = isMandatory;

  // 문항 삭제버튼
  let deleteBtn = document.createElement("button");
  deleteBtn.className = "btn_deleteQuestion";
  deleteBtn.type = "button";
  deleteBtn.value = "delete";
  deleteBtn.innerText = "삭제";

  // 문항 삭제 리스너
  deleteBtn.addEventListener("click", function () {
    // totalDiv를 부모 노드에서 삭제
    totalDiv.parentNode.removeChild(totalDiv);

    rearrangeQuestionNumbers();
  });

  bottomDiv.appendChild(mandatoryCheck);
  bottomDiv.appendChild(mandatoryLabel);
  bottomDiv.appendChild(deleteBtn);

  return bottomDiv;
}

// 문항 번호 재정렬
function rearrangeQuestionNumbers() {
  // 모든 문항 레이블을 선택합니다.
  let questionLabels = document.querySelectorAll(".input_questionText");

  // 각 문항 레이블에 대해 순서대로 번호를 업데이트합니다.
  for (let i = 0; i < questionLabels.length; i++) {
    questionLabels[i].innerText = "Q" + (i + 1) + ".";
  }

  // formCount1도 업데이트합니다.
  formCount1 = questionLabels.length;
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// : 버튼 시작점

// // 리모컨 로직
// document.addEventListener("DOMContentLoaded", initializeValues);

// function initializeValues() {
//   let answerType = sessionStorage.getItem("answerTypeSelect") || "선택안함";
//   let inputRequirement =
//     sessionStorage.getItem("inputRequirementSelect") || "필수입력";

//   document.getElementById("answerTypeSelect").value = answerType;
//   document.getElementById("inputRequirementSelect").value = inputRequirement;
// }

// function updateSessionStorage(selectElement) {
//   let key = selectElement.id;
//   let value = selectElement.value;

//   sessionStorage.setItem(key, value);
//   console.log("SessionStorage updated with key:", key, "and value:", value);
// }

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
