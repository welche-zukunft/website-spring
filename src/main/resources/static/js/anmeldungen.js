angular.module('main').controller('anmeldungen', ['ChangeContentService', 
	
	
	function(ChangeContentService) {
		
	
	console.log('anmeldungen ');
	var self = this;
	
	self.sortType = 'nachname';
	self.sortReverse = false;
	self.searchFish = '';
	

	self.users = [];
	self.allUsers = [];
	self.wsUsers = [];
	self.wlUsers = [];
	self.olUsers = [];
	self.zuUsers = [];
	self.niUsers = [];
	
	
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
	console.log("all users : ");
	console.log(self.allUsers);
	self.users = self.allUsers;
	
	function getUsers(){
    	console.log('get users...');
        ChangeContentService.getUsers()
            .then(
            function(result){
            	console.log(result);
                
                
                for (user in result){
                	if (user.status == 'ZURÜCKGEMELDET'){
                		self.zuUsers.push(user);
                	}
                	if (user.status == 'WARTELISTE'){
                		self.wlUsers.push(user);
                	}
                	if (user.status == 'ZUGELASSEN'){
                		self.wsUsers.push(user);
                	}
                	if (user.modus == 'OLYMPISCH'){
                		self.olUsers.push(user);
                	}
                }     
                
                return result;
                
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
	

	
	function filter(users){
		console.log("filter users. Only show :");
		console.log(users);
		self.users = users;
	}
	
	

	
	
}]);