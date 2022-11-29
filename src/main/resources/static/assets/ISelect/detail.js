//jqueryzoom
!function(a){a.fn.jqueryzoom=function(b){var c={xzoom:200,yzoom:200,offset:10,position:"right"},d=a(this);a.extend(c,b);var e="";a(this).parent().hover(function(){var b=d.offset().left,f=d.offset().top,g=a(this).find("img").get(0).offsetWidth,h=a(this).find("img").get(0).offsetHeight,i=e=a(this).find("img").attr("alt");a(this).find("img").attr("alt",""),0==a("div.zoomdiv").size()&&a(this).find("img").after("<div class='zoomdiv'><img class='bigimg' src='"+i+"'/></div>"),0==a("div.zoomspan").size()&&a(this).find("img").eq(0).after("<div class='zoomspan'></div>"),a("div.zoomdiv").css({top:c.top,left:c.left}).width(c.xzoom).height(c.yzoom).show(),a("div.zoomspan").show(),a(document.body).mousemove(function(c){var d=a(".bigimg").get(0).offsetWidth,e=a(".bigimg").get(0).offsetHeight,i="x",j="y";if(isNaN(j)|isNaN(i))var j=Math.round(d/g),i=Math.round(e/h);var k={};k.x=c.pageX,k.y=c.pageY;var l=k.x-b-100,m=k.y-f-100;0>l&&(l=0),l>g-200&&(l=g-200),0>m&&(m=0),m>h-200&&(m=h-200),a("div.zoomspan").css({left:l+"px",top:m+"px"}),scrolly=k.y-f-1*a("div.zoomdiv").height()/i/2,a("div.zoomdiv").scrollTop(scrolly*i),scrollx=k.x-b-1*a("div.zoomdiv").width()/j/2,a("div.zoomdiv").scrollLeft(scrollx*j)})},function(){a(this).find("img").attr("alt",e),a("div.zoomdiv").hide().remove(),a(document.body).unbind("mousemove"),a(".lenszoom").remove(),a("div.zoomspan").hide()})}}(jQuery);
//json2.js 浏览记录用到
if(!this.JSON){JSON={};}(function(){function f(n){return n<10?'0'+n:n;}if(typeof Date.prototype.toJSON!=='function'){Date.prototype.toJSON=function(key){return this.getUTCFullYear()+'-'+f(this.getUTCMonth()+1)+'-'+f(this.getUTCDate())+'T'+f(this.getUTCHours())+':'+f(this.getUTCMinutes())+':'+f(this.getUTCSeconds())+'Z';};String.prototype.toJSON=Number.prototype.toJSON=Boolean.prototype.toJSON=function(key){return this.valueOf();};}var cx=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,escapable=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,gap,indent,meta={'\b':'\\b','\t':'\\t','\n':'\\n','\f':'\\f','\r':'\\r','"':'\\"','\\':'\\\\'},rep;function quote(string){escapable.lastIndex=0;return escapable.test(string)?'"'+string.replace(escapable,function(a){var c=meta[a];return typeof c==='string'?c:'\\u'+('0000'+a.charCodeAt(0).toString(16)).slice(-4);})+'"':'"'+string+'"';}function str(key,holder){var i,k,v,length,mind=gap,partial,value=holder[key];if(value&&typeof value==='object'&&typeof value.toJSON==='function'){value=value.toJSON(key);}if(typeof rep==='function'){value=rep.call(holder,key,value);}switch(typeof value){case'string':return quote(value);case'number':return isFinite(value)?String(value):'null';case'boolean':case'null':return String(value);case'object':if(!value){return'null';}gap+=indent;partial=[];if(Object.prototype.toString.apply(value)==='[object Array]'){length=value.length;for(i=0;i<length;i+=1){partial[i]=str(i,value)||'null';}v=partial.length===0?'[]':gap?'[\n'+gap+partial.join(',\n'+gap)+'\n'+mind+']':'['+partial.join(',')+']';gap=mind;return v;}if(rep&&typeof rep==='object'){length=rep.length;for(i=0;i<length;i+=1){k=rep[i];if(typeof k==='string'){v=str(k,value);if(v){partial.push(quote(k)+(gap?': ':':')+v);}}}}else{for(k in value){if(Object.hasOwnProperty.call(value,k)){v=str(k,value);if(v){partial.push(quote(k)+(gap?': ':':')+v);}}}}v=partial.length===0?'{}':gap?'{\n'+gap+partial.join(',\n'+gap)+'\n'+mind+'}':'{'+partial.join(',')+'}';gap=mind;return v;}}if(typeof JSON.stringify!=='function'){JSON.stringify=function(value,replacer,space){var i;gap='';indent='';if(typeof space==='number'){for(i=0;i<space;i+=1){indent+=' ';}}else if(typeof space==='string'){indent=space;}rep=replacer;if(replacer&&typeof replacer!=='function'&&(typeof replacer!=='object'||typeof replacer.length!=='number')){throw new Error('JSON.stringify');}return str('',{'':value});};}if(typeof JSON.parse!=='function'){JSON.parse=function(text,reviver){var j;function walk(holder,key){var k,v,value=holder[key];if(value&&typeof value==='object'){for(k in value){if(Object.hasOwnProperty.call(value,k)){v=walk(value,k);if(v!==undefined){value[k]=v;}else{delete value[k];}}}}return reviver.call(holder,key,value);}cx.lastIndex=0;if(cx.test(text)){text=text.replace(cx,function(a){return'\\u'+('0000'+a.charCodeAt(0).toString(16)).slice(-4);});}if(/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,'@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,']').replace(/(?:^|:|,)(?:\s*\[)+/g,''))){j=eval('('+text+')');return typeof reviver==='function'?walk({'':j},''):j;}throw new SyntaxError('JSON.parse');};}}());
var inventorySize;
var checkedService=0;
var sourceStr = getQueryString('source');
if(sourceStr == undefined || sourceStr == null ){
	sourceStr = "default"
}
sourceStr = filterXSS(sourceStr);

function filterXSS(str){
	var pattern = new RegExp("[`~&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——{}【】‘；：”“'。，、？]");
	var rs = "";
	for (var i = 0; i < str.length; i++) {
		rs = rs+str.substr(i, 1).replace(pattern, '');
	}
	return rs;
};
function getSelfInfo(vendorId,firstcategoryid) {
    // 查询商家资质
    var vendorUrl = "http://item.secoo.com/product_module/qualification/"+vendorId+"/qualification_" +vendorId+"_"+ firstcategoryid+".shtml"; //url地址
    $.ajax({
        type: "GET",
        cache: false,
        url:vendorUrl,
        async: false,
        success: function(data, textStatus) {
            //获取返回的数据
            $("#qualificationContent").html("").append(data);
        },
        error: function(){
            vendorUrl = "http://item.secoo.com/product_module/qualification/"+vendorId+"/qualification_" +vendorId+".shtml"; //url地址
            $.ajax({
                type: "GET",
                cache: false,
                url:vendorUrl,
                async: false,
                success: function(data, textStatus) {
                    //获取返回的数据
                    $("#qualificationContent").html("").append(data);
                },
                error: function(){
                    var html='<div class="product-detail-div clearfix" ><div class="t-info"> <div>' +
                        '<div style="height: 500px; margin:10px auto;"> <span style="text-align:center;display:block;height: 500px;line-height: 500px; font-size: 16px;color: #999999; ">' +
                        '信息正在完善中，敬请期待</span> </div> </div> </div> </div></div>';
                    $("#qualificationContent").html("").append(html);
                }
            });
        }
    });
}

$(function (){
	
	laodBanner();
	//第二套分类品牌尺码
	brandSizeTable(brandSizeTitle);
	
	var marketprice = $("#marketprice").val();
	var areaType = $("#areaType").val();
	var additionalVal = $("#additionalVal").val();
	var xxlgVar = $("#xlgg_not_found").val();
	var ppgsVar = $("#ppgs_not_found").val();
	if(xxlgVar && ppgsVar){
		$("#ppgsDiv").hide();
	}	
	
 	$("#showAdds").delegate('.showLeftCon02 ul li','mouseenter',function(){
        var html = '<img class="show_bigPic" src="'+$(this).find('img').attr('src').replace('50/50','80/80')+'" style="width:50px;height:50px;position:absolute; left:0;top:20px; box-shadow:0 0 3px #e8e8e8;" >';
        $(this).append(html);
        $(this).find('.show_bigPic').stop(true,true).animate({"width":"80px","height":"80px","left":"-24px","top":"1px"},'fast')
    }).delegate('li','mouseleave',function(){
        $(this).find('.show_bigPic').stop(true,true).animate({"width":"50px","height":"50px","left":"0","top":"20px"},'fast',function(){
            $(this).remove();
        });
    });
	//关闭购物车弹层
    $(".winboxClose,.buy").click(winboxCloses);
	$("span.brand").parent().next().css('border','1px solid #dfdfdf');

	//如果登陆，隐藏评价登陆文案
	if($.cookie('ume') != null && $.cookie('upk') != null){
		$('.td02 > b').hide();
	}
	//赠送服务点击处理方式
	/**	$("#zsSpec").delegate('li','click',function(){
		if($(this).attr('class') == 'on'){//选择自己不调整
			$(this).removeClass('on');
			checkedService = 0;
		}else{
			$(this).addClass('on');	
			checkedService = 1;
		}
	});
	*/

	$("#zsSpec").remove();
	// 移动共享价图片替换
	$("#exclusiveDiv").on('mouseover',function(){
		$("#app_link_pic").lazyload();
	});
	//头部热词
	headHotKey();

	var categoryId = $('#categoryid').val();
	if (categoryId == 707 || categoryId == 774 || categoryId == 706) {
		$('.product-detail-banner').hide();
	}

	//增加二维码0427
	var productId = $("#productIdVal").val();
    var str = "https://m.secoo.com/proDetail_1217.html?prodId="+productId+'&addFrom=pc_to_m&utm_source=pc_to_m';
    $("#pqrCode").qrcode({
      render: "table",
      width: 159,
      height:147,
      text: str
    });

    var firstcategoryid=$("#tx_firstcategoryid").val();//一级分类
    var vendorId=$("#vendorId").val();//商家id

    //商品自营信息
    var selfUrl = "http://las.secoo.com/api/product/selfInfo?pid="+productId; //url地址
    $.ajax({
        type: "GET",
        cache: false,
        url: selfUrl,
        async: false,
        success: function(data, textStatus) {
            data=JSON.parse(data);
            //获取返回的数据
            if(data.retCode == 200 && data.result != null){
                console.log(data.result);
                var selfStatu = data.result[0].isSelf;
                console.log(selfStatu);
                var selfInfo = '自营';
                if(selfStatu == 1){
                    selfInfo = '非自营';
                    //指定位置增加
                    if ($("#firstNav ul li:eq(1)").text() != '商家信息') {
                        $("#firstNav ul li:eq(0)").after('<li><a href="javascript:;">商家信息</a></li>');
                    }
                    $("#qualificationContent").remove();
                    var html = '<div class="product-detail-div clearfix" ><div class="t-info"> <div>' +
                        '<div style="height: 500px; margin:10px auto;"> <span style="text-align:center;display:block;height: 500px;line-height: 500px; font-size: 16px;color: #999999; ">' +
                        '信息正在完善中，敬请期待</span> </div> </div> </div> </div></div>';
                    $("#helpInfoContent").before('<div id="qualificationContent"> ' + html + '</div>');
                    getSelfInfo(vendorId, firstcategoryid)
                }else {
                    //去除自营下资质信息脏数据
                    if($("#firstNav ul li:eq(1)").text()=='商家信息'){
                        $("#qualificationContent").remove();
                        $("#firstNav ul li:eq(1)").remove();
                    }
                }
                var productInfoDivF =  $(".proDetails#proDetails").children()[5];
                var productInfoDivC = $(productInfoDivF).children();
                var productInfoDivCV1 = productInfoDivC[0];
                if($(productInfoDivCV1).text() == '商品信息'){
                    var productInfoDivCV2 = productInfoDivC[1];
                    $(productInfoDivCV2).html('<span>'+selfInfo+'</span></br>')
                }
            }else{
                console.log(data);
            }
        },
        error: function(){
            console.log('获取self失败,默认显示逻辑.');
        }
    });
});
$(window).load(function(){
	if($('.topanic_end').size()>0){
		//售罄时候隐藏促销信息
		$("#promotionInfoSpan").remove();
		//隐藏购买数量
		$(".pNum").hide();
	}
   
});


//加载横幅
function laodBanner(){
//	var areaType = $("#areaType").val();
//	if(areaType!=0){		
//		$(".s-left-banner").load("http://item.secoo.com/commonBanner.html",function(){
//			$(".s-left-banner").find('img').lazyload();
//		});
//	}else{
//		$(".s-left-banner").hide();
//	}
	$(".s-left-banner").hide();
	$(".product-detail-banner").load("http://item.secoo.com/payBanner.html",function(){
		$(".product-detail-banner").find('img').lazyload();
	});
}


// 通过当前商品对应的分类或品牌相关的热搜词
function headHotKey(){
	var  brandid = $("#tx_brandid").val();// 品牌ID
	 $.ajax({
         type: "post",
         url: "http://lr.secooimg.com/search_hot_keyword?callback=?",
         data: {brand_id: brandid },
         dataType: "jsonp",
         success: function(data) {
        	if(data != null ){
        		var keywordInput = data.keywordInput;
        		var keywordRecommend = data.keywordRecommend;
        		if(keywordInput != undefined &&  keywordInput !=null){
        			if(keywordRecommend != undefined && keywordRecommend != null){
        				var remcommendArr = keywordRecommend.split(",");
        				if(remcommendArr.length > 4){
        					var remcommendStr ="";
        					for(var i = 0 ; i < remcommendArr.length ; i++){
        						if(i < 7) {
        							var hotKey = remcommendArr[i];
            						var encodeKey = encodeURIComponent(encodeURIComponent(hotKey));
            						remcommendStr += '<a href="http://search.secoo.com/search?keyword=' + encodeKey + '" onclick="analytical(\'热门关键词\',\'搜索_'+(i+1)+'\');">' + hotKey + '</a>';
                	    		}
        					}
        					$(".searchHot").html(remcommendStr);
        				    $("#keyword").val(keywordInput);
        				}
        			}
        		} 
         	}
          }
	  });  
}
//多规格处理
function productSpecDispose(productSpecJSON){
    if(!productSpecJSON || productSpecJSON.length==0){
        return;
    }
    var productId = $("#productIdVal").val();
    var htmlFragments = [];
    var checkQtyId=[];
    $.each(productSpecJSON, function(i, n) {
        htmlFragments.push('<div class="proList clearfix psize">');
        htmlFragments.push('<div class="pdt fl" id="flagColor1">'+n.first+'</div>');
        htmlFragments.push('<div class="pdd fl">');
        if(i==0){
            htmlFragments.push('<ul>');
        }else{
            htmlFragments.push('<ul id="shopSize">');
        }
        $.each(n.second, function (ii, nn) {
            var specValue = nn.first;
            var sepcInfo = nn.second;
            var skuIdsStr = ',' + sepcInfo.skuIds.join(",") + ','
            var liFunc = ' onclick = "selSpec(this)" codes="' + skuIdsStr + '" imgFlag="' + sepcInfo.isAffectImg + '" ';
            var tempSkuId = sepcInfo.productid;
            if (tempSkuId) {
                liFunc = liFunc + " skuId=" + tempSkuId;
                if(productSpecJSON.length==1||(productSpecJSON.length>1&&i>0)){
                    checkQtyId.push(tempSkuId);
                }
            }
            if (tempSkuId) {// 确定选中
                if (tempSkuId == productId) {
                    htmlFragments.push('<li name="productSpecInfo" ' + liFunc + ' class="on">');
                } else {
                    htmlFragments.push('<li name="productSpecInfo" ' + liFunc + '>');
                }
            } else {
                if (i == 0) {
                    htmlFragments.push('<li name="productSpecInfo" ' + liFunc + '>');
                } else {
                    htmlFragments.push('<li name="productSpecInfo" class="disable" style="cursor: not-allowed;" codes="'+skuIdsStr+'" imgFlag="' + sepcInfo.isAffectImg + '">');
                }
            }
            htmlFragments.push('<a href="javascript:void(0);">');
            htmlFragments.push('<span>');
            if (sepcInfo.isAffectImg == 1) {
                htmlFragments.push('<img src="http://pic11.secooimg.com/product/50/50/' + sepcInfo.imgUrl + '" title="' + specValue + '" width="30" height="30">');
            } else {
                htmlFragments.push(specValue);
            }
            htmlFragments.push('</span>');
            htmlFragments.push('</a><label></label>');
            htmlFragments.push('</li>');

        });
        htmlFragments.push('</ul>');

        //查看尺码的按钮
        if(n.first.indexOf("尺码")>-1){
            htmlFragments.push('<div class="size_table"'+($(".cm_show,#cm_show").size()>0?"style='display:inline-block'":"")+'><a href="#cm_show"><i></i>尺码详情</a></div>');
        }
        htmlFragments.push('</div>');//end 选项
        htmlFragments.push('</div>');//end 行
    });
    $(".proList_box").prepend(htmlFragments.join(''));
    if(checkQtyId.length>0){
        productSpecHandelr(checkQtyId.join(","));
    }
}
function selSpec(obj) {
    if ($(obj).hasClass("on")) {
        return;
    }
    var imgFlag = $(obj).attr("imgFlag");
    var codes = $(obj).attr("codes");
    var skuId = $(obj).attr("skuId");
    $(obj).siblings("li").removeClass("on");
    $(obj).addClass("on");
    if ("0" == imgFlag) {
        productStore(skuId);
        // window.location.href = 'http://item.secoo.com/' + skuId + '.shtml?source=' + sourceStr;
    } else {
        var flag = true;
        if (skuId) {
            productStore(skuId);
            //window.location.href = 'http://item.secoo.com/' + skuId + '.shtml?source=' + sourceStr;
        }
        var checkQtyId=[];
        $("#shopSize").find('li').each(function (i, val) {
            $(val).removeClass("on");
            var tempCodes = $(val).attr('codes');
            var tempId = compareCodes(codes, tempCodes);
            if (tempId) {
                $(val).removeClass('disable');
                $(val).removeAttr('style');
                $(val).attr("onclick", "selSpec(this)");
                $(val).attr("skuId", tempId);
                checkQtyId.push(tempId);
            } else {
                $(val).addClass('disable');
                $(val).css({"cursor": "not-allowed"});
            }
        });
        if(checkQtyId.length>0){
            productSpecHandelr(checkQtyId.join(","));
        }
    }
}
function compareCodes(codes, tempCodes) {
    var code1 = codes.substring(0, codes.length - 1).substring(1, codes.length).split(",");
    var code2 = tempCodes.substring(0, tempCodes.length - 1).substring(1, tempCodes.length).split(",");
    for (var i = 0; i < code1.length; i++) {
        for (var j = 0; j < code2.length; j++) {
            if (code1[i] == code2[j]) {
                return code1[i];
            }
        }
    }
    return null;
}
//查询商品库存
function productStore(id,callback){
	var requestUrl = "http://lr.secooimg.com/products?productIds=" + id;
	$.ajax({
		type: "GET",
		cache: false,
		url:requestUrl,
		dataType:"jsonp",
		async: false,
		error: function(a, b, c){
			console.error("ajax repertory error !");
		},
		success: function(data, textStatus) {
			if (data.length > 0) {
				inventoryStatusFlag = true;
				inventoryJson = data[0];
				
				if(inventoryJson.status == 0 || (inventoryJson.status == 1  && inventoryJson.size == 0 )){
					inventoryStatusFlag = false;
				}
				if(inventoryStatusFlag){
					window.location.href='http://item.secoo.com/'+id+'.shtml?source='+sourceStr;
					return;
				}
			}
		}
	});
}



//检查多规格商品库存
function productSpecHandelr(checkQtyId, callback) {
    var requestUrl = "http://lr.secooimg.com/products?productIds=" + checkQtyId+"&fields=productId,status,size";
    var inventoryJson = "";
    $.ajax({
        type: "GET",
        cache: false,
        url: requestUrl,
        dataType: "jsonp",
        async: false,
        success: function (data, textStatus) {
            var _this = $("#shopSize");
            if (_this.length == 0) {
                $("li[name='productSpecInfo']").addClass("disable");
                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        //上架且有库存商品
                        if (data[i].status == 1 && data[i].size > 0) {
                            $("li[skuId='" + data[i].productId + "']").removeClass("disable");
                        }
                    }
                    $("li[name='productSpecInfo']").filter(".disable").removeClass("on");
                    $("li[name='productSpecInfo']").filter(".disable").find("a").removeAttr("href");
                }
            } else {
                _this.find("li[name='productSpecInfo']").addClass("disable");
                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        //上架且有库存商品
                        if (data[i].status == 1 && data[i].size > 0) {
                            _this.find("li[skuId='" + data[i].productId + "']").removeClass("disable");
                        }
                    }
                    _this.find("li[name='productSpecInfo']").filter(".disable").removeClass("on");
                    _this.find("li[name='productSpecInfo']").filter(".disable").find("a").removeAttr("href");

                    if (_this.find('li.on').size() == 0) {
                        $("#shopSize li").css('background', '#ededed');
                    }
                }
            }
        }
    });
}

