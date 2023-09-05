// 전체리스트 정렬방식별
let sortBox = document.getElementById("surveyOptionBySort");
sortBox.addEventListener("change", function (event) {
  viewSelected();
});

// 주제별
let subjectBox = document.getElementById("surveyOptionBySubject");

subjectBox.addEventListener("change", function (event) {
  viewSelected();
});

function viewSelected() {
  let sortIndex = sortBox.selectedIndex;
  let selectedSort = sortBox.options[sortIndex];
  console.log("선택한 정렬 방식: ", selectedSort.text);

  let subjectIndex = subjectBox.selectedIndex;
  let selectedSubject = subjectBox.options[subjectIndex];
  console.log("선택한 주제: ", selectedSubject.text);

  fetch("/surveasy/main/view", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      selectedSort: selectedSort.text,
      selectedSubject: selectedSubject.text,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}
