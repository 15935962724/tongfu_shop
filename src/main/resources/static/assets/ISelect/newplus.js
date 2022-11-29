$(function(){
    $('.fg_g').hover(
        function(){
            var i = $(this).index();
            $('.fg_g_y').eq(i).hide();
            $('.fg_g_no').eq(i).show(1500);
            $(this).animate({"width":"280px","height":"200px","background":"#007FDC"});
        },
        function(){

        }
    )
})