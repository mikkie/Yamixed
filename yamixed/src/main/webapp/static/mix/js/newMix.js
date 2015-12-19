window.com.yamixed.NewMix = (function($) {

	var ELS_IDS = {
		IMG_URL : 'imageUrl',
		IMG_LINK : 'imglink',
		LINK_IMG : 'linkimg',
		IMG_LINK_DIV : 'imglinkDiv',
		CHK_IMG_LINK : 'chkimglink',
		SAVE_MIX : 'saveMix',
		TITLE : 'title',
		DESC : 'desc',
		SAVE_MIX_FORM : 'saveMixForm' 
	};

	var ELS_CLASS = {
		PREVIEW_IMG : 'previewImg'
	};


	var bind = {
		previewImg_click : function(){
			$('.' + ELS_CLASS.PREVIEW_IMG).on('click',function(){
				var $this = $(this); 
				var src = $this.attr('src');
				$('#' + ELS_IDS.IMG_URL).val(src);
				$('.' + ELS_CLASS.PREVIEW_IMG).css('border','1px solid #ddd');
				$this.css('border','2px solid #FF8F00');
			});  
		},
		image_link_focusout : function(){
			$('#' + ELS_IDS.IMG_LINK).focusout(function(){
				var imglink = $(this).val();
				if(imglink){
					var $linkimg = $('#' + ELS_IDS.LINK_IMG);
					$linkimg.attr('src',imglink);
					$linkimg.trigger('click');
					$('#' + ELS_IDS.IMG_LINK_DIV).show();
				}
			});
		},
		chk_imagelink_click : function(){
			$('#' + ELS_IDS.CHK_IMG_LINK).click(function(){
				if($(this).is(':checked')){
					var $imglink = $('#' + ELS_IDS.IMG_LINK);
					$imglink.removeAttr('readonly');
					if($imglink.val()){
						$('#' + ELS_IDS.LINK_IMG).trigger('click');
						$('#' + ELS_IDS.IMG_LINK_DIV).show();
					} 
				}
				else{
					var $imglink = $('#' + ELS_IDS.IMG_LINK);
					$imglink.attr('readonly','readonly');
					$('#' + ELS_IDS.IMG_LINK_DIV).hide();
					$('.' + ELS_CLASS.PREVIEW_IMG +':first').trigger('click');
				}
			});
		},
		save_mix_click : function(){
			$('#' + ELS_IDS.SAVE_MIX).click(function(){
				var $title = $('#' + ELS_IDS.TITLE);
				if(!$title.val()){
					$title.focus(); 
					return;
				}
				var $desc = $('#' + ELS_IDS.DESC);
				if(!$desc.val()){
					$desc.focus(); 
					return;
				}
				$('#' + ELS_IDS.SAVE_MIX_FORM).submit();
			});
		}
	};


	var init = function(){
		for(var m in bind){
			if(typeof bind[m] == 'function'){
				bind[m](); 
			} 
		}
		$('.' + ELS_CLASS.PREVIEW_IMG + ':first').trigger('click');  
	};


	return {
		init : init
	};

})(jQuery);
$(document).ready(window.com.yamixed.NewMix.init);