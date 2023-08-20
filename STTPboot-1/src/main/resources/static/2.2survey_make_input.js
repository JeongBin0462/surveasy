const naviLink = document.querySelector(".naviLink");
const hiddenDiv = document.querySelector("#hiddenDiv");

naviLink.addEventListener("mouseover", function() {
    hiddenDiv.style.display = "flex";
});

document.addEventListener("mousemove", function(event) {
    const buffer = 10;
    const naviLinkRect = naviLink.getBoundingClientRect();
    const hiddenDivRect = hiddenDiv.getBoundingClientRect();

    if (
        event.clientX < naviLinkRect.left - buffer ||
        event.clientX > hiddenDivRect.right + buffer ||
        event.clientY < naviLinkRect.top - buffer ||
        event.clientY > hiddenDivRect.bottom + buffer
    ) {
        hiddenDiv.style.display = "none";
    }
});

document.addEventListener('DOMContentLoaded', function() {
    setVisibility('hidden', 'hidden');
  
    document.getElementById('targetSelection').addEventListener('change', function() {
      const selectedValue = this.value;
  
      if (selectedValue === "employees") {
        setVisibility('visible', 'hidden');
      } else if (selectedValue === "student") {
        setVisibility('hidden', 'visible');
      } else {
        setVisibility('hidden', 'hidden');
      }
    });
  });
  
  function setVisibility(employeesVisibility, studentVisibility) {
    document.getElementById('employeesInputDiv').style.visibility = employeesVisibility;
    document.getElementById('studentInputDiv').style.visibility = studentVisibility;
  }
  