var URL_INFO = {
    secoo_uid: "https://passport.secoo.com/login/isLoginJP.jsp?callback=?"
};
var LOGIN_ITEM = {
    SECOO_UID: "",USER_ID:""
};

Array.prototype.constains = function (element) {
     for (var i = 0; i < this.length; i++) {
        if (this[i] == element) {
            return true;
        }
    }
    return false;
};

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
 }


function get_secoo_uid(callback, async, param,bid,orgcode,cfid,level) {
	$.getJSON(URL_INFO.secoo_uid, function(datas) {
		if(datas.shortId != 0 && datas.userId != 0 ){
            LOGIN_ITEM.SECOO_UID = datas.shortId;
        	userTrack(param,bid,orgcode,cfid,level);

		}else{
			userTrack(param,bid,orgcode,cfid,level);

			LOGIN_ITEM.SECOO_UID = 0;
		}
	});	   

}

function userTrack(param,bid,orgcode,cfid,level){
	var uid = LOGIN_ITEM.SECOO_UID;
	if(!uid){
		var uuid = $.cookie("n1_ut_id");
		if(!uuid){
			uid = new Date().getTime()+(parseInt(Math.random()*99+1).toString());
			$.cookie("n1_ut_id",uid,{expires: 3650, path: '/',domain: '.secoo.com'});
		}else {
			uid = uuid;
		}
	}
	var img = new Image();
	img.src = "/track.gif?uid="+uid+"&pid="+param.id+"&price="+param.secooprice+"&bid="+bid+"&orgcode="+orgcode+"&cfid="+cfid+"&level="+level+"&_="+new Date().getTime();

}


function calXpressEquippedPrice(price,localSecooPrice,selfId,pkgPriceid) {
	var xpressEquippedPKGPriceInfo = $("#xpressEquippedPKGPrice"+pkgPriceid);
	if($("#"+selfId).attr("checked") == "checked"){
		xpressEquippedPKGPriceInfo.text(Number(xpressEquippedPKGPriceInfo.text())+Number(price));
	}else{
		xpressEquippedPKGPriceInfo.text(Number(xpressEquippedPKGPriceInfo.text())-Number(price));
	}
}

