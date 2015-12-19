window.com.yamixed.article = (function(){
	var ELE_CLASS = {
		UP_AND_DOWN : 'upAndDown',
		SHARE_TAG : 'shareTag',
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
				var linkId = $this.attr('linkId');
				$.get(ctx + '/fav/link/upAndDown',{isUp:isUp,linkId:linkId});
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
		bind.up_and_down_click(); 
		bind.share_tag_click();
	};


	return {
		init : init  
	};

})();
$(document).ready(window.com.yamixed.article.init);