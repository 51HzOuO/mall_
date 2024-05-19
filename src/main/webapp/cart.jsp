<%@ page import="com.demo.mall1.beans.User" %>
<%@ page import="com.demo.mall1.beans.Cart" %>
<%@ page import="com.demo.mall1.beans.CartItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>韩顺平教育-家居网购</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css"/>
    <script src="assets/js/jquery-3.7.1.min.js"></script>
    <script>
        $(function () {
            $("#clear-cart").click(function () {
                if (confirm("确定要清空购物车吗？")) {
                    $.ajax({
                        url: "customer?action=clearCart",
                        success: function () {
                            window.location.reload();
                        },
                        error: function () {
                            alert("清空失败，请稍后重试。");
                        }
                    });
                }
            })

            function load() {
                var CartPlusMinus = $(".cart-plus-minus");
                CartPlusMinus.prepend('<div class="dec qtybutton">-</div>');
                CartPlusMinus.append('<div class="inc qtybutton">+</div>');
                $(".qtybutton").on("click", function (event) {
                    event.preventDefault(); // 防止事件冒泡
                    var $button = $(this);
                    var $input = $button.siblings("input.cart-plus-minus-box");
                    var oldValue = parseInt($input.val());
                    var id = $button.closest('td').find('input[type="hidden"]').val();
                    var newVal;

                    if ($button.hasClass("inc")) {
                        newVal = oldValue + 1;
                    } else {
                        // Don't allow decrementing below one
                        if (oldValue > 1) {
                            newVal = oldValue - 1;
                        } else {
                            newVal = 1;
                        }
                    }

                    $input.val(newVal);
                    updateCart(id, newVal, $button.closest("tr"));
                });
            }

            load();

            $(".icon-close").click(function () {
                // 显示确认弹窗
                if (confirm("确定要删除该商品吗？")) {
                    // 获取ID
                    var id = $(this).closest('td').find('input[type="hidden"]').val();
                    var $row = $(this).closest('tr'); // 缓存当前行，避免在AJAX内部使用 $(this)

                    // 发送AJAX请求
                    $.ajax({
                        url: "customer?action=deleteCart",
                        data: {
                            id: id
                        },
                        success: function () {
                            // 删除成功后移除该行
                            $row.remove();
                            updateCartTotal();
                        },
                        error: function () {
                            alert("删除失败，请稍后重试。");
                        }
                    });
                }
            });

            // 处理数量输入框的直接输入
            $(".cart-plus-minus-box").change(function () {
                var count = parseInt($(this).val());
                var id = $(this).closest('td').find('input[type="hidden"]').val();
                if (count >= 1) {
                    updateCart(id, count, $(this).closest("tr"));
                } else {
                    $(this).val(1);
                    updateCart(id, 1, $(this).closest("tr"));
                }
            });

            // 更新购物车
            function updateCart(id, count, $row) {
                $.ajax({
                    type: "POST",
                    url: "customer",
                    data: {
                        action: "setItemCount",
                        id: id,
                        count: count
                    },
                    success: function (data) {
                        var origin = parseInt(data);

                        // 更新当前行的金额
                        var price = parseFloat($row.find(".product-price-cart .amount").text().substring(1));
                        var newTotal = (price * count).toFixed(2);
                        $row.find(".product-subtotal").text(newTotal);

                        updateCartTotal();
                    },
                    error: function () {
                        alert("更新失败，请稍后重试。");
                    }
                });
            }

            function updateCartTotal() {
                var $cartTotal = $(".cart-shiping-update-wrapper h4");
                var totalCount = 0;
                var totalPrice = 0.0;

                $(".cart-plus-minus-box").each(function () {
                    var qty = parseInt($(this).val());
                    var itemPrice = parseFloat($(this).closest("tr").find(".product-price-cart .amount").text().substring(1));
                    totalCount += qty;
                    totalPrice += qty * itemPrice;
                });

                totalPrice = totalPrice.toFixed(2);
                $cartTotal.html("共" + totalCount + "件商品 总价 " + totalPrice + "元");
            }

            // 显示支付弹窗
            $("#pay").click(function () {
                $("#payment-modal").show();
                $("#modal-overlay").show();
            });

            // 关闭支付弹窗并跳转
            $("#payment-done").click(function () {
                $("#payment-modal").hide();
                $("#modal-overlay").hide();
                window.location.href = "支付完成后的跳转页面.jsp"; // 替换为实际的跳转页面
            });

            // 点击遮罩层关闭弹窗
            $("#modal-overlay").click(function () {
                $("#payment-modal").hide();
                $("#modal-overlay").hide();
            });
        });
    </script>


</head>

