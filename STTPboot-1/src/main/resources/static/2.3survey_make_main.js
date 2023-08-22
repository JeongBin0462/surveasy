let newSurveyBtns = document.getElementsByClassName("newSurveyBtn");

let newSurveyArea = document.getElementById("survey-question");

let formCount1 = 0;
for (let i = 0; i < newSurveyBtns.length; i++) {
  newSurveyBtns[i].addEventListener("click", function (e) {
    formCount1++;

    let newDiv = document.createElement("div");
    newDiv.id = "newDiv" + formCount1;
    newDiv.className = "newDiv";
    
    let newQuestionLabel = document.createElement("p");
    newQuestionLabel.innerText = formCount1;
    newQuestionLabel.id = "newQuestionLabel" + newQuestionLabel;

    let inputQuestion = document.createElement("input");
    inputQuestion.type = "text";
    inputQuestion.id = "inputQuestion" + formCount1;

    let newAnswerBtn = document.createElement("button");
    newAnswerBtn.type = "button";
    newAnswerBtn.innerText = "+ 답변 추가";
    newAnswerBtn.addEventListener("click", function() { createAnswer(newDiv); });
    newAnswerBtn.id = "newAnswerBtn" + formCount1;

    // 새로운 select 요소 생성
    let selectElem = document.createElement("select");
    selectElem.className = "answerTypeCombo";
    selectElem.id = "selectElem" + formCount1;
        
    // Option 예제 - 원하는 만큼 option을 추가하십시오.
    let option1 = document.createElement("option");
    option1.value = "option1";
    option1.text = "체크박스";
    selectElem.appendChild(option1);

    let option2 = document.createElement("option");
    option2.value = "option2";
    option2.text = "라디오버튼";
    selectElem.appendChild(option2);

    let option3 = document.createElement("option");
    option3.value = "option3";
    option3.text = "서술형";
    selectElem.appendChild(option3);

    // 더 많은 옵션을 추가하려면 위의 패턴을 반복하십시오.
    newDiv.appendChild(newQuestionLabel);
    newDiv.appendChild(inputQuestion);
    newDiv.appendChild(selectElem);  // select 요소를 div에 추가
    newDiv.appendChild(newAnswerBtn);
  
    newSurveyArea.append(newDiv);
    
    newDiv.scrollIntoView({ behavior: "smooth" });
  });
}

let formCount2 = 0;
function createAnswer(newDiv) {
  formCount2++;

  let answerDiv = document.createElement("div");
  answerDiv.className = "answerDiv";
  answerDiv.id = "answerDiv" + formCount1 + formCount2;

  // checkbox 요소 생성
  var checkboxElement = document.createElement("input");
  checkboxElement.type = "checkbox";
  checkboxElement.id = "checkboxElement" + formCount1 + formCount2;
  
  // input type="text" 요소 생성
  var inputElement = document.createElement("input");
  inputElement.type = "text";
  inputElement.id = "inputElement" + formCount1 + formCount2;
  
  // button 요소 생성
  var buttonElement = document.createElement("button");
  buttonElement.innerText = "X";
  buttonElement.id = "buttonElement" + formCount1 + formCount2;

  // buttonElement.addEventListener("click", function() {
  //   newDiv.removeChild(answerDiv);
  // });
  
  // 요소들을 div에 추가
  answerDiv.appendChild(checkboxElement);
  answerDiv.appendChild(inputElement);
  answerDiv.appendChild(buttonElement);

  newDiv.appendChild(answerDiv);
}




// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// : 버튼 시작점

// 삭제 로직
// function deleteFormLogic(newForm) {
//   newForm.remove();

//   // 이후의 모든 form 요소들의 이름 및 아이디 갱신
//   for (let i = formCount; i > newForm.dataset.formNumber; i--) {
//       const formToUpdate = document.querySelector(`[data-form-number='${i}']`);
//       if (formToUpdate) {
//           formToUpdate.setAttribute("data-form-number", i - 1);
		  

//           const questionLabel = formToUpdate.querySelector(`[for='input${i}']`);
//           if (questionLabel) {
//               questionLabel.setAttribute("for", "input" + (i - 1));
//               questionLabel.innerText = "질문 " + (i - 1);
//           }

//           const questionContent = formToUpdate.querySelector(`#input${i}`);
//           if (questionContent) {
//               questionContent.setAttribute("id", "input" + (i - 1));
//               questionContent.setAttribute("name", "input" + (i - 1));
//           }
//       }
//   }
//   // formCount 값 감소
//   formCount--;
// }

// // 삭제 버튼 생성
// function createDeleteButton(newForm) {
//   let deleteButton = document.createElement("button");
//   deleteButton.innerText = "삭제";

//   deleteButton.addEventListener("click", function (event) {
//       event.stopPropagation();
//       event.preventDefault();
//       deleteFormLogic(newForm);
//   });

//   return deleteButton;
// }

// function createImageDiv(newForm) {
//   let imageDiv = document.createElement("div");
//   imageDiv.setAttribute("style", 'width:40px;height:40px;background:url("/static/img/점.png") center/contain no-repeat;cursor:pointer;');
//   imageDiv.setAttribute("id", "imageDiv");
//   addImageDivClickEvent(imageDiv, newForm);
//   newForm.appendChild(imageDiv);
// }

// function addImageDivClickEvent(imageDiv, newForm) {
//   let bigDiv;
//   imageDiv.onclick = function () {
//     if (!bigDiv) {
//       bigDiv = createBigDiv(newForm);
//       newForm.appendChild(bigDiv);
//     } else {
//       bigDiv.remove();
//       bigDiv = null;
//     }
//   };
// }

// function createBigDiv(newForm) {
//   let bigDiv = document.createElement('div');
//   bigDiv.setAttribute('style', 'border:1px solid black;padding:20px;margin:10px;');
//   bigDiv.appendChild(createDeleteDiv(newForm));
//   bigDiv.appendChild(createRadioDiv());
//   bigDiv.appendChild(createCheckboxDiv());
//   return bigDiv;
// }

// function createDeleteDiv(newForm) {
//   let deleteDiv = document.createElement('div');
//   let deleteButton = createDeleteButton(newForm);
//   deleteDiv.appendChild(deleteButton);
//   return deleteDiv;
// }

// function createRadioDiv() {
//   let radioDiv = document.createElement('div');
//   let types = [
//     { type: "checkbox", text: "체크박스" },
//     { type: "radio", text: "라디오" },
//     { type: "descriptive", text: "서술형" },
//   ];
//   types.forEach((item) => {
//     let radio = document.createElement('input');
//     radio.setAttribute('type', 'radio');
//     radio.setAttribute('name', 'answerType');
//     radio.setAttribute('value', item.type);
    
//     let label = document.createElement('label');
//     label.innerText = item.text;
//     radioDiv.appendChild(radio);
//     radioDiv.appendChild(label);
//   });
//   return radioDiv;
// }

// function createCheckboxDiv() {
//   let checkboxDiv = document.createElement('div');
//   let checkbox = document.createElement('input');
//   checkbox.setAttribute('type', 'checkbox');
//   checkboxDiv.appendChild(checkbox);
//   return checkboxDiv;
// }

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

// // 설정화면 로직
// function showScreen(screenId) {
//   // 모든 화면을 숨김
//   const screens = document.querySelectorAll(".screen");
//   screens.forEach((screen) => {
//     screen.classList.remove("active");
//   });

//   // 선택한 화면만 표시
//   const selectedScreen = document.getElementById(screenId);
//   selectedScreen.classList.add("active");
// }
