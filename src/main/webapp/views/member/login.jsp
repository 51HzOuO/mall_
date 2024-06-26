<%@ page isELIgnored="false" %>
<%--<%@ page contentType="text/html;charset=UTF-8" %>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>韩顺平教育-家居网购</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="../../assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="../../assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="../../assets/css/style.min.css"/>
    <script src="../../script/jquery-3.6.0.min.js"></script>
    <script>
        $(function () {
            $("#username").blur(function () {
                const username = $(this).val();
                if (username === "") {
                    $(".errorMsg").text("用户名不能为空").css("color", "red");
                    return;
                }
                $.ajax({
                    url: "../../user?action=checkUserName",
                    type: "post",
                    data: {
                        username: username
                    },
                    success: function (data) {
                        if (data === "ok") {
                            $(".errorMsg").text("用户名可用").css("color", "green");
                        } else if (data === 'format-error') {
                            $(".errorMsg").html("用户名格式不正确<br>4-16位字母数字下划线").css("color", "red");
                        } else {
                            $(".errorMsg").text(data).css("color", "red");
                        }
                    }
                })
            })
            $("#sub-btn").click(function (event) {
                event.preventDefault();  // 取消submit按钮的默认提交行为
                const username = $("#username").val();
                const password = $("#password").val();
                const repwd = $("#repwd").val();
                const email = $("#email").val();
                const code = $("#code").val();
                const regU = /^\w{4,16}$/
                const regP = /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\W_!@#$%^&*`~()-+=]+$)(?![0-9\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\W_!@#$%^&*`~()-+=]/
                const regE = /^[A-Za-z0-9一-龥]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
                if (username === "" || password === "" || repwd === "" || email === "" || code === "") {
                    $(".errorMsg").text("请填写完整信息").css("color", "red");
                    return;
                }
                if (!regU.test(username)) {
                    $(".errorMsg").html("用户名格式不正确<br>4-16位字母数字下划线").css("color", "red");
                    return;
                }
                if (!regP.test(password)) {
                    $(".errorMsg").html("密码格式不正确<br>大写字母，小写字母，数字，特殊符号 任意3项").css("color", "red");
                    return;
                }
                if (password !== repwd) {
                    $(".errorMsg").text("两次密码不一致").css("color", "red");
                    return;
                }
                if (!regE.test(email)) {
                    $(".errorMsg").text("邮箱格式不正确").css("color", "red");
                    return;
                }
                $.ajax({
                    url: "../../loginService",
                    type: "post",
                    data: JSON.stringify({
                        username: username,
                        password: password,
                        email: email,
                        code: code
                    }),
                    dataType: "json",
                    success: function (data) {
                        $(".errorMsg").text("注册成功,您的id是" + data.id + "!").css("color", "green");
                    },
                    error: function (xhr) {
                        $(".errorMsg").text(xhr.responseText).css("color", "red");
                    }
                })
            });
            $("#login-btn").click(function (event) {
                event.preventDefault();  // 取消submit按钮的默认提交行为
                const username = $("input[name='user-name']").val();
                const password = $("input[name='user-password']").val();
                if (username === "" || password === "") {
                    $(".errorMsg1").text("请填写完整信息").css("color", "red");
                    return;
                }
                let regU = /^\w{4,16}$/
                let regP = /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\W_!@#$%^&*`~()-+=]+$)(?![0-9\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\W_!@#$%^&*`~()-+=]/
                if (!regU.test(username)) {
                    $(".errorMsg1").html("用户名格式不正确").css("color", "red");
                    return;
                }
                if (!regP.test(password)) {
                    $(".errorMsg1").html("密码格式不正确").css("color", "red");
                    return;
                }
                $.ajax({
                    url: "../../loginService",
                    type: "post",
                    data: JSON.stringify({
                        username: username,
                        password: password
                    }),
                    success: function (data) {
                        //重定向到login_ok.html
                        if (data === "ok")
                            window.location.href = "../../login_ok.jsp";
                        else {
                            $(".errorMsg1").text(data).css("color", "red");
                            $("#login-password").val("");
                        }
                    }
                })
            })
        })
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
                        <a href="../../index.jsp"><img src="../../assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->
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
                        <a href="index.html"><img width="280px" src="../../assets/images/logo/logo.png"
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
<!-- login area start -->
<div class="login-register-area pt-70px pb-100px">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 col-md-12 ml-auto mr-auto">
                <div class="login-register-wrapper">
                    <div class="login-register-tab-list nav">
                        <a class="active" data-bs-toggle="tab" href="#lg1">
                            <h4>会员登录</h4>
                        </a>
                        <a data-bs-toggle="tab" href="#lg2">
                            <h4>会员注册</h4>
                        </a>
                    </div>
                    <div class="tab-content">
                        <div id="lg1" class="tab-pane active">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <form method="post">
                                        <span class="errorMsg1"
                                              style="float: right; font-weight: bold; font-size: 20pt; margin-left: 10px;"></span>
                                        <input type="text" name="user-name" placeholder="Username"/>
                                        <input type="password" name="user-password" placeholder="Password"
                                               id="login-password"/>
                                        <div class="button-box">
                                            <div class="login-toggle-btn">
                                                <input type="checkbox"/>
                                                <a class="flote-none" href="javascript:void(0)">Remember me</a>
                                                <a href="#">Forgot Password?</a>
                                            </div>
                                            <button type="submit" id="login-btn"><span>Login</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div id="lg2" class="tab-pane">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <span class="errorMsg"
                                          style="float: right; font-weight: bold; font-size: 20pt; margin-left: 10px;"></span>
                                    <form>
                                        <input type="text" id="username" name="user-name" placeholder="Username"/>
                                        <input type="password" id="password" name="user-password"
                                               placeholder="输入密码"/>
                                        <input type="password" id="repwd" name="user-password" placeholder="确认密码"/>
                                        <input name="user-email" id="email" placeholder="电子邮件" type="email"/>
                                        <input type="text" id="code" name="user-name" style="width: 50%" id="code"
                                               placeholder="验证码" 　　>
                                        <img alt=""
                                             src="../../assets/images/code/code.bmp">四位数即可通过
                                        <div class="button-box">
                                            <button type="submit" id="sub-btn"><span>会员注册</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- login area end -->

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
                                        <li class="li"><a class="single-link" href="login">登录</a></li>
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
<script src="../../assets/js/vendor/vendor.min.js"></script>
<script src="../../assets/js/plugins/plugins.min.js"></script>
<!-- Main Js -->
<script src="../../assets/js/main.js"></script>
</body>
</html>