function addToLastBrousePre(secooPrice,mobilePrice){
	var productName = $("#productName").val();
	var productImage = $("#productImage").val();
	var marketPrice = $("#marketprice").val();
	var productCode = $("#productCode").val();
	var productId = $("#productIdVal").val();
	var areaType = $("#areaType").val();
	marketPrice = (marketPrice>100)?marketPrice:0;
	var upkStr = $.cookie("upk") == null ? "" : $.cookie("upk");
	var lastBrouseUrl = 'http://lr.secooimg.com/recent_browse?productId='+productId+'&productName='+encodeURIComponent(productName)
			+'&productImage='+productImage+'&productCode='+productCode
			+'&secooPrice='+secooPrice+'&mobilePrice='+mobilePrice+'&marketPrice='+marketPrice+'&isflash=0&browseType=0&upk='+upkStr;
	$.ajax({
		type: "GET",
		cache: false,
		dataType:"jsonp",
		url:lastBrouseUrl,
		success: function(datas, textStatus) {
			if (datas) {
				readBrowse(datas);
			}
		}
	});
	
}

function formatNum(num) {
	num = Math.round(num).toString();
	num = num.toString().replace(/\$|\,/g,'');  
    if(isNaN(num))  
    num = "0";  
    sign = (num == (num = Math.abs(num)));  
    num = Math.floor(num*100+0.50000000001);  
    cents = num%100;  
    num = Math.floor(num/100).toString();  
    if(cents<10)  
    cents = "0" + cents;  
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)  
    num = num.substring(0,num.length-(4*i+3))+','+  
    num.substring(num.length-(4*i+3));  
    //cents=小数点后的数字
    return (((sign)?'':'-')  + num);  
}

function readBrowse(datas){
	if(datas){
		var lastbrowsehtml ="<ul>";
		for(var i=(datas.length-1);i>=0;i--){
			var lazyImg="http://pic16.secooimg.com/imagesPic/lazyPic.png";
			lastbrowsehtml+="<li class='clearfix'>";
			lastbrowsehtml+="<div class='sPic fl'><img src='"+lazyImg+"' data-original='http://pic11.secooimg.com/product/50/50/"+datas[i].p+"' width='50' height='50'></div>";
			lastbrowsehtml+="<div class='sbox fr'>";
			lastbrowsehtml+="<div class='sName'>"+datas[i].n+"</div>";
			
			lastbrowsehtml+="<div class='sPri'><strong><span class='rmb'>&yen;</span>"+formatNum(datas[i].s)+"</strong></div>";
			
			lastbrowsehtml+="</div>";
			lastbrowsehtml+="<a href='http://item.secoo.com/"+datas[i].i+".shtml?source=detail_browse' title='"+datas[i].n+"' target='_blank'></a>";
			lastbrowsehtml+="</li>";
		 }		
		lastbrowsehtml+="</ul>";
		$("#lastBroswerProduct").append(lastbrowsehtml);	
		$("#lastBroswerProduct").find('img').lazyload();
	}
}
//大家都在买
function togetherBuy(){
	$.ajax({
		type: "GET",
		cache: false,
		dataType:"jsonp",
		url:'http://recommend.secoo.com/foreteller/recom?key=guesslike&type=a&userId=0&count=5&exSelf=true&hasRatio=true&qids=0' ,
		async: false,
		error: function(a, b, c){
		},
		success: function(datas, textStatus) {
			if (datas) {
				assemblyData(datas,"togetherBuy");
			}
		}
	});
}
//相关商品
function relatedProduct(){
	$.ajax({
		type: "GET",
		cache: false,
		dataType:"jsonp",
		url:'http://recommend.secoo.com/foreteller/recom?key=guesslike&type=a&userId=0&count=5&exSelf=true&hasRatio=true&qids='+$("#productIdVal").val() ,
		async: false,
		error: function(a, b, c){
		},
		success: function(datas, textStatus) {
			if (datas) {
				assemblyData(datas,"relatedProduct");
			}
		}
	});
}

function assemblyData(datas,id){
	if(datas){
		var lastbrowsehtml ="<ul>";
		for(var i=(datas.length-1);i>=0;i--){
			var lazyImg="http://pic16.secooimg.com/imagesPic/lazyPic.png";
			var urlImg = datas[i].imageUrl;
			if(urlImg!=null && urlImg.indexOf('http')>-1){
				urlImg = urlImg.substring(urlImg.indexOf('product/160/160/')+16,urlImg.length);
			}
			lastbrowsehtml+="<li class='clearfix'>";
			lastbrowsehtml+="<div class='sPic fl'><img src='"+lazyImg+"' data-original='http://pic11.secooimg.com/product/50/50/"+urlImg+"' width='50' height='50'></div>";
			lastbrowsehtml+="<div class='sbox fr'>";
			lastbrowsehtml+="<div class='sName'>"+datas[i].name+"</div>";
			
			lastbrowsehtml+="<div class='sPri'><strong><span class='rmb'>&yen;</span>"+formatNum(datas[i].userPrice)+"</strong></div>";
			
			lastbrowsehtml+="</div>";
			lastbrowsehtml+="<a href='http://item.secoo.com/"+datas[i].id+".shtml?source=detail_browse' title='"+datas[i].name+"' target='_blank'></a>";
			lastbrowsehtml+="</li>";
		 }		
		lastbrowsehtml+="</ul>";
		$("#"+id).append(lastbrowsehtml);	
		$("#"+id).find('img').lazyload();
	}
}

function setSubTitle(productId,brandId,categoryId){
	var requestUrl = "http://lr.secooimg.com/subtitle?productId="+productId+"&brandId="+brandId+"&categoryId="+categoryId;
	$.ajax({
		type: "GET",
		cache: false,
		dataType:"jsonp",
		url:requestUrl ,
		async: false,
		error: function(a, b, c){
		},
		success: function(datas, textStatus) {
			if (datas) {
				if(datas.length > 0){
					var data = datas.pop();
					var subhead = data.s;
					if(typeof(subhead)!="undefined" && subhead!=""){
						//副标题长度限制35 柳悦
						if(subhead.length > 35){
							subhead = subhead.substring(0,35);
						}
					}else{
						subhead = "";
					}

					var sign = data.showInDetailPage;
					if(typeof(sign)!="undefined" && sign!="" && sign==1){
						if(subhead!=""){
							subhead +="，";
						}
						subhead +='<a href="http://list.secoo.com/'+data.urlName+'" target="_blank">更多惊喜尽在'+data.enName+'寺库旗舰店</a>';
					}
					
					$("#subHeadSpan").html(subhead);
				}else{
					
                    $("#subHeadSpan").hide().remove();
                }
			}else{
                $("#subHeadSpan").hide().remove();
            }
		}
	});
}

function setSpecial(productId){
	var requestUrl = "http://lr.secooimg.com/special?productId="+productId;
	$.ajax({
		type: "GET",
		cache: false,
		dataType:"jsonp",
		url:requestUrl ,
		async: false,
		error: function(a, b, c){
		},
		success: function(datas, textStatus) {
			if (datas) {
				if(datas.length > 0){
					var type = datas[0].t;
					if(type == '3') { // 3,优惠券  
						var specialVal = $("#specialSign").html();
						specialVal +="<span>本商品为特例品,不支持使用优惠券</span></br>"
						$("#specialSign").html(specialVal);
						$("#specialSign").parent().show();
					}else if(type == '2' ){ // 会员折扣
						var specialVal = $("#specialSign").html();
						specialVal +="<span>本商品为特例品,不参与会员折扣</span></br>"
						$("#specialSign").html(specialVal);
						$("#specialSign").parent().show();
					}else if(type == '2,3' || type == '3,2') {
						var specialVal = $("#specialSign").html();
						specialVal +="<span>本商品为特例品,不支持优惠券和不参与会员折扣</span></br>"
						$("#specialSign").html(specialVal);
						$("#specialSign").parent().show();
					}
				
				}
			}
		}
	});
}

function showPickUpProductByCustomerReduceMoneyTips(inventoryJson){
	if($(".mtips").size() == 0){
		return ;
	}
	var secooPrice = inventoryJson.secooPrice;//寺库价
	var showMtips = false;
	//本商品到香港Secoo自提立减
		showMtips = true;
		var inventorys = inventoryJson.inventorys ;
		for(var i = 0,length = inventorys.length; i<length; i++){
			//保税仓(16:深圳保税仓)/香港中心仓(17)商品不展示到店自提立减
			var warehouseId = inventorys[i].warehouseId;
			if(warehouseId == 16) {
				showMtips = false;
				break;
			}
		}
	if(showMtips){
		//$(".mtips").show();
	}
}

function updatePrice(sign,secooPrice){
	if(sign){		
		$("#secooNameJs").html("<span class='colorRed f18'>一&nbsp;口&nbsp;价</span>");
	}
	$("#secooPriceJs").html(formatNum(secooPrice));
	
	var mprice = $(".mprice").text().split("¥")[1].replace(new RegExp(',','gm'),'');
	
	if((secooPrice>=mprice) || (mprice<100) ){
		$(".mprice").hide();
		$(".sale").hide();
	}else{
		// 去掉折扣价
	}
}

