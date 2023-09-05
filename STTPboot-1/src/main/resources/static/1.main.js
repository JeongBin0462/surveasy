// 전체리스트 정렬방식별
let sortBox = document.getElementById("surveyOptionBySort");
// 주제별
let subjectBox = document.getElementById("surveyOptionBySubject");


sortBox.addEventListener("change", function(event) {
	viewSelected();
});

subjectBox.addEventListener("change", function(event) {
	viewSelected();
});

document.addEventListener("DOMContentLoaded", function () {
    // 클라이언트 측 JavaScript 코드

    // 이벤트 리스너 등록
    document.getElementById("surveyOptionBySort").addEventListener("change", viewSelected);

    // viewSelected 함수 정의
    function viewSelected() {
        let sortBox = document.getElementById("surveyOptionBySort");
        let subjectBox = document.getElementById("surveyOptionBySubject");
        let selectedSort = sortBox.options[sortBox.selectedIndex].text;
        let selectedSubject = subjectBox.options[subjectBox.selectedIndex].text;

        fetch("/surveasy/main/update", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                selectedSort: selectedSort,
                selectedSubject: selectedSubject,
            }),
        })
            .then((response) => response.json())
            .then((data) => {
                const topListContainer = document.querySelector(".survey-view-container");
                topListContainer.innerHTML = "";
                data.topList.forEach((topelem) => {
                    const surveyView = document.createElement("div");
                    surveyView.classList.add("survey-view");

                    const title = document.createElement("div");
                    title.classList.add("survey-view-text-title");
                    title.textContent = topelem.surveytitle;

                    const person = document.createElement("div");
                    person.classList.add("survey-view-text-person");
                    person.textContent = topelem.participants;

                    const date = document.createElement("div");
                    date.classList.add("survey-view-text-date");
                    date.textContent = topelem.deadline;

                    surveyView.appendChild(title);
                    surveyView.appendChild(person);
                    surveyView.appendChild(date);

                    topListContainer.appendChild(surveyView);
                });


                const bottomListContainer = document.querySelector("#survey-view-bottom .survey-view-container");
                bottomListContainer.innerHTML = "";
                data.bottomList.forEach((bottomelem) => {
                    const surveyView = document.createElement("div");
                    surveyView.classList.add("survey-view");

                    const title = document.createElement("div");
                    title.classList.add("survey-view-text-title");
                    title.textContent = bottomelem.surveytitle;

                    const person = document.createElement("div");
                    person.classList.add("survey-view-text-person");
                    person.textContent = bottomelem.participants;

                    const date = document.createElement("div");
                    date.classList.add("survey-view-text-date");
                    date.textContent = bottomelem.deadline;

                    surveyView.appendChild(title);
                    surveyView.appendChild(person);
                    surveyView.appendChild(date);

                    bottomListContainer.appendChild(surveyView);
                });
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    }
});
