$(function() {
	$('#profileImg').on("click", function(e){
		$('#profileImgFile').trigger("click");
	});
	
	$("#profileImgFile").on("change", function(e){
		e.preventDefault();
		submitProfileImage();
	});
	
	/*별점*/
	
	$('.starRev span').click(function() {
		$(this).parent().children('span').removeClass('on');
		$(this).addClass('on').prevAll('span').addClass('on');
		return false;
	});

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
		success: function(resp){
			if(resp == 'success'){
				
			}
		}, error:function(resp){
			alert("통신 실패...");
		}
	});
}


