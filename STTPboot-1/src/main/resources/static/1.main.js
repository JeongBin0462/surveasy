var topPageDir = "";
var bottomPageDir = "";
var topPageNum = 0;
var bottomPageNum = 0;

document.addEventListener("DOMContentLoaded", function () {
  // 전체리스트 정렬방식별
  document
    .getElementById("surveyOptionBySort")
    .addEventListener("change", function () {
      topPageNum = -1;
      viewTop(topPageNum);
    });

  // 주제별
  document
    .getElementById("surveyOptionBySubject")
    .addEventListener("change", function () {
      bottomPageNum = -1;

      viewBottom(bottomPageNum);
    });

  // 좌우 버튼
  document.getElementById("nextBtn1").addEventListener("click", () => {
    topPageDir = "next";
    topPageNum = document.getElementById("topPageNum").value;
    bottomPageNum = document.getElementById("bottomPageNum").value;

    viewTop(topPageNum);
  });

  document.getElementById("nextBtn2").addEventListener("click", () => {
    bottomPageDir = "next";
    topPageNum = document.getElementById("topPageNum").value;
    bottomPageNum = document.getElementById("bottomPageNum").value;

    viewBottom(bottomPageNum);
  });

  document.getElementById("backBtn1").addEventListener("click", () => {
    topPageDir = "prev";
    topPageNum = document.getElementById("topPageNum").value;
    bottomPageNum = document.getElementById("bottomPageNum").value;

    viewTop(topPageNum);
  });

  document.getElementById("backBtn2").addEventListener("click", () => {
    bottomPageDir = "prev";
    topPageNum = document.getElementById("topPageNum").value;
    bottomPageNum = document.getElementById("bottomPageNum").value;

    viewBottom(bottomPageNum);
  });
});

function viewTop(topPageNum) {
  let sortBox = document.getElementById("surveyOptionBySort");
  let subjectBox = document.getElementById("surveyOptionBySubject");
  let selectedSort = sortBox.options[sortBox.selectedIndex].text;
  let selectedSubject = subjectBox.options[subjectBox.selectedIndex].text;

  fetch("/surveasy/update", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      selectedSort: selectedSort,
      selectedSubject: selectedSubject,
      topPageNum: topPageNum,
      topPageDir: topPageDir,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      document.getElementById("topPageNum").value = data.newTopPage;

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

        const rdate = document.createElement("div");
        rdate.classList.add("survey-view-text-rdate");
        rdate.textContent = topelem.regidate;

        const date = document.createElement("div");
        date.classList.add("survey-view-text-date");
        date.textContent = topelem.deadline;

        surveyLink.appendChild(title);
        surveyLink.appendChild(person);
        surveyLink.appendChild(rdate);
        surveyLink.appendChild(date);

        surveyView.appendChild(surveyLink);

        topListContainer.appendChild(surveyView);

        const link = document.createElement("link");
        link.rel = "stylesheet";
        link.href = "/1.main.css";
        document.head.appendChild(link);
      });
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}

function viewBottom(bottomPageNum) {
  let sortBox = document.getElementById("surveyOptionBySort");
  let subjectBox = document.getElementById("surveyOptionBySubject");
  let selectedSort = sortBox.options[sortBox.selectedIndex].text;
  let selectedSubject = subjectBox.options[subjectBox.selectedIndex].text;

  fetch("/surveasy/update", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      selectedSort: selectedSort,
      selectedSubject: selectedSubject,
      bottomPageNum: bottomPageNum,
      bottomPageDir: bottomPageDir,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      document.getElementById("bottomPageNum").value = data.newBottomPage;

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

        const rdate = document.createElement("div");
        rdate.classList.add("survey-view-text-rdate");
        rdate.textContent = bottomelem.regidate;

        const date = document.createElement("div");
        date.classList.add("survey-view-text-date");
        date.textContent = bottomelem.deadline;

        surveyLink.appendChild(title);
        surveyLink.appendChild(person);
        surveyLink.appendChild(rdate);
        surveyLink.appendChild(date);

        surveyView.appendChild(surveyLink);

        bottomListContainer.appendChild(surveyView);

        const link = document.createElement("link");
        link.rel = "stylesheet";
        link.href = "/1.main.css";
        document.head.appendChild(link);
      });
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}
