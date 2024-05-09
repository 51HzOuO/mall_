<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>韩顺平教育-家居网购</title>
    <!-- 移动端适配 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="../../assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="../../assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="../../assets/css/style.min.css">
    <style>
        .product-thumbnail {
            position: relative; /* 设置为相对定位 */
            display: inline-block; /* 使容器适应内容的大小 */
        }

        .input-file {
            position: absolute;
            left: 0;
            top: 0;
            width: 100%; /* 宽度和高度调整为100%，以覆盖整个图片 */
            height: 100%;
            opacity: 0;
            cursor: pointer;
        }

        .product-thumbnail img {
            border-radius: 50%;
            width: 100px; /* 控制图片显示的大小 */
            height: 100px;
        }
    </style>

    <script src="../../assets/js/jquery-3.7.1.min.js"></script>
    <script>

        function validateForm() {
            var fileInput = document.getElementById('fileInput');
            var price = $('#priceInput').val();
            var sales = $('#salesInput').val();
            var stock = $('#stockInput').val();
            // 验证图片
            if (!fileInput.files.length || !['image/jpeg', 'image/png', 'image/gif'].includes(fileInput.files[0].type)) {
                alert('请选择图片文件（JPEG, PNG, GIF）');
                return false;
            }

            console.log('Price:', price);
            console.log('Sales:', sales);
            console.log('Stock:', stock);
            // 验证价格
            if (!/^\d*\.?\d*$/.test(price)) {
                alert('非法输入');
                return false;
            }

            // 验证销量和总量
            if (!/^\d+$/.test(sales) || !/^\d+$/.test(stock)) {
                alert('非法输入');
                return false;
            }

            return true;
        }

        function previewFile(input) {
            var file = input.files[0];
            if (file) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    document.getElementById('img-preview').src = e.target.result;
                };
                reader.readAsDataURL(file);
            }
        }

        $(function () {
            $('#addHomeForm').submit(function (event) {
                event.preventDefault(); // 阻止表单的默认提交行为

                if (!validateForm()) {
                    return; // 如果验证失败，则不提交表单
                }

                var furnitureName = $('#furnitureName').val(); // 获取家具名称
                var fileInput = document.getElementById('fileInput');
                var file = fileInput.files[0];

                if (file) {
                    // 创建新的 File 对象，文件名为家具名称
                    var newFile = new File([file], furnitureName + '.' + file.name.split('.').pop(), {type: file.type});

                    // 使用新文件对象创建 FormData
                    var formData = new FormData(this);
                    formData.set('imagefile', newFile); // 替换文件数据

                    // 打印新的文件名检查
                    console.log('New file name:', newFile.name);
                }

                $.ajax({
                    url: '<%=request.getContextPath()%>/manage', // 服务器端 URL
                    type: 'POST',
                    data: formData,
                    processData: false, // 告诉 jQuery 不处理发送的数据
                    contentType: false, // 告诉 jQuery 不设置内容类型头
                    success: function (response) {
                        alert(response);
                    },
                    error: function (xhr, status, error) {
                        console.error('Error:', error);
                        alert('上传失败: ' + error);
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
                        <a href="../../index.jsp"><img src="../../assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->

                <!-- Header Action Start -->
                <div class="col align-self-center">
                    <div class="header-actions">

                        <!-- Single Wedge Start -->
                        <div class="header-bottom-set dropdown">
                            <a href="<%=request.getContextPath() + "/manage?action=listFurn&page=1"%>">家居管理</a>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="order.jsp">订单管理</a>
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
                        <a href="../../index.jsp"><img width="280px" src="../../assets/images/logo/logo.png"
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
<!-- Cart Area Start -->
<div class="cart-main-area pt-100px pb-100px">
    <div class="container">
        <h3 class="cart-page-title">家居后台管理-添加家居</h3>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <form id="addHomeForm" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="addFurn"/>
                    <div class="table-content table-responsive cart-table-content">
                        <table>
                            <thead>
                            <tr>
                                <th>图片</th>
                                <th>家居名</th>
                                <th>商家</th>
                                <th>价格</th>
                                <th>销量</th>
                                <th>总量</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="product-thumbnail">
                                    <img src="../../assets/images/product-image/default.jpg" id="img-preview"
                                         class="img-responsive ml-3">
                                    <input type="file" name="imagefile" id="fileInput" class="input-file"
                                           onchange="previewFile(this);">
                                </td>
                                <td class="product-name"><input name="name" id="furnitureName" style="width: 60%"
                                                                type="text"/></td>
                                <td class="product-name"><input name="maker" style="width: 90%" type="text"/></td>
                                <td class="product-price-cart"><input name="price" id="priceInput" style="width: 90%"
                                                                      type="text"/></td>
                                <td class="product-quantity">
                                    <input name="sales" id="salesInput" style="width: 90%" type="text"/>
                                </td>
                                <td class="product-quantity">
                                    <input name="stock" id="stockInput" style="width: 90%" type="text"/>
                                </td>
                                <td>
                                    <!--                                    <a href="#"><i class="icon-pencil"></i></a>-->
                                    <!--                                    <a href="#"><i class="icon-close"></i></a>-->
                                    <input type="submit"
                                           style="width: 90%;background-color: silver;border: silver;border-radius: 20%;"
                                           value="添加家居"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
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
                                        <li class="li"><a class="single-link" href="../../cart.jsp">我的购物车</a></li>
                                        <li class="li"><a class="single-link" href="login.html">登录</a></li>
                                        <li class="li"><a class="single-link" href="wishlist.html">感兴趣的</a></li>
                                        <li class="li"><a class="single-link" href="../../checkout.jsp">结账</a></li>
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