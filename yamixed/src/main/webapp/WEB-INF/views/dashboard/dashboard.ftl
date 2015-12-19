<!DOCTYPE html>
<html>
<head>
	<title>控制台</title>
	<link href="${rc.getContextPath()}/static/index/css/index.css" rel="stylesheet">
</head>
<body>
	<div>
		<#if mixs??>
		<#list mixs as mix>
		<div class="media">
			<a class="media-left" href="${mix.url}" target="_blank">
				<div class="media-left-div">
					<img src="${mix.previewImgUrl}" alt="..." onerror="this.onerror=null;this.src='${rc.getContextPath()}/static/images/no_image.jpg';">
				</div>
			</a>
			<div class="media-body">
				<div class="media-body-left">
					<a href="${mix.url}" target="_blank" class="mixLink" mixId="${mix.id}">
						<h4 class="media-heading"><span class="title">${mix.title}</span><span class="info"> ${mix.createTime}</span></h4>
					</a>
					<h5 class="content">${mix.description}</h5>
					<input class="mixId" type="hidden" value="${mix.id}"/>
					<#if mix.cate??>
					<input class="cate" type="hidden" value="${mix.cate.id}"/>
					</#if>
					<button class="btn btn-primary btn-xs commentBtn" type="button" mixId="${mix.id}">评论<span class="badge">${mix.comments}</span></button>
					<button class="btn btn-danger btn-xs delBtn" type="button" mixId="${mix.id}">删除</button>
					<button class="btn btn-success btn-xs updateBtn" type="button" mixId="${mix.id}">修改</button>
					<div class="jiathis_style shareTag" style="display:inline-block;" data-summary="${mix.description}" data-title="${mix.title}" data-pic="${mix.previewImgUrl}" data-url="http://www.yamixed.com/comment/view?mixId=${mix.id}">
						<a class="jiathis_button_qzone"></a>
						<a class="jiathis_button_tsina"></a>
						<a class="jiathis_button_tqq"></a>
						<a class="jiathis_button_weixin"></a>
						<a class="jiathis_button_renren"></a>
						<a class="jiathis_button_xiaoyou"></a>
					</div>
				</div>
				<div class="media-body-right">
					<a href="javascript:void(0);" class="upAndDown" isUp="true" mixId="${mix.id}"><span class="glyphicon glyphicon-thumbs-up"></span><span class="badge">${mix.up}</span></a>
					<a>
						<a href="javascript:void(0);" class="upAndDown" isUp="false" mixId="${mix.id}"><span class="glyphicon glyphicon-thumbs-down"></span><span class="badge">${mix.down}</span></a>
					</div>	
				</div>
			</div>
			</#list>
			<ul class="pager">
				<#if (pageNum>0)>
				<li class="previous">
					<a href="${rc.getContextPath()}/mix/dashboard/${pageNum-1}">上一页</a>
				</li>
				</#if>
				<#if (pageNum<pages)>
				<li class="next">
					<a href="${rc.getContextPath()}/mix/dashboard/${pageNum+1}">下一页</a>
				</li>
				</#if>
			</ul>
			</#if>
		</div>
		<div class="modal fade" id="modifyDialog" tabindex="-1" role="dialog" aria-labelledby="modifyLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title" id="modifyLabel">修改</h4>
					</div>
					<div class="modal-body" style="height:210px;">
					  <form id="updateMixForm" class="form-horizontal" role="form" method="post" action="${rc.getContextPath()}/mix/update/${pageNum}">	
						<input type="hidden" id="idUpdate" name="mixId"/>
						<div class="form-group">
							<label for="title" class="col-sm-offset-1 col-sm-2 control-label">标题</label>
							<div class="col-sm-8">
								<input type="text" name="title" class="form-control" id="titleUpdate" placeholder="标题" value="">
							</div>
						</div>
						<div class="form-group">
							<label for="desc" class="col-sm-offset-1 col-sm-2 control-label">描述</label>
							<div class="col-sm-8">
								<textarea class="form-control" name="desc" rows="3" id="descUpdate"></textarea>
							</div>
						</div> 
						<#if allCates??>
						<div class="form-group">
							<label for="cate" class="col-sm-offset-1 col-sm-2 control-label">分类</label>
							<div class="col-sm-8">
								<select name="category" style="width:100%;" id="cateUpdate">
									<#list allCates as cate>
									<option value="${cate.id}">${cate.name}</option>
									</#list>
								</select>
							</div>
						</div> 
						</#if>
					  </form>	
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="modifyMix">修改</button>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="${rc.getContextPath()}/static/index/js/index.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			$('.delBtn').on('click',function(){
				var $this = $(this);
				var mixId = $this.attr('mixId');
				window.location.href = '${rc.getContextPath()}/mix/delMix?mixId=' + mixId + '&pageNum=' + ${pageNum};
			});
			$('.updateBtn').on('click',function(){
				var $div = $(this).parent();
				var $title = $div.find('span.title');
				var $content = $div.find('h5.content');
				var $id = $div.find('input.mixId');
				var $cate = $div.find('input.cate');
                $('#titleUpdate').val($title.text()); 
                $('#descUpdate').val($content.text()); 
                $('#idUpdate').val($id.val());
                var cate = $cate.val();
                if(!cate){
                  cate = 1;  
                }
                $('#cateUpdate').val(cate);
                $('#modifyDialog').modal('show');                    
			});
			$('#modifyMix').click(function(){
				var $title = $('#titleUpdate');
			    if(!$title.val()){
			       $title.focus();
                   return;
			    }
			    var $content = $('#descUpdate');
			    if(!$content.val()){
			       $content.focus();
                   return;
			    }
			    $('#updateMixForm').submit();
			});
		});   
		</script>
	</body>
	</html>