<body>
<!-- Header Area start  -->
<div class="header section">
    <!-- Header Top Message Start -->
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
                            <a id="src2" href="javascript:void(0)" class="header-action-btn search-btn"><i
                                    class="icon-magnifier"></i></a>
                            <div class="dropdown_search">
                                <form class="action-form" action="#">
                                    <input class="form-control" placeholder="Enter your search key" type="text">
                                    <button class="submit" type="submit" id="src0"><i class="icon-magnifier"></i>
                                    </button>
                                </form>
                            </div>
                        </div>
                        <%
                            User user = (User) session.getAttribute("user");
                            if (user == null) {
                        %>
                        <div class="header-bottom-set dropdown">
                            <a href="views/member/login.jsp">登录|注册</a>
                        </div>
                        <%
                        } else {
                        %>
                        <div class="header-bottom-set dropdown">
                            <a>Welcome: <%=user.getUsername()%>
                            </a>
                        </div>
                        <%
                            if (user.getType() == 1) {
                        %>
                        <div class="header-bottom-set dropdown">
                            <a href="manage_login">后台管理</a>
                        </div>
                        <%
                            }
                        %>
                        <div class="header-bottom-set dropdown">
                            <a href="user?action=logout">退出</a>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
                <!-- Header Action End -->
            </div>
        </div>
    </div>
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.jsp"><img width="280px" src="assets/images/logo/logo.png"
                                                 alt="Site Logo"/></a>
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
<!-- Header Area End  -->

<!-- OffCanvas Cart Start -->

<!-- OffCanvas Cart End -->

<!-- OffCanvas Menu Start -->

<!-- OffCanvas Menu End -->


<!-- breadcrumb-area start -->


<!-- breadcrumb-area end -->

<!-- Cart Area Start -->
<div class="cart-main-area pt-100px pb-100px">
    <div class="container">
        <h3 class="cart-page-title">Your cart items</h3>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <form action="#">
                    <div class="table-content table-responsive cart-table-content">
                        <table>
                            <thead>
                            <tr>
                                <th>图片</th>
                                <th>家居名</th>
                                <th>单价</th>
                                <th>数量</th>
                                <th>金额</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <%
                                Cart cart = (Cart) session.getAttribute("cart");
                                if (cart == null) {
                                    cart = new Cart();
                                    session.setAttribute("cart", cart);
                                }
                            %>
                            <tbody>
                            <%
                                for (CartItem cartItem : cart.getItems()) {
                            %>
                            <tr>
                                <td class="product-thumbnail">
                                    <a href="#"><img class="img-responsive ml-3" src="<%=cartItem.getPath()%>"
                                                     alt=""/></a>
                                </td>
                                <td class="product-name"><a href="#"><%=cartItem.getName()%>
                                </a></td>
                                <td class="product-price-cart"><span class="amount">￥<%=cartItem.getPrice()%></span>
                                </td>
                                <td class="product-quantity">
                                    <div class="cart-plus-minus">
                                        <input class="cart-plus-minus-box" type="text" name="qtybutton"
                                               value="<%=cartItem.getCount()%>"/>
                                        <input type="hidden" value="<%=cartItem.getId()%>" name="id"/>
                                    </div>
                                </td>
                                <td class="product-subtotal"><%=cartItem.getTotalPrice()%>
                                </td>
                                <td class="product-remove">
                                    <a href="#"><i class="icon-close"></i></a>
                                    <input type="hidden" value="<%=cartItem.getId()%>" name="id"/>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="cart-shiping-update-wrapper">
                                <h4>共<%=cart.getTotalCount()%>件商品 总价 <%=cart.getTotalPrice()%>元</h4>
                                <div class="cart-shiping-update">
                                    <a id="pay">购 物 车 结 账</a>
                                </div>
                                <div class="cart-clear">
                                    <button>继 续 购 物</button>
                                    <a id="clear-cart">清 空 购 物 车</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Cart Area End -->
<!-- Payment Modal Start -->
<div id="payment-modal"
     style="display:none; position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:300px; padding:20px; background:white; box-shadow:0px 0px 10px rgba(0, 0, 0, 0.1); z-index:1000;">
    <h4>支付确认</h4>
    <p>请确认您已完成支付</p>
    <img src="assets/images/pay-img/pay.jpg" alt="支付图片" style="width:100%; height:auto;"/>
    <button id="payment-done" style="margin-top:20px;">我已支付</button>
</div>
<!-- Payment Modal End -->
<div id="modal-overlay"
     style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0, 0, 0, 0.5); z-index:999;"></div>

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
                                        <li class="li"><a class="single-link" href="checkout.jsp">结账</a></li>
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