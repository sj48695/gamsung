/* JS Document */

/******************************

[Table of Contents]

1. Vars and Inits
2. Set Header
3. Init Search
4. Init Menu
5. Init Image
6. Init Quantity
7. Init Isotope


******************************/

$(document).ready(function()
{
	"use strict";

	/* 

	1. Vars and Inits

	*/

	var header = $('.header');
	var hambActive = false;
	var menuActive = false;

	setHeader();

	$(window).on('resize', function()
	{
		setHeader();
	});

	$(document).on('scroll', function()
	{
		setHeader();
	});

	initSearch();
	initMenu();
	initImage();
	initQuantity();
	initIsotope();

	/* 

	2. Set Header

	*/

	function setHeader()
	{
		if($(window).scrollTop() > 100)
		{
			header.addClass('scrolled');
		}
		else
		{
			header.removeClass('scrolled');
		}
	}

	/* 

	3. Init Search

	*/

	function initSearch()
	{
		if($('.search').length && $('.search_panel').length)
		{
			var search = $('.search');
			var panel = $('.search_panel');

			search.on('click', function()
			{
				panel.toggleClass('active');
			});
		}
	}

	/* 

	4. Init Menu

	*/

	function initMenu()
	{
		if($('.hamburger').length)
		{
			var hamb = $('.hamburger');

			hamb.on('click', function(event)
			{
				event.stopPropagation();

				if(!menuActive)
				{
					openMenu();
					
					$(document).one('click', function cls(e)
					{
						if($(e.target).hasClass('menu_mm'))
						{
							$(document).one('click', cls);
						}
						else
						{
							closeMenu();
						}
					});
				}
				else
				{
					$('.menu').removeClass('active');
					menuActive = false;
				}
			});

			//Handle page menu
			if($('.page_menu_item').length)
			{
				var items = $('.page_menu_item');
				items.each(function()
				{
					var item = $(this);

					item.on('click', function(evt)
					{
						if(item.hasClass('has-children'))
						{
							evt.preventDefault();
							evt.stopPropagation();
							var subItem = item.find('> ul');
						    if(subItem.hasClass('active'))
						    {
						    	subItem.toggleClass('active');
								TweenMax.to(subItem, 0.3, {height:0});
						    }
						    else
						    {
						    	subItem.toggleClass('active');
						    	TweenMax.set(subItem, {height:"auto"});
								TweenMax.from(subItem, 0.3, {height:0});
						    }
						}
						else
						{
							evt.stopPropagation();
						}
					});
				});
			}
		}
	}

	function openMenu()
	{
		var fs = $('.menu');
		fs.addClass('active');
		hambActive = true;
		menuActive = true;
	}

	function closeMenu()
	{
		var fs = $('.menu');
		fs.removeClass('active');
		hambActive = false;
		menuActive = false;
	}

	/* 

	5. Init Image

	*/

	function initImage()
	{
		var images = $('.details_image_thumbnail');
		var selected = $('.details_image_large img');

		images.each(function()
		{
			var image = $(this);
			image.on('click', function()
			{
				var imagePath = new String(image.data('image'));
				selected.attr('src', imagePath);
				images.removeClass('active');
				image.addClass('active');
			});
		});
	}

	/* 

	6. Init Quantity

	*/

	function initQuantity()
	{
		// Handle product quantity input
		if($('.product_quantity').length)
		{
			var input = $('#quantity_input');
			var incButton = $('#quantity_inc_button');
			var decButton = $('#quantity_dec_button');

			var originalVal;
			var endVal;

			incButton.on('click', function()
			{
				originalVal = input.val();
				endVal = parseFloat(originalVal) + 1;
				input.val(endVal);
			});

			decButton.on('click', function()
			{
				originalVal = input.val();
				if(originalVal > 0)
				{
					endVal = parseFloat(originalVal) - 1;
					input.val(endVal);
				}
			});
		}
	}

	/* 

	7. Init Isotope

	*/

	function initIsotope()
	{
		var sortingButtons = $('.product_sorting_btn');
		var sortNums = $('.num_sorting_btn');

		if($('.product_grid').length)
		{
			var grid = $('.product_grid').isotope({
				itemSelector: '.product',
				layoutMode: 'fitRows',
				fitRows:
				{
					gutter: 30
				},
	            getSortData:
	            {
	            	price: function(itemElement)
	            	{
	            		var priceEle = $(itemElement).find('.product_price').text().replace( '$', '' );
	            		return parseFloat(priceEle);
	            	},
	            	name: '.product_name',
	            	stars: function(itemElement)
	            	{
	            		var starsEle = $(itemElement).find('.rating');
	            		var stars = starsEle.attr("data-rating");
	            		return stars;
	            	}
	            },
	            animationOptions:
	            {
	                duration: 750,
	                easing: 'linear',
	                queue: false
	            }
	        });
		}
	}

});


