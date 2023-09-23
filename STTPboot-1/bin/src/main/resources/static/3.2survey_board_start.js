function sendPostRequest() {
	const currentUrl = window.location.href;
	const surveyNoValue = parseInt(document.getElementById("surveyno-hidden-div").value, 10);
	console.log(surveyNoValue);
	fetch(currentUrl, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify({ surveyKey: surveyNoValue })
	})
		.then(response => response.json())
		.then(data => {
			if (data && data.bookmark !== undefined) {
				console.log("여기로오니?");
				console.log(data.bookmark);
				const pElement = document.querySelector(".survey-view-text-number");
				pElement.textContent = "☆ " + data.bookmark;

				if (data.checkBookmark) {
					pElement.style.color = "yellow";
				} else {
					pElement.style.color = "initial";
				}
			}
		})
		.catch((error) => {
			console.error('Error:', error);
		});
}