function detailPageService(pkgJson,xpressEquippedJson,productJson,secooPrice,areaType) {
	if ($("#specialSign").html().trim() == "") {
		$("#specialSign").parent().hide();
	}
	 var thirdCategoryName = $('#tName').val();
	 if(thirdCategoryName == '出境游' ){
		 $("#inventoryDiv").hide();
	 }
	var proId =   $('#productIdVal').val();
	if(proId == "13645131" || proId == "13645124" || proId == "13645138" || proId == "14529959"){// 1217 活动屏蔽商品
		 $("#inventoryDiv").hide();
	}
	if(xpressEquippedJson && productJson){
		var productDetail = productJson;
		var xpressEquipped = xpressEquippedJson;
		
		//库存
		var inventoryJson="";
		var inventoryStatusFlag = false;
		var requestUrl = "http://lr.secooimg.com/products?productIds=" + productDetail.id;
		$.ajax({
			type: "GET",
			cache: false,
			url:requestUrl,
			dataType:"jsonp",
			async: false,
			error: function(a, b, c){
				console.error("ajax repertory error !");
			},
			success: function(data, textStatus) {
				if (data.length > 0) {
					inventoryStatusFlag = true;
					inventoryJson = data[0];
					
					var newSecooPrice = inventoryJson.secooPrice;//寺库价
					var phActName = inventoryJson.phActName;// 活动名称
					var phActPhStartDate = inventoryJson.phActPhStartDate;//活动预热开始时间
					var phActPrice= inventoryJson.phActPrice;//活动价
					var phActStartDate=inventoryJson.phActStartDate;//活动开始时间
					var phActEndDate=inventoryJson.phActEndDate;//活动结束时间
					
					var exclusiveStartDate=inventoryJson.activityExclusiveStartDate//移动专享价开始时间
					var exclusiveEndDate=inventoryJson.activityExclusiveEndDate//移动专享价开始时间
					var activityExclusivePrice=inventoryJson.activityExclusivePrice;//移动专享价
					var activityExclusivePic = inventoryJson.appLinkPicUrl; // 移动专享二维码
				 	var isPresale = inventoryJson.isNewPresale; // 预售标识
				 	
					//动态变动价格
					if(typeof(inventoryJson.originalSecooPrice)!="undefined"){
						updatePrice(true,newSecooPrice)//修改价格
					}else{
						updatePrice(false,newSecooPrice)
					}
					//显示自提立减信息
					showPickUpProductByCustomerReduceMoneyTips(inventoryJson)
					
					if(areaType != 0){//海外商品
						var sendDayStr = "7-15";
						switch (areaType) {
							case 1:sendDayStr = "5-10";break;
							case 2:sendDayStr = "7-20";break;
							case 3:sendDayStr = "10-20";break;
							case 4:sendDayStr = "7-10";break;
							case 7:sendDayStr = "10-20";break;
							case 8:sendDayStr = "10-20";break;
						}
						var descriptionDelivery = "<i>预计<span class='color9d0'>"+sendDayStr+"</span>个工作日内送达</i>";
						$("#inventoryDiv").find(".delivery").html(descriptionDelivery);
					}else{
                        var inventorys = inventoryJson.inventorys;
                        for(var i=0;i<inventorys.length;i++){
                        	var inventory=inventorys[i];
                            if(inventory.warehouseId!=5&&inventory.size>0){
                                $("#inventoryDiv").find(".delivery").empty().html("17点前完成下单，预计72小时内送达");
                                break;
							}
						}
                    }
					
					if(isPresale == 1){
						$("#secondCart").parent().remove();
						$("#secooNameJs").html("");
				 		$("#secooNameJs").attr("class","fl");
				 		$("#secooNameJs").removeAttr("style")
				 		$("#secooNameJs").css({
				 			"background":"url(http://mpic.secooimg.com/images/2016/05/30/icon_ysj.png) no-repeat",
				 			"width":"48px",
				 			"height":"22px",
				 			"display":"inline-block",
				 			"margin":"4px 36px 0 -7px"
				 		});

				 		$("#youhuo").html("预定");
				 		$("#inventoryDiv").find(".delivery").html("<i>预计支付完成后3天内发货</i>");
				 		$("html").data("isPresale",isPresale);
				 	}
					
					if(inventoryJson.status == 0 || (inventoryJson.status == 1  && inventoryJson.size == 0 )){
						inventoryStatusFlag = false;
					}
					
					//有库存并且secooPrice大于零则正常进行。否则售罄。
					if(inventoryStatusFlag && newSecooPrice > 0){
						//修改库存信息
						wearhouse(productDetail,inventoryJson);
						$("#secondCart").css('display','block');
						
						if(isPresale == 1) {
							$("#addCarInfo").remove();
							$("#addBindCar").attr("class", "btn_detail fl");
							$("#specialSign").find("span").filter(":contains('本商品支持货到付款')").html("本商品不支持货到付款");
							var pickTips = $("#specialSign").find("span").filter(":contains('到店自提赠送精美礼品')");
							pickTips.nextAll().filter("br").remove();
							pickTips.remove();
							//可以支付尾款
							if(inventoryJson.canPayPresaleFinalPayment == 1){
								$("#addBindCar").attr("title", "立即购买");
								$("#addBindCar").html("立即购买");
							} else{
								var priceDisplayDiv = $("#secooNameJs").siblings().filter("div[class='pdd fl']");
						 		priceDisplayDiv.css({
						 			"width":"auto",
						 			"margin-right":"15px"
						 		});
				 				var depositAmount = newSecooPrice * 0.2;
				 				priceDisplayDiv.after("<div class='fl' style='color:#000; line-height:38px; height:30px; font-family:Microsoft yahei;'>(<span style='color:#e93a38; font-family:Microsoft yahei;'> 定金 ¥"+parseFloat(depositAmount.toFixed(2))+"</span> + 尾款 ¥"+parseFloat((newSecooPrice-depositAmount).toFixed(2))+" )</div>");
								$("#addBindCar").attr("title", "支付定金");
								$("#addBindCar").html("支付定金")
							}
							$("#appointment_bt").css("display","none")
						}
					}else{
						//售罄
						soldOut();
					}
					
					//预热活动
					tempActivity(phActName,phActPhStartDate,phActPrice,phActStartDate,phActEndDate);
					//手机专享价
					activityExclusive(exclusiveStartDate,exclusiveEndDate,activityExclusivePrice,activityExclusivePic);
					var valSing;
					if(!inventoryStatusFlag){
						valSing = 0;
					}else {
						valSing = 1;
					}
					
					suixinpei(productDetail,xpressEquipped,newSecooPrice);
					
					setTimeout(function () {
						//调用其它需要该数据的服务
						//cookie
						try{	
							//大家都在买，相关商品
							togetherBuy();
							relatedProduct();
						    var mobilePrice =0;
							if(activityExclusivePrice !=undefined && activityExclusivePrice !=""){
								if(exclusiveStartDate < nowTime   &&  exclusiveEndDate > nowTime ){
									mobilePrice = activityExclusivePrice;
								}
							 }
							addToLastBrousePre(newSecooPrice,mobilePrice);
							
							//晶赞
							thirdpartyRecommend(inventoryJson);
							marketInfo(valSing,newSecooPrice);
						 }catch(e){}
					}, 1000);

				}
			}
		});
	
	}else {
	
		$("#favorable-suit").hide();
		$("#inventoryDiv").hide();
		$("#addCarInfo").removeClass("addCar").addClass("addCar-no"); 

	}
			
}
//格式化预售发货时间
function  formatPreSaleSendDate(time){
	var now = new Date(time); 
	var month=now.getMonth()+1; 
	var day=now.getDate(); 
	return month+"月"+day+"日";
} 

//格式化预售结束时间
function  formatPreSaleEndDate(time){
	var now = new Date(time); 
	var month=now.getMonth()+1; 
	var day=now.getDate(); 
	
	var hour=now.getHours(); 
	if(hour<10) {hour = "0" + hour};
	var minute=now.getMinutes();
    if(minute<10) {minute = "0" + minute};
    var second=now.getSeconds(); 
    if(second<10) {second = "0" + second};
    
	return month+"月"+day+"日 " +hour+":"+minute+":"+second ;
} 

//预热活动
function tempActivity(phActName,phActPhStartDate,phActPrice,phActStartDate,phActEndDate){
	
  if(phActPrice != undefined && phActPrice !=""  && phActName != undefined && phActName !=""){
	 if(nowTime > phActPhStartDate  &&  phActStartDate > nowTime ){
			$("#tempActivityText").html(phActName);
			$("#tempActivityPrice").html("<span class='rmb'>¥</span>"+phActPrice);
			activityTempCountDown(phActStartDate-new Date(nowTime).getTime());
			$("#tempActivityDiv").show();
		}
	}
}
//活动预热倒计时
var acitivityTempTimer;
function activityTempCountDown(time) {
    var maxtime = time; 
    acitivityTempTimer=window.setInterval(function(){
        if (maxtime >= 0) {
            var day = Math.floor(maxtime / (1000 * 60 * 60 * 24)) ;
            var hours = Math.floor(maxtime / (1000 * 60 * 60))% 24 ;
            var minutes = Math.floor(maxtime / (1000 * 60)) % 60;
            var seconds = Math.floor(maxtime / 1000) % 60;
            var millsecond = Math.floor(maxtime / 100) % 100;
            if(minutes<10) minutes = "0" + minutes;
            if(seconds<10) seconds = "0" + seconds;
            if(millsecond<10) millsecond = "0" + millsecond;
            if(day >= 1){
                msg = "距离活动开始 <b>" + day + "</b>" + " 天 " + "<b>" + hours + "</b>" + " 时 " + "<b>" + minutes + "</b>" + " 分 " + "<b>" +seconds + "</b>" + " 秒 " + "<b>" + millsecond + "</b>" +" 毫秒" ;
            }else {
                msg = "距离活动开始 <b>" + hours + "</b>" + " 时 " + "<b>" + minutes + "</b>" + " 分 " + "<b>" +seconds + "</b>" + " 秒 " + "<b>" + millsecond + "</b>" +" 毫秒" ;
            }
            if(document.getElementById("tempActivityTimer")) {
                document.getElementById("tempActivityTimer").innerHTML = msg;
            }else {
                return;
            }
            maxtime -= 100;
        }else {
        	 clearInterval(acitivityTempTimer);
        	
        }        
    },100);        
}

//手机专享价促销信息
function activityExclusive(exclusiveStartDate,exclusiveEndDate,activityExclusivePrice,activityExclusivePic){
	 if(activityExclusivePrice !=undefined && activityExclusivePrice !=""){
		 if( exclusiveStartDate < nowTime   &&  exclusiveEndDate > nowTime ){
				var exclusiveStr="<div class='tips_hover'>";
				exclusiveStr+="<span class='prom_tips fl'>手机专享价</span>";
				exclusiveStr+="<span class='smallDprice fl padLeft_10 padRight_10'><span class='rmb'>¥</span>"+activityExclusivePrice+"</span>";
				exclusiveStr+="<span class='prom_text fl'   id='phone_text'><i class='icophone'></i>&nbsp;去手机购买 <i class='darrow'></i></span>";
				exclusiveStr+="</div>";
				exclusiveStr+="<div class='tips_prom' id='ewmTimer'>";
				var countDownMsg = activityExclusiveCountDown(exclusiveEndDate);
				if(countDownMsg !=""){
					exclusiveStr+=countDownMsg;
				}
				
				exclusiveStr+="<img src='http://pic12.secooimg.com/home/app_ewm.jpg'   data-original='http://pic12.secooimg.com/"+activityExclusivePic+"'  id='app_link_pic'    width='100' height='100' />";
				exclusiveStr+="</div>";
				
				$("#exclusiveDiv").html(exclusiveStr);
				$("#exclusiveDiv").show();
				$("#promotionInfoSpan").show();
				
				
		 }
	 }
}
function activityExclusiveCountDown(exclusiveEndDate){
	var betweenTime = exclusiveEndDate -nowTime;
	var  countDownMsg="";
	if(betweenTime > 0){
		 var day = Math.floor(betweenTime / (1000 * 60 * 60 * 24)) ;
         var hours = Math.floor(betweenTime / (1000 * 60 * 60))% 24 ;
         if(day >= 1){
        	 countDownMsg ="<p>手机专享倒计时&nbsp;<b>" + day + "</b>" + " 天 " + "<b>" + hours + " 小时</b></p>";
         }else {
        	 countDownMsg ="<p>手机专享倒计时&nbsp;<b>" + 0 + "</b>" + " 天 " + "<b>" + hours + " 小时</b></p>";
         }
 	}
	return countDownMsg;
}


//库存信息
function wearhouse(productDetail,inventoryJson){
	var address = inventoryJson.inventorys[0].warehouseId;//发货仓库
	var inventoryStr="";
	
	if(productDetail.source != 2){
		reserver(productDetail,address);//预存弹层内容填充
	}

	var areaType = $("#areaType").val();
	if(areaType == 0){
		inventoryStr ="大陆";
	}else if(areaType == 1){
		inventoryStr ="香港";
	}else if(areaType == 2){
		inventoryStr = "美国";
	}else if(areaType == 3){
		inventoryStr = "日本";
	}else if(areaType == 4){
		inventoryStr = "欧洲";
	}else if(areaType == 5){
		inventoryStr = "法国";
	}else if(areaType == 6){
		inventoryStr = "韩国";
	}else if(areaType == 7){
		inventoryStr = "澳洲";
	}else if(areaType == 8){
		inventoryStr = "新西兰";
	}else if(areaType == 9){
		inventoryStr = "马来西亚";
		$("#inventoryDiv .delivery").css("margin-left","85px");
	}else{
		inventoryStr = "其它";
	}
	
	var levelVal = $("#levelVal").val();
	if((address == 2 || address == 3 || address == 4) && productDetail.source != 2  ){
		$("#addCarInfo").after('<a href="javascript:void(0);" class="appointment fl" title="预约到会所购买" id="appointment_bt"><i></i>预约会所购买</a>');
	}
						
	$("#location-t").html("").append(inventoryStr);
	
	
	inventorySize = inventoryJson.size;
	if(Number(inventorySize) <= 5){
		$("#lastProductNum").html("仅剩 <span class='color9d0' >"+inventorySize+"</span> 件，抓紧时间购买哦！");
	}
	var buyNumVal = $("#buyNumVal").val();
	if (Number(buyNumVal) == Number(inventorySize)) {
		$("#addProduct").attr("class", "up fl nMinus");
	}
}

