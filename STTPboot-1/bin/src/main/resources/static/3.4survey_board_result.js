document.addEventListener("DOMContentLoaded", function () {
        var copyButton = document.getElementById("url-copy-btn");
        var urlText = document.getElementById("url-text");

        copyButton.addEventListener("click", function () {
            var textToCopy = urlText.textContent || urlText.innerText;
            var textArea = document.createElement("textarea");

            textArea.value = textToCopy;
            document.body.appendChild(textArea);
            textArea.select();
            document.execCommand("copy");
            document.body.removeChild(textArea);

            copyButton.textContent = "[ 복사됨 ]";

            setTimeout(function () {
                copyButton.textContent = "[ 복사 ]";
            }, 2000);
        });
    });