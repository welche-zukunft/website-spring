angular.module('main').controller('anmeldungen', ['ChangeContentService', 
	
	
	function(ChangeContentService) {
		
	
	console.log('anmeldungen ');
	var self = this;
	
	self.setActiveUser = setActiveUser;

	self.user={
			id: -1,
			datum: 0,
			workshopId: -1,
			motivation:'',
			mail:'', 
			mailConfirm:'', 
			vorname:'', 
			nachname:'', 
			internText: '',
			status:'', 
			agb:'',
			modus: '',
			sprache: ''
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
	
	function setActiveUser(user){
		console.log("Set active User");
		self.user = user;
		console.log(self.user);
	}

	
	
}]);