window.com.yamixed.IndexPage = (function($){
   
   var ELS_CLASS = {
   	  UP_AND_DOWN : '.upAndDown',
   	  COMMENT_BTN : '.commentBtn',
   	  SHARE_TAG : '.shareTag'
   };

   var bind = {
   	  up_and_down_click : function(){
   	  	$(ELS_CLASS.UP_AND_DOWN).on('click',function(){
		   var $this = $(this);
		   var $badge = $this.find('span.badge');	
		   var newAmount = parseInt($badge.text()) + 1;
		   $badge.text(newAmount);
		   var isUp =  $this.attr('isUp');
		   var mixId = $this.attr('mixId');
		   $.get(ctx + '/mix/upAndDown',{isUp:isUp,mixId:mixId});
		   $this.off('click');
	    });
   	  },
   	  comment_btn_click : function(){
        $(ELS_CLASS.COMMENT_BTN).on('click',function(){
		   var $this = $(this);
		   var mixId = $this.attr('mixId');
		   window.location.href= ctx + '/comment/view?mixId=' + mixId;
	    });    
   	  },
   	  share_tag_click : function(){
   	  	$(ELS_CLASS.SHARE_TAG).mouseover(function(){
		  var $this = $(this);
		  jiathis_config.url = $this.attr('data-url');
		  jiathis_config.summary = $this.attr('data-summary');
		  jiathis_config.title = $this.attr('data-title');
		  jiathis_config.pic = $this.attr('data-pic');
	    });
   	  }
   };
 
   var init = function(){
      for(var m in bind){
          if(typeof bind[m] == 'function'){
             bind[m](); 
          }
      }
   };


   return {
     init : init   
   };

})(jQuery);

$(document).ready(window.com.yamixed.IndexPage.init);
