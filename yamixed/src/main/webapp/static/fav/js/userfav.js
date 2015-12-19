window.com.yamixed.userfav = (function(){
	var ELE_IDS = {
       TAG_ID : 'tagid',
       INTRODUCE : 'introduce',
       EDIT_INTRODUCE : 'editIntroduce',
       INTRODUCE_INPUT : 'introduceInput'   
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
  },


  edit_introduce_click : function(){
    $('#' + ELE_IDS.EDIT_INTRODUCE).click(function(){
       $(this).hide();
       var $introduce = $('#' + ELE_IDS.INTRODUCE);
       var txt = $introduce.text();
       $introduce.replaceWith('<input type="text" placeHolder="自我介绍下吧!" id="introduceInput" value="'+ txt +'">');
       $('#' + ELE_IDS.INTRODUCE_INPUT).focus();
    });    
  },

  introduce_save_click : function(){
    $(document).on('focusout','#' + ELE_IDS.INTRODUCE_INPUT,function(){
        var $this = $(this);
        var txt = $this.val();
        $this.replaceWith('<span id="introduce">'+ txt +'</span>'); 
        $('#' + ELE_IDS.EDIT_INTRODUCE).show();
        $.ajax({
          url : ctx + '/fav/saveIntroduce',
          type : 'post',
          dataType : 'text',
          data : {
            introduce : txt
          }
        });
    });
  }

 };


	var init = function(){
       bind.edit_introduce_click();
       bind.introduce_save_click();
       var $tag = $('#' + ELE_IDS.TAG_ID);
       if($tag.length == 0){
          return;  
       }
       var tagid = $tag.val();		
       $('.' + ELE_CLASS.TAG).each(function(){
           var $this = $(this);
           if($this.attr('data') == tagid){
              $this.removeClass('btn-success').addClass('btn-danger');    
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
$(document).ready(window.com.yamixed.userfav.init);