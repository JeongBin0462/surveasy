let newSurveyBtn = document.getElementById("newSurveyBtn");
let newSurveyArea = document.getElementById("survey-question");

newSurveyBtn.addEventListener("click", function (e) {
    let newQuestion = document.createElement("p");
    newQuestion.innerText = "새로 생성"

    newSurveyArea.append(newQuestion);
})