$(function() {
	$('.addToHeart').on('click', function(event) {		
		var formData = $('#addHeartForm').serialize();
		$.ajax({
			url:"/product/addheart",
			methods:"get",
			data:formData,
			success:function(data, status, xhr){
				var ok = confirm('찜 되었습니다.');
				if (ok) {
					window.location.href = "/";
				}
			},
			error:function(status, xhr, err){
				alert("찜 할 수 없습니다.\n" + err);
			}
		});
	});
	
	

});