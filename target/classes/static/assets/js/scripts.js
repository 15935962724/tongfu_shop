if (!window.location.origin) {
  window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
}
$(document).ready(function() {
  // 瀵艰埅鏍忓垽鏂璦ctive
  var path = window.location.pathname;
  if (path[path.length - 1] === '/') path = path.substr(0, path.length - 1);
  if (path.indexOf('news') !== -1) path = location.origin + '/official/news';
  var pathList = path.split('official');
  path = pathList[pathList.length - 1];
  $('header a, .pro-menu--simple a').each(function() {
    var _this = $(this);
    var href = _this.attr('href');
	if (!href) {
		return;
	}
    if (href && href[href.length - 1] === '/') href = href.substr(0, href.length - 1);
    var hrefList = href.split('official');
    href = hrefList[hrefList.length - 1];
    if(href === path) {
      var li = _this.parent('li');
      if (li) {
        if (li.hasClass('li-menulist')) {
          li.addClass('actived');
          li.parent('.pro-menu--simple ul').toggle();
          li.parents('li').addClass('current');
        } else {
          li.addClass('current');
        }
      }
      var div = _this.parent('.menu-subtitle');
      if (div) {
        div.addClass('actived');
      }
    }
  });

  // 椤堕儴瀵艰埅鏍忔粴鍔ㄨ秴杩嘼anner鍒囨崲class
  // var banner = $($('.banner-wrap, .banner')[0]);
  // var offsetHeight = banner.height() - 80;
  var offsetHeight = /detail/.test(window.location.pathname) ? -1 : 1;
  var header = $('header');
  function scrollHeader() {
    if (window.pageYOffset > offsetHeight) {
      if (!header.hasClass('smaller')) {
        $('header').addClass('smaller');
      }
    } else {
      if (header.hasClass('smaller')) {
        $('header').removeClass('smaller');
      }
    }
  }
  scrollHeader();
  $(window).on('scroll', function() {
    scrollHeader();
  });
  header.hover(function() {
    if (!header.hasClass('smaller')) {
      header.addClass('smaller');
    }
  }, function() {
    if (window.pageYOffset <= offsetHeight && header.hasClass('smaller')) {
      $('header').removeClass('smaller');
    }
  });


  // 瀵艰埅鏍廻over妯悜绉诲姩鍔ㄧ敾
  var currentPos = $('.nav > ul > li.current > a,.user > ul > li.current > a').position();
  var currentPageNav = $('.nav > ul > li.current,.user > ul > li.current');
  function changePos() {
    currentPos = $('.nav > ul > li.current > a,.user > ul > li.current > a').position();
	if (!currentPos) {
		return;	
	}
    $('.border').stop().animate({
      left: currentPos.left, 
      width: $('.nav > ul > li.current > a,.user > ul > li.current > a').innerWidth()
    }, 300);
  }
   changePos();
  $('.nav > ul > li > a,.user > ul > li > a').on('mouseenter', function() {
    $(this).parent().addClass('current').siblings('.current').removeClass('current');
    // 绔嬪嵆鐧诲綍涓嶆坊鍔犳粦鍔� 
    if(!$(this).parent('li').hasClass('login-button')) {
      changePos();
    }
    // 濡傛灉鏈変簩绾ц彍鍗曪紝鍒欎簩绾ц彍鍗曞姩鐢诲睍寮€
    if($(this).parent('li').find('section').length > 0) {
      $(this).next('section').animate({height: 'show'}, 'fast');
      // $(this).parent('li').addClass('hover');
    }
  });
  $('.nav > ul > li,.user > ul > li').on('mouseleave', function() {
    // $(this).removeClass('hover');
    $(this).children('a').next('section').animate({height: 'hide'}, 'fast');
  })
  $('.header').on('mouseleave', function() {
    // 鍥炲埌褰撳墠椤甸潰鎵€灞炰綅缃�
    currentPageNav.addClass('current').siblings('.current').removeClass('current');
    changePos();
  });

  // 搴曢儴鍚堜綔浼欎即鍥剧墖棰勫姞杞�
 
});