angular.module('MusicCabinetApp.LoginController', []).
    controller('LoginController', function ($scope, $state, $http, $cookieStore, $location) {
        var serverAdd = $location.protocol()+"://"+$location.host()+":"+ $location.port();
        $scope.loginUser = {};
        $scope.goToRegister = function () {
            $state.go('register');
        };
        $cookieStore.remove('id');
        console.log($cookieStore.get('id'));
        $scope.submit = function () {

                console.log($scope.loginUser);

                $http.post(serverAdd+'/restful-services/api/getUser', JSON.stringify($scope.loginUser))
                    .success(function (data, status) {
                        if (status == 200) {
                            alert("User Account Successfully Created");
                            alert(data);
                            console.log(data);

                            $scope.Verifieduser = data;
                            console.log($scope.Verifieduser);
                            console.log($scope.Verifieduser.user);
                            console.log($scope.Verifieduser.user.id, "ID");
                            if($scope.Verifieduser.user != 0) {
                                $cookieStore.put('id',$scope.Verifieduser.user.id);
                                $cookieStore.put('user',$scope.Verifieduser.user);
                                sessionStorage.id = $scope.Verifieduser.user.id;
                                sessionStorage.user = $scope.Verifieduser.user;
                                $state.go('dashboard', {
                                    user: $scope.Verifieduser
                                });
                            }
                        }
                    })
                    .error(function (error) {
                        alert("An error has occurred");
                    });
        };
});
