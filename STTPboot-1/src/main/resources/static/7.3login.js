document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('registerButton').addEventListener('click', function(event) {
        event.preventDefault();
        console.log("1");
        window.location.href = '././joinagree'; 
    });
});