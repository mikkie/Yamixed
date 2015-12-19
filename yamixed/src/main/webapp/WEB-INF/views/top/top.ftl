<!DOCTYPE html>
<html>
<head>
	<title>热门</title>
	<link href="${rc.getContextPath()}/static/index/css/index.css" rel="stylesheet">
	<style type="text/css">
	.navbar-inverse .navbar-nav>li:nth-child(1)>a {
        color: #ffffff;
    }
    </style>
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
		   			<button class="btn btn-primary btn-xs commentBtn" type="button" mixId="${mix.id}">评论<span class="badge">${mix.comments}</span></button>
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
		</#if>
	</div>
	<script type="text/javascript" src="${rc.getContextPath()}/static/index/js/index.js"></script>
</body>
</html>