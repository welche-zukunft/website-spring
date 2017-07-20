angular.module('main').controller('changecontents', ['ChangeContentService', function(ChangeContentService) {
	var self = this;
	
	console.log("change contents");

	
	self.workshop={id:null,leiterIn:'',address:'',email:'', kurzinfo:''};
    self.workshops=[];
 
    self.submit = submit;

    
    function submit() {
        if(self.workshop.id===null){
            console.log('Saving workshop', self.workshop);
            changeContent(self.workshop);
        }else{
            // updateUser(self.user, self.user.id);
            console.log('User updated with id ', self.workshop.id);
        }
        // reset();
    }
    
    function changeContent(workshop){
    	console.log('Change contnent...');
        ChangeContentService.changeContent(workshop)
            .then(
            function(errResponse){
                console.error('Error while changing content 2');
            }
        );
    }

}]);