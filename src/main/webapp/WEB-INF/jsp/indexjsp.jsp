<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/12
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="http://cdn.static.runoob.com/libs/angular.js/1.4.6/angular.min.js"></script>

</head>
<body>
hello world jspp
<div ng-app="">
    <p>名字 : <input type="text" ng-model="name"></p>
    <h1>Hello {{name}}</h1>
    <p>我的第一个表达式： {{ 5 + 5 }}</p>
</div>
<!--<div ng-app="myApp" ng-controller="myCtrl">
    {{ firstName + " " + lastName }}
</div>-->
<div ng-controller="UserController">
    用户名：<input type="text" ng-model="name" placeholder="用户名"/>
    密码：<input type="password" ng-model="pword" placeholder="密码"/>
    <button ng-click="login()">提交</button>
    <p>您输入的用户名：{{name}}</p>
    <p>您输入的密码：{{pword}}</p>
</div>
<div ng-app="myApp" ng-controller="todoCtrl">
    <h2>我的备忘录</h2>

    <form ng-submit="todoAdd()">
        <input type="text" ng-model="todoInput" size="50" placeholder="新增">
        <input type="submit" value="新增">
    </form>

    <br>

    <div ng-repeat="x in todoList">
        <input type="checkbox" ng-model="x.done"> <span ng-bind="x.todoText"></span>
    </div>

    <p><button ng-click="remove()">删除记录</button></p>
</div>
<script type="text/javascript" th:src="@{/js/index.js}"></script>
<script>
    var app = angular.module('myApp', []);
    app.controller('todoCtrl', function($scope) {
        $scope.todoList = [{todoText:'Clean House', done:false}];

        $scope.todoAdd = function() {
            $scope.todoList.push({todoText:$scope.todoInput, done:false});
            $scope.todoInput = "";
        };

        $scope.remove = function() {
            var oldList = $scope.todoList;
            $scope.todoList = [];
            angular.forEach(oldList, function(x) {
                if (!x.done) $scope.todoList.push(x);
            });
        };
    });
</script>
</body>
</body>
</html>
