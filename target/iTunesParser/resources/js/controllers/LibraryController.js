angular.module('MusicCabinetApp.LibraryController', []).
    controller('LibraryController', function ($scope, $http, $rootScope, $cookieStore, $state, $location, Services){
        if(!$cookieStore.get('id')){
            $state.go('login');
        }else {
            $scope.mainUser = $cookieStore.get('user');
            console.log($scope.mainUser.id, "Profile");
        }
        var serverAdd = $location.protocol()+"://"+$location.host()+":"+ $location.port();

        $scope.userTracks= Services.getTracks();

        if($scope.userTracks.length == 0){
            $scope.libraryId = $cookieStore.get('libraryId');
            $http.post(serverAdd+'/restful-services/api/track/getLibraryTracks', JSON.stringify($scope.libraryId))
                .success(function (data, status) {
                    if (status == 200) {
                        $scope.userTracks = data;
                        return data;
                    }
                })
                .error(function (error) {
                    alert("An error has occurred");
                });
        }

        if(!$rootScope.currentView){
                    $scope.libraryId = $cookieStore.get('libraryId');
                    $http.post(serverAdd+'/restful-services/api/track/getLibraryPlaylists', JSON.stringify($scope.libraryId))
                        .success(function (data, status) {
                            if (status == 200) {
                                $scope.playlists = data;
                                return data;
                            }
                        })
                        .error(function (error) {
                            alert("An error has occurred");
                        });
        }

        $scope.userId = $cookieStore.get('id');

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

        $scope.removeTrack = function (track) {
            var index = $scope.userTracks.indexOf(track);
            $scope.userTracks.splice(index, 1);
        };
});