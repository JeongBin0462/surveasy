var topPageNum;
var bottomPageNum;

document.addEventListener("DOMContentLoaded", function () {
  topPageNum = parseInt(document.getElementById("topPageNum").value, 10);
  bottomPageNum = parseInt(document.getElementById("bottomPageNum").value, 10);
  console.log("초기 topPageNum: " + topPageNum);
  console.log("초기 bottomPageNum: " + bottomPageNum);

  // 전체리스트 정렬방식별
  let sortBox = document.getElementById("surveyOptionBySort");
  // 주제별
  let subjectBox = document.getElementById("surveyOptionBySubject");

  sortBox.addEventListener("change", function () {
    viewSelected(0, bottomPageNum);
  });

  subjectBox.addEventListener("change", function () {
    viewSelected(topPageNum, 0);
  });

  // 좌우 버튼
  document.getElementById("nextBtn1").addEventListener("click", () => {
    console.log("nextBtn1 눌림");
    topPageNum = plusPageNum(topPageNum);
    pageNumLog(topPageNum, bottomPageNum);
    updatePageNum(topPageNum, bottomPageNum);
    viewSelected(topPageNum, bottomPageNum);
  });

  document.getElementById("nextBtn2").addEventListener("click", () => {
    console.log("nextBtn2 눌림");
    bottomPageNum = plusPageNum(bottomPageNum);
    pageNumLog(topPageNum, bottomPageNum);
    updatePageNum(topPageNum, bottomPageNum);
    viewSelected(topPageNum, bottomPageNum);
  });

  document.getElementById("backBtn1").addEventListener("click", () => {
    console.log("backBtn1 눌림");
    topPageNum = minusPageNum(topPageNum);
    pageNumLog(topPageNum, bottomPageNum);
    updatePageNum(topPageNum, bottomPageNum);
    viewSelected(topPageNum, bottomPageNum);
  });

  document.getElementById("backBtn2").addEventListener("click", () => {
    console.log("backBtn2 눌림");
    bottomPageNum = minusPageNum(bottomPageNum);
    pageNumLog(topPageNum, bottomPageNum);
    updatePageNum(topPageNum, bottomPageNum);
    viewSelected(topPageNum, bottomPageNum);
  });
});

// viewSelected 함수 정의
function viewSelected(topPageNum, bottomPageNum) {
  console.log("Sending topPageNum:", topPageNum);
  console.log("Sending bottomPageNum:", bottomPageNum);

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
      topPageNum: topPageNum,
      bottomPageNum: bottomPageNum,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      updatePageNum(data.currentTopPageNum, data.currentBottomPageNum);

      const topListContainer = document.querySelector(".survey-view-container");
      topListContainer.innerHTML = "";
      data.topList.forEach((topelem) => {
        const surveyView = document.createElement("div");
        surveyView.classList.add("survey-view");

        const linkAddress = `http://localhost:8080/surveasy/survey/${topelem.link}?no=${topelem.surveyno}`;

        let surveyLink = document.createElement("a");
        if (topelem.link) {
          surveyLink.href = linkAddress;
          surveyLink.classList.add("survey-urls-div");
        }

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

        surveyView.appendChild(surveyLink);

        topListContainer.appendChild(surveyView);
      });

      const bottomListContainer = document.querySelector(
        "#survey-view-bottom .survey-view-container"
      );

      bottomListContainer.innerHTML = "";
      data.bottomList.forEach((bottomelem) => {
        const surveyView = document.createElement("div");
        surveyView.classList.add("survey-view");

        const linkAddress = `http://localhost:8080/surveasy/survey/${bottomelem.link}?no=${bottomelem.surveyno}`;

        let surveyLink = document.createElement("a");
        if (bottomelem.link) {
          surveyLink.href = linkAddress;
          surveyLink.classList.add("survey-urls-div");
        }

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

        surveyView.appendChild(surveyLink);

        bottomListContainer.appendChild(surveyView);
      });
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}

function plusPageNum(pageNum) {
  pageNum = (pageNum + 1 + 3) % 3;
  return pageNum;
}

function minusPageNum(pageNum) {
  pageNum = (pageNum - 1 + 3) % 3;
  return pageNum;
}

function pageNumLog(topPageNum, bottomPageNum) {
  console.log("topPageNum : " + topPageNum);
  console.log("bottomPageNum : " + bottomPageNum);
}

function updatePageNum(topPageNum, bottomPageNum) {
  document.getElementById("topPageNum").value = topPageNum;
  document.getElementById("bottomPageNum").value = bottomPageNum;
}
