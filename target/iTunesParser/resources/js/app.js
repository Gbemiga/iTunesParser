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

