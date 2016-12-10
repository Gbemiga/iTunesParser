angular.module('MusicCabinetApp.ProductController', []).
    controller('ProductController', function ($scope, $http, $cookieStore, $state, $location) {
    if($cookieStore.get('id')){
        $state.go('dashboard');
    }else {
        console.log($location.protocol()+"://"+$location.host()+":"+ $location.port());
    }
});