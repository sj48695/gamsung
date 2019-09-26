$(function() {
	
	

});

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