$(document).ready(function () {
    const TOKEN = localStorage.getItem("access_token");
    const total = localStorage.getItem("total");
    const email = $("#email");
    const phone = $("#phone");
    const address = $("#address");
    const formOrder = $("#formOrder");
    $(".total").text("Your Total Bill: " + total);

    if (TOKEN == null) {
        window.location.href = "/auth/login"
    }

    if (total == null) {
        alert("You must to choose the product before get order!")
        window.location.href = "/"
    }

    let id_user;
    let id_order;
    let id_cart;
    let id_product = [];
    formOrder.on("submit", function (e) {
        e.preventDefault()
        $.ajax({
            url: "/api/order/add",
            type: "POST",
            contentType: "application/json",
            headers: {
                Authorization: "Bearer " + TOKEN,
            },
            data: JSON.stringify({
                email: email.val(),
                phone: phone.val(),
                address: address.val(),
                total: total,
                user_id: id_user,
            }),
            success: function (data) {
                console.log(data);
                id_order = data.id;
                localStorage.setItem("id_order", id_order);
                get(id_cart);
                // window.location.href = "/order/checkout";
            },
            error: function (e) {
                console.log(e);
            }
        })
    })
    $.ajax({
        url: "/api/user/get_user_by_token",
        type: "POST",
        contentType: "application/json",
        headers: {
            Authorization: "Bearer " + TOKEN,
        },
        data: TOKEN,
        success: function (data) {
            console.log(data);
            id_user = data.id;
            getCartByUser(id_user)
            if (checkUserHaveOrder(id_user)) {
                console.log("true");
            }
        },
        error: function (e) {
            console.log(e);
        }
    })

    function checkUserHaveOrder(id) {
        $.ajax({
            url: "api/order/get_by_id",
            type: "POST",
            contentType: "application/json",
            data: id,
            success: function (data) {
                console.log(data);
                data.map((item) => {
                    email.val(item.email);
                    phone.val(item.phone);
                    address.val(item.address);
                })
                return true
            },
            error: function (e) {
                console.log(e);
                return false
            }
        })
        return false
    }

    function getCartByUser(id_user) {
        $.ajax({
            url: "/api/cart/get_cart_of_user",
            type: "POST",
            contentType: "application/json",
            headers: {
                Authorization: "Bearer " + TOKEN,
            },
            data: id_user,
            success: function (data) {
                console.log(data);
                id_cart = data.id;
            },
            error: function (e) {
                console.log(e);
            }
        })
    }

    // setTimeout(function () {
    //     $.ajax({
    //         url: "/api/cart_detail/get_cart_detail_of_cart",
    //         type: "POST",
    //         contentType: "application/json",
    //         headers: {
    //             Authorization: "Bearer " + TOKEN,
    //         },
    //         data: id_cart,
    //         success: function (data) {
    //             console.log(data);
    //             data.forEach(function (item) {
    //                 Object.values(item.id).forEach(function (value) {
    //                     if (value !== id_cart) {
    //                         id_product.push(value);
    //                         console.log(id_product);
    //                         id_product.forEach(function (id) {
    //                             console.log(id);
    //                             addProductToOrderDetail(id_order, id)
    //                         })
    //                     }
    //                 })
    //             })
    //         },
    //         error: function (e) {
    //             console.log(e);
    //         }
    //     })
    // }, 2000)
    function get(id_cart) {
        $.ajax({
            url: "/api/cart_detail/get_cart_detail_of_cart",
            type: "POST",
            contentType: "application/json",
            headers: {
                Authorization: "Bearer " + TOKEN,
            },
            data: id_cart,
            success: function (data) {
                console.log(data);
                data.forEach(function (item) {
                    Object.values(item.id).forEach(function (value) {
                        if (value !== id_cart) {
                            id_product.push(value);
                            console.log(id_product);
                            id_product.forEach(function (id) {
                                console.log(id);
                                addProductToOrderDetail(id_order, id)
                            })
                        }
                    })
                })
            },
            error: function (e) {
                console.log(e);
            }
        })
    }


    function addProductToOrderDetail(id_order, id_product) {
        $.ajax({
            url: "/api/order_detail/add",
            type: "POST",
            contentType: "application/json",
            headers: {
                Authorization: "Bearer " + TOKEN,
            },
            data: JSON.stringify({
                product_id: id_product,
                order_id: id_order
            }),
            success: function (data) {
                console.log(data);
                window.location.href = "/order/checkout";
            },
            error: function (e) {
                console.log(e);
            }
        })
    }

    // localStorage.removeItem("total");
})
