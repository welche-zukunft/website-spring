angular.module('main', [ 'ngRoute', 'ngFileUpload' ])


// ROUTING CONFIGURATION


.config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home',
		controllerAs: 'ctrl'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation',
		controllerAs: 'controller'
	})
	.when('/anmeldungen', {
		templateUrl : 'anmeldungen.html',
		controller : 'anmeldungen as ctrl',
		controllerAs: 'controller'
	})
	.when('/workshops', {
		templateUrl : 'workshops.html',
		controller : 'workshops as ctrl',
		controllerAs: 'controller'
	})
	.when('/changecontents/:param1', {
		templateUrl : 'changecontents.html',
		controller : 'changecontents as ctrl',
		controllerAs: 'controller'
	})
	.when('/mailing', {
		templateUrl : 'mailing.html',
		controller : 'mailing',
		controllerAs: 'ctrl'
	})
	.when('/uploads', {
		templateUrl : 'uploads.html',
		controller : 'uploads',
		controllerAs: 'ctrl'
	}).otherwise('/login');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

})


// DEFINE SERVICES

.factory('ChangeContentService', ['$http', '$q', '$location', function($http, $q, $location){
 
    var REST_SERVICE_URI = '//' + $location.$$host + ':' + $location.$$port + '/admin';
 
    var factory = {
    	changeWorkshop : changeWorkshop,
    	getWorkshop : getWorkshop,
    	getWorkshops : getWorkshops,
    	getUsers : getUsers,
    	changeUser : changeUser,
    	deleteUser : deleteUser,
    	sendMail : sendMail,
    	getNewUsers : getNewUsers,
    	getFiles : getFiles,
    	getEvents : getEvents,
    	removeEvent : removeEvent
    };
 
    return factory;
 
 
    function changeWorkshop(workshop) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/changeworkshop/", workshop)
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
    
    
    
    function getUsers(filter, workshopId) {
    	
    	if(typeof workshopId === "undefined") {
    		workshopId = "";
    	}
    	
    	
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/getusers/" + filter + "/" + workshopId )
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
    	console.log('in change User service');
    	console.log(user);
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
    
    function deleteUser(user) {
    	console.log('in delete User service');
    	console.log(user);
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/deleteuser/", user)
            .then(
            function (response) {
                console.log('Success on delete user');
                deferred.resolve(response.data);
                console.log('response data : ' + response);
            },
            function(errResponse){
                console.error('Error while delete user ');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function sendMail(mail) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/sendmail/", mail)
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
    
    
    function getNewUsers() {
    	
    	if(typeof workshopId === "undefined") {
    		workshopId = "";
    	}
    	
    	
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/getnewusers/" )
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

	

	function getFiles() {
	
		var deferred = $q.defer();
		$http.post(REST_SERVICE_URI + "/getfiles/" )
        .then(
        	function (response) {
        		console.log('Success on getting files');
        		deferred.resolve(response.data);
        		console.log('response data : ' + response.data);
        	},
        	function(errResponse){
        		console.error('Error while getting files ');
            	deferred.reject(errResponse);
        	}
        );
    	return deferred.promise;
	}
	
	function getEvents(id) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/getevents/" + id + "/")
            .then(
            function (response) {
                console.log('Success on getting events');
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while getting events ');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
	
	
	function removeEvent(event) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI + "/deleteevent/", event)
            .then(
            function (response) {
                console.log('Success on deleting events');
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting events ');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
	


 
}])


// DEFINE CONTROLLERS


		
	



