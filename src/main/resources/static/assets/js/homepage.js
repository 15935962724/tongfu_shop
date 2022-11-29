$(document).ready(function() {
  $('.single-item').not('.slick-initialized,.slick-init').slick({
    dots: true,
    infinite: true,
    arrows:true,
    autoplay:true,
    autoplaySpeed:7000,
    speed: 300
  });
  $('.single-it').not('.slick-initialized,.slick-init').slick({
    dots: false,
    infinite: true,
    arrows:true,
    autoplay:true,
    autoplaySpeed:7000,
    speed: 300
  });
  $('.single-item .slick-prev,.single-item .slick-next,.single-it .slick-prev,.single-it .slick-next').addClass('display-none');
  $('.single-item,.single-it').mouseenter(function(){
	  $(this).find('.slick-prev,.slick-next').addClass('display-block').removeClass('display-none');
  }).mouseleave(function(){
	  $(this).find('.slick-prev,.slick-next').addClass('display-none').removeClass('display-block');
  })
  
  $('.single-item,.single-it').on('beforeChange', function() {
    setTimeout(function() {
      $('.slick-dots li,.slick-dots1 li').removeClass('slick-pass');
      $('.slick-active').prev().addClass('slick-pass');
    }, 0)
  })

  $('.bannerbg:not(:first-child)').show();

  var maxHeight = function(item){
    return Math.max.apply(null, $(item).map(function ()
    {
      return $(this).height();
    }).get());
  };

  // $('.zhuanjia-wrap .li-sec').hover(
  //   function(){ $(this).addClass('li-sec-active') },
  //   function(){ $(this).removeClass('li-sec-active') }
  // )


  // $('.ai-height').css('height', maxHeight('.ai-height') + 'px');
  // $('.doctor-height').css('height', maxHeight('.doctor-height') + 'px');
});