angular.module('MusicCabinetApp.RegisterController', []).
    controller('RegisterController', function ($scope, $http, $state, $cookieStore, $location) {
        $scope.registerUser = {};
        var serverAdd = $location.protocol()+"://"+$location.host()+":"+ $location.port();
        $scope.error = false;

        $scope.submit = function () {
            if ($scope.registerUser.password == $scope.registerUser.c_password) {

                console.log($scope.registerUser);
                var parameter = JSON.stringify($scope.registerUser);
                $http.post(serverAdd+'/restful-services/api/createUser', parameter)
                    .success(function (data, status) {
                        if (status == 200) {
                            $scope.error = false;
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
                        $scope.error = true;
                    });
            } else {
                $scope.error = true;
            }
        };

        $scope.goToLogin = function () {
            $state.go('login');
        }
});
