window.com.yamixed.comment = (function(){
	var ELS_CLASS = {
		COMMENT_TD : 'commentTD',
		FACE : 'face',
		INPUT : 'showCommentInput',
		SUBMIT : 'submitComment'   
	};

	var bind = {
		show_input : function(){
			$('.' + ELS_CLASS.INPUT).on('click',function(){
				var $this = $(this);
				$this.next().show();
				$this.hide();
			});
		},
		submit_comment : function(){
			$('.' + ELS_CLASS.SUBMIT).on('click',function(){
				var $this = $(this);
				var $form = $this.parent();
				var $textarea = $form.find('textarea');
				if($textarea.val()){
					$form.submit();   
				}
				else{
					$textarea.focus();
				}
			});
		} 
	};

	var init = function(){
		var callbacks = $.Callbacks();
		var displayFace = function(){
		  $('.' + ELS_CLASS.COMMENT_TD).each(function(){
			var $this = $(this);
			$this.replaceface($this.html());
		  });
		};
		callbacks.add(displayFace);
		var initFace = function(){
		  $('.' + ELS_CLASS.FACE).smohanfacebox({
		   Event : "click",	//触发事件	
		   divid : "commentform", //外层DIV ID
		   textid : "commentarea" //文本框 ID
		  });
		};
		callbacks.add(initFace);
		callbacks.fire();
		for(var m in bind){
			if(typeof bind[m] == 'function'){
				bind[m]();
			}   
		}
	};

	return {
		init : init
	};
})();
$(document).ready(window.com.yamixed.comment.init);