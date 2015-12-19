<#import "/static/include/spring.ftl" as spring/>
<@spring.bind "newMix" />
<!DOCTYPE html>
<html>
<head>
	<title>新链接</title>
	<link href="${rc.getContextPath()}/static/mix/css/newMix.css" rel="stylesheet">
</head>
<body>
	<div>
		<form id="saveMixForm" class="form-horizontal" role="form" method="post" action="${rc.getContextPath()}/mix/save">
			<div class="form-group">
				<label for="urladdress" class="col-sm-offset-1 col-sm-1 control-label">链接</label>
				<div class="col-sm-8">
					<input type="text" name="url" class="form-control" id="urladdress" readonly="readonly" value="${newMix.url}">
					<!-- <@spring.formInput "newMix.url" /> -->
				</div>
			</div>
			<div class="form-group">
				<label for="title" class="col-sm-offset-1 col-sm-1 control-label">标题*</label>
				<div class="col-sm-8">
					<input type="text" name="title" class="form-control" id="title" placeholder="标题" value="${newMix.title!''}">
				</div>
			</div>
			<div class="form-group">
				<label for="desc" class="col-sm-offset-1 col-sm-1 control-label">描述*</label>
				<div class="col-sm-8">
					<textarea class="form-control" name="desc" rows="3" id="desc">${newMix.description!''}</textarea>
				</div>
			</div> 
			<#if allCates??>
			<div class="form-group">
				<label for="cate" class="col-sm-offset-1 col-sm-1 control-label">分类</label>
				<div class="col-sm-8">
					<select name="category" style="width:100%;">
						<#list allCates as cate>
						<option value="${cate.id}">${cate.name}</option>
						</#list>
					</select>
				</div>
			</div> 
			</#if>
			<#if newMix.imageUrls??>
			<div class="form-group">
				<label for="title" class="col-sm-offset-1 col-sm-1 control-label">图片</label>
				<div class="col-sm-8">
					<#list newMix.imageUrls as imageUrl>
					<a href="javascript:void(0);"><img src="${imageUrl}" alt="..." class="img-thumbnail previewImg"></a>
					</#list>
				</div>
			</div>
			</#if>
			<input type="hidden" id="imageUrl" name="imageUrl" value=""/>
			<div class="form-group">
				<label for="imglink" class="col-sm-offset-1 col-sm-1 control-label">
                   <input type="checkbox" id="chkimglink">外链图片
                </label>
                <div class="col-sm-8">
				   <input type="text" readonly="readonly" name="imglink" id="imglink" class="form-control" placeholder="外链图片地址" value="">
				</div> 
				<div class="col-sm-8" id="imglinkDiv" style="display:none;">
				   <a href="javascript:void(0);"><img src="" id="linkimg" alt="..." class="img-thumbnail previewImg"></a>
				</div>	
			</div>	
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-8">
					<button type="button" id="saveMix" class="btn btn-primary">发布</button>
				</div>
			</div>
		</form>
	</div> 	
	<script src="${rc.getContextPath()}/static/mix/js/newMix.js"></script>
</body>
</html>