//售罄
function soldOut(){
	$("#location-t").next().text('无货');
	$("#addBindCar").removeClass("topanic").addClass('topanic_end');
	$("#addBindCar").removeAttr("id");
	$('#addCarInfo').remove();
	$('.product_more').show();
	//售罄页面隐藏随心配 liuyue
	$(".folloWith").hide();
	
	$(window).load(function(){
		$("#secondCart").css('display','none');
		$(".to_cart_end").css('display','block');
	});
}

//随心配处理
function suixinpei(productDetail,xpressEquipped,price){
	
	if(xpressEquipped.xpressEquipped == "null"){
		$(".folloWith").hide();
	}else{
		$(".folloWith").show();
		
		var sxpInfo=[];
		var fBox_title=[];
		var fBox_pic_l=[];
		var fBox_pic_r_move_box=[];
		fBox_title.push('<div class="fBox_title">');
		fBox_pic_r_move_box.push('<div class="fBox_pic_r_move_box" style="top: 0px;">');
		
		var fBox_titles=[];
		var sum = 0;
		$.each(xpressEquipped,function(key,value){
			var ilen = value.length;
			sum = sum + ilen;
			fBox_titles.push('<a href="###">'+key+'（'+ilen+'）</a>');	
			for(var i=0;i<ilen;i++){
				fBox_pic_r_move_box.push('<dl pname="'+key.replace(' ','')+'" pId='+value[i].id+'>');	
				fBox_pic_r_move_box.push('<dd>');	
				fBox_pic_r_move_box.push('<a href="http://item.secoo.com/'+value[i].id+'.shtml" target="_blank"><img src="http://pic11.secooimg.com/product/120/120/'+value[i].imgUrl+'" width="120" height="120"></a>');	
				fBox_pic_r_move_box.push('<div>');	
				fBox_pic_r_move_box.push('<h6>搭配价</h6>');	
				fBox_pic_r_move_box.push('<span price='+value[i].secooPrice+'>￥<i>'+formatNum(value[i].secooPrice)+'</i></span>');	
				if(value[i].marketprice > value[i].secooPrice){												
					fBox_pic_r_move_box.push('<del>￥'+value[i].marketprice+'</del>');	
				}else{
					fBox_pic_r_move_box.push('<del class="none">&nbsp;</del>');	
				}
				fBox_pic_r_move_box.push('<label>选中购买<input type="checkbox"></label>');	
				fBox_pic_r_move_box.push('</div>');	
				fBox_pic_r_move_box.push('</dd>');	
				fBox_pic_r_move_box.push('<dt><a href="http://item.secoo.com/'+value[i].id+'.shtml" target="_blank">'+value[i].name+'</a></dt>');	
				fBox_pic_r_move_box.push('</dl>');
			}
		});
		
		fBox_pic_r_move_box.push('</div>');
		
		fBox_title.push('<a href="###" class="on">全部（'+sum+'）</a>')
		fBox_title.push(fBox_titles.join(''));
		fBox_title.push('</div>');
		
		fBox_pic_l.push('<div class="fBox_pic_l fl">');
		fBox_pic_l.push('<dl>');
		fBox_pic_l.push('<dd><a href="###"><img src="http://pic11.secooimg.com/product/120/120/'+productDetail.imgUrl+'" width="120" height="120"></a></dd>');
		fBox_pic_l.push('<dt><a href="" target="_blank">'+productDetail.name+'</a></dt>');
		fBox_pic_l.push('</dl>');
		fBox_pic_l.push('</div>');
		
		sxpInfo.push(fBox_title.join(''));
		sxpInfo.push('<div class="fBox_pic clearfix">');
			sxpInfo.push(fBox_pic_l.join(''));
			sxpInfo.push('<div class="f_plus fl"></div>');
			sxpInfo.push('<div class="fBox_pic_r fl">');
			sxpInfo.push(fBox_pic_r_move_box.join(''));
			sxpInfo.push('</div>');
			sxpInfo.push('<div class="fBox_btn fl">');
			sxpInfo.push('<a href="javascript:;" class="up"></a>');
			sxpInfo.push('<a href="javascript:;" class="down"></a>');
			sxpInfo.push('</div>');
			sxpInfo.push('<div class="fBox_cart fl">');
			sxpInfo.push('搭配总价');
			sxpInfo.push('<span price='+price+'><b>￥</b>'+formatNum(price)+'</span>');
			sxpInfo.push('<a href="###" pId='+productDetail.id+'>加入购物车</a>');
			sxpInfo.push('</div>');
		sxpInfo.push('</div>');	
		$("#sxpInfo").html(sxpInfo.join('')).show();
		$(".fBox_pic_r_move_box dl:visible").last().addClass('noBk');
		suixinpeiTabs();
		suixinpeiFun();
		suixinpeiJ();
		addXeToCar();
	}	
}
function suixinpeiJ(){
	$(".fBox_pic_r_move_box dl input").click(function(){
		var price = parseInt($(".fBox_cart span").attr('price'));
		var thisprice = parseInt($(this).parents('dl').find('span').attr('price'));			
		if($(this).attr('checked')){
			$(".fBox_cart span").attr('price',price+thisprice).html('<b>￥</b>'+formatNum(price+thisprice))
		}else{
			$(".fBox_cart span").attr('price',price-thisprice).html('<b>￥</b>'+formatNum(price-thisprice))
		}
	})
}
function suixinpeiTabs(){
	$("#sxpInfo").delegate('.fBox_title a','click',function(){
		$(".fBox_pic_r_move_box dl").removeClass('noBk')
		$(this).addClass('on').siblings().removeClass('on');
		if($.trim($(this).text().split('（')[0])=="全部"){
			$(".fBox_pic_r_move_box dl").show();
		}else{			
			$(".fBox_pic_r_move_box dl").show();
			$(".fBox_pic_r_move_box dl[pname!='"+$.trim($(this).text().split('（')[0]).replace(' ','')+"']").hide();			
		}
		if($(".fBox_pic_r_move_box dl:not(:hidden)").size()<=3){
			$(".fBox_btn").css('visibility','hidden');
		}else{
			$(".fBox_btn").css('visibility','visible');
		}
		$(".fBox_pic_r_move_box dl:visible").last().addClass('noBk');
        if($("body").hasClass('skSmallStyle')){
            window['fBoxNum'] = 0;
            window['sizeNum'] = 2;
        }else{
            window['fBoxNum'] = 0;
            window['sizeNum'] = 3;	                
        }
        $('.fBox_pic_r_move_box').css('top',0);
	});
}
function suixinpeiFun(){
	if($('.fBox_pic_r_move_box dl:visible').size()<=3){
		$(".fBox_btn").css('visibility','hidden');
		return;
	}
	window['fBoxNum'] = 0,window['xNum'] = 1,window['timer'];
	window['sizeNum'] =3 ;
        $(window).resize(function(){
        	clearTimeout(window['timer']);
        	timer = setTimeout(function(){
	            if($("body").hasClass('skSmallStyle')){
	                fBoxNum = 0;
	                window['sizeNum'] = 2;
	            }else{
	                fBoxNum = 0;
	                window['sizeNum'] = 3;	                
	            }
	            $('.fBox_pic_r_move_box').css('top',0);
            },500)
        }).trigger('resize');
        $(".fBox_btn").delegate('a.up','click',function(){
            var box = $('.fBox_pic_r_move_box');
            fBoxNum--;
            if(fBoxNum<0){fBoxNum=0;}
            box.stop(true,true).animate({top:'-'+(fBoxNum*160)+'px'});
        }).delegate('a.down','click',function(){
                    var box = $('.fBox_pic_r_move_box');
                    var page = parseInt(box.find('dl:visible').size()/window['sizeNum']);
                    if(box.find('dl:visible').size()%window['sizeNum']!=0){
                        page+=1;
                    }
                    fBoxNum++;
                    if(fBoxNum>page-xNum){fBoxNum=page-xNum;}
                    box.stop(true,true).animate({top:'-'+fBoxNum*160+'px'});
                });

}
//加入购物车，未完成
function addXeToCar() {
	$(".fBox_cart").delegate('a','click',bind);
	function bind(){
		var pid = $(this).attr('pId');
		dataLayer.push({
		     'productID': pid,   //此处赋值商品id
		     'buttonlabel': '商品页随心配',
		     'event': 'buttonclick'});	
		var ids = [];
		ids.push(pid);
		$(".fBox_pic_r_move_box input:checked").each(function(){
			ids.push($(this).parents('dl').attr('pId'));
		});
		ids.push('');
		var url = "http://shopping.secoo.com/cart/cart.jsp?process=6&items=";
		var para = ids.join("$0$1#");
		
		//取得所有勾选中的随心配ID
		
		if(para.lastIndexOf("#") != -1){
			para = para.substring(0, para.length-1);
		}
		para = encodeURIComponent(para);
		url+=para;
		window.location=url;
	}
}


function productCarOper(oper) {
	var buyNumVal = $("#buyNumVal").val();
	if(oper == "add"){
		if(Number(buyNumVal) < Number(inventorySize) ){
			buyNumVal = Number(buyNumVal)+1; 
			$("#buyNumVal").val(buyNumVal);
		}
	}else if(oper == "sub"){
		if(Number(buyNumVal) > 1  ){
			buyNumVal = Number(buyNumVal)-1; 
			$("#buyNumVal").val(buyNumVal);
		}
	}
	if(Number(buyNumVal) > 1){
		$("#subProduct").attr("class", "down fl");
		if (Number(buyNumVal) == Number(inventorySize)) {
			$("#addProduct").attr("class", "up fl nMinus");
		}else {
			$("#addProduct").attr("class", "up fl");
		}
	} 
	if (Number(buyNumVal) == 1) {
		$("#subProduct").attr("class", "down fl nMinus");
	}
} 




function checkCarOper() {
	var buyNumVal = $("#buyNumVal").val();
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(buyNumVal)){
		$("#buyNumVal").val("1");
	}
	
	if(Number(buyNumVal) > Number(inventorySize) ){
		$("#buyNumVal").val(inventorySize);
	}else if (Number(buyNumVal) < 1 ) {
		$("#buyNumVal").val("1");
	}
} 

function bindCar() {
//	$("#addCarInfo").live("click",function (){
		if($("#shopSize").size()>0){
			if($("#shopSize").find('li.on').size()==0){
				return false;
			}
		}
		var pid = $("#productIdVal").val();
		var quantity = $("#buyNumVal").val();
		
		var isPresale = $("html").data("isPresale")
		if(isPresale == 1){
			var presaleForwardUrl = "";
			if(sourceStr != null){
				presaleForwardUrl = "http://shopping.secoo.com/cart/cart_confirm.jsp?process=0&from=presale&source="+sourceStr+"&itemKey="+pid+"&quantity="+quantity+"&checkedService="+checkedService;
			}else{
				presaleForwardUrl = "http://shopping.secoo.com/cart/cart_confirm.jsp?process=0&from=presale&itemKey="+pid+"&quantity="+quantity+"&checkedService="+checkedService;
			}
			window.location=presaleForwardUrl;
			return ;
		}

		dataLayer.push({
		     'productID': pid,   //此处赋值商品id
		     'buttonlabel': '商品页购物车',
		     'event': 'buttonclick'});	
		var url ="";
		if(sourceStr != null){
			url = "http://shopping.secoo.com/cart/cart.jsp?process=1&source="+sourceStr+"&productId="+pid+"&quantity="+quantity+"&checkedService="+checkedService;
		}else {
			url = "http://shopping.secoo.com/cart/cart.jsp?process=1&productId="+pid+"&quantity="+quantity+"&checkedService="+checkedService;
		}
		window.location=url;
//	});
} 

