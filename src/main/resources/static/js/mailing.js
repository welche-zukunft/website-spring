angular.module('main').controller('mailing', ['ChangeContentService', "$http", function(ChangeContentService, $http) {
	
	console.log('mailing ');
	var self = this;
	
	self.fileToUpload = '';
	
	self.mail = {
			titel: 0,
			inhalt:'',
			ziel:''
	};
	
	
	self.sendMail = sendMail;
	self.files = [];
	self.file = '';
	
	getFiles();
	
	
	function sendMail(mail){
		console.log('get workshop...');
        ChangeContentService.sendMail(mail)
            .then(
            function(result){
                console.log(result);
            },
            function(errResponse){
                console.error('Error while getting Workshops');
            }
        );
	}
	
	function getFiles(){
		console.log('get files...');
        ChangeContentService.getFiles()
            .then(
            function(result){
            	self.files = result;
                console.log(result);
            },
            function(errResponse){
                console.error('Error while getting Files');
            }
            );
	}
	

	

	
}]);

