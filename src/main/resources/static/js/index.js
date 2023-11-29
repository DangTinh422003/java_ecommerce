$(document).ready(function () {
    const details = $(".details")
    const txt = $(".txt")
    const search = $("#search")
    const formSearch = $("#formSearch")
    const min_price = $(".min_price")
    const max_price = $(".max_price")
    let id_product = []
    let logout = $(".logout")
    const TOKEN = localStorage.getItem("token")

    $.ajax({
        url: "api/product/get_all_products",
        type: "POST",
        contentType: "application/json",
        success: function (data) {
            console.log(data)
            const template = data.map((item) => {
                id_product.push(item.id)
                return `
                         <tr>
                          <th scope="row">${item.name}</th>
                          <td>${item.brand}</td>
                          <td>${item.color}</td>
                          <td>${item.price}</td>
                          <td>${item.categoryName}</td>
                          <td><a href="/product/${item.id}" class="btn">Detail</a></td>
                        </tr>
                        `
            })
            txt.html(template)
        },
        error: function (error) {
            console.log(error)
        }
    })

    formSearch.on("submit", function (e) {
        e.preventDefault()
        const value = search.val()
        const min = min_price.val()
        const max = max_price.val()
        console.log(min_price.val())
        console.log(max_price.val())
        if (value !== "" && min === "" && max === "") {
            $.ajax({
                url: "api/product/filter_product?filter=" + value,
                type: "GET",
                success: function (data) {
                    console.log(data)
                    let html = ``
                    data.map((item) => {
                        html += `
                         <tr>
                          <th scope="row">${item.name}</th>
                          <td>${item.brand}</td>
                          <td>${item.color}</td>
                          <td>${item.price}</td>
                          <td>${item.categoryName}</td>
                          <td><a href="/product/${item.id}" class="btn">Detail</a></td>
                        </tr>
                        `
                    })
                    txt.html(html)
                },
                error: function (error) {
                    console.log(error)
                   txt.html("Not found")
                }
            })

            $.ajax({
                url: "api/product/filter_product?category=" + value,
                type: "GET",
                success: function (data) {
                    console.log(data)
                    let html = ``
                    data.map((item) => {
                        html += `
                         <tr>
                          <th scope="row">${item.name}</th>
                          <td>${item.brand}</td>
                          <td>${item.color}</td>
                          <td>${item.price}</td>
                          <td>${item.categoryName}</td>
                          <td><a href="/product/${item.id}" class="btn">Detail</a></td>
                        </tr>
                        `
                    })
                    txt.html(html)
                },
                error: function (error) {
                    console.log(error)
                    txt.html("Not found")
                }
            })
        } else if (min !== "" && max !== "" && value !== "") {
            $.ajax({
                url: "api/product/filter_product?filter=" + value + "&minPrice=" + min + "&maxPrice=" + max,
                type: "GET",
                success: function (data) {
                    console.log(data)
                    let html = ``
                    data.map((item) => {
                        html += `
                         <tr>
                          <th scope="row">${item.name}</th>
                          <td>${item.brand}</td>
                          <td>${item.color}</td>
                          <td>${item.price}</td>
                          <td>${item.categoryName}</td>
                          <td><a href="/product/${item.id}" class="btn">Detail</a></td>
                        </tr>
                        `
                    })
                    txt.html(html)
                },
                error: function (error) {
                    console.log(error)
                    txt.html("Not found")
                }
            })
        } else if (min !== "" && max !== "" && value === "") {
            $.ajax({
                url: "api/product/filter_product?minPrice=" + min + "&maxPrice=" + max,
                type: "GET",
                success: function (data) {
                    console.log(data)
                    let html = ``
                    data.map((item) => {
                        html += `
                         <tr>
                          <th scope="row">${item.name}</th>
                          <td>${item.brand}</td>
                          <td>${item.color}</td>
                          <td>${item.price}</td>
                          <td>${item.categoryName}</td>
                          <td><a href="/product/${item.id}" class="btn">Detail</a></td>
                        </tr>
                        `
                    })
                    txt.html(html)
                },
                error: function (error) {
                    console.log(error)
                    txt.html("Not found")
                }
            })
        }
    })

    logout.on("click", function (e) {
        e.preventDefault()
        $.ajax({
            url: "/api/v1/auth/logout",
            type: "POST",
            contentType: "application/json",
            data: TOKEN,
            success: function (data) {
                console.log(data)
                localStorage.clear()
                window.location.href = "/auth/login"
            },
            error: function (error) {
                console.log(error)
            }
        })
    })
})