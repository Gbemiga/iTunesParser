angular.module('MusicCabinetApp', [
    'ngRoute',
    'ngSanitize',
    'chart.js',
    'ui.router',
    'ngCookies',
    'ngFileUpload',
    'MusicCabinetApp.ProductController',
    'MusicCabinetApp.navbar',
    'MusicCabinetApp.onReadFile',
    'MusicCabinetApp.RegisterController',
    'MusicCabinetApp.LoginController',
    'MusicCabinetApp.DashboardController',
    'MusicCabinetApp.LibraryController',
    'MusicCabinetApp.Services'
]).
config(function($stateProvider, $urlRouterProvider) {

    // For any unmatched url, redirect to /state1
    $urlRouterProvider.otherwise("/product");
    //
    // Now set up the states
    $stateProvider
        .state('product', {
            url: "/product",
            templateUrl: "resources/js/views/product.html",
            controller:'ProductController'
        })
        .state('register', {
            url: "/account/register",
            templateUrl: "resources/js/views/register.html",
            controller:'RegisterController'
        })
        .state('login', {
            url: "/account/login",
            templateUrl: 'resources/js/views/login.html',
            controller: 'LoginController'
        })
        .state('library', {
            url: "/account/library",
            templateUrl: 'resources/js/views/library.html',
            controller: 'LibraryController'
        })
        .state('dashboard', {
            url: "/account/dashboard",
            templateUrl: 'resources/js/views/dashboard.html',
            controller: 'DashboardController',
            params : { user: null, }
        });
});


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

angular.module('MusicCabinetApp.ProductController', []).
    controller('ProductController', function ($scope, $http, $cookieStore, $state, $location) {
    if($cookieStore.get('id')){
        $state.go('dashboard');
    }else {
        console.log($location.protocol()+"://"+$location.host()+":"+ $location.port());
    }
});
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

angular.module('MusicCabinetApp.onReadFile',[]).
    directive('onReadFile', function($parse){
    return {
        restrict: 'A',
        scope: false,
        link: function(scope, element, attrs) {
            var fn = $parse(attrs.onReadFile);

            element.on('change', function(onChangeEvent) {
                var reader = new FileReader();

                reader.onload = function(onLoadEvent) {
                    scope.$apply(function() {
                        fn(scope, {$fileContent:onLoadEvent.target.result});
                    });
                };

                reader.readAsText((onChangeEvent.srcElement || onChangeEvent.target).files[0]);
            });
        }
    };
});

angular.module('MusicCabinetApp.Services', []).
service('Services', function () {
        var TrackList = [];
        var searchKeyword;
        var trackView;

        var addTrack = function(newObj) {
            TrackList.push(newObj);
        };

        var getTracks = function(){
            return TrackList;
        };

        var setTracks = function(allTracks){
            TrackList = allTracks;
                };
        var getsearchKeyword = function(){
            return searchKeyword;
        };

        var setsearchKeyword = function(Keyword){
            searchKeyword = Keyword;
        };

        var getTrackView = function(){
            return trackView;
        };

        var setTrackView = function(x){
            trackView = x;
        };

        return {
            addTrack: addTrack,
            getTracks: getTracks,
            setTracks: setTracks,
            getsearchKeyword: getsearchKeyword,
            setsearchKeyword: setsearchKeyword,
            getTrackView: getTrackView,
            setTrackView: setTrackView
        };
});