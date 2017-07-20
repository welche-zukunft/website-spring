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
	.when('/changecontents', {
		templateUrl : 'changecontents.html',
		controller : 'changecontents',
		controllerAs: 'controller'
	})
	.when('/mailing', {
		templateUrl : 'mailing.html',
		controller : 'mailing',
		controllerAs: 'controller'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

})


// DEFINE SERVICES

.factory('ChangeContentService', ['$http', '$q', function($http, $q){
 
    var REST_SERVICE_URI = 'http://localhost:8080/admin/changecontents/';
 
    var factory = {
    	changeContent : changeContent
    };
 
    return factory;
 
 
    function changeContent(workshop) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, workshop)
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
 
}])


// DEFINE CONTROLLERS


.controller('navigation',

		function($rootScope, $http, $location, $route) {
	
			console.log("init...");
			
			var self = this;

			self.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			var authenticate = function(credentials, callback) {

				var headers = credentials ? {
					authorization : "Basic "
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};

				$http.get('user', {
					headers : headers
				}).then(function(response) {
					if (response.data.name) {
						$rootScope.authenticated = true;
					} else {
						$rootScope.authenticated = false;
					}
					callback && callback($rootScope.authenticated);
				}, function() {
					$rootScope.authenticated = false;
					callback && callback(false);
				});

			}
			
			authenticate();

			self.credentials = {};
			self.login = function() {
				authenticate(self.credentials, function(authenticated) {
					if (authenticated) {
						console.log("Login succeeded")
						$location.path("/admin");
						self.error = false;
						$rootScope.authenticated = true;
					} else {
						console.log("Login failed")
						$location.path("/login");
						self.error = true;
						$rootScope.authenticated = false;
					}
				})
			};

			self.logout = function() {
				$http.post('logout', {}).finally(function() {
					$rootScope.authenticated = false;
					$location.path("/");
					$route.reload();
				});
			}

})
		
	


.controller('home', function($http) {
		console.log("Home...");
		var self = this;
})

.controller('workshops', function($http) {
		console.log("workshops...");
		var self = this;
})

.controller('anmeldungen', function($http) {
		console.log("anmeldungen...")
		var self = this;
})





