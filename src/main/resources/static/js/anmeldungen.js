angular.module('main').controller('anmeldungen', ['ChangeContentService', 
	
	
	function(ChangeContentService) {
		
	
	console.log('anmeldungen ');
	var self = this;
	
	self.users = [];
	self.getUsers = getUsers;
	self.setActiveUser = setActiveUser;
	self.changeUser = changeUser;

	self.user={
			id: '-',
			datum: '-',
			workshopId: '-',
			motivation:'',
			mail:'-', 
			mailConfirm:'', 
			vorname:'-', 
			nachname:'-', 
			internText: '-',
			status:'-', 
			agb:'-',
			modus: '-',
			sprache: '-'
	};
	
	


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
	
	function changeUser(user){
		console.log('change user...');
		ChangeContentService.changeUser(user)
        .then(
        function(result){
        	self.users = result;
            console.log(result);
        },
        function(errResponse){
            console.error('Error while changing user');
        }
    );
	}

	
	
}]);