function readURL(input,target) {
	if (input.files && input.files[0]) {
		var imgfiles = [];
		var files = input.files;
		var filesArr = Array.prototype.slice.call(files);
		
		var index = 0;
		filesArr.forEach(function(f){
			if(!f.type.match("image.*")){
				alert("확장자는 이미지 확장자만 가능합니다.");
				return;
			}
			
			imgfiles.push(f);
			
			var reader = new FileReader();
			reader.onload = function(e) {
				var result = "<img class='fileImg col-4 py-2' src='" + e.target.result + "'>";
				target.append(result);
				console.log(result);
				index++;
			}
			reader.readAsDataURL(f);
			
		});
		
	}
}


function report(){
	 
	
	var seller = $('#seller').attr('value');
    //팝업창출력
    //width : 300px크기
    //height : 300px크기
    //top : 100px 위의 화면과 100px 차이해서 위치
    //left : 100px 왼쪽화면과 100px 차이해서 위치
    //툴바 X, 메뉴바 X, 스크롤바 X , 크기조절 X
    window.open('/report/'+ seller,'popName',
                'width=700,height=900,top=100,left=100,toolbar=no,menubar=no,scrollbars=no,resizable=no,status=no');
}


/* ----------- product (write, update) ---------- */
$(function () {

	/* ----------- Img preview ---------- */
	$("#writeForm").on("change", "#titleImgFile", function (event) {
		$('.product_image').empty();
		readURL(this, $(".product_image"));
	});
	$("#writeForm").on("change", "#imgFile", function (event) {
		$('.product_images').empty();
		readURL(this, $(".product_images"));
	});
	
	$("#updateForm").on("change", "#titleImgFile", function (event) {
		$(".product_image").empty();
	    readURL(this, $(".product_image"));
	});
	$("#updateForm").on("change", "#imgFile", function (event) {
	    readURL(this, $(".product_images")); //empty= 하위파일을 일시적으로 안보이게 설정(데이터는 남아있음)
	});
	
	//리뷰 파일
	$("#review_writeForm").on("change", "#imgFile", function (event) {
		$('.product_images').empty();
		readURL(this, $(".product_images"));
	});
	
	$("#review_updateForm").on("change", "#imgFile", function (event) {
	    readURL(this, $(".product_images")); //empty= 하위파일을 일시적으로 안보이게 설정(데이터는 남아있음)
	});
	
	
	$("#dealForm").on("click","#back_button", function(event){
		var productNo = $(this).attr("data-productNo");
		var ok = confirm( productNo + "번 자료를 삭제할까요?");
		if(ok){
		location.href="/product/delete/" + productNo;
		}
	});
	
	//이미지 삭제
	$('.delete').on('click', function() {
		var productFileNo = $(this).attr('data-fileNo');
		var deleteBtn = $(this).parent();
		$.ajax({
			url: "/product/delete-file",
			method: "GET",
			data: {"productFileNo" : productFileNo },
			success: function(data, status, xhr){
				deleteBtn.remove();
			},
			error: function(xhr, status, err){
				alert('삭제 실패');
			}
		});
	   
	});
	
	//리뷰 이미지 삭제
	$('.del_review_file').on('click', function() {
		var reviewFileNo = $(this).attr('data-fileNo');
		var deleteBtn = $(this).parent();
		$.ajax({
			url: "/product/delete-reviewfile",
			method: "GET",
			data: {"reviewFileNo" : reviewFileNo },
			success: function(data, status, xhr){
				deleteBtn.remove();
			},
			error: function(xhr, status, err){
				alert('삭제 실패');
			}
		});
	   
	});
	
	//검색버튼

	$(document).on('click', '#btnSearch', function(e){

		e.preventDefault();

		var url = "${getBoardList}";    // <c:url>로 선언한 url을 사용

		url = url + "?searchType=" + $('#searchType').val();

		url = url + "&keyword=" + $('#keyword').val();

		location.href = url;
		console.log(url);

	});	

	$('#type_form').on('change', function(event) {
		this.form.submit();
	});
	
	$('#category_form').on('change', function(event) {
		this.form.submit();
	});
});

