$(function() {
	

	/*별점*/
	
	$('.starRev span').click(function() {
		$(this).parent().children('span').removeClass('on');
		$(this).addClass('on').prevAll('span').addClass('on');
		return false;
	});

	
	/*chat*/
//	document.addEventListener("DOMContentLoaded", function(){
//		WebSocket.init();
//	});
	//소켓서버 접속, 정보셋팅, 응답 fn 정의
	connect(function(obj){
		var message = '('+obj.sender+')'+' - '+obj.contents;
		$('#greetings').append('<tr><td>'+message+'</td></tr>');
		$('#name').val('');
	});
	
	//메세지 전송
	$('#send').click(function(){
		sendContent($('#name').val());
	})
});

/*찜하기*/
function heart(productno){
	$.ajax({
		url:"/product/heart",
		methods:"get",
		data:{productNo : productno},
		success:function(data, status, xhr){
				 if(data == "success") {
	                 $('#heart').attr("class","fa fa-heart");
	             }else if(data == "removeheart"){
	                 $('#heart').attr("class","fa fa-heart-o");
	             }else if(data == "error"){
	            	 alert("로그인 후 이용가능합니다.");
	             }

		},
		error:function(status, xhr, err){
			alert("찜 할 수 없습니다.\n" + err);
		}
	});
}


/*chat*/
var sendNickName = $("#hiddenToken").val();

function connect(fn){
	var socket = new SockJS('http://localhost:8088/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({},function(frame){
		console.log('Connected : '+frame);
		stompClient.subscribe('/topic/roomId', function(obj){
			fn(JSON.parse(obj.body));
		});
		stompClient.subscribe('/topic/out', function(obj){
			fn(JSON.parse(obj.body));
		});
		stompClient.subscribe('/topic/in', function(obj){
			fn(JSON.parse(obj.body));
		});
		stompClient.subscribe('/queue/info', function(obj){
			fn(JSON.parse(obj.body));
		});
		stompClient.send("/app/in",{},sendNickName+' is in chatroom');
	});
}

function disconnect(){
	if(stompClient!==null){
		stompClient.send("/app/out", {},sendNickName+ 'is out chatroom');
		stompClient.disconnect();
	}
	console.log('Disconnected');
}

function sendContent(contents){
	var query={}
	query.sender=sendNickName;
	query.contents = contents;
	stompClient.send('/app/hello',{}, JSON.stringify(query));
//	stompClient.send('/app/info',{}, JSON.stringify(query));
}