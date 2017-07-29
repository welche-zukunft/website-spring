angular.module('main').controller('anmeldungen', ['ChangeContentService', 
	
	
	function(ChangeContentService) {
		
	
	console.log('anmeldungen ');
	var self = this;

	self.user={
			id: -1,
			workshopId: -1,
			motivation:'',
			mail:'', 
			mailConfirm:'', 
			vorname:'', 
			nachname:'', 
			status:'', 
			agb:''
	};
	
	
	self.users = [];
	self.getUsers = getUsers;

	getUsers();
	
	function getUsers(){
    	console.log('get users...');
        ChangeContentService.getUsers()
            .then(
            function(result){
            	self.users = result;
                console.log(result);
            },
            function(errResponse){
                console.error('Error while getting users');
            }
        );
    }

	
	
}]);