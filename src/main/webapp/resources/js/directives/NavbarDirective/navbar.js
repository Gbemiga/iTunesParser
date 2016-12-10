angular.module('MusicCabinetApp.navbar',[]).
    directive('navbar', function($state, $cookieStore, Services, $http, $location, $rootScope){
    return{
        restrict:'E',
        scope : {
            loggedIn: '=',
            libraries: '=',
            libraryMode: '='
            // search: '&'
        },
        link:function(scope, element, attrs){
            console.log(scope.loggedIn);
            console.log(scope.libraries);

            var serverAdd = $location.protocol()+"://"+$location.host()+":"+ $location.port();

            scope.loginPage = function(){
                $state.go('login');
            };

            scope.trackView = function(){
                $rootScope.currentView = true;
                console.log($rootScope.currentView);
            };

            scope.playlistView = function(){
                $rootScope.currentView = false;
                console.log($rootScope.currentView);
            };

            if($rootScope.currentView === undefined){
                $rootScope.currentView = true;
            }

            scope.registerPage = function(){
                $state.go('register');
            };

            scope.profile = function(){
                $state.go('profile');
            };

            scope.logout = function(){
                $cookieStore.remove('id');
                $cookieStore.remove('user');
                $cookieStore.remove('libraryId');
                $state.go('product');
                $scope.loggedIn = false;
            };

            scope.openNav = function () {
                    document.getElementById("mySidenav").style.width = "250px";
            };

            scope.closeNav = function () {
                    document.getElementById("mySidenav").style.width = "0";
            };

            scope.showLibrary = function (libraryId) {
                $http.post(serverAdd+'/restful-services/api/track/getLibraryTracks', JSON.stringify(libraryId))
                    .success(function (data, status) {
                        if (status == 200) {
                            scope.userTracks = data;
                            Services.setTracks(data);
                            $cookieStore.put('libraryId',libraryId);
                            if($state.$current=='library') {
                                $state.reload('library');
                            }else {
                                $state.go('library');
                            }
                            return data;
                        }
                    })
                    .error(function (error) {
                        alert("An error has occurred");
                    });
            };

            $rootScope.global = {
                search: ''
            };
            scope.searchWord = '';
            scope.doSearch = function () {
                $rootScope.global.search = scope.searchWord;
            };

            scope.uploadFile = function(file) {
                scope.userId = $cookieStore.get('id');
                scope.f = file;
                if (file) {
                    scope.params = {
                      "file": file, 'userId': scope.userId
                    };
                    $http.post(serverAdd+'/restful-services/api/uploadFile', JSON.stringify(scope.params))
                        .success(function (data, status) {
                            if (status == 200) {
                                alert(data);
                                console.log(data);
                                $state.go('dashboard');
                            }
                        })
                        .error(function (error) {
                            // alert("An error has occurred");
                        });
                }
            }

        },
        templateUrl:'resources/js/directives/NavbarDirective/navbar.html',
        replace:true
    }
});
