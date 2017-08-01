angular.module('main').controller('anmeldungen', ['ChangeContentService', '$scope', 
	
	
	function(ChangeContentService, $scope) {
		
	
	console.log('anmeldungen ');
	var self = this;
	
	self.sortType = 'nachname';
	self.sortReverse = false;
	self.searchFish = '';
	

	self.users = [];

	self.getUsers = getUsers;
	
	self.setActiveUser = setActiveUser;
	self.changeUser = changeUser;
	self.deleteUser = deleteUser;

	
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
	

	getUsers('ALL');

	
	function getUsers(filter){
    	console.log('get users...');
        ChangeContentService.getUsers(filter)
            .then(
            function(result){
            	console.log(result);
                self.users = result;
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
            console.log(result);
        },
        function(errResponse){
            console.error('Error while changing user');
        }
        );
	}
	
	
	function deleteUser(user){
		console.log('delete user...');
		ChangeContentService.deleteUser(user)
        .then(
        function(result){
            console.log(result);
            getUsers('ALL');
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
        },
        function(errResponse){
            console.error('Error while deleting user');
            getUsers('ALL');
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
        }
        );
	}
	
	
	self.selected = '';
	
	self.switchFilter = function() {
	    filter = self.selected;
	    getUsers(filter);
	  }

	
}]);