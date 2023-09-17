async function redirectToBoardWithParams() {
	let subject = document.getElementById('survey-option-subject').value;
	const sortOption = document.getElementById('survey-option-rate').value;
	const search = document.getElementById('survey-search').value;

	// '주제선택'일 경우 subject를 null로
	if (subject === '주제선택') {
		subject = null;
	}
	// 정렬
	let queryString = `/surveasy/survey/board?sort=${encodeURIComponent(sortOption)}`;
	// currentPage를 항상 1로 초기화
	queryString += `&currentPage=${encodeURIComponent(1)}`;
	// 주제
	if (subject) {
		queryString += `&subject=${encodeURIComponent(subject)}`;
	}
	// 검색
	if (search) {
		queryString += `&search=${encodeURIComponent(search)}`
	}
	// json인지 아닌지
	queryString += `&json=true`;
	console.log(queryString);
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

	const MM = String(date.getMonth() + 1).padStart(2, '0');
	const DD = String(date.getDate()).padStart(2, '0');
	const HH = String(date.getHours()).padStart(2, '0');
	const mm = String(date.getMinutes()).padStart(2, '0');

	return `${MM}-${DD} ${HH}:${mm}`;
}


function updateSurveyData(data) {
	const container = document.querySelector(".survey-view-container");

	// survey-view-title이 아닌 survey-view 클래스를 가진 모든 요소들을 삭제
	const surveyViews = container.querySelectorAll(".survey-view:not(#survey-view-title)");
	surveyViews.forEach(view => view.remove());


	if (!data || !data.surveyPapers || !Array.isArray(data.surveyPapers)) {
		console.error("Invalid data structure:", data);
		return;
	}

	// 현재 페이지와 전체 페이지 수를 업데이트
	const currentPage = data.currentPage;
	const totalPage = data.totalPage;
	console.log("버튼 제약 currentPage" + currentPage);
	console.log("버튼 제약 totalPage" + totalPage);

	// 페이지 이동 버튼의 활성화/비활성화 상태 갱신
	const prevPageButton = document.querySelectorAll('.survey-view-btn')[0]; // 첫 번째 버튼 선택
	if (currentPage == 1) {
		prevPageButton.disabled = true;
	} else {
		prevPageButton.disabled = false;
	}

	const nextPageButton = document.querySelectorAll('.survey-view-btn')[1]; // 두 번째 버튼 선택
	if (currentPage == totalPage) {
		nextPageButton.disabled = true;
	} else {
		nextPageButton.disabled = false;
	}
	
	// 현재 페이지 및 전체 페이지 숫자 갱신
	updatePageNumbers(currentPage, totalPage);
	
	// hidden input값 갱신
	document.querySelector('input[name="currentPage"]').value = data.currentPage || 1;
	document.querySelector('input[name="totalPage"]').value = data.totalPage;
	document.querySelector('.survey-search').value = data.search || "";
	document.querySelector('.survey-subject').value = data.subject || "";
	document.querySelector('.survey-sort').value = data.sort || "";

	// surveyPaper 갱신
	data.surveyPapers.forEach(surveyPaper => {
		const surveyDiv = document.createElement('div');
		surveyDiv.id = 'survey-papers';

		const linkAddress = `http://localhost:8080/surveasy/survey/${surveyPaper.link}?no=${surveyPaper.surveyno}`;

		let surveyLink = document.createElement('a');
		if (surveyPaper.link) {
			surveyLink.href = linkAddress;
			surveyLink.className = 'survey-urls-div';
		}

		let surveyContentView = document.createElement('div');
		surveyContentView.className = 'survey-view';

		surveyContentView.innerHTML = `
        <p class="survey-view-text-title">${surveyPaper.surveytitle}</p>
        <p class="survey-view-text-person">${surveyPaper.participants}명</p>
        <p class="survey-view-text-regidate">${formatDate(surveyPaper.regidate)}</p>
        <p class="survey-view-text-deadline">${formatDate(surveyPaper.deadline)}</p>
        <p class="survey-view-text-number">${surveyPaper.bookmark}</p>
    `;

		surveyLink.appendChild(surveyContentView);
		surveyDiv.appendChild(surveyLink);

		container.appendChild(surveyDiv);
	});
}

