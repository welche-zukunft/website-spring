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
	self.uploadAttachment = uploadAttachment;
	
	
	
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
	
	
	function uploadAttachment(){
		
		console.log(self.fileToUpload)
		
		/*
		var fd = new FormData();
		fd.append('file', self.fileToUpload);
		fd.append('geheimnis', 'Geheimnis');
		$http.post(uploadUrl, fd, {
		   transformRequest: angular.identity,
		   headers: {'Content-Type': undefined}
		})
		.success(function(){
		})
		.error(function(){
		});
		*/

		
		
	}
	
}])


.directive("fileModel",function() {
	return {
		restrict: 'EA',
		scope: {
			setFileData: "&"
		},
		link: function(scope, ele, attrs) {
			
			//console.log(ele);
			//console.log(scope);
			//console.log(attrs);
			
			ele.on('change', function() {
				
				scope.$apply(function() {
						
					console.log(ele[0]);
					var val = ele[0].files[0];
					scope.fileToUpload =  val;
					console.log(scope.fileToUpload);
				});
			});
		}
	}
});



