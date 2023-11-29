$(document).ready(function () {
    const username = $("#username");
    const password = $("#password");
    const formSignIn = $("#formSignIn");
    const errorText = $(".error");

    formSignIn.on("submit", function (event) {
        event.preventDefault()
        const data = JSON.stringify({
            username: username.val(),
            password: password.val()
        })
        $.ajax({
            url: "/api/v1/auth/login",
            type: "POST",
            contentType: "application/json",
            data: data,
            success: function (data) {
                const {access_token, refresh_token} = data;
                localStorage.setItem("access_token", access_token);
                localStorage.setItem("refresh_token", refresh_token);
                getUser(access_token);
            },
            error: function (error) {
                console.log(error);
                errorText.text(error.responseJSON.message);
            }
        })

        function getUser(token) {
            $.ajax({
                url: "/api/user/get_user_by_token",
                type: "POST",
                contentType: "application/json",
                data: token,
                success: function (data) {
                    console.log(data)
                    getCartId(data.id)
                },
                error: function (error) {
                    console.log(error);
                }
            })
        }

        function getCartId(id) {
            $.ajax({
                url: "/api/cart/get_cart_of_user",
                type: "POST",
                contentType: "application/json",
                data: id,
                success: function (data) {
                    console.log(data)
                    localStorage.setItem("cart_id", data.id)
                    window.location.href = "/"
                },
                error: function (error) {
                    console.log(error);
                }
            })
        }
    });

})