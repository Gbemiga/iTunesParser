angular.module('MusicCabinetApp.DashboardController', []).
    controller('DashboardController', function ($scope, $http, $stateParams, $cookieStore, $state, $location) {
        if(!$cookieStore.get('id')){
            $state.go('login');
        }else {
            $scope.mainUser = $cookieStore.get('user');
            console.log($scope.mainUser.id, "Profile");
        }
        // $scope.userTracks=[];
        var serverAdd = $location.protocol()+"://"+$location.host()+":"+ $location.port();

            $scope.userId = $cookieStore.get('id');
            console.log($scope.userId);

            $http.post(serverAdd+'/restful-services/api/getUsersLibrary', JSON.stringify($scope.userId))
                .success(function (data, status) {
                    if (status == 200) {
                        console.log(data);

                        $scope.userLibraries = data;
                        return data;
                    }
                })
                .error(function (error) {
                    alert("An error has occurred");
                });


           $http.post(serverAdd+'/restful-services/api/track/getUserTracks', JSON.stringify($scope.userId))
                        .success(function (data, status) {
                            if (status == 200) {
                                console.log(data);

                                $scope.userTracks = data;
                                $scope.recentActivity = $scope.userTracks.slice(-8);
                                return data;
                            }
                        })
                        .error(function (error) {
                            alert("An error has occurred");
                        });



});