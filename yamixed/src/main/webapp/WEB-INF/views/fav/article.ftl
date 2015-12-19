<!DOCTYPE html>
<html>
<head>
	<title>${article.content}</title>
	<link href="${rc.getContextPath()}/static/fav/css/fav.css" rel="stylesheet">
	<style type="text/css">
	.navbar-inverse .navbar-nav>li:nth-child(3)>a {
		color: #ffffff;
	}
	</style>
</head>
<body>
	<div id="profile">
		<div class="media">
			<a class="media-left" href="${rc.getContextPath()}/fav/userfav/${channel.id}/${article.user.id}/${article.tag.id}/0">
				<div class="media-left-div">
					<img src="${article.user.avatar}" alt="..." class="img-circle"/>
				</div>
			</a>
			<div class="media-body">
				<div class="media-body-left">
					<h3 class="uname">${article.user.name}</h3>	
					<h4>${article.content}</h4>
				</div>
			</div>
		</div>
	</div>	
	<div class="clear"></div>
	<div id="articleDiv">
		<#if article.links??>
		<#list article.links as link>
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
			</#if>
		</div>
		<script type="text/javascript" src="${rc.getContextPath()}/static/fav/js/article.js"></script>
	</body>
	</html>