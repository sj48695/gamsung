$(function() {
	

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
		url:"/product/addheart",
		methods:"get",
		data:{productNo : productno},
		success:function(data, status, xhr){
			var ok = confirm('찜 되었습니다.');
			if (ok) {
				window.location.href = "/product/detail/"+productno;
			}
		},
		error:function(status, xhr, err){
			alert("찜 할 수 없습니다.\n" + err);
		}
	});
}

