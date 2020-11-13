class ReactDemo extends React.Component{

    constructor() {
        super();
        this.state = {
            flag : true,
        };
        this.handlerClick = this.handlerClick.bind(this);
        this.submitMsg = this.submitMsg.bind(this);
    };

    render() {
        let text = this.state.flag ? "true" : "false";
        return(
            <div>
                <h1>Hello {this.props.value}!</h1>
                <h2>{text}</h2>
                <button  onClick={this.handlerClick}>{text}</button>
                <button  onClick={this.submitMsg}>提交</button>
            </div>
        );
    };

    handlerClick() {
        this.setState({
            flag : !this.state.flag
        });
    };

    submitMsg() {
        let po = {
            name: "jack",
            phone: "123456",
            sfzh : "456789",
            hjdxz : "xxxxx"
        };
        let myHeaders = new Headers();
        myHeaders.append('Content-Type', 'application/json');
        fetch('/admin/lsbk', {
            method: 'post',
            headers:myHeaders,
            body: JSON.stringify(po),
        }).then(function(response) {
            console.log( response.text());
        }).catch(function(text) {
            console.log(text);
        });
        /*$.ajax({
            url:"/admin/lsbk",
            type:"post",
            contentType : "application/json",
            data:JSON.stringify(po),
            dataType:"json",
            success:function(data){
                //成功后的回调函数
            },
            error:function(){
            }
        });*/
    }
}

ReactDOM.render(
    <ReactDemo value="Jhon"/>,
    document.getElementById("root")
);











/*
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
app.controller('myCtrl', function($scope) {
    $scope.firstName= "John";
    $scope.lastName= "Doe";
});*/
