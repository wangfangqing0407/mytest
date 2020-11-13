var formApp = angular.module('formApp', []);

formApp.controller("myForm", function($scope, $http) {
    $scope.user= {};
    $scope.submitForm = function() {
        /*var user = $scope.user;
        $http({
            method: 'POST',
            url: '/lsbk/insert',
            data: $scope.user,
        }).then(
            function successCallback(response) {
                console.log(response.data.succes);
        },
            function errorCallback(response) {
                console.log(response);
        });*/
    };
});
