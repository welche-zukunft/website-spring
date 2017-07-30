angular.module('main').controller('workshops', ['ChangeContentService', function(ChangeContentService) {
	console.log('workshops ');
	var self = this;
	
	self.workshops = [];
	
	getWorkshops();
	
	self.setActiveWorkshop = setActiveWorkshop;
	
	
	
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
				console.log(result);
			},
			function(errResponse){
				console.error('Error while getting Teilnehmer');
			}
		);



	}
	
}]);