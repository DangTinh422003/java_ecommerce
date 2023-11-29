$(document).ready(function () {
    const TOKEN = localStorage.getItem("access_token");
    const total = localStorage.getItem("total");
    const id_order = localStorage.getItem("id_order");
    const card = $(".card-group");
    const totalBill = $(".total-bill");
    let quantity;

    if (total == null) {
        alert("You must to choose the product before get order!")
        window.location.href = "/"
    }


    if (TOKEN == null) {
        window.location.href = "/auth/login"
    }

    let id_cart = localStorage.getItem("cart_id");
    function getAllOrderDetail() {
        $.ajax({
            url: "/api/order_detail/get_by_order_id",
            type: "POST",
            contentType: "application/json",
            headers: {
                Authorization: "Bearer " + TOKEN,
            },
            data: id_order,
            success: function (data) {
                let html = ``
                data.forEach(function (item) {
                quantity = total /item.total
                    html += `
                    <tr>
                        <th scope="row">${item.product}</th>
                        <td>${total / item.total}</td>
                        <td>${item.total}</td>
                        <td><a href="/" role="button" class="delete btn">Delete</a></td>
                    </tr>
                `
                    $(".txt").html(html);
                    $(".delete").on("click", function (e) {
                        e.preventDefault();
                        deleteOrderDetail(id_order, item.id.productId, quantity)
                    })
                    totalBill.text("Your Total Bill: " + total);
                    $(".checkout").removeClass("d-none");
                })
            },
            error: function (e) {
                console.log(e);
            }
        })
    }

    function deleteOrderDetail(id_order, id, quantity) {
        $.ajax({
            url: "/api/order_detail/delete",
            type: "DELETE",
            contentType: "application/json",
            headers: {
                Authorization: "Bearer " + TOKEN,
            },
            data: JSON.stringify({
                product_id: id,
                order_id: id_order
            }),
            success: function (data) {
                console.log(data);
                updateQuantityAgain(id, quantity)
                // window.location.href = "/order/checkout"
            },
            error: function (e) {
                console.log(e);
            }
        })
    }

    getAllOrderDetail()

    $(".checkout").on("click", function (e) {
        e.preventDefault()
        $(".details").addClass("d-none")
        $.ajax({
            url: "/api/cart_detail/delete_cart_detail_of_cart",
            type: "DELETE",
            contentType: "application/json",
            headers: {
                Authorization: "Bearer " + TOKEN,
            },
            data: id_cart,
            success: function (data) {
                console.log(data);
                window.location.href = "/success"
            },
            error: function (e) {
                console.log(e);
            }
        })
    })


    function updateQuantityAgain(id, quantity) {
        let number = quantity.toString()
        $.ajax({
            url: "/api/product/update_quantity_again/" + id,
            type: "PUT",
            contentType: "application/json",
            data: number,
            success: function (data) {
                console.log(data)
                window.location.href = "/order/checkout"
            },
            error: function (error) {
                console.log(error);
            }
        })
    }

})