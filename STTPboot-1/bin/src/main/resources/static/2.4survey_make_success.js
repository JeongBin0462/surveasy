fetch("/surveasy/makesurvey/success/req", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
  },
  body: JSON.stringify({
    test: "테스트",
  }),
})
  .then((response) => response.json())
  .then((data) => {
    console.log(data);
    console.log(data.surveyTitle);
    console.log(data.link);

    if (data.surveyTitle && data.link) {
      document.getElementById("survey-title").textContent = data.surveyTitle;
      document.getElementById("survey-link").textContent = data.link;
    } else {
      console.error("Invalid data received from server");
    }
  })
  .catch((error) => {
    console.error("Error:", error);
  });
