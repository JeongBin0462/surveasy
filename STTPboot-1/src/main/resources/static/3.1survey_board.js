async function redirectToBoardWithParams() {
    let subject = document.getElementById('survey-option-subject').value;
    const sortOption = document.getElementById('survey-option-rate').value;

    // '주제선택'일 경우 subject를 null로
    if (subject === '주제선택') {
        subject = null;
    }

    let queryString = `/surveasy/survey/board?sort=${encodeURIComponent(sortOption)}`;
    if (subject) {
        queryString += `&subject=${encodeURIComponent(subject)}`;
    }
    queryString += `&json=true`;

    try {
        const response = await fetch(queryString);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        updateSurveyData(data);
    } catch (error) {
        console.error('Fetch error:', error);
    }
}

// 시간 parse
function formatDate(dateString) {
    const date = new Date(dateString);

    const YYYY = date.getFullYear();
    const MM = String(date.getMonth() + 1).padStart(2, '0');
    const DD = String(date.getDate()).padStart(2, '0');
    const HH = String(date.getHours()).padStart(2, '0');
    const mm = String(date.getMinutes()).padStart(2, '0');
    const ss = String(date.getSeconds()).padStart(2, '0');

    return `${YYYY}-${MM}-${DD} ${HH}:${mm}:${ss}`;
}


function updateSurveyData(data) {
    const container = document.querySelector(".survey-view-container");

    // survey-view-title이 아닌 survey-view 클래스를 가진 모든 요소들을 삭제
    const surveyViews = container.querySelectorAll(".survey-view:not(#survey-view-title)");
	surveyViews.forEach(view => view.remove());


    if (!Array.isArray(data)) {
        console.error("Returned data is not an array:", data);
        return;
    }

    data.forEach(surveyPaper => {
        const surveyDiv = document.createElement('div');
        surveyDiv.className = 'survey-view';

        surveyDiv.innerHTML = `
            <p class="survey-view-text-title">${surveyPaper.surveytitle}</p>
            <p class="survey-view-text-person">${surveyPaper.participants}명</p>
            <p class="survey-view-text-date">${formatDate(surveyPaper.deadline)}</p>
            <p class="survey-view-text-number">${surveyPaper.bookmark}</p>
        `;
        container.appendChild(surveyDiv);
    });
}

document.getElementById('survey-option-subject').addEventListener('change', redirectToBoardWithParams);
document.getElementById('survey-option-rate').addEventListener('change', redirectToBoardWithParams);
