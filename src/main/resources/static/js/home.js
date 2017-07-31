angular.module('main').controller('home', ['ChangeContentService', '$scope', function(ChangeContentService, $scope) {
	console.log('home ');
	var self = this;	


	self.getNewUsers = getNewUsers;
	
	self.sortType = 'datum';
	self.sortReverse = false;
	
	self.setBearbeitet = setBearbeitet;
	
	self.users = [];
	
	getNewUsers();
	
	
	function getNewUsers(){
		ChangeContentService.getNewUsers()
    	.then(
    	function(result){
    		self.users = result;
    		console.log(result);
    		if(!$scope.$$phase) {
    			$scope.$apply();
    		}
    	},
    	function(errResponse){
        	console.error('Error while getting Wartende');
        	if(!$scope.$$phase) {
        		$scope.$apply();
        	}
    	}
    );
	}
	
	function setBearbeitet(user){
		user.stand = "BEARBEITET";
		
		ChangeContentService.changeUser(user)
        .then(
        function(result){
            console.log(result);
            getNewUsers();
        },
        function(errResponse){
            console.error('Error while changing user');
            getNewUsers();
        }
        );
	}

	
}])
