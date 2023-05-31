<%--
  Created by IntelliJ IDEA.
  User: 10762
  Date: 2022/5/8
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>$Title$</title>
  <!-- 新 Bootstrap 核心 CSS 文件 -->
  <link href="https://cdn.bootcss.com/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

  <!-- jQuery文件。务必在bootstrap.min.js 之前引入，bootstrap.js 基于 jQuery 实现 -->
  <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>

  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

  <script type="text/javascript">

    $(function () {
      $.ajax({
        url: 'getAllOrderFood',
        type: 'get',
        success: function (data) {
          console.log(data);
        }
      })
    })

    function getData1() {
      $.ajax({
        url: 'checkToken',
        type: 'get',
        data: {
          "token": JSON.parse(localStorage.getItem("token"))
        },
        success: function (data) {
          console.log(data);
        }
      })
    }

    function getData2() {
      $.ajax({
        url: 'getFoodList',
        type: 'get',
        dataType: 'json',
        data: {
          query: '',
          pageNum: 1,
          pageSize: 5
        },
        success: function (data) {
          console.log(data);
        }
      })
    }
  </script>
</head>
<body>

  <button onclick="getData1()">获取数据1</button>
  <button onclick="getData2()">获取数据2</button>


</body>
</html>
