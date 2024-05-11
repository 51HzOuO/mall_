<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.demo.mall1.beans.Furn" %>
<%@ page import="com.demo.mall1.beans.Page" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=request.getContextPath()%>/">
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>韩顺平教育-家居网购</title>
    <!-- 移动端适配 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css">
    <%--导入jquery--%>
    <script src="assets/js/jquery-3.7.1.min.js"></script>
    <script>
        $(function () {
            // 注意我们在这里使用了类选择器 `.close` 而不是 ID 选择器 `#close`，因为可能有多个 close 按钮
            $(".close").click(function (e) {
                e.preventDefault(); // 阻止链接默认的跳转行为

                var productId = $(this).parent().siblings().eq(0).text().trim();
                var productName = $(this).parent().siblings().eq(2).text().trim();
                //弹窗确认
                if (confirm('确定删除\nid = ' + productId + "\nname = " + productName + ' 吗？')) {
                    // 使用 jQuery 发送 POST 请求
                    $.post('manage', {
                        action: 'delete',
                        id: productId
                    }, function (data) {
                        alert(data);
                        location.reload();
                    }).fail(function (xhr, status, error) {
                        alert('删除失败' + error);
                        location.reload();
                    });
                }
            });
            $("a.pencil").click(function (e) {
                e.preventDefault(); // 阻止链接默认的跳转行为
                var productId = $(this).parent().siblings().eq(0).text().trim();
                $.ajax({
                    url: 'manage',
                    type: 'post',
                    data: {
                        action: 'update',
                        id: productId
                    },
                    success: function (data) {
                        location.href = 'views/manage/furn_update.jsp';
                    }
                });
            });
        });

    </script>

</head>

<body>
<!-- Header Area start  -->
<div class="header section">
    <!-- Header Top  End -->
    <!-- Header Bottom  Start -->
    <div class="header-bottom d-none d-lg-block">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.jsp"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->

                <!-- Header Action Start -->
                <div class="col align-self-center">
                    <div class="header-actions">
                        <div class="header_account_list">
                            <a href="javascript:void(0)" class="header-action-btn search-btn"><i
                                    class="icon-magnifier"></i></a>
                            <div class="dropdown_search">
                                <form class="action-form" action="#">
                                    <input class="form-control" placeholder="Enter your search key" type="text">
                                    <button class="submit" type="submit"><i class="icon-magnifier"></i></button>
                                </form>
                            </div>
                        </div>
                        <!-- Single Wedge Start -->
                        <div class="header-bottom-set dropdown">
                            <a href="views/manage/furn_add.jsp">添加家居</a>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="manage?action=update">修改家居</a>
                        </div>
                    </div>
                </div>
                <!-- Header Action End -->
            </div>
        </div>
    </div>
    <!-- Header Bottom  End -->
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.jsp"><img width="280px" src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->
            </div>
        </div>
    </div>
    <!-- Main Menu Start -->
    <div style="width: 100%;height: 50px;background-color: black"></div>
    <!-- Main Menu End -->
