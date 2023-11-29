$(document).ready(function () {
    const TOKEN = localStorage.getItem("access_token");
    let url = window.location.pathname;
    console.log(url.substring(url.lastIndexOf('/') + 1))
    let id = url.substring(url.lastIndexOf('/') + 1)
    const txt = $(".txt");

    if (TOKEN == null) {
        window.location.href = "/auth/login"
    }

    $.ajax({
        url: "/api/product/get_product",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            id: id
        }),
        headers: {
            Authorization: "Bearer " + TOKEN
        },
        success: function (data) {
            let html = ``;
            html += `
                   <tr>
                          <th scope="row">${data.name}</th>
                            <td>${data.brand}</td>
                            <td>${data.color}</td>
                            <td>${data.price}</td>
                            <td>${data.categoryName}</td>
                            <td><a role="button" class="btn" href="/">Back</a></td>
                            <td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#modal">Buy</button>
                                <div class="modal" tabindex="-1" id="modal">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">Quantity</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <form action="/" method="post" id="formChangeQuantity">
                                                    <div class="modal-body">
                                                        <input type="text" placeholder="1" name="number" style="width: 100px" class="text-center" id="number-quantity">
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn" data-bs-dismiss="modal">Close</button>
                                                        <input name="Buy" type="submit" role="button" class="btn">
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                </div>
                            </td>
                   </tr>
                    
                `
            txt.html(html)
            let formChangeQuantity = $("#formChangeQuantity")
            let number = $("#number-quantity")
            formChangeQuantity.on("submit", function (e) {
                e.preventDefault()
                addQuantity(id, cart_id, number.val())
            })

        },
        error: function (error) {
            console.log(error);
        }
    })
    let id_user;
    $.ajax({
        url: "/api/user/get_user_by_token",
        type: "POST",
        contentType: "application/json",
        data: TOKEN,
        success: function (data) {
            id_user = data.id
            console.log(id_user)
            console.log(data)
        },
        error: function (error) {
            console.log(error);
        }
    })
    let cart_id;
    setTimeout(function () {
        $.ajax({
            url: "/api/cart/get_cart_of_user",
            type: "POST",
            contentType: "application/json",
            data: id_user,
            success: function (data) {
                cart_id = data.id
                console.log(cart_id)
                console.log(data)
            },
            error: function (error) {
                console.log(error);
            }
        })
    }, 1000)

    function addQuantity(id, cart_id, number) {
        $.ajax({
            url: "/api/cart_detail/add",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                product_id: id,
                cart_id: cart_id,
                quantity: number
            }),
            success: function (data) {
                console.log(data)
                updateQuantity(id, number)
                window.location.href = "/cart"
            },
            error: function (error) {
                console.log(error);
            }
        })
    }

    function updateQuantity(id, number) {
        $.ajax({
            url: "/api/product/update_quantity/" + id,
            type: "PUT",
            contentType: "application/json",
            data: number,
            success: function (data) {
                console.log(data)
            },
            error: function (error) {
                console.log(error);
                alert("Quantity is not enough")
                window.location.href = "/product/" + id
            }
        })
    }
})