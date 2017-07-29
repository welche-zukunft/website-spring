angular.module('main', [ 'ngRoute' ])


// ROUTING CONFIGURATION


.config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home',
		controllerAs: 'controller'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation',
		controllerAs: 'controller'
	})
	.when('/anmeldungen', {
		templateUrl : 'anmeldungen.html',
		controller : 'anmeldungen',
		controllerAs: 'controller'
	})
	.when('/workshops', {
		templateUrl : 'workshops.html',
		controller : 'workshops',
		controllerAs: 'controller'
	})
	.when('/changecontents/:param1', {
		templateUrl : 'changecontents.html',
		controller : 'changecontents',
		controllerAs: 'controller'
	})
	.when('/mailing', {
		templateUrl : 'mailing.html',
		controller : 'mailing',
		controllerAs: 'controller'
	}).otherwise('/login');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

})


// DEFINE SERVICES

.factory('ChangeContentService', ['$http', '$q', function($http, $q){
 
    var REST_SERVICE_URI = 'http://welchezukunft.org:8080/admin';
 
    var factory = {
    	changeContent : changeContent,
    	getWorkshop : getWorkshop,
    	getWorkshops : getWorkshops,
    	getUsers : getUsers,
    	changeUser : changeUser
    };
 
    return factory;
 
 
    function changeContent(workshop) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/changecontents/", workshop)
            .then(
            function (response) {
                console.log('Success on changing content');
                deferred.resolve(response.data);
                console.log('response data : ' + response.data.kurzinfo);
            },
            function(errResponse){
                console.error('Error while changing content');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function getWorkshop(id) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/getworkshop/" + id + "/")
            .then(
            function (response) {
                console.log('Success on getting workshop');
                deferred.resolve(response.data);
                console.log('response data : ' + response.data.kurzinfo);
            },
            function(errResponse){
                console.error('Error while getting workshop ');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    
    function getWorkshops() {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/getworkshops/")
            .then(
            function (response) {
                console.log('Success on getting workshops');
                deferred.resolve(response.data);
                console.log('response data : ' + response.data[1].titel);
            },
            function(errResponse){
                console.error('Error while getting workshop ');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    
    
    function getUsers() {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/getanmeldungen/")
            .then(
            function (response) {
                console.log('Success on getting users');
                deferred.resolve(response.data);
                console.log('response data : ' + response.data);
            },
            function(errResponse){
                console.error('Error while getting users ');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function changeUser(user) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/changeuser/", user)
            .then(
            function (response) {
                console.log('Success on changing user');
                deferred.resolve(response.data);
                console.log('response data : ' + response);
            },
            function(errResponse){
                console.error('Error while changing user ');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
}])


// DEFINE CONTROLLERS


		
	


.controller('home', function($http) {
		console.log("Home...");
		var self = this;
})

.controller('workshops', function($http) {
		console.log("workshops...");
		var self = this;
})




