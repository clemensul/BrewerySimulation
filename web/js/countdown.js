

let startCountdown = function () {

    // Set the date we're counting down to
    var countDownDate = new Date().getTime() + 2 * 60000;

    // Update the count down every 1 second
    var x = setInterval(function () {

        // Get todays date and time
        var now = new Date().getTime();

        // Find the distance between now an the count down date
        var distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        minutes = minutes < 10 ? "0" + minutes.toString() : minutes.toString();
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);
        seconds = seconds < 10 ? "0" + seconds.toString() : seconds.toString();

        // Display the result in the element with id="demo"
        document.getElementById("countdown").innerHTML = minutes + ":" + seconds;

        // If the count down is finished, write some text 
        if (distance < 0) {
            clearInterval(x);
            document.getElementById("countdown").innerHTML = "EXPIRED";
        }
    }, 1000);
}

startCountdown();
