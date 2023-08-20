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

    // 체크박스 라디오 버튼의 이벤트 리스너
    checkboxRadio.addEventListener("change", function () {
      if (checkboxRadio.checked) {
        let checkboxInput = document.createElement("input");
        checkboxInput.setAttribute("type", "checkbox");
        checkboxInput.setAttribute("name", "checkboxAnswer" + formCount);
        newForm.appendChild(checkboxInput);
      }
    });

    // 서술형 라디오 버튼의 이벤트 리스너
    descriptiveRadio.addEventListener("change", function () {
      if (descriptiveRadio.checked) {
        let textarea = document.createElement("textarea");
        textarea.setAttribute("name", "descriptiveAnswer" + formCount);
        newForm.appendChild(textarea);
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

    newSurveyArea.append(newForm);

    // 새로 생성된 form 요소로 스크롤 이동
    newForm.scrollIntoView({ behavior: "smooth" });
  });
}
