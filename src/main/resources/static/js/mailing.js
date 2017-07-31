angular.module('main').controller('mailing', ['ChangeContentService', function(ChangeContentService) {
	console.log('mailing ');
	var self = this;
	
	
	self.mail = {
			titel: 0,
			inhalt:'',
			ziel:''
	};
	
	
	self.sendMail = sendMail;
	
	
	
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
	
}]);