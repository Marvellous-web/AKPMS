var timeout;
$(document).ready(function() {
	getNotifications();
});

function getNotifications()
{
	clearTimeout(timeout);
	$.ajax({
		type : "GET",
		dataType : "json",
		url : contextPath + "/notifications/json",
		data : "recordCount="+recordCount,
		success : function(response) {
			$('#notificationUL').text("");
			if(response != null){
				$('.view-all').show();
				for(var i=0 ; i < response.length ; i++){
					$('#notificationUL').append('<li id=notification_'+response[i].id+'>'+response[i].content+'<a href="javascript:void(0)" onclick="hideNotification('+response[i].id+')"> hide</a></li>');
				}
			}else{
				$('.view-all').hide();
				$('#notificationUL').append('<li> No notification available</li>');
			}
		},
		error: function (data){
//			alert(data);
		}
	  });

	timeout = setTimeout(function(){getNotifications();},10000);
}

function hideNotification(notificationId)
{
	$.ajax({
		type : "GET",
		dataType : "json",
		url : contextPath + "/notifications/json/hide",
		data : "notificationId="+notificationId,
		success : function(response) {
			getNotifications();
		},
		error: function (data){
//			alert(data);
		}
	  });
}