var css1 = {
    display:"none"
}
var css2 ={
    display: "block",

}

function showSection(sectionId){
    $("#dashboard-section").css(css1);
    $("#field-section").css(css1);
    $("#crop-section").css(css1);
    $("#vehicle-section").css(css1);
    $("#staff-section").css(css1);
    $("#equipment-section").css(css1);
    $("#logs-section").css(css1);
    $("#profile-section").css(css1);
    $(`#${sectionId}`).css(css2);
}

// Set up click listeners for each navigation item
$("#nav-dashboard").click(function () {
    handleNavClick($(this).attr("id"), "Dashboard");
});
$("#nav-field").click(function () {
    handleNavClick($(this).attr("id"), "Field Management");
});
$("#nav-crop").click(function () {
    handleNavClick($(this).attr("id"), "Crops Management");
});
$("#nav-equipment").click(function () {
    handleNavClick($(this).attr("id"), "Equipment Management");
});
$("#nav-logs").click(function () {
    handleNavClick($(this).attr("id"), "Monitoring Logs");
});
$("#nav-staff").click(function () {
    handleNavClick($(this).attr("id"), "Staff Management");
});
$("#nav-vehicles").click(function () {
    handleNavClick($(this).attr("id"), "Vehicles Management");
});
$("#nav-profile").click(function () {
    handleNavClick($(this).attr("id"), "Profile");
});
function handleNavClick(clickedElementId,title){
    $(".dashboard-topic").text(title);

    switch (clickedElementId) {
        case "nav-dashboard":
            showSection("dashboard-section");
            break;
        case "nav-field":
            showSection("field-section");
            break;
        case "nav-crop":
            showSection("crop-section");
            break;
        case "nav-equipment":
            showSection("equipment-section");
            break;

        case "nav-logs":
            showSection("logs-section");
            break;

        case "nav-staff":
            showSection("staff-section");
            break;

        case "nav-vehicles":
            showSection("vehicle-section");
            break;

        case "nav-profile":
            showSection("profile-section");

    }
}

$(document).ready(function() { // This function runs when the document is ready
    /*  handleNavClick("nav-home");*/
    $("#homeSection").css(css1);
    $("#signUp-section").css(css1);

    $("body").css({

        backgroundColor:"#8fa98f"
    })
    // Show signup section and hide login section when signup button is clicked
    $("#login-signUp").click(function () {
        $("#login-section").css(css1);
        $("#signUp-section").css(css2);
    });

    // Show login section and hide signup section when signin button is clicked
    $("#signInBtn").click(function () {
        $("#login-section").css(css2);
        $("#signUp-section").css(css1);
    });

    $("#login-signIn").click(function () {
        $("#login-section").css(css1);
        $("#signUp-section").css(css1);
        $("#homeSection").css(css2)
        handleNavClick("nav-dashboard");
        $("body").css({
            backgroundColor:"#FAF5E6"
        })
    });

    // Sign-Up

    $('#signUpBtn').click(function () {
        const email = $('.userName').val();
        const password = $('.password').val();
        const role = $('#SelectRole').val();

        $.ajax({
            url: 'http://localhost:8080/greenShadow/api/v1/auth/signup',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ email, password, role }),
            success: function (response) {
                // Store the token from the response
                localStorage.setItem('token', response.token);
                alert("Sign-up successful!");
                $("#login-section").css(css2);
                $("#signUp-section").css(css1);
            },
            error: function () {
                alert("Sign-up failed. Please try again.");
            }
        });
    });

});