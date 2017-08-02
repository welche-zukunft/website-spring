angular.module('main').controller('workshops', ['ChangeContentService', '$scope', function(ChangeContentService, $scope) {
	console.log('workshops ');
	var self = this;
	
	self.workshops = [];
	self.changeWorkshop = changeWorkshop;
	
	self.workshop={
			id: 0,
			leiterIn:'',
			titel:'',
			logline:'', 
			kurzinfo:'', 
			intro:'', 
			cvWorkshopleiterIn:'', 
			moderatorIn:'', 
			cvModeratorIn:'',
			max : 0,
			blockiert : 0,
			warteliste : 0,
			belegt : 0,
			anmerkungen : ''
	};

	
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
	
	
	
	function changeWorkshop(workshop){
    	console.log('Change contnent...');
        ChangeContentService.changeWorkshop(workshop)
            .then(
            function(errResponse){
                console.error('Error while changing content 2');
            }
        );
    }
	
}]);