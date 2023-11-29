$(document).ready(function () {
    const TOKEN = localStorage.getItem("access_token");
    const card = $(".card");
    const total = $(".total");
    let id_user;
    if (TOKEN == null) {
        window.location.href = "/auth/login"
    }

    $.ajax({
        url: "/api/user/get_user_by_token",
        type: "POST",
        contentType: "application/json",
        data: TOKEN,
        success: function (data) {
            console.log(data)
            id_user = data.id
        },
        error: function (error) {
            console.log(error);
        }
    })

    let cart_id;
    let id_product = [];
    let quantity;
    setTimeout(function () {
        $.ajax({
            url: "/api/cart/get_cart_of_user",
            type: "POST",
            contentType: "application/json",
            data: id_user,
            success: function (data) {
                console.log(data)
                cart_id = data.id
                let html = ``
                data.cart_detail.forEach(function (item) {
                    Object.values(item.id).forEach(function (value) {
                        // console.log(value)
                        if (value !== cart_id) {
                            console.log(value)
                            id_product.push(value)
                            quantity = item.quantity
                            console.log(quantity)
                        }
                    })
                    html += `   
                    <tr>
                        <th scope="row">${item.product}</th>
                        <td>${item.price / item.quantity}</td>
                        <td>${item.quantity}</td>
                        <td><a role="button" href="/" class="delete btn">Delete</a></td>
                    </tr>
                    `
                })
                $(".txt").html(html)
                total.text("Your Total Bill: " + data.total)
                localStorage.setItem("total", data.total)
                if (data.cart_detail.length === 0) {
                    total.addClass("d-none")
                    localStorage.removeItem("total")
                }
                $(".delete").on("click", function (e) {
                    e.preventDefault();
                    deleteCartDetail(cart_id, id_product.pop())
                })
            },
            error: function (error) {
                console.log(error);
            }
        })
    }, 1000)

    function deleteCartDetail(cart_id, product_id) {
        $.ajax({
            url: "/api/cart_detail/delete_product_of_cart_detail",
            type: "DELETE",
            contentType: "application/json",
            data: JSON.stringify({
                cart_id: cart_id,
                product_id: product_id
            }),
            success: function (data) {
                console.log(data)
                localStorage.removeItem("total")
                total.addClass("d-none")
                updateQuantity(product_id, quantity)
                // window.location.href = "/cart"
            },
            error: function (error) {
                console.log(error);
            }
        })
    }

    function updateQuantity(id, quantity) {
        let number = quantity.toString()
        console.log(typeof number)
        $.ajax({
            url: "/~api/product/update_quantity_again/" + id,
            type: "PUT",
            contentType: "application/json",
            data: number,
            success: function (data) {
                console.log(data)
                window.location.href = "/"
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
})