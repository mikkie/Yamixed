window.com.yamixed.NewLinkPage = (function($){
	var IDS = {
		CHANNEL_ID : 'channelid',
		TAG_DIALOG : 'tagDialog',
		LINK_DIALOG : 'linkDialog',
		CUR_TAG : 'curTag',
		ENABLE_CUSTOM_TAG : 'enableCustomTag',
		TAG_SELECT : 'tagSelect',
		LINK_INPUT_TEMP : 'linkInputTemp',
		ADD_LINK : 'addlink',
		VIEW_PARSE : 'viewParse',
		TITLE : 'title',
		LINK_DESC : 'linkdesc',
		LINK_IMG_LIST : 'linkimglist',
		CHK_IMG_LINK : 'chkimglink',
		IMG_LINK : 'imglink',
		LINK_IMG : 'linkimg',
		IMG_LINK_DIV : 'imglinkDiv',
		LINK_IMG_GROUP : 'linkimggroup',
		LINK_TEMP : 'linkTemp',
		SAVE_ARTICLE : 'saveArticle',
		TAG_LIST : 'tagslist',
	 	SELECT_TAG_ID : 'selectTagId',
	 	SAVE_FORM : 'saveArticleForm',
	 	DESC : 'desc',
	 	OLD_TAG : 'oldTag',
	 	LINK_PRIVATED : 'linkPrivated',
	 	TAG_UL : 'tagul',
	 	TAG_TEMP : 'tagTemp',
	 	TAG_PRE : 'tagpre',
	 	TAG_NEXT : 'tagnext',
	 	TAG_DIV : 'tagdiv',
	 	CHOOSE_TAG : 'chooseTag',
	 	AVA_ARC_TEMP : 'avaArcTemp',
	 	AVA_ARC_UL : 'avaArcUl',
	 	AVA_ARC_DIV : 'avaArcDiv',
	 	DESC : 'desc',
	 	ARC_ID : 'arcId',
	 	DESC_VAL : 'descVal'
	};

	var CLASS = {
		TAG_LI : 'tagli',
		TAG : 'tag',
		SUB_LINK : 'sublink',
		PARSE_BTN : 'parseBtn',
		PRE_IMG_S : 'previewImgSmall',
		MODIFY_BTN : 'modifyBtn',
		MEDIA : 'media',
		URL : 'url',
		DEL_LINK : 'delLink',
		AVA_ARC_LI : 'avaArcLi',
		AVA_ARC_BTN : 'avaArcBtn'
	};

	var $curLinkInput = null; 

	function clearOldData(){
		window.com.yamixed.util.loaded();
		$('#' + IDS.TITLE).val('');
		$('#' + IDS.LINK_DESC).val('');
		$('#' + IDS.LINK_IMG_LIST).empty();
		$('#' + IDS.CHK_IMG_LINK).removeAttr('checked');
		$('#' + IDS.LINK_PRIVATED).removeAttr('checked');
		$('#' + IDS.IMG_LINK).val('');
		$('#' + IDS.IMG_LINK_DIV).hide();
		$('#' + IDS.IMG_LINK).attr('readonly','readonly');
		var $link_img_group = $('#' + IDS.LINK_IMG_GROUP);
		if(!$link_img_group.is(':visible')){
			$('#' + IDS.LINK_IMG_GROUP).show();
		}
	};

	var _checkEmpty = function(){
		for(var i = 0; i < arguments.length; i++){
          var $widget = arguments[i];
          if(!$widget.val()){
            $widget.focus();
            return false; 
          }   
		}
        return true;
	};


    var buildPage = function(data){
       if(data.first){
          $('#' + IDS.TAG_PRE).hide();
       }
       else{
       	  var $pre = $('#' + IDS.TAG_PRE);
       	  $pre.find('a').attr('href','javascript:window.com.yamixed.NewLinkPage.loadTags('+(data.number - 1)+',40);');
       	  $pre.show();
       }
       if(data.last){
         $('#' + IDS.TAG_NEXT).hide(); 
       }
       else{
         var $next = $('#' + IDS.TAG_NEXT);
       	 $next.find('a').attr('href','javascript:window.com.yamixed.NewLinkPage.loadTags('+(data.number + 1)+',40);');
       	 $next.show();  
       }
    };

	var loadTags = function(page,size){
		window.com.yamixed.util.loading(IDS.TAG_DIV);
        $.get(ctx + '/tag/findTagsByPage/'+ $('#' + IDS.CHANNEL_ID).val() +'/' + page + '/' + size,function(data){
            window.com.yamixed.util.loaded();
            var $ul = $('#' + IDS.TAG_UL);
            $ul.empty();
            if(data.totalElements > 0){
               var html = $('#' + IDS.TAG_TEMP).tmpl(data.content);
               $ul.append(html);
            }
            buildPage(data);
            $('#' + IDS.CHOOSE_TAG).removeAttr('disabled');
        },'json');
	};

	var bind = {
		avaArcBtn_click : function(){
           $(document).on('click','.' + CLASS.AVA_ARC_BTN,function(){
           	  $('.' + CLASS.AVA_ARC_BTN).removeClass('btn-danger').addClass('btn-primary');
              var $this = $(this);
              $this.removeClass('btn-primary').addClass('btn-danger');
              var btn_arc_id = $this.attr('articleId');
              if(btn_arc_id){
                 $('#' + IDS.ARC_ID).val(btn_arc_id);
                 $('#' + IDS.DESC_VAL).val('');
              }
              else{
              	 $('#' + IDS.DESC_VAL).val($this.text());
              	 $('#' + IDS.ARC_ID).val('');
              }
           });   
		},
		new_article : function(){
           $(document).on('keyup focusout','#' + IDS.DESC,function(e){
               if(e.type == 'focusout' || (e.type == 'keyup' && e.keyCode == 13)){
               	  var $this = $(this); 
                  var value = $this.val();
                  if(!value){
                     return false;
                  }
                  var $newArc = $('<li class="avaArcLi"><button type="button" class="avaArcBtn btn btn-primary btn-xs">'+ value +'</button></li>')
                  $this.parent().before($newArc);
                  $this.val('');
                  $newArc.find('button').trigger('click');
                  return false;
               }
           });   
		}, 
		parse_click : function(){
			$(document).on('click','.' + CLASS.PARSE_BTN,function(){
				clearOldData();
				var $this = $(this);
				$curLinkInput = $this;
				var $url = $this.parent().prev();    
				var url = $url.val();
				if(!$.trim(url)){
					$url.focus();
					return false;
				}
				$('#' + IDS.LINK_DIALOG).modal('show');
				$('#urladdress').val(url);
				window.com.yamixed.util.loading(IDS.LINK_DIALOG);
				$.ajax({
					url : ctx + '/fav/parseLink',
					type : 'post',
					dataType : 'json',
					data : {
						url : $('#urladdress').val(),
						tempID : $this.attr('tempID')
					}
				}).done(function(data){
					$('#' + IDS.VIEW_PARSE).removeAttr('disabled');
					window.com.yamixed.util.loaded();
					if(data && data.link){
						$('#' + IDS.LINK_TEMP).data('link',data.link);
						var link = data.link;	
						$('#' + IDS.VIEW_PARSE).attr('linkid',link.tempID);
						$('#' + IDS.TITLE).val(link.title);
						$('#' + IDS.LINK_DESC).val(link.description);
						var $list = $('#' + IDS.LINK_IMG_LIST);
						if(link.imageUrls && link.imageUrls.length > 0){
							for(var i = 0; i < link.imageUrls.length; i++){
								$list.append('<a href="javascript:void(0);"><img src="'+ link.imageUrls[i] +'" alt="..." class="img-thumbnail previewImgSmall"></a>'); 
							}
							$('.' + CLASS.PRE_IMG_S + ':first').trigger('click');
						}
						else{
							$('#' + IDS.LINK_IMG_GROUP).hide();
						}   
					}
					else{
                        alert('无法解析该网址:' + url); 
                        $('#' + IDS.LINK_DIALOG).modal('hide');
					}
				});
			});
},
tag_select : function(){
    $('#' + IDS.TAG_SELECT + ' button:eq(1)').click(function(){
        $('#' + IDS.TAG_DIALOG).modal('show');
        loadTags(0,40);
    	return false;
    });
},
enable_custom_tag : function(){
	$('#' + IDS.ENABLE_CUSTOM_TAG).click(function(){
		var $this = $(this);
		if($this.is(':checked')){
			$('#customTagInput').removeAttr('readonly');
			if($('#' + IDS.AVA_ARC_DIV).length > 0){
			   $('.' + CLASS.AVA_ARC_LI +':not(:last)').remove();
			}
		}
		else{
			var selectTagId = $('#' + IDS.SELECT_TAG_ID).val();
			if(selectTagId){
				buildAvailableArticles(selectTagId);
			}
			$('#customTagInput').attr('readonly','readonly');
		}
		$('#' + IDS.TAG_SELECT).toggle();
	});
},
add_link : function(){
	$(document).on('click','#' + IDS.ADD_LINK,function(){
		var $temp = $('#' + IDS.LINK_INPUT_TEMP);
		var $cloneLink = $temp.clone(true,true);
		$temp.before($cloneLink);
		$cloneLink.removeAttr('id').show();
	});
},
sub_link : function(){
	$('.' + CLASS.SUB_LINK).on('click',function(){
		var $this = $(this);
		$this.parents('.form-group').remove();
	});
},
pre_img_s_click : function(){
	$(document).on('click','.' + CLASS.PRE_IMG_S,function(){
		var $this = $(this);
		$('.' + CLASS.PRE_IMG_S).css('border','1px solid #ddd');
		$this.css('border','2px solid #FF8F00');
		var link = $('#' + IDS.LINK_TEMP).data('link');
		if(link){
			link.previewImgUrl = $this.attr('src');
		} 
	});
},
chk_img_link_click : function(){
	$('#' + IDS.CHK_IMG_LINK).click(function(){
		if($(this).is(':checked')){
			var $imglink = $('#' + IDS.IMG_LINK);
			$imglink.removeAttr('readonly');
			if($imglink.val()){
				$('#' + IDS.LINK_IMG).trigger('click');
				$('#' + IDS.IMG_LINK_DIV).show();
			} 
		}
		else{
			var $imglink = $('#' + IDS.IMG_LINK);
			$imglink.attr('readonly','readonly');
			$('#' + IDS.IMG_LINK_DIV).hide();
			$('.' + CLASS.PRE_IMG_S + ':first').trigger('click');
		}
	});
},
img_link_focusout : function(){
	$('#' + IDS.IMG_LINK).focusout(function(){
		var imglink = $(this).val();
		if(imglink){
			var $linkimg = $('#' + IDS.LINK_IMG);
			$linkimg.attr('src',imglink);
			$linkimg.trigger('click');
			$('#' + IDS.IMG_LINK_DIV).show();
		}
	});
},
view_parse_click : function(){
	$('#' + IDS.VIEW_PARSE).click(function(){
		if(!_checkEmpty($('#' + IDS.TITLE),$('#' + IDS.LINK_DESC))){
           return false;
		}
		$(this).attr('disabled','disabled');
		var $link_temp = $('#' + IDS.LINK_TEMP); 
		var link = $link_temp.data('link');
		link.title = $('#' + IDS.TITLE).val();
		link.description = $('#' + IDS.LINK_DESC).val();
		link.privated = $('#' + IDS.LINK_PRIVATED).is(':checked') ? 'on' : 'off';
		$.ajax({
			url : ctx + '/fav/selectPreviewImg',
			type : 'post',
			dataType : 'json',
			data : {
               tempid : $(this).attr('linkid'),
               imgurl : link.previewImgUrl,
               title : link.title,
               desc : link.description,
               privated : link.privated
			}
		});
		var html = $link_temp.tmpl(link);
		var $form_group = $curLinkInput.parents('.form-group'); 
		if($form_group){
			var $parseBtn = $form_group.find('button:eq(0)');
			$parseBtn.attr('tempID',link.tempID);
			var backup = $form_group.html();
			var $button = $form_group.find('button:eq(1)');
			var $html = $(html);
			$html.find('button:eq(1)').replaceWith($button[0]);
			$html.data('backup',backup);
			$form_group.empty().append($html);
		}
		$('#' + IDS.LINK_DIALOG).modal('hide');
	});
},
modify_btn_click : function(){
	$(document).on('click','.' + CLASS.MODIFY_BTN,function(){
		$form_group = $(this).parents('.form-group');
		var backup = $form_group.children().data('backup');
		if(backup){
           $form_group.empty().append(backup);    
		}
	});
},

del_link_click : function(){
   $(document).on('click','.' + CLASS.DEL_LINK,function(){
      if(!confirm('确定删除该链接?')){
         return false;
      }
      var $this = $(this);
      var id = $this.attr('data');
      $.get(ctx + '/link/del/' + id);
      $this.parents('.form-group').remove();
   });	
},

save_article : function(){
	$('#' + IDS.SAVE_ARTICLE).click(function(){
		if($('#' + IDS.ENABLE_CUSTOM_TAG).is(':checked')){
           if(!$('#customTagInput').val()){
             $('#customTagInput').focus();
             return;
           }
		}
		else if(!$('#' + IDS.SELECT_TAG_ID).val()){
		   $('#' + IDS.CUR_TAG).focus();	
           return;  
		}
		if($('#' + IDS.AVA_ARC_DIV).length > 0){
		  var $selArcBtn = $('.' + CLASS.AVA_ARC_BTN + '.btn-danger');
		  var $desc = $('#' + IDS.DESC);
		  if($selArcBtn.length == 0){
            $desc.focus();
            return;
		  }
		}
		if($('.' + CLASS.MEDIA).length < 1){
		   $('.' + CLASS.URL + ':eq(0)').focus();
           return;   
		}
        $('#' + IDS.SAVE_FORM).submit(); 
	});
},
tag_click : function(){
  $('#' + IDS.TAG_UL).on('click','.' + CLASS.TAG,function(){
     var $this = $(this);
     $('#' + IDS.TAG_UL + ' .' + CLASS.TAG).removeClass('btn-danger').addClass('btn-warning');
     $(this).removeClass('btn-warning').addClass('btn-danger');
  });
},
choose_tag : function(){
  $('#' + IDS.CHOOSE_TAG).click(function(){
  	 var $selectedBtn = $('#' + IDS.TAG_UL + ' .btn-danger');
  	 if($selectedBtn.length == 0){
        alert('请选择标签');
        return false;
  	 }
  	 $('#' + IDS.CUR_TAG).text($selectedBtn.text());
  	 var tagid = $selectedBtn.attr('data');
  	 $('#' + IDS.SELECT_TAG_ID).val(tagid);
  	 if($('#' + IDS.AVA_ARC_DIV).length > 0){
  	    buildAvailableArticles(tagid);
  	 }
  	 $('#' + IDS.TAG_DIALOG).modal('hide');
  });	
}
};


var buildAvailableArticles = function(tagid){
    $.get(ctx + '/article/findUserArticles/' + tagid,function(data){
    	var $div = $('#' + IDS.AVA_ARC_DIV);
    	var $ul = $('#' + IDS.AVA_ARC_UL);
    	$ul.empty();
    	if(data.success){
          var html = $('#' + IDS.AVA_ARC_TEMP).tmpl(data.success);   
    	  $ul.append(html);
    	  $ul.append('<li class="avaArcLi"><input type="text" id="desc" name="desc" class="form-control" placeholder="新收藏夹名称"></input></li>');
    	  $div.show();
    	  $('.' + CLASS.AVA_ARC_BTN + ':eq(0)').trigger('click');
    	}
    	else{
     	  $div.hide();	
    	}
    },'json');
};


var init = function(){
	bind.avaArcBtn_click();
	bind.tag_select();   
	bind.enable_custom_tag();
	bind.add_link();
	bind.sub_link();
	bind.parse_click();
	bind.pre_img_s_click();
	bind.chk_img_link_click();
	bind.img_link_focusout();
	bind.view_parse_click();
	bind.modify_btn_click();
	bind.del_link_click();
	bind.save_article();
	bind.tag_click();
	bind.choose_tag();
	bind.new_article();
	var $oldtag = $('#' + IDS.OLD_TAG);
	if($oldtag.length > 0){
       $('#' + IDS.CUR_TAG).text($oldtag.val());
	}
};

return {
	init : init,
	loadTags : loadTags
};

})(jQuery);
$(document).ready(window.com.yamixed.NewLinkPage.init);