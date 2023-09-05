// 전체리스트 정렬방식별
let sortBox = document.getElementById("surveyOptionBySort");
// 주제별
let subjectBox = document.getElementById("surveyOptionBySubject");

viewSelected();

sortBox.addEventListener("change", function (event) {
  viewSelected();
});

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

  fetch("/surveasy/main/update", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      selectedSort: selectedSort.text,
      selectedSubject: selectedSubject.text,
    }),
  }).catch((error) => {
    console.error("Error:", error);
  });
}
