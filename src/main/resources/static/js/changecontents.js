angular.module('main').controller('changecontents', ['ChangeContentService', '$routeParams', '$scope', 'Upload', '$http', function(ChangeContentService, $routeParams, $scope, Upload, $http) {
	var self = this;
	
	console.log("change contents " + $routeParams.param1);
	
	getWorkshop($routeParams.param1);
	
	self.newTupel = {
		event : { ueberschrift : '', jahr : '', text : '', embedcode : '', workshopId : 0},
		file : null
	}

	self.eventTupels = [];
		
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
	
	self.newEvent={
		id : 0,
		ueberschrift : 'sdfds',
		jahr : '1999',
		text : 'blubbfischifischi',
		embedcode : 'nix'
	};
 
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
                getEvents(self.workshop.id);
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
    
    
    
    self.upload = function (file, event) {
        Upload.upload({
            url: '/admin/changeEvent',
            data: {file: file, event : Upload.jsonBlob(event), 'username': $scope.username}
        }).then(function (resp) {
            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
            
            getEvents(self.workshop.id);            
            
        }, function (resp) {
            console.log('ToDo : Error status: ' + resp.status);
            
            getEvents(self.workshop.id);
            
            
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };
    
    
    self.submitEvent = function(tupel) {
    	
    	tupel.event.workshopId = self.workshop.id;
    	
    	if (tupel.file != null){
        	//tupel.event.filename = tupel.file.name;
    	}
    	
    	console.log(tupel.event);	
    	self.upload(tupel.file, tupel.event);
    };

    
    
    function getEvents(workshopId){
    	console.log('get events for workshop ' + workshopId);
        ChangeContentService.getEvents(workshopId)
            .then(
            function(result){
            	
            	if (result.length == 0){
            		return;
            	} 
            	
                console.log('Result : ' + result[0].ueberschrift);
            	
            	self.eventTupels = [];
            	
            	
            	result.forEach(function (eventToAdd, index){
            		console.log('EventToAdd ' + eventToAdd.ueberschrift);
            		
            		tupelToAdd = {
            				event : eventToAdd,
            				file : null
            		}
            		
            		self.eventTupels.push(tupelToAdd);
            		
            		console.log('EventToAdd ' + eventToAdd);
            	});
            		
            	self.newTupel = {
            			event : {
            				ueberschrift : '',
            				jahr : '',
            				filename : '',
            				embedcode : ''
            			},
            			file : null
            	}
            	            	
                console.log('Event Tupels  '+ self.eventTupels);
            },
            function(errResponse){
                console.error('Error while getting Workshops');
            }
        );
    	
    	
    }

    
    
    
}]);