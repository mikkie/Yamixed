<!DOCTYPE html>
<html>
<head>
	<title>${key}</title>
	<link href="${rc.getContextPath()}/static/fav/css/fav.css" rel="stylesheet">
</head>
<body>
	<div id="linksDiv">
		<#if links?? && (links?size > 0)>
		<#list links as link>
		<div class="media">
			<a class="media-left" href="${link.url}" target="_blank">
				<div class="media-left-div">
					<img src="${link.previewImgUrl}" alt="..." onerror="this.onerror=null;this.src='${rc.getContextPath()}/static/images/no_image.jpg';">
				</div>
			</a>
			<div class="media-body">
				<div class="media-body-left">
					<a href="${link.url}" target="_blank" class="mixLink">
						<h4 class="media-heading"><span class="title">${link.title}</span><span class="info"> ${link.createTime}</span></h4>
					</a>
					<h5 class="content">${link.description}</h5>
					<div class="jiathis_style shareTag" style="display:inline-block;" data-summary="${link.description}" data-title="${link.title}" data-pic="${link.previewImgUrl}" data-url="">
						<a class="jiathis_button_qzone"></a>
						<a class="jiathis_button_tsina"></a>
						<a class="jiathis_button_tqq"></a>
						<a class="jiathis_button_weixin"></a>
						<a class="jiathis_button_renren"></a>
						<a class="jiathis_button_xiaoyou"></a>
					</div>
				</div>
				<div class="media-body-right">
					<a href="javascript:void(0);" class="upAndDown" isUp="true" linkId="${link.id}"><span class="glyphicon glyphicon-thumbs-up"></span><span class="badge">${link.up}</span></a>
					<a>
						<a href="javascript:void(0);" class="upAndDown" isUp="false" linkId="${link.id}"><span class="glyphicon glyphicon-thumbs-down"></span><span class="badge">${link.down}</span></a>
					</div>	
				</div>
			</div>
			</#list>
			<#else>
			  暂无任何书签<a href="${rc.getContextPath()}/fav/${channel.id}/0/40" class="btn btn-info" role="button">返回</a>
			</#if>
		</div>
		<script type="text/javascript" src="${rc.getContextPath()}/static/fav/js/article.js"></script>
	</body>
	</html>