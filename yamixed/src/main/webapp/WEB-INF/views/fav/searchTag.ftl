<!DOCTYPE html>
<html>
<head>
	<title>搜索标签-${key}</title>
	<link href="${rc.getContextPath()}/static/fav/css/fav.css" rel="stylesheet">
	<style type="text/css">
	.navbar-inverse .navbar-nav>li:nth-child(1)>a {
		color: #ffffff;
	}
	</style>
</head>
<body>
	<div>
		<ul id="tagUl" class="nav nav-pills" role="tablist">
			<#if tags??>
			<#list tags as tag>
			<li role="presentation" class="active">
			    <#if tag.id == tagId>
			    <button type="button" class="tag btn btn-danger btn-xs" data="${tag.id}" onclick="javascript:window.location.href='${rc.getContextPath()}/fav/search?channelid=${channel.id}&key=${key}&isTag=true&tagId=${tag.id}';">${tag.name}<span class="badge">${tag.articleCount}</span></button>
			    <#else>
			    <button type="button" class="tag btn btn-warning btn-xs" data="${tag.id}" onclick="javascript:window.location.href='${rc.getContextPath()}/fav/search?channelid=${channel.id}&key=${key}&isTag=true&tagId=${tag.id}';">${tag.name}<span class="badge">${tag.articleCount}</span></button>
			    </#if>
			</li>
			</#list>
			</#if>
		</ul>
	</div>	
	<div id="articleDiv">
		<#if articles?? &&articles.content?? && (articles.content?size > 0)>
		<#list articles.content as article>
		<div class="media">
			<a class="media-left" href="${rc.getContextPath()}/fav/article/${channel.id}/${article.id}">
				<div class="media-left-div">
					<ul style="width:100px;height:100px;list-style:none;padding:0;">
					  <#list article.previewImgUrls as previewImgUrl>	
					  <li style="float:left;">
					     <img src="${previewImgUrl}" alt="..." onerror="this.onerror=null;this.src='${rc.getContextPath()}/static/images/no_image.jpg';" style="width:50px;height:50px;">
					  </li>	
					  </#list>
					</ul>	
				</div>
			</a>
			<div class="media-body">
				<div class="media-body-left">
					<a href="${rc.getContextPath()}/fav/article/${channel.id}/${article.id}" class="mixLink" mixId="${article.id}">
						<h4 class="media-heading"><span class="info"> ${article.createTime}</span></h4>
					</a>
					<h5 class="content">${article.content}</h5>
					<div class="jiathis_style shareTag" style="display:inline-block;" data-summary="${article.content}" data-title="${article.content}" data-pic="${article.previewImgUrls[0]}" data-url="http://www.yamixed.com/fav/article/${channel.id}/${article.id}">
						<a class="jiathis_button_qzone"></a>
						<a class="jiathis_button_tsina"></a>
						<a class="jiathis_button_tqq"></a>
						<a class="jiathis_button_weixin"></a>
						<a class="jiathis_button_renren"></a>
						<a class="jiathis_button_xiaoyou"></a>
					</div>
				</div>
				<div class="media-body-right">
					<a href="javascript:void(0);" class="upAndDown" isUp="true" articleId="${article.id}"><span class="glyphicon glyphicon-thumbs-up"></span><span class="badge">${article.up}</span></a>
					<a href="javascript:void(0);" class="upAndDown" isUp="false" articleId="${article.id}"><span class="glyphicon glyphicon-thumbs-down"></span><span class="badge">${article.down}</span></a>
				</div>	
				<div class="articleowner media-body-right">
					<a href="${rc.getContextPath()}/fav/userfav/${channel.id}/${article.user.id}/${article.tag.id}/0/0/40">
						<img src="${article.user.avatar}" alt="..." class="img-circle"/>
					</a>
				</div>	
			</div>
		</div>
		</#list>
		<ul class="pager">
			<#if (articles.number>0)>
			<li class="previous">
				<a href="${rc.getContextPath()}/fav/search?channelid=${channel.id}&key=${key}&isTag=true&tagId=${tagId}&arcPage=${articles.number-1}" title="上一页链接">上一页</a>
			</li>
			</#if>
			<#if (articles.number<articles.totalPages - 1)>
			<li class="next">
				<a href="${rc.getContextPath()}/fav/search?channelid=${channel.id}&key=${key}&isTag=true&tagId=${tagId}&arcPage=${articles.number+1}" title="下一页链接">下一页</a>
			</li>
			</#if>
		</ul>
		<#else>
		该标签暂无任何文章。
		</#if>
	</div>
	<script type="text/javascript" src="${rc.getContextPath()}/static/fav/js/public.js"></script>
	</body>
	</html>