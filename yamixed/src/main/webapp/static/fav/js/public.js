window.com.yamixed.publicfav = (function(){
	var ELE_IDS = {
   TAG_ID : 'tagid'   
 };

 var ELE_CLASS = {
   TAG : 'tag',
   UP_AND_DOWN : 'upAndDown',
   SHARE_TAG : 'shareTag'
 };


 var bind = {
   up_and_down_click : function(){
     $('.' + ELE_CLASS.UP_AND_DOWN).click(function(){
      var $this = $(this);
      var $badge = $this.find('span.badge'); 
      var newAmount = parseInt($badge.text()) + 1;
      $badge.text(newAmount);
      var isUp =  $this.attr('isUp');
      var articleId = $this.attr('articleId');
      $.get(ctx + '/fav/upAndDown',{isUp:isUp,articleId:articleId});
      $this.off('click');
    });
   },

   share_tag_click : function(){
    $('.' + ELE_CLASS.SHARE_TAG).mouseover(function(){
      var $this = $(this);
      jiathis_config.url = $this.attr('data-url');
      jiathis_config.summary = $this.attr('data-summary');
      jiathis_config.title = $this.attr('data-title');
      jiathis_config.pic = $this.attr('data-pic');
    });
  }
};

var init = function(){
 var tagid = $('#' + ELE_IDS.TAG_ID).val();		
 $('.' + ELE_CLASS.TAG).each(function(){
   var $this = $(this);
   if($this.attr('data') == tagid){
    $this.removeClass('btn-warning').addClass('btn-danger');    
    return false; 
  }
});
 bind.up_and_down_click(); 
 bind.share_tag_click();
};

return {
  init : init  
};
})(); 
$(document).ready(window.com.yamixed.publicfav.init);