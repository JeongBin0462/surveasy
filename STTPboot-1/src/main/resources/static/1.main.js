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
    })
  });
  // .then(response)
  // .then(data => {
  // 	const topListContainer = document.getElementById("topListContainer");
  // 	const bottomListContainer = document.getElementById("bottomListContainer");

  // 	// 기존 리스트 내용을 지운다
  // 	topListContainer.innerHTML = "";
  // 	bottomListContainer.innerHTML = "";

  // 	// 새로운 topList 항목들을 추가한다
  // 	data.topList.forEach(item => {
  // 		const newItem = document.createElement("div");
  // 		newItem.textContent = item.someProperty;  // someProperty는 서버로부터 받은 객체의 속성
  // 		topListContainer.appendChild(newItem);
  // 	});

  // 	// 새로운 bottomList 항목들을 추가한다
  // 	data.bottomList.forEach(item => {
  // 		const newItem = document.createElement("div");
  // 		newItem.textContent = item.someOtherProperty;  // someOtherProperty도 서버로부터 받은 객체의 속성
  // 		bottomListContainer.appendChild(newItem);
  // 	.catch ((error) => {
  // 			console.error("Error:", error);
  // 		});
}
