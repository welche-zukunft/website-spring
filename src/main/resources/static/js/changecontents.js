angular.module('main').controller('changecontents', ['ChangeContentService', '$routeParams', function(ChangeContentService, $routeParams) {
	var self = this;
	
	console.log("change contents " + $routeParams.param1);
	
	getWorkshop($routeParams.param1);
		
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
			max : 10,
			blockiert : 0,
			warteliste : 0,
			belegt : 0
	};
	
    self.events=[1,2,3,4,5];
 
    self.submit = submit;
    self.submitEvent = submitEvent;
    self.addEvent = addEvent;
    self.removeEvent = removeEvent;


    
    function submit() {
            console.log('Saving workshop', self.workshop);
            changeWorkshop(self.workshop);
    }

    
    function submitEvent() {
    	console.log("Hallo i bims 1 Event");
    }
    function removeEvent() {
    	console.log("Hallo i bims k1 Event");
    }
    function addEvent() {
    	console.log("Hallo i bims add Event");
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
    
    function getWorkshop(id){
    	console.log('get workshop...');
        ChangeContentService.getWorkshop(id)
            .then(
            function(result){
            	self.workshop = result;
                console.log(result.leiterIn);
            },
            function(errResponse){
                console.error('Error while getting Workshop');
            }
        );
    }
    
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

}]);