function detailPageServiceWithPromotion(productId,brandId,orgcode,firstcategoryid) {
	if(firstcategoryid == 424){
		$("#promotionInfoSpan").hide();
		return;
	}
	var requestUrl = "http://lr.secooimg.com/promotions?productId="+productId+"&brandId="+brandId+"&org_code="+orgcode+"&source=10";
	$.ajax({
		type: "GET",
		cache: false,
		url:requestUrl,
		async: false,
		dataType: "jsonp",
		error: function(a, b, c){
		},
		success: function(datas, textStatus) {
			if (datas) {
				if(datas.length > 0){
					var data = datas.pop();
					var remark = data.r;
					var markingName = data.m;
					var link = data.l;
					var name = data.n;
					var productPromotionStr="<div class='tips_hover'>";
					productPromotionStr+="<span class='prom_tips fl'>";
					productPromotionStr+= markingName;
					productPromotionStr+="</span>";
					productPromotionStr+="<span class='prom_text fl'>";
					productPromotionStr+=name;
					productPromotionStr+="</span>";
					
					if(typeof(link)!="undefined" && link!=""){
						if(link.indexOf("http://") != -1){
							productPromotionStr+="<a href="+link+" class='fl padLeft_10'  target='blank' >详情</a>";
						}else {
							productPromotionStr+="<a href=http://"+link+" class='fl padLeft_10' target='blank' >详情</a>";
						} 
						productPromotionStr+="&nbsp;<i class='darrow'></i>";
					}else{
						productPromotionStr+="&nbsp;<i class='darrow'></i>";
					}

					productPromotionStr+="</div>";
					productPromotionStr+="<div class='tips_prom'>";
					if(typeof(remark)!="undefined"){								
						productPromotionStr+="<p>"+remark+"</p>";
					}
					productPromotionStr+="</div>";
					$("#promotionRealDiv").html(productPromotionStr);
					$("#promotionRealDiv").show();
					$("#promotionInfoSpan").show();
				}
			}
		},complete:function(){
			$('#promotionRealDiv .tips_prom').width($('#promotionRealDiv .tips_hover').width());
		}
	});
} 


/*add by jiangnan 2013-08-26 */
function reserver(productJson,address){
	var rearea = "";
	if(address == 2){
		$("#reserver_huisuo").html('您预约的会所：<span class="color9d0">北京会所</span>');
		$("#reserver_info").html('会所信息：<span class="color9d0">北京市东城区金宝街18-8号。联系电话：010-65287779。营业时间：10：00--21：00</span>');
		rearea = 'bj';
	}else if(address == 3){
		$("#reserver_huisuo").html('您预约的会所：<span class="color9d0">上海会所</span>');
		$("#reserver_info").html('会所信息：<span class="color9d0">上海市南京西路758号。联系电话：021-62627779。营业时间：10：00--21：00</span>');
		rearea = 'sh';
	}else if(address == 4){
		$("#reserver_huisuo").html('您预约的会所：<span class="color9d0">成都会所</span>');
		$("#reserver_info").html('会所信息：<span class="color9d0">成都市人民南路111号航天科技大厦3层。联系电话：028-65111118。营业时间：10：00-22：00</span>');
		rearea = 'cd';
	}
	var productDetail = productJson;
	$("#appointment_bt").live("click",function (){
		$.getJSON(URL_INFO.secoo_uid, function(datas) {
			if(datas.shortId != 0 && datas.userId != 0 ){
				//加载时间控件
				$.getScript('http://misc.secoo.com/js/DatePicker/LotteryWdatePicker.js',null);
				
				LOGIN_ITEM.SECOO_UID = datas.shortId;
				LOGIN_ITEM.SECOO_LID = datas.userId;
				$(".newblackMask").height($(document).height()).fadeTo("fast",0.5);
				$(".qucikLogin,.loginMsg").show();
			}else {
				LOGIN_ITEM.SECOO_UID = "";
	            LOGIN_ITEM.SECOO_LID = "";
	            var url = document.URL;
	            document.domain="secoo.com";
				user_login_reg(url,null,''); 
				setBkHeightForIE7();
			}
		});	
		
	});
	$(".loginMsgClose,.newblackMask").live("click",function(){
		$(".newblackMask").fadeTo("fast",0,function(){$(".newblackMask").hide();});
        $(".qucikLogin,.loginMsg,.popwinBox,.popwinBox_guanzhu,.popbox5fq,.popwinBox_jrgwc,.popwinBox_sccg,.popwinBox_gz").hide();
    });
	$("#retel").live("blur",function(){
		valideReForm("retel");
	});
	$("#rename").live("blur",function(){
		valideReForm("rename");
	});
	$("#redate").live("blur",function(){
		valideReForm("redate");
	});
	
	var timer_id;
	var m=30;
	$("#resubmit").live("click",function(){
		$("#remsg_error").html("");
		if(subReForm(productDetail.id,rearea)==false){
			return;
		}
		$("#resubmit").text("若未收到"+m+"秒后可再提交");
		$("#resubmit").addClass('btnStyle01').removeClass('btnStyle02');
		$("#resubmit").attr("id","resubmit1");
		timer_id=window.setInterval(function(){
			if(m>0){
				$("#resubmit1").text("若未收到"+m+"秒后可再提交");
				m--;
			}else{
				m = 30;
				$("#remsg_error").html("");
				$("#resubmit1").attr("id","resubmit");
				$("#resubmit").addClass('btnStyle02').removeClass('btnStyle01');
				$("#resubmit").text("提交");
				$("#rename").val("");
				$("#retel").val("");
				window.clearInterval(timer_id);
			} 
		},1000);
	});
	$("#recheck").live("click",function(){
		dataLayer.push({
		     'productID': productDetail.id,   //此处赋值商品id
		     'buttonlabel': '商品页购物车',
		     'event': 'buttonclick'});	
		var url="";
		if(sourceStr != null){
			url = "http://shopping.secoo.com/cart/cart.jsp?process=1&source="+sourceStr+"&productId="+productDetail.id+"&quantity=1";
		}else {
			url = "http://shopping.secoo.com/cart/cart.jsp?process=1&productId="+productDetail.id+"&quantity=1";
		}		
		
		window.location=url;	
	});
	
}
function valideReForm(type){
	if(type=="retel"){
		var retel = $.trim($("#retel").val());
		$("#retel_error").html("");
		if(retel==""||retel==null){
			$("#retel_error").html("请输入手机号码！");
			return null;
		}
		
		if(!retel.match(/^(1([34578][0-9]))\d{8}$/)){
			$("#retel_error").html("请输入正确的手机号码！");
			return null;
		}else{
			return retel;
		}
	}
	if(type=="rename"){
		var rename = $.trim($("#rename").val());
		$("#rename_error").html("");
		if(rename==""||rename==null){
			$("#rename_error").html("请输入您的姓名！");
			return null;
		}
		if(rename.match(/^[\u4e00-\u9fa5]+$/g)){
			if(rename.length>6||rename.length<2){
				$("#rename_error").html("您的姓名长度不符合规定范围！");
				return null;
			}
			return rename;
		}else if(rename.match(/^[a-zA-Z]+$/g)){
			if(rename.length>20||rename.length<3){
				$("#rename_error").html("您的姓名长度不符合规定范围！");
				return null;
			}
			return rename;
		}else{
			$("#rename_error").html("请输入正确的姓名！");
			return null;
		}
	}
	if(type=="redate"){
		var redate = $.trim($("#redate").val());
		$("#redate_error").html("");
		if(redate==""||redate==null){
			$("#redate_error").html("请选择预约到店日期！");
			return null;
		}
		if(!redate.match(/^([0-9]{4}-[0-9]{2}-[0-9]{2})$/)){
			$("#redate_error").html("请输入正确的日期！");
			return null;
		}else{
			var begin = new Date(Number(redate.substring(0,4)) , Number(redate.substring(5,7))-1 , Number(redate.substring(8,10)));
			var now   = new Date();
			now.setDate(now.getDate()-1);
			var y = now.getFullYear();
		    var m = now.getMonth()+1;//获取当前月份的日期
		    var d = now.getDate();
			if(begin.getTime()<=now.getTime()){
				$("#redate_error").html("预约日期须在"+y+"-"+m+"-"+d+"后！");
				return null;
			}
		}
		return redate;
	}
	if(type=="remark"){
		var remark = $.trim($("#remark").val());
		if(remark==null){
			return "";
		}
		$("#remark_error").html("");
		if(remark.length>150){
			$("#remark_error").html("备注不能超过150字！");
			return null;
		}
		return remark;
	}
}
function subReForm(productId,rearea){
	var retel = valideReForm("retel");
	var rename = valideReForm("rename");
	var redate = valideReForm("redate");
	var remark = valideReForm("remark");
	if(retel==null){
		return false;
	}
	if(rename==null){
		return false;
	}
	rename = encodeURI(rename);
	if(redate==null){
		return false;
	}
	if(remark==null){
		return false;
	}
	remark = encodeURI(remark);
	
	var result = false;
	
	
	var url= "http://sa.secoo.com/activity/bookingClubs";
	$.ajax({
		type : "post", 
		dataType:"jsonp",
		data : {rename:rename,retel:retel,redate:redate,rearea:rearea,productId:productId,remark:remark}, 
		url:url,
		async:false,
		success:function(data){
			if(data){
				if(data.result==500){
					$("#remsg_error").html("发送失败，请于30秒后重新提交！");
				}else if(data.result==200){
					$("#remsg_error").html("预约成功，短信已发送至您的手机。您的预约号是："+retel+"。请等待销售专员与您电话联系！");
				}
				result = true;
			}else{
				$("#remsg_error").html("发送失败，请于30秒后重新提交！");
				return true;
			}
		}	
	});
}

/**
 * Created by Administrator on 2014/11/20. ***********************************************************************************
 */
