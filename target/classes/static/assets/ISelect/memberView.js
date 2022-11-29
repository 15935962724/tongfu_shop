
$().ready(function() {

	$('.nav,.gou').children("li:has(ul)").hover(
		function(){
			//$(this).find("ul>li:last").addClass("li-last-s1");//给每个下拉菜单的最后一个li元素添加css样式，此处非必要
			$(this).children("ul").slideDown(300);//JQ的slideDown方法，显示下拉菜单
		},
		function(){
			$(this).children("ul").hide();//隐藏下拉菜单
		}
	);

	$('.nar,.nav_g').children("li:has(ul)").hover(
		function(){
			//$(this).find("ul>li:last").addClass("li-last-s1");//给每个下拉菜单的最后一个li元素添加css样式，此处非必要
			$(this).children("ul").slideDown(300);//JQ的slideDown方法，显示下拉菜单
		},
		function(){
			$(this).children("ul").hide();//隐藏下拉菜单
		}
	);


});

