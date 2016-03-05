<#import "/static/include/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
	<title>新书签</title>
	<link href="${rc.getContextPath()}/static/mix/css/newMix.css" rel="stylesheet">
	<link href="${rc.getContextPath()}/static/fav/css/article.css" rel="stylesheet">
</head>
<body>
	<div>
		<form id="saveArticleForm" class="form-horizontal" role="form" method="post" action="${rc.getContextPath()}/fav/saveArticle/${channel.id}">
			<input type="hidden" name="channelid" value="${channel.id}" id="channelid"/>
			<input type="hidden" name="selectTagId" id="selectTagId" value=""/>
			<div class="form-group">
				<label for="tag" class="col-sm-offset-1 col-sm-1 control-label">标签</label>
				<div class="col-sm-8" name="tag">
					<div id="tagSelect" class="btn-group" style="float:left;">
						<button type="button" class="btn btn-warning" id="curTag">选择书签</button>
						<button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
							<span class="caret"></span>
							<span class="sr-only">Toggle Dropdown</span>
						</button>
						<#if oldtag??>
						<input type="hidden" id="oldTag" value="${oldtag}"/>
						</#if>
						<ul class="dropdown-menu" role="menu" id="tagslist">
							<#if taglist??>
							<#list taglist as tag>
							<li class="tagli"><a href="javascript:void(0);" data="${tag.id}">${tag.name}</a></li>
							</#list>
							</#if>
						</ul>
					</div>
					<div class="input-group">
						<span class="input-group-addon">
							<input type="checkbox" aria-label="..." id="enableCustomTag">
						</span>
						<input type="text" id="customTagInput" class="form-control" aria-label="..." placeholder="自定义标签" readonly="readonly" name="customTag">
					</div>
				</div>
			</div> 
			<div class="form-group">
				<label for="desc" class="col-sm-offset-1 col-sm-1 control-label">描述*</label>
				<div class="col-sm-8">
					<textarea class="form-control" name="desc" rows="3" id="desc"></textarea>
				</div>
			</div>
			<div class="form-group">
				<label for="privated" class="col-sm-offset-1 col-sm-1 control-label">私有</label>
				<div class="col-sm-8">
                    <input type="checkbox" name="privated"/> 
				</div>
			</div> 
			<div class="form-group">
				<label for="url" class="col-sm-offset-1 col-sm-1 control-label">链接*</label>
				<div class="col-sm-8">
					<div class="input-group">
						<input type="text" class="form-control url" placeholder="链接地址">
						<span class="input-group-btn">
							<button type="button" class="parseBtn btn btn-info" style="border-radius:0;">解析</button>
						</span>
						<span class="input-group-btn">
							<button type="button" id="addlink" class="btn btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
						</span>
					</div>	
				</div>
			</div>
			<div class="form-group" style="display:none;" id="linkInputTemp">
				<label for="url" class="col-sm-offset-1 col-sm-1 control-label">链接*</label>
				<div class="col-sm-8">
					<div class="input-group">
						<input type="text" class="form-control url" placeholder="链接地址">
						<span class="input-group-btn">
							<button type="button" class="parseBtn btn btn-info" style="border-radius:0;">解析</button>
						</span>
						<span class="input-group-btn">
							<button type="button" class="sublink btn btn-danger"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span></button>
						</span>
					</div>	
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-8">
					<button type="button" id="saveArticle" class="btn btn-primary">发布</button>
				</div>
			</div>
		</form>
	</div>
	<div class="modal fade" id="tagDialog" tabindex="-1" role="dialog" aria-labelledby="cateLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:700px;">
			<div class="modal-content">
			    <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title" id="tagLabel">选择标签</h4>
				</div>
				<div class="modal-body" style="min-height:200px;">
				   <div id="tagdiv">
				   	 <ul id="tagul">
				   	 </ul>
				   </div>
				   <ul id="tagpage" class="pager">
				     <li id="tagpre" class="previous">
				        <a href="javascript:void(0);">上一页</a>
			         </li>
			         <li id="tagnext" class="next">
				        <a href="javascript:void(0);">下一页</a>
			         </li>
				   </ul>
				</div>
				<div class="modal-footer">
					<button type="button" id="chooseTag" class="btn btn-primary" disabled="disabled">确定</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="linkDialog" tabindex="-1" role="dialog" aria-labelledby="cateLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:700px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title" id="cateLabel">链接</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" method="post" action="${rc.getContextPath()}/mix/save">
						<div class="form-group">
							<label for="urladdress" class="col-sm-3 control-label">链接</label>
							<div class="col-sm-8">
								<input type="text" name="url" class="form-control" id="urladdress" readonly="readonly" value="">
							</div>
						</div>
						<div class="form-group">
							<label for="title" class="col-sm-3 control-label">标题*</label>
							<div class="col-sm-8">
								<input type="text" name="title" class="form-control" id="title" placeholder="标题" value="">
							</div>
						</div>
						<div class="form-group">
							<label for="desc" class="col-sm-3 control-label">描述*</label>
							<div class="col-sm-8">
								<textarea class="form-control" name="desc" rows="3" id="linkdesc"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="privated" class="col-sm-3 control-label">私有</label>
							<div class="col-sm-8">
                                <input id="linkPrivated" type="checkbox" name="privated"/> 
							</div>
						</div> 
						<div class="form-group" id="linkimggroup">
							<label for="linkimg" class="col-sm-3 control-label">图片</label>
							<div class="col-sm-8" id="linkimglist">
							</div>
						</div>
						<input type="hidden" id="imageUrl" name="imageUrl" value=""/>
						<div class="form-group">
							<label for="imglink" class="col-sm-3 control-label">
								<input type="checkbox" id="chkimglink">外链图片
							</label>
							<div class="col-sm-8">
								<input type="text" readonly="readonly" name="imglink" id="imglink" class="form-control" placeholder="外链图片地址" value="">
							</div> 
							<div class="col-sm-offset-3 col-sm-8" id="imglinkDiv" style="display:none;">
								<a href="javascript:void(0);"><img src="" id="linkimg" alt="..." class="img-thumbnail previewImgSmall"></a>
							</div>	
						</div>
					</form>	
				</div>
				<div class="modal-footer">
					<button type="button" id="viewParse" class="btn btn-primary" disabled="disabled">确定</button>
				</div>
			</div>
		</div>
	</div> 	
	<script src="${rc.getContextPath()}/static/fav/js/newlink.js"></script>
	<script src="${rc.getContextPath()}/static/common/js/jquery.tmpl.js" type="text/javascript"></script>
	<script id="linkTemp" type="text/html">
	<#noparse>
	<label for="url" class="col-sm-offset-1 col-sm-1 control-label">链接</label>
	<div class="col-sm-8">
	<div class="media">
	<a class="media-left" href="${url}" target="_blank">
	<div class="media-left-div">
	<img src="${previewImgUrl}" alt="..." onerror="this.onerror=null;this.src='</#noparse>${rc.getContextPath()}<#noparse>/static/images/no_image.jpg';">
	</div>
	</a>
	<div class="media-body">
	<div class="media-body-left" style="width:80%;">
	<a href="${url}" target="_blank" class="mixLink">
	<h4 class="media-heading"><span class="title">${title}</span><span class="info"> ${createTime}</span></h4>
	</a>
	<h5 class="content">${description}</h5>
	</div>
	<div class="media-body-right">
	<span class="input-group-btn">
	<button type="button" class="modifyBtn btn btn-info" style="border-radius:0;" data="${tempID}">修改</button>
	</span>
	<span class="input-group-btn">
	<button type="button" id="addlink" class="btn btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
	</span>
	</div>
	</div>
	</div>
	</div>
	</#noparse>
	</script>
	<script id="tagTemp" type="text/html">
	<#noparse>
	<li class="tagli"><button type="button" data="${id}" class="tag btn btn-warning btn-xs">${name}</button></li>
	</#noparse>
	</script>
</body>
</html>