// JavaScript Document
$(function(){
	//详情图片滚动，放大
    $("img.jqzoom").jqueryzoom({
            xzoom: 460,
            yzoom: $("div.info_l")[0].offsetHeight-2,
            offset: 30,
            left: 430,
            top: -1,
            position: "right"
        });
    var i = 0;
    $(".info_l").delegate('div.prev','click',function(){
        var box = $(this).next().find('div');
        i--;
        if(i<0){i=0;}
        box.stop(true,true).animate({top:'-'+(i*90)+'px'});
    }).delegate('div.next','click',function(){
        var box = $(this).prev().find('div');
        var max = box.find('a').size()-4;
        i++;
        if(i>max){i=max;}
        box.stop(true,true).animate({top:'-'+i*90+'px'});
    }).delegate('div.move_box a','mouseenter',function(){
        $(this).siblings().removeClass('on').find('i').remove();
        $(this).append('<i></i>').addClass('on');

    });
    tabimg('#imgshow dd a', '#imgshow dt img', 'imgselect', 'bigsrc', 'supsrc');
	//加入购物车按钮添加
	$("#secondCart").bind('click',addToCart);
	//tabs随动
	$(window).scroll(function(){
        var nav = $("#firstNav"),navBox = $(".showAddsRight");
		var detail_comment= $(".detail_comment").offset().top;
		var commentNav = $("#detail_comment .dc_title");
        if($(this).scrollTop()>navBox.offset().top && $(this).scrollTop()<detail_comment-50){
            nav.addClass('onfix').next().css('padding-top','36px');
        }else{
            nav.removeClass('onfix').next().removeAttr('style');
        }
		if($(this).scrollTop()>detail_comment && $(this).scrollTop() < detail_comment + $(".detail_comment").height()){
			commentNav.addClass('onfix').next();
		}else{
			commentNav.removeClass('onfix').next();
		}
		if($(this).scrollTop()>$(window).height()){
			$('.fsFixedTopContent').fadeIn();	
		}else{
			$('.fsFixedTopContent').fadeOut();	
		}
    })

    
    
	$(".fsFixedTop a.fsbacktotop").click(function(){$("html,body").stop().animate({scrollTop:0},400)});
    (function($){$(document).ready(function(){$('.cloud-zoom, .cloud-zoom-gallery').CloudZoom()});function format(a){for(var i=1;i<arguments.length;i++){a=a.replace('%'+(i-1),arguments[i])}return a}function CloudZoom(g,i){var j=$('img',g);var k;var l;var m=null;var n=null;var o=null;var p=null;var q=null;var r=null;var s;var t=0;var u,ch;var v=0;var z=0;var A=0;var B=0;var C=0;var D,my;var E=this,zw;setTimeout(function(){if(n===null){var w=g.width();g.parent().append(format('<div style="width:%0px;position:absolute;top:75%;left:%1px;text-align:center" class="cloud-zoom-loading" >Loading...</div>',w/3,(w/2)-(w/6))).find(':last').css('opacity',0.5)}},200);var F=function(){if(r!==null){r.remove();r=null}};this.removeBits=function(){if(o){o.remove();o=null}if(p){p.remove();p=null}if(q){q.remove();q=null}F();$('.cloud-zoom-loading',g.parent()).remove()};this.destroy=function(){g.data('zoom',null);if(n){n.unbind();n.remove();n=null}if(m){m.remove();m=null}this.removeBits()};this.fadedOut=function(){if(m){m.remove();m=null}this.removeBits()};this.controlLoop=function(){if(o){var x=(D-j.offset().left-(u*0.5))>>0;var y=(my-j.offset().top-(ch*0.5))>>0;if(x<0){x=0}else if(x>(j.outerWidth()-u)){x=(j.outerWidth()-u)}if(y<0){y=0}else if(y>(j.outerHeight()-ch)){y=(j.outerHeight()-ch)}o.css({left:x,top:y});o.css('background-position',(-x)+'px '+(-y)+'px');v=(((x)/j.outerWidth())*s.width)>>0;z=(((y)/j.outerHeight())*s.height)>>0;B+=(v-B)/i.smoothMove;A+=(z-A)/i.smoothMove;m.css('background-position',(-(B>>0)+'px ')+(-(A>>0)+'px'))}t=setTimeout(function(){E.controlLoop()},30)};this.init2=function(a,b){C++;if(b===1){s=a}if(C===2){this.init()}};this.init=function(){$('.cloud-zoom-loading',g.parent()).remove();n=g.parent().append(format("<div class='mousetrap' style='background-image:url(\".\");z-index:999;position:absolute;width:%0px;height:%1px;left:%2px;top:%3px;\'></div>",j.outerWidth(),j.outerHeight(),0,0)).find(':last');n.bind('mousemove',this,function(a){D=a.pageX;my=a.pageY});n.bind('mouseleave',this,function(a){clearTimeout(t);if(o){o.fadeOut(299)}if(p){p.fadeOut(299)}if(q){q.fadeOut(299)}m.fadeOut(300,function(){E.fadedOut()});return false});n.bind('mouseenter',this,function(a){D=a.pageX;my=a.pageY;zw=a.data;if(m){m.stop(true,false);m.remove()}var b=i.adjustX,yPos=i.adjustY;var c=j.outerWidth();var d=j.outerHeight();var w=i.zoomWidth;var h=i.zoomHeight;if(i.zoomWidth=='auto'){w=c}if(i.zoomHeight=='auto'){h=d}var e=g.parent();switch(i.position){case'top':yPos-=h;break;case'right':b+=c;break;case'bottom':yPos+=d;break;case'left':b-=w;break;case'inside':w=c;h=d;break;default:e=$('#'+i.position);if(!e.length){e=g;b+=c;yPos+=d}else{w=e.innerWidth();h=e.innerHeight()}}m=e.append(format('<div id="cloud-zoom-big" class="cloud-zoom-big" style="display:none;position:absolute;left:0px;top:0px;width:%2px;height:%3px;background-image:url(\'%4\');z-index:99;"></div>',b,yPos,w,h,s.src)).find(':last');if(j.attr('title')&&i.showTitle){m.append(format('<div class="cloud-zoom-title">%0</div>',j.attr('title'))).find(':last').css('opacity',i.titleOpacity)}if($.browser.msie&&$.browser.version<7){r=$('<iframe frameborder="0" src="#"></iframe>').css({position:"absolute",left:b,top:yPos,zIndex:99,width:w,height:h}).insertBefore(m)}m.fadeIn(500);if(o){o.remove();o=null}u=(j.outerWidth()/s.width)*m.width();ch=(j.outerHeight()/s.height)*m.height();o=g.append(format("<div class = 'cloud-zoom-lens' style='display:none;z-index:98;position:absolute;width:%0px;height:%1px;'></div>",u,ch)).find(':last');n.css('cursor',o.css('cursor'));var f=false;if(i.tint){o.css('background','url("'+j.attr('src')+'")');p=g.append(format('<div style="display:none;position:absolute; left:0px; top:0px; width:%0px; height:%1px; background-color:%2;" />',j.outerWidth(),j.outerHeight(),i.tint)).find(':last');p.css('opacity',i.tintOpacity);f=true;p.fadeIn(500)}if(i.softFocus){o.css('background','url("'+j.attr('src')+'")');q=g.append(format('<div style="position:absolute;display:none;top:2px; left:2px; width:%0px; height:%1px;" />',j.outerWidth()-2,j.outerHeight()-2,i.tint)).find(':last');q.css('background','url("'+j.attr('src')+'")');q.css('opacity',0.5);f=true;q.fadeIn(500)}if(!f){o.css('opacity',i.lensOpacity)}if(i.position!=='inside'){o.fadeIn(500)}zw.controlLoop();return})};k=new Image();$(k).load(function(){E.init2(this,0)});k.src=j.attr('src');l=new Image();$(l).load(function(){E.init2(this,1)});l.src=g.attr('href')}$.fn.CloudZoom=function(d){try{document.execCommand("BackgroundImageCache",false,true)}catch(e){}this.each(function(){var c,opts;eval('var	a = {'+$(this).attr('rel')+'}');c=a;if($(this).is('.cloud-zoom')){$(this).css({'position':'relative','display':'block'});$('img',$(this)).css({'display':'block'});if($(this).parent().attr('id')!='wrap'){$(this).wrap('<div id="wrap" style="top:0px;z-index:9999;position:relative;"></div>')}opts=$.extend({},$.fn.CloudZoom.defaults,d);opts=$.extend({},opts,c);$(this).data('zoom',new CloudZoom($(this),opts))}else if($(this).is('.cloud-zoom-gallery')){opts=$.extend({},c,d);$(this).data('relOpts',opts);$(this).bind('click',$(this),function(a){var b=a.data.data('relOpts');$('#'+b.useZoom).data('zoom').destroy();$('#'+b.useZoom).attr('href',a.data.attr('href'));$('#'+b.useZoom+' img').attr('src',a.data.data('relOpts').smallImage);$('#'+a.data.data('relOpts').useZoom).CloudZoom();return false})}});return this};$.fn.CloudZoom.defaults={zoomWidth:'auto',zoomHeight:'auto',position:'right',tint:false,tintOpacity:0.5,lensOpacity:0.5,softFocus:false,smoothMove:3,showTitle:true,titleOpacity:0.5,adjustX:0,adjustY:0}})(jQuery);
    $(".mprice").html('<span class="rmb">¥</span><span class="rmb" style="font-family: tahoma;">'+$(".mprice").text().split("¥")[1]+'</span>');
    
    $(".shopColor_Pic a").click(function(){
        if($(this).attr("class") != "disable"){
            $(this).addClass("lightColor").siblings("a").removeClass("lightColor")
        }
    });
    $(".zsPro").hover(function(){
        $(this).addClass("menu-hover")
        $(".zsProShow").show()
    },function(){
        $(this).removeClass("menu-hover")
        $(".zsProShow").hide()
    });

	if($(".move_box a").size()>4){
            $("#imgshow").mouseenter(function(){
                $('div.prev,div.next').fadeIn();
            }).mouseleave(function(){
                $('div.prev,div.next').fadeOut();
            });
			$("#imgshow .next,#imgshow .prev").mouseenter(function(){
				$(this).addClass('hov');
			}).mouseleave(function(){
				$(this).removeClass('hov');
			});
        }

    
    $('.carousel').elastislide({
        imageW 	: 80,
        minItems	: 5,
        margin : 7
    });
    $(".es-nav span").hover(function(){
        $(this).css("background-color","#f5f5f5")
    },function(){
        $(this).css("background-color","")
    })
    $(".pdd").delegate('.tips_hover','mouseenter',function(){
    	$(this).parent().addClass("menu-hover");
        $(this)[0].timerout1;
    	
    }).delegate('.tips_hover','mouseleave',function(){
    	var _s = $(this);
        $(this)[0].timeout1 = setTimeout(function(){
            _s.parent().removeClass("menu-hover");
        },200)
    	
    })
//    $(".tips_hover").hover(function(){
//        $(this).parent().addClass("menu-hover");
//        $(this)[0].timerout1;
//    },function(){
//        var _s = $(this);
//        $(this)[0].timeout1 = setTimeout(function(){
//            _s.parent().removeClass("menu-hover");
//        },200)
//    });
    $(".pdd").delegate(".tips_level,.tips_give,.tips_prom,.tips_delivery",'mouseenter',function(){
    	clearTimeout($(this).prev()[0].timeout1);
    }).delegate(".tips_level,.tips_give,.tips_prom,.tips_delivery",'mouseleave',function(){
    	$(this).parent().removeClass("menu-hover");
    })
//    $(".tips_level,.tips_give,.tips_prom,.tips_delivery").hover(function(){
//        clearTimeout($(this).prev()[0].timeout1);
//    },function(){
//        $(this).parent().removeClass("menu-hover");
//    });
    $(".items li:first").addClass("active");
    $('.es-carousel').on("mouseover", ".items a" ,function(e) {
        $(this).parent().addClass("active").siblings().removeClass("active")
        if ($(this).attr('href') && $(this).attr("rel")) {
            e.preventDefault();
            var mainImgSrc = $(this).attr('href'); //��ͼ
            var zoomedImgSrc = $(this).attr("rel"); //��ͼ
            var mainImgAlt = $(this).find('img').attr('alt');
            $('.cloud-zoom').attr('href', mainImgSrc);
            $('#main-prod-img').attr('src', zoomedImgSrc).attr('alt', mainImgAlt);
        }
        // Init cloud zoom
        $('.cloud-zoom').siblings().each(function() {
            if ($(this).hasClass('mousetrap'))
                $(this).empty().remove();
        });
        $('a.cloud-zoom').CloudZoom();
    });
    /****/
    (function($){
        $(".content").mCustomScrollbar({
            scrollButtons:{enable:true},
            axis:"x",
            advanced:{
                autoExpandHorizontalScroll:true
            }
        });

    })(jQuery);
    /***/
    $(".showAddsLeft h4").on("hover","a",function(e){
        e.preventDefault();
        $(this).addClass("active").siblings().removeClass("active");
        
        $(".showLeftCon03").hide().eq($(this).index()).show().find('img').lazyload();
    })

    //商品标签切换
    $(".s-left-list").eq(0).delegate('li:not(:last)','click',function(){
        $(this).addClass("link-suit-select").siblings().removeClass("link-suit-select");
        var _this = $(this)
        if($("#helpInfoContent").children().size()==0){
        	var firstcategoryid=$("#tx_firstcategoryid").val();//一级分类
        	var similarUrl = "http://item.secoo.com/product_module/helpInfo/helpInfo_content_" + firstcategoryid+".shtml"; //url地址
        	$.ajax({
        		type: "GET",
        		cache: false,
        		url:similarUrl,
        		async: false,
        		success: function(data, textStatus) {
        			//获取返回的数据
        			$("#helpInfoContent").html("").append(data);
        	        $(".product-detail-div").hide().eq(_this.index()).show();					
        		},
                error:function () {
                    $(".product-detail-div").hide().eq(_this.index()).show();
                }
        	});
        }else{
        	$(".product-detail-div").hide().eq($(this).index()).show();
        }
		window.scrollTo(0,$(".s-left-box").offset().top-36);
    });

//    var ListTop = $(".s-left-list").offset().top;
//    $(window).scroll(function(){
//        var detSt = $(window).scrollTop();
//        if(detSt>ListTop){
//            $(".s-left-listCopy").show();
//            $(".s-left-list").css({
//                "position" : "fixed",
//                "top": -2
//            });
//            $(".s-left-list a").attr("href","#showAdds")
//        }else{
//            $(".s-left-listCopy").hide();
//            $(".s-left-list").css({"position" : "static"})
//            $(".s-left-list a").attr("href","javascript:;")
//        }
//    })


    /***/
    var suiji = parseInt(5*Math.random());
    $(".ys_tab li").eq(suiji).addClass("ys_active");
    $(".sk_ys_tu").eq(suiji).show();
    $(".ys_tab li:last").addClass("last")
    $(".ys_tab li").mouseover(function(){
        $(".ys_tab li").removeClass("ys_active");
        $(this).addClass("ys_active");
        $(".sk_ys_tu").hide();
        $(".sk_ys_tu").eq($(this).index()).show();
        $(".sk_ys_tu").eq($(this).index()).find("img").lazyload();

    });

    /***/
    $(".place").hover(function(){
        $(this).css({"height" : "auto"}).addClass("menu-hover")

    },function(){
        $(this).css({"height" : "23px"}).removeClass("menu-hover");
    });

    $(".place li").click(function(){
        $(this).parent(".place").height("23px");
        $(this).prependTo(".place");
    });


    $(".popshow").click(function(e){
        e.preventDefault();
        $(".popbox").slideDown("fast");
        $(".newblackMask").height($("body,document").height()).fadeTo("fast",0.5).show();
    });
    /*
     $(".toyd").click(function(e){
     e.preventDefault();
     $(".popbox4destine").slideDown("fast");
     $(".newblackMask").height($("body,document").height()).fadeTo("fast",0.5).show();
     });
     */
    $(".close").click(function(){
        $(".popbox").slideUp("fast");
        $(".newblackMask").fadeTo("fast",0).hide();
    })
    $(".close4destine").click(function(){
        $(".popbox4destine").slideUp("fast");
        $(".newblackMask").fadeTo("fast",0).hide();
    })
    /****/
    $('.elseCon').elastislide({
        imageW 	: 160,
        minItems	: 1,
        margin : 50
    });

    /***/
    $(".reviewTab ul li.move").width($(".reviewTab li:first").width())

    $(".reviewTab li").click(function(e){
        e.preventDefault();
        $(this).addClass("active").siblings().removeClass("active")
        $(".reviewTab ul li.move").width($(this).width()).animate({left : $(this).position().left})
    });

    /***/


    /**ԤԼ����*/


    $(".loginMsgClose,.newblackMask").click(function(){
        $(".newblackMask").fadeTo("fast",0,function(){$(".newblackMask").hide()});
        $(".qucikLogin,.loginMsg,.popwinBox,.popwinBox_guanzhu,.popbox5fq,.popwinBox_jrgwc,.popwinBox_sccg,.popwinBox_gz").hide();
    });

    $(".djsm").click(function(e){
        e.preventDefault();
        $(".djTable").is(":hidden") ? $(".djTable").show() : $(".djTable").hide();
        e.stopPropagation();
    });

    $(document).click(function(){
        $(".djTable").hide();
    });

    $(".dTab01 h4").on("click","a",function(e){
        e.preventDefault();
        $(this).addClass("active").siblings().removeClass("active");
        $(".content01box").hide().eq($(this).index()-1).show();
    })
    $("#addBindCar,#fqBindCar").click(bindCar);
    $("#addCarInfo").click(addToCart);
    $("#addfq").click(addFq);
	
	/*评论的标签hover*/
	$("#comment_tag").delegate('span','mouseenter',function(){
		$(this).find('label').css('background-color','#ededed');
	}).delegate('span','mouseleave',function(){
		$(this).find('label').removeAttr('style');
	})
})
function addFq(){
    $(".popbox5fq").show();
    $(".newblackMask").height($("body,document").height()).fadeTo("fast",0.5).show();
}
//收藏 拍卖 降价关注
function iWish(flag) {
    //1是想要降价 2是想要拍卖 3收藏
    $.getJSON(URL_INFO.secoo_uid, function(datas) {
        if(datas.shortId != 0 && datas.userId != 0 ){
            if(flag == "3"){
                var userFavoriteUrl = "http://my.secoo.com/rpc/userfavorite.jsp?productId="+$("#productIdVal").val()+"&callback=?";
                $.getJSON(userFavoriteUrl, function(ret) {
                    if(ret.retCode == 0){
                        $(".popwinBox_sccg .love_tips_text").text("收藏成功！").append('<p class="winMsg"><a href="http://my.secoo.com/favorite/myfavorites.jsp" target="_blank">查看我的收藏&gt;&gt;</a></p>');
                        $(".popwinBox_sccg").show();
                        $(".newblackMask").height($("body,document").height()).fadeTo("fast",0.5).show();
                    }else{
                        $(".popwinBox_sccg .love_tips_text").text("商品已收藏！").append('<p class="winMsg"><a href="http://my.secoo.com/favorite/myfavorites.jsp" target="_blank">查看我的收藏&gt;&gt;</a></p>');
                        $(".popwinBox_sccg").show();
                        $(".newblackMask").height($("body,document").height()).fadeTo("fast",0.5).show();
                    }
                });
            }else {
                $.getJSON("http://my.secoo.com/rpc/userfavorite.jsp?callback=?&productId="+$("#productIdVal").val()+"&type="+flag, function(ret) {
                    var text = 'bigo';
                    if(ret.retCode!=10&&ret.retCode!=12){
                        text = 'bigi';
                    }
                    if(ret){
                    	$(".popwinBox_gz .love_tips_title").html('关注成功<a href="javascript:;" class="winboxClose" onclick="winboxCloses()"></a>');
                        $(".popwinBox_gz .love_tips_text").html('关注成功<a href="http://my.secoo.com/favorite/myfavorites.jsp" target="_blank">查看我的关注&gt;&gt;</a>');
                        $(".popwinBox_gz").show();
                        $(".newblackMask").height($("body,document").height()).fadeTo("fast",0.5).show();
                    }else {
                        $(".popwinBox_gz .love_tips_title").html('关注失败<a href="javascript:;" class="winboxClose" onclick="winboxCloses()"></a>');
                        $(".popwinBox_gz .love_tips_text").html('服务器忙，请稍后再试！');
                        $(".popwinBox_gz").show();
                        $(".newblackMask").height($("body,document").height()).fadeTo("fast",0.5).show();
                    }
                });
            }
        }else {
            user_login_reg('http://item.secoo.com/'+$("#productIdVal").val()+'.shtml',null,'iWish("'+flag+'")');
			setBkHeightForIE7();
        }
    });
}
function setBkHeightForIE7(){
	var inTimer;
	if(SYS.ie=='7.0'){
		inTimer = setInterval(function(){
			if($(".blockUI").size()>0){
				$(".blockUI").height($(document).height());
				clearInterval(inTimer);
			}
		},500);
	}
}
function addToCart(event) {
	if($("#shopSize").size()>0){
		if($("#shopSize").find('li.on').size()==0){
			return false;
		}
	}
    event = event ? event : window.event;
    var obj = event.srcElement ? event.srcElement : event.target;
    var $obj = $(obj);
    var productId = $("#productIdVal").val();
    var quantity = $("#buyNumVal").val();
    if (productId == null || productId == "") {
        return
    }
    $.ajax({
        type: "post",
        cache: false,
        url: "http://shopping.secoo.com/cart/cart_shoppinglist.jsp?time=" + (new Date()).getTime(), async: false, data: {process: 1, productId: productId, quantity: quantity, source:sourceStr, checkedService:checkedService},
        dataType: "jsonp",
        success: function (data) {
            if (data) {
                if (data.retMsg != "" && data.retMsg != undefined ) {
                    alert(data.retMsg)
                } else {
                    if (data.retCode == 200) {
                        $("#beforeLoginCarNum,#afterLoginCarNum").html("(" + data.cart.checkedCommonTotalCount + ")");
                        $(".popwinBox_jrgwc,.popwinboxBg,.shadeDow_div").show();
                        $(".newblackMask").height($("body,document").height()).fadeTo("fast",0.5).show();
                        //触发页面购物车刷新
                        refreshCarNum();
                    }
                }
            }
        }})
};

