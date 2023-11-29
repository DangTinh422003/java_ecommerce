$(document).ready(function () {
    const username = $("#username");
    const email = $("#email");
    const password = $("#password");
    const formSignUp = $("#formSignUp");
    const errorText = $(".error");

    formSignUp.on("submit", function (event) {
        event.preventDefault()
        const data = JSON.stringify({
            username: username.val(),
            email: email.val(),
            password: password.val()
        })
        $.ajax({
            url: "/api/v1/auth/register",
            type: "POST",
            contentType: "application/json",
            data: data,
            success: function (data) {
                console.log(data);
                alert("Register success!")
                window.location.href = "/auth/login";
            },
            error: function (error) {
                console.log(error);
                errorText.text(error.responseJSON.message);
            }
        })
    })
})