document.getElementById('survey-option-subject').addEventListener('change', redirectToBoardWithParams);
document.getElementById('survey-option-rate').addEventListener('change', redirectToBoardWithParams);


// 페이징버튼 비동기화
async function loadPageData(currentPage, search, subject, sort) {
	let queryString = `/surveasy/survey/board?sort=${encodeURIComponent(sort)}`;
	queryString += `&currentPage=${encodeURIComponent(currentPage)}`;
	console.log(currentPage);
	if (subject && subject !== '주제선택') {
		queryString += `&subject=${encodeURIComponent(subject)}`;
	}
	if (search) {
		queryString += `&search=${encodeURIComponent(search)}`
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

const buttons = document.querySelectorAll('.survey-view-btn');

buttons.forEach(button => {
	button.addEventListener('click', function(event) {
		event.preventDefault();

		let currentPage = parseInt(document.querySelector('input[name="currentPage"]').value);
		let totalPage = parseInt(document.querySelector('input[name="totalPage"]').value);
		
		console.log("여기오니1 ?" + currentPage);
		if (currentPage == 0) {
			currentPage = 1;
			console.log("여기오니2 ?" + currentPage);
		}
		// 첫 번째 버튼(이전)을 눌렀을 때
		if(button.textContent.trim() == '◀' && currentPage > 1) {
			currentPage--;
			document.querySelector('input[name="currentPage"]').value = currentPage;
			updateCurrentNumber(currentPage);
		}
		// 두 번째 버튼(다음)을 눌렀을 때
		else if(button.textContent.trim() == '▶' && currentPage < totalPage) {
			console.log("여기오니?");
			currentPage++;
			console.log("여기오니?" + currentPage);
			document.querySelector('input[name="currentPage"]').value = currentPage;
			updateCurrentNumber(currentPage);
		}
		
		const search = document.querySelector('input[name="search"]').value;
		const subject = document.querySelector('input[name="subject"]').value;
		const sort = document.querySelector('input[name="sort"]').value;

		loadPageData(currentPage, search, subject, sort);
	});
});

// 현재페이지 강조 업데이트
function updateCurrentNumber(newPage) {
    let currentNumber = document.querySelector('.highlight-number');
    if (currentNumber) {
        currentNumber.classList.remove('highlight-number');
        currentNumber.textContent = currentNumber.textContent.trim();
    }

    let pageNumberDivs = document.querySelectorAll('.page-number-list .page-span');
    pageNumberDivs.forEach((span, index) => {
        if ((index + 1) === newPage) {
            span.classList.add('highlight-number');
        }
        span.textContent = index + 1;
    });
    document.querySelector('input[name="currentPage"]').value = newPage;
}

// 현재페이지, 전체페이지 갱신
function updatePageNumbers(currentPage, totalPage) {
    const pageNumbersContainer = document.querySelector(".page-number-div");

    // 기존 페이지 숫자 삭제
    const oldPageNumberLists = pageNumbersContainer.querySelectorAll(".page-number-list");
    oldPageNumberLists.forEach(list => list.remove());

    // 새 페이지 숫자 생성 및 추가
    for (let i = 1; i <= totalPage; i++) {
        const pageNumberListDiv = document.createElement("div");
        pageNumberListDiv.classList.add("page-number-list");

        const span = document.createElement("span");
        span.textContent = i;
        span.classList.add("page-span");
        if (i === currentPage) {
            span.classList.add("highlight-number");
        }
        pageNumberListDiv.appendChild(span);
        pageNumbersContainer.appendChild(pageNumberListDiv);
    }
}


document.querySelector('.page-number-div').addEventListener('click', function(event) {
    if (event.target.classList.contains('page-span')) {
        const clickedPage = parseInt(event.target.textContent);
        redirectToPage(clickedPage);
    }
});

function redirectToPage(pageNumber) {
    // 여기서 currentPage를 pageNumber로 업데이트하고 필요한 로직 수행
    document.querySelector('input[name="currentPage"]').value = pageNumber;
    
    // 이제 기존 로직을 사용해 페이지 데이터를 로드
    const search = document.querySelector('input[name="search"]').value;
    const subject = document.querySelector('input[name="subject"]').value;
    const sort = document.querySelector('input[name="sort"]').value;

    loadPageData(pageNumber, search, subject, sort);
}
