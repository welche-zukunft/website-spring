angular.module('main').controller('workshops', ['ChangeContentService', '$scope', function(ChangeContentService, $scope) {
	console.log('workshops ');
	var self = this;
	
	self.workshops = [];
	
	getWorkshops();
	
	self.setActiveWorkshop = setActiveWorkshop;
	
	
	self.teilnehmer = [];
	self.wartende = [];
	self.changeUser = changeUser;
	
	
	
	function getWorkshops(){
    	console.log('get workshop...');
        ChangeContentService.getWorkshops()
            .then(
            function(result){
            	self.workshops = result;
                console.log(result);
            },
            function(errResponse){
                console.error('Error while getting Workshops');
            }
        );
    }
	
	
	
	function setActiveWorkshop(workshop){
		
		
		self.workshop = workshop;
		
		ChangeContentService.getUsers('WORKSHOP', workshop.id)
        	.then(
        	function(result){
        		self.teilnehmer = result;
        		if(!$scope.$$phase) {
            		$scope.$apply();
        		}
        		getWorkshops();
        		console.log(result);
        	},
        	function(errResponse){
            	console.error('Error while getting Wartende');
        	}
        );
		
		ChangeContentService.getUsers('WARTELISTE', workshop.id)
			.then(
			function(result){
				self.wartende = result;
				if(!$scope.$$phase) {
            		$scope.$apply();
        		}
				console.log(result);
			},
			function(errResponse){
				console.error('Error while getting Teilnehmer');
			}
		);


	}
	
	
	
	function changeUser(user){
		user.status = 'ZUGELASSEN';
		
		console.log('change user...');
		ChangeContentService.changeUser(user)
        .then(
        function(result){
            console.log(result);
            setActiveWorkshop(self.workshop);
        },
        function(errResponse){
            console.error('Error while changing user');
            setActiveWorkshop(self.workshop);
        }
        );
	}
	
}]);