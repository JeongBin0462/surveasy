// 질문 추가 버튼
let newSurveyBtns = document.getElementsByClassName("newSurveyBtn");

// 질문이 들어가는 영역
let newSurveyArea = document.getElementById("survey-question");

// 현재 답변 개수를 저장하는 객체
const answerCounts = {
  체크박스: 0,
  라디오버튼: 0,
  서술형: 0,
};

let maxAnswerCounts = {
  체크박스: 20,
  라디오버튼: 7,
  서술형: 1,
};

// 답변 개수 확인 함수
function checkAnswerCount(type, newAnswerBtn) {
  if (answerCounts[type] > maxAnswerCounts[type]) {
    newAnswerBtn.style.display = "none";
  } else {
    newAnswerBtn.style.display = "block";
  }
}

// 질문 번호 카운트용
let formCount1 = 0;

// 문항 생성 함수
function addQuestion(answerSetting, selectRequire) {
  formCount1++;
  let totalDiv = document.createElement("div");
  totalDiv.className = "totalDiv";

  // 1. 질문div
  let questionDiv = createQuestionDiv(formCount1);

  // 2. 답변 설정 div
  let answerSettingDiv = createAnswerSettingDiv(formCount1);

  // 3. 답변div
  let answerDiv = createAnswerDiv(formCount1, answerSetting);

  // 3.5. 답변div를 담는 답변들div
  let answersDiv = document.createElement("div");
  answersDiv.className = "answersDiv";

  // 4. 답변 삭제, 필수/선택 여부 div
  let bottomDiv = createBottomDiv(formCount1, selectRequire);

  totalDiv.append(questionDiv);
  totalDiv.append(answerSettingDiv);
  answersDiv.append(answerDiv);
  totalDiv.append(answersDiv);
  totalDiv.append(bottomDiv);

  newSurveyArea.append(totalDiv);

  // 아래는 리스너 --------------------------------------------------------------

  // select 답변유형 변경
  let selectElems = document.getElementsByClassName("answerTypeCombo");
  for (let i = 0; i < selectElem.length; i++) {}
  selectElems[i].addEventListener("change", function () {
    // 모든 답변을 제거
    let answers = topDiv.querySelectorAll(".answerContainer");
    answers.forEach((answer) => topDiv.removeChild(answer));

    // 답변 카운트 초기화
    answerCounts["체크박스"] = 0;
    answerCounts["라디오버튼"] = 0;
    answerCounts["서술형"] = 0;

    // 새로운 유형에 따라 답변 개수 확인
    checkAnswerCount(
      selectElem.options[selectElem.selectedIndex].text,
      newAnswerBtn
    );

    // 요소들이 topDiv에 이미 존재하는지 확인
    let minLabelExists = topDiv.querySelector("label.minLabel");
    let maxLabelExists = topDiv.querySelector("label.maxLabel");
    let minRequiredExists = topDiv.querySelector("input.input_minRequired");
    let maxRequiredExists = topDiv.querySelector("input.input_maxRequired");

    if (selectElem.options[selectElem.selectedIndex].text != "체크박스") {
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
  });

  // 답변 추가 리스너
  let newAnswerBtns = document.getElementsByClassName("btn_addAnswer");

  for (let i = 0; i < newAnswerBtns.length; i++) {
    newAnswerBtns.addEventListener("click", function () {
      // 현재의 답변 유형 텍스트 추출
      let selectedIndex = selectElem.selectedIndex;
      let selectedText = selectElem.options[selectedIndex].text;
      answerCounts[selectedText]++;
      console.log("answerCounts 상태: " + answerCounts[selectedText]);
      checkAnswerCount(selectedText, newAnswerBtn);
      let div1 = createAnswerDiv();

      let answersDiv = document.createElement("div");
      answersDiv.className = "answersDiv";
    });
  }
}

// 1. 질문div
// 질문 레이블 (Q)
// 질문 입력 필드
function createQuestionDiv() {
  let questionDiv = document.createElement("div");
  questionDiv.className = "questionDiv";

  let questionLabel = document.createElement("p");
  questionLabel.innerText = "Q" + formCount1 + ".";
  questionLabel.className = "questionLabel";

  let questionInput = document.createElement("input");
  questionInput.type = "text";
  questionInput.className = "questionInput";

  questionDiv.appendChild(questionLabel);
  questionDiv.appendChild(questionInput);

  return questionDiv;
}

function createAnswerSettingDiv() {
  // 2. 답변 설정 div
  let answerSettingDiv = document.createElement("div");
  answerSettingDiv.className = "answerDiv";

  // 답변 추가 버튼 생성
  let newAnswerBtn = document.createElement("button");
  newAnswerBtn.type = "button";
  newAnswerBtn.innerText = "+ 답변 추가";
  newAnswerBtn.className = "btn_addAnswer";
  newAnswerBtn.setAttribute("data-question-num", formCount1);

  // select
  let selectElem = selectOptionBox();

  answerSettingDiv.appendChild(newAnswerBtn);
  answerSettingDiv.appendChild(selectElem);
}

function selectOptionBox() {
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
}

// 3. 답변 생성
function createAnswerDiv(selectedText) {
  if (answerCounts[selectedText] > maxAnswerCounts[selectedText]) {
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
      defaultAnswerContent(answerDiv);
      break;

    case "라디오버튼":
      var radioElement = document.createElement("input");
      radioElement.type = "radio";
      radioElement.name = "rad" + questionNum;
      radioElement.className = "answerRadio";
      answerDiv.appendChild(radioElement);
      defaultAnswerContent(answerDiv);
      break;

    case "서술형":
      defaultAnswerContent(answerDiv);
      break;

    default:
      break;
  }
}
// 답변 요소 공통 생성 메소드
function defaultAnswerContent(answerDiv) {
  // input type="text" 요소 생성
  let inputElement = document.createElement("input");
  inputElement.type = "text";
  inputElement.className = "answerInput";

  // 삭제 버튼 요소 생성
  let buttonElement = document.createElement("button");
  buttonElement.type = "button";
  buttonElement.innerText = "X";
  buttonElement.className = "btn_answerDelete";

  // 요소들을 div에 추가
  answerDiv.appendChild(inputElement);
  answerDiv.appendChild(buttonElement);

  return answerDiv;
}

function createBottomDiv(selectRequire) {
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

  // 문항 삭제버튼
  let deleteBtn = document.createElement("button");
  deleteBtn.className = "btn_deleteQuestion";
  deleteBtn.type = "button";
  deleteBtn.value = "delete";
  deleteBtn.innerText = "삭제";

  bottomDiv.appendChild(mandatoryCheck);
  bottomDiv.appendChild(mandatoryLabel);
  bottomDiv.appendChild(deleteBtn);

  return bottomDiv;
}

// 문항 번호 재정렬
function rearrangeQuestionNumbers() {
  // 모든 문항 레이블을 선택합니다.
  let questionLabels = document.querySelectorAll(".questionLabel");

  // 각 문항 레이블에 대해 순서대로 번호를 업데이트합니다.
  for (let i = 0; i < questionLabels.length; i++) {
    questionLabels[i].innerText = "Q" + (i + 1) + ".";
  }

  // formCount1도 업데이트합니다.
  formCount1 = questionLabels.length;
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
