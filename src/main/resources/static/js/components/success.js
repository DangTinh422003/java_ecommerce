$(document).ready(function () {
    const TOKEN = localStorage.getItem("access_token");
    const total = localStorage.getItem("total");
    const id_order = localStorage.getItem("id_order");
    const totalBill = $(".total-bill");
    const txt = $(".txt");
    if (TOKEN == null) {
        window.location.href = "/auth/login"
    }
    if (total == null) {
        alert("You must to choose the product before get order!")
        window.location.href = "/"
    }

    $.ajax({
        url: "/api/order_detail/get_by_order_id",
        type: "POST",
        contentType: "application/json",
        headers: {
            Authorization: "Bearer " + TOKEN,
        },
        data: id_order,
        success: function (data) {
            console.log(data);
            let html = ``
            data.forEach(function (item) {
                html += `
                   <tr>
                  <th scope="row">${item.product}</th>
                  <th scope="row">${item.date_created}</th>
                </tr>
                `
            })
            txt.html(html);
            totalBill.text("Total Your Bill: " + total)
        },
        error: function (e) {
            console.log(e);
        }
    })
})