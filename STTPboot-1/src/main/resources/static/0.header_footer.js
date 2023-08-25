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


