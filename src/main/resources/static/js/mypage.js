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
		$("#rating").val($(this).attr('data-star'));
		return false;
	});

	/*chat*/
//	document.addEventListener("DOMContentLoaded", function(){
//		WebSocket.init();
//	});
	//소켓서버 접속, 정보셋팅, 응답 fn 정의
	connect(function(obj){
		var message = obj.contents;
		$('#greetings').append('<div class="text-right">'+message+'</div>');
		$('#contents').val('');
	});
	
	//메세지 전송
	$('#send').click(function(){
		sendContent($('#contents').val());
	})
});

/*찜하기*/
function heart(productno){
	//var heart_cnt = Number($("#heart_count").text());
	$.ajax({
		url:"/product/heart",
		methods:"get",
		data:{productNo : productno},
		success:function(data, status, xhr){
			 if(data == "success") {
				 //heart_cnt =heart_cnt+1;
	             $('#heart'+productno).attr("class","fa fa-heart");    
	             $("#heart_count").text(Number($("#heart_count").text())+1);
	         }else if(data == "removeheart"){
				 //heart_cnt =heart_cnt-1;
	             $('#heart'+productno).attr("class","fa fa-heart-o");
	             $("#heart_count").text(Number($("#heart_count").text())-1 );
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
function chatpop(){

	var seller = $('.seller').attr('value');	
    //팝업창출력
    //width : 300px크기
    //height : 300px크기
    //top : 100px 위의 화면과 100px 차이해서 위치
    //left : 100px 왼쪽화면과 100px 차이해서 위치
    //툴바 X, 메뉴바 X, 스크롤바 X , 크기조절 X
    window.open('/member/chatting/'+seller,'popName',
                'width=700,height=900,top=100,left=100,toolbar=no,menubar=no,scrollbars=no,resizable=no,status=no');
}
var senderNickName = $("#senderNickName").val();
var senderId = $("#senderId").val();
var receiverNickName = $("#receiverNickName").val();
var receiverId = $("#receiverId").val();

function connect(fn){
	var socket = new SockJS('http://localhost:8088/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({},function(frame){
		console.log('Connected : '+frame);
		stompClient.subscribe('/topic/roomId', function(obj){
			fn(JSON.parse(obj.body));
		});
		stompClient.subscribe('/queue/info', function(obj){
			fn(JSON.parse(obj.body));
		});
	});
}

function sendContent(contents){
	var query={}
	query.senderNickName=senderNickName;
	query.sender=senderId;
	query.receiverNickName=receiverNickName;
	query.receiver=receiverId;
	query.contents = contents;
	stompClient.send('/app/hello',{}, JSON.stringify(query));
}
