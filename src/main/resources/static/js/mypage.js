$(function() {
	// 프로필 이미지를 클릭하면 input[type=file] 창이 뜨게 하는 이벤트 리스너 등록
	$('#profileImgIcon').on("click", function(e){
		$('#profileImgFile').trigger("click");
	});
	
	// 프로필 사진이 등록되면 이를 감지하여 프로필 사진을 전송
	$("#profileImgFile").on("change", function(e){
		e.preventDefault();
		submitProfileImage();
	});
	
	//소개글 수정 아이콘 눌렀을 때
	$('#update').on("click", function(e){
		
		var introduction = $(this).attr('data-introduction');
		$('#intro_view').empty();
		$('#intro_view').html('<textarea class="form-control col-8" id="intro" name="introduction">'+introduction+'</textarea>\
				<a class="btn bnt-primary col-2" onclick="javascript:introduction();">수정</a>');
		$(this).remove();
	});
	
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
	                 $('#heart'+productno).attr("class","fa fa-heart");
	             }else if(data == "removeheart"){
	                 $('#heart'+productno).attr("class","fa fa-heart-o");
	             }else if(data == "error"){
	            	 alert("로그인 후 이용가능합니다.");
	             }

		},
		error:function(status, xhr, err){
			alert("찜 할 수 없습니다.\n" + err);
		}
	});
}

//프로필 이미지를 ajax로 전송
//jpg, jpeg, png, gif, bmp포맷만 등록이 가능하도록 제한해야 한다.
function submitProfileImage(){
	var profileImgFile = document.querySelector("#profileImgFile").files[0];
	var formData = new FormData();
	//폼 객체에 파일추가, append("변수명", 값)
	formData.append("profileImgFile", profileImgFile);
	
	$.ajax({
		type: "post",
		url: "/member/mypage/fileUpload",
		contentType: false,
		dataType: "text",
		processData: false,
		data: formData,
		success: function(data, resp){
			//통신 성공
			$(".profile").attr('src','/files/profile-files/'+ data);
		}, error:function(resp){
			alert("통신 실패...");
		}
	});
}

/*소개글*/
function introduction(){
	var formData = $("#introduction").serialize();
	var intro = $('#intro').val();
	$.ajax({
		url:"/member/mypage/introduction",
		methods:"post",
		data:formData,
		success:function(data, status, xhr){
				$("#intro_div").empty();
				$("#intro_div").html('<i id="update" class="fa fa-edit col-2" data-introduction="'+data+'"></i>\
						<div id="intro_view" class="col-8 row">'+intro+'</div>');
			
        	 alert("수정성공!");
		},
		error:function(status, xhr, err){
			alert("수정할 수 없습니다.\n" + err);
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
//	stompClient.send('/app/hello',{}, JSON.stringify(query));
	stompClient.send('/app/info',{}, JSON.stringify(query));
}