function winboxCloses(){
    $(".popwinBox,.popbox5fq,.popwinBox_guanzhu,.popwinBox_jrgwc,.popwinBox_sccg,.popwinBox_gz").hide();
    $(".newblackMask").hide();
}

function productCarOper(oper) {
    var buyNumVal = $("#buyNumVal").val();
    if(oper == "add"){
        if(Number(buyNumVal) < Number(inventorySize) ){
            buyNumVal = Number(buyNumVal)+1;
            $("#buyNumVal").val(buyNumVal);
        }
    }else if(oper == "sub"){
        if(Number(buyNumVal) > 1  ){
            buyNumVal = Number(buyNumVal)-1;
            $("#buyNumVal").val(buyNumVal);
        }
    }
    if(Number(buyNumVal) > 1){
        $("#subProduct").attr("class", "down fl");
        if (Number(buyNumVal) == Number(inventorySize)) {
            $("#addProduct").attr("class", "up fl nMinus");
        }else {
            $("#addProduct").attr("class", "up fl");
        }
    }
    if (Number(buyNumVal) == 1) {
        $("#subProduct").attr("class", "down fl nMinus");
    }
 
}

/**
 * 品牌尺码数据填充
 * @param {} callback
 */
function brandSizeTable(callback){
	
	if(typeof sizedata == 'undefined'){
		$("#brandSizeSecond").remove();
	}else{
		try {
			sizedata = JSON.parse(sizedata);
		} catch (e) {
			console.error(e);
		}
	
		var htmlArr =[];
		
		//内容
		for(var i=0,ilen=sizedata.length;i<ilen;i++){
			htmlArr.push('<tr>');
			for(var j=0,jlen=sizedata[i].length;j<jlen;j++){
				htmlArr.push('<td>'+sizedata[i][j]+'</td>');
			}
			htmlArr.push('</tr>');
		}
		
		$("table.size").append(htmlArr.join(''));
		callback();
	}
}

/**
 * 第二套品牌尺码对照表表头信息
 */
function brandSizeTitle(){
	var tName = $('#tName').val();
	var sName = $('#sName').val();
	var fName = $('#fName').val();
	
	var name = '';
	if(tName!=''){
		name = tName;
	}else if(sName!=''){
		name = sName;
	}else{
		name = fName;
	}
	
	var bName = $('#bName').val();
	var colspan	= $(".size_detail_table table tr:eq(0) td").size()
	$(".size_detail_table table tr:first").before('<tr><th colspan="'+colspan+'">'+bName+'&nbsp;'+name+'&nbsp;尺码</th></tr>');
}

/*收藏的hover*/
$(".live_icon").mouseenter(function(){
    $(this).children('div').show();
}).mouseleave(function(){
    $(this).children('div').hide();
});
/*分享的hover*/
var shareTimer;
$(".share_icon").hover(function(){
	clearTimeout(shareTimer);
	$(".share_box_icon,#bdshare").show();
},function(){
	shareTimer = setTimeout(function(){
        $(".share_box_icon,#bdshare").hide();
	},800);
});
$(".share_box_icon").hover(function(){
	clearTimeout(shareTimer);
},function(){
	$(".share_box_icon,#bdshare").hide();
});