/* **********************************************
 * 					comments
 * **********************************************/

$(function() {
	var productNo = $('#productNo').val();
	$('#writecomment').on('click', function(event) {
		
		//serialize : <form에 포함된 입력 요소의 값을 이름=값&이름=값&... 형식으로 만드는 함수
		var formData = $('#commentform').serialize();
		// alert(formData)
		
			$.ajax({
			url: "/product/write-comment",
			method: "POST",
			data: formData,
			success: function(data, status, xhr) {
				 alert(data);
				$("#comment-list").load('/product/comment-list', 
										{ "productNo" : productNo,
											"pageNo" : 1 }, 
										function() {})
			},
			error: function(xhr, status, err) {
				alert(err);
			} 
		});
	});
	
	var currentCommentNo = -1;
	//$('.editcomment').on('click', function(event) {
	$('#comment-list').on('click', '.editcomment', function(event) {
		commentNo = $(this).attr('data-commentno'); // $(event.target) == $(this)
		
		//이전에 편집중인 항목을 원래 상태로 복구
		if (currentCommentNo != -1) {
			$('#commentview' + currentCommentNo).css('display', '');
			$('#commentedit' + currentCommentNo).css('display', 'none');
		}
		
		$('#commentview' + commentNo).css('display', 'none');
		$('#commentedit' + commentNo).css('display', '');
		currentCommentNo = commentNo;
	});
	
	$('#comment-list').on('click', '.cancel', function(event) {
		commentNo = $(this).attr('data-commentno'); // $(event.target) == $(this)
		$('#commentview' + commentNo).css('display', '');
		$('#commentedit' + commentNo).css('display', 'none');
		currentCommentNo = -1;			
	});
	
	$('#comment-list').on('click', '.deletecomment', function(event) {
		commentNo = $(this).attr('data-commentno');
		$.ajax({
			url: "/product/delete-comment",
			method: "DELETE",
			data: "commentNo=" + commentNo,
			success: function(data, status, xhr) {
				if (data == 'success') {
					$('#tr' + commentNo).remove();
					alert('삭제했습니다.');
				} else {
					alert('삭제 실패 1');
				}
			},
			error: function(xhr, status, err) {
				alert('삭제 실패 2');
			}
		});
	});
	
	$('#comment-list').on('click', '.updatecomment', function(event) {
		//현재 클릭된 <a 의 data-commentno 속성 값 읽기
		var commentNo = $(this).attr('data-commentno');
		var content = $('#updateform' + commentNo + ' textarea').val();
		var inputData = $('#updateform' + commentNo).serialize();
		
		//ajax 방식으로 데이터 수정
		$.ajax({
			"url": "/product/update-comment",
			"method": "POST",
			"data": inputData,
			"success": function(data, status, xhr) {
				alert('댓글을 수정했습니다.');
				var span = $('#commentview' + commentNo + ' span');					
				span.html(content.replace(/\n/gi, '<br>'));
				//view-div는 숨기고, edit-div는 표시하기	
				$('#commentview' + commentNo).css('display', 'block');
				$('#commentedit' + commentNo).css('display', 'none');
			},
			"error": function(xhr, status, err) {
				alert('댓글 수정 실패');
			}
		});
	});
	
	$('#comment-list').on('click', '.recomment-link', function(event) {
		var commentNo = $(this).attr('data-commentno');
		$('#write-recomment-modal input[name=commentNo]').val(commentNo);
		$('#write-recomment-modal').modal('show'); //show bootstrap modal
	});
	
	$('#write-recomment').on('click', function(event) {
		
		var content = $('#recomment-form textarea').val();
		if (content.length == 0) return;
		
		var recommentData = $('#recomment-form').serialize();
		
		$.ajax({
			url: "/product/write-recomment",
			method: "POST",
			data: recommentData,
			success: function(data, status, xhr) {
				alert('success');					
				$('#recomment-form').each(function() {
					this.reset();
				});
				$("#comment-list").load('/product/comment-list', 
						{ "productNo" : productNo,
							"pageNo" : 1 }, 
						function() {})
				
			},
			error: function(xhr, status, err) {
				alert('fail');
			}
		});
	
	});	


});