</div>
<!-- Cart Area Start -->
<div class="cart-main-area pt-100px pb-100px">
    <div class="container">
        <h3 class="cart-page-title">家居后台管理</h3>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <form action="#">
                    <div class="table-content table-responsive cart-table-content">
                        <table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>图片</th>
                                <th>家居名</th>
                                <th>商家</th>
                                <th>价格</th>
                                <th>销量</th>
                                <th>库存</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                Page<Furn> furnInfo = (Page<Furn>) session.getAttribute("page");
                                if (furnInfo == null) {
                                    response.sendRedirect(request.getContextPath() + "/manage?action=listFurn&page=1");
                                    return;
                                }
                                if (furnInfo.getItems().size() == 0) {
                                    response.sendRedirect(request.getContextPath() + "/manage?action=listFurn&page=" + (furnInfo.getMaxPage()));
                                    return;
                                }
                                for (Furn furn : furnInfo.getItems()) {
                            %>
                            <tr>
                                <td>
                                    <%=furn.getId()%>
                                </td>
                                <td class="product-thumbnail">
                                    <a href="#"><img class="img-responsive ml-3" src="<%=furn.getPath()%>"
                                                     alt=""/></a>
                                </td>
                                <td class="product-name"><a href="#" class="product--name"><%=furn.getName()%>
                                </a></td>
                                <td class="product-name"><a href="#"><%=furn.getMerchant()%>
                                </a></td>
                                <td class="product-price-cart"><span
                                        class="amount"><%=furn.getPrice()%></span>
                                </td>
                                <td class="product-quantity">
                                    <%=furn.getSales()%>
                                </td>
                                <td class="product-quantity">
                                    <%=furn.getTotal() - furn.getSales()%>
                                </td>
                                <td class="product-remove">
                                    <a class="pencil" href="views/manage/furn_update.jsp"><i
                                            class="icon-pencil"></i></a>
                                    <a class="close"><i class="icon-close"></i></a>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </div>
        <div class="pro-pagination-style text-center mb-md-30px mb-lm-30px mt-6" data-aos="fade-up">
            <ul>
                <%
                    int currentPage = furnInfo.getPageNo();
                    int maxPages = furnInfo.getMaxPage();
                    int startPage, endPage;

                    if (maxPages <= 7) {
                        // 如果总页数不超过7，显示所有页码
                        startPage = 1;
                        endPage = maxPages;
                    } else {
                        // 根据当前页计算起始页和结束页
                        if (currentPage - 4 <= 1) {
                            startPage = 1;
                            endPage = 7;
                        } else if (currentPage + 4 >= maxPages) {
                            startPage = maxPages - 6;
                            endPage = maxPages;
                        } else {
                            startPage = currentPage - 2;
                            endPage = currentPage + 2;
                        }
                    }
                %>
                <li><a href="manage?action=listFurn&page=<%=Math.max(currentPage - 1, 1)%>">上一页</a></li>
                <%
                    if (startPage > 1) {
                %>
                <li><a href="manage?action=listFurn&page=1">1</a></li>
                <li>...</li>
                <% }
                    for (int i = startPage; i <= endPage; i++) {
                        if (i == currentPage) {
                %>
                <li><a class="active"><%=i%>
                </a></li>
                <% } else {
                %>
                <li><a href="manage?action=listFurn&page=<%=i%>"><%=i%>
                </a></li>
                <% }
                }
                    if (endPage < maxPages) {
                %>
                <li>...</li>
                <li><a href="manage?action=listFurn&page=<%=maxPages%>"><%=maxPages%>
                </a></li>
                <% }
                %>
                <li><a href="manage?action=listFurn&page=<%=Math.min(currentPage + 1, maxPages)%>">下一页</a>
                </li>
                <li><a>共<%=furnInfo.getTotalRow()%>记录</a></li>
            </ul>
        </div>

    </div>
</div>
<!-- Cart Area End -->

<!-- Footer Area Start -->
<div class="footer-area">
    <div class="footer-container">
        <div class="footer-top">
            <div class="container">
                <div class="row">
                    <!-- Start single blog -->
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-sm-6 col-lg-3 mb-md-30px mb-lm-30px" data-aos="fade-up"
                         data-aos-delay="400">
                        <div class="single-wedge">
                            <h4 class="footer-herading">信息</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="about.html">关于我们</a></li>
                                        <li class="li"><a class="single-link" href="#">交货信息</a></li>
                                        <li class="li"><a class="single-link" href="privacy-policy.html">隐私与政策</a>
                                        </li>
                                        <li class="li"><a class="single-link" href="#">条款和条件</a></li>
                                        <li class="li"><a class="single-link" href="#">制造</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-2 col-sm-6 mb-lm-30px" data-aos="fade-up" data-aos-delay="600">
                        <div class="single-wedge">
                            <h4 class="footer-herading">我的账号</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="my-account.html">我的账号</a>
                                        </li>
                                        <li class="li"><a class="single-link" href="cart.html">我的购物车</a></li>
                                        <li class="li"><a class="single-link" href="login.html">登录</a></li>
                                        <li class="li"><a class="single-link" href="wishlist.html">感兴趣的</a></li>
                                        <li class="li"><a class="single-link" href="checkout.html">结账</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-3" data-aos="fade-up" data-aos-delay="800">

                    </div>
                    <!-- End single blog -->
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <div class="row flex-sm-row-reverse">
                    <div class="col-md-6 text-right">
                        <div class="payment-link">
                            <img src="#" alt="">
                        </div>
                    </div>
                    <div class="col-md-6 text-left">
                        <p class="copy-text">Copyright &copy; 2021 韩顺平教育~</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer Area End -->
<script src="assets/js/vendor/vendor.min.js"></script>
<script src="assets/js/plugins/plugins.min.js"></script>
<!-- Main Js -->
<script src="assets/js/main.js"></script>
</body>
</html>