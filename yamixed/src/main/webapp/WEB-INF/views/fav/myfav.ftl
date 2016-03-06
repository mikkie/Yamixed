<!DOCTYPE html>
<html>
<head>
	<title>我的书签 - ${channel.name}</title>
	<link href="${rc.getContextPath()}/static/fav/css/fav.css" rel="stylesheet">
	<style type="text/css">
	.navbar-inverse .navbar-nav>li:nth-child(2)>a {
		color: #ffffff;
	}
	</style>
</head>
<body>
	<div id="profile">
		<div class="media">
			<a class="media-left" href="javascript:void(0);" target="_blank">
				<div class="media-left-div">
					<img src="${loginUser.avatar}" alt="..." class="img-circle"/>
				</div>
			</a>
			<div class="media-body">
				<div class="media-body-left">
					<h3 class="uname">${loginUser.name}</h3>	
					<span id="introduce">${loginUser.introduce}</span>&nbsp;
                    <#if editable??>
					<span id="editIntroduce" class="glyphicon glyphicon-pencil"></span>
					</#if>
				</div>
			</div>
		</div>
	</div>	
	<div class="clear"></div>
	<div id="tagDiv">
		<#if tagid??>
		<input type="hidden" id="tagid" value="${tagid}"/>
		</#if>
		<ul id="tagUl" class="nav nav-pills" role="tablist">
			<#if tags.content??>
		    <#list tags.content as tag>
			<li role="presentation" class="active"><button type="button" data="${tag.id}" class="tag btn btn-success btn-xs" onclick="javascript:window.location.href='${rc.getContextPath()}/fav/userfav/${channel.id}/${loginUser.id}/${tag.id}/0/${tags.number}/40';">${tag.name}<span class="badge">${tag.articleCount}</span></button></li>
		    </#list>
		    </#if>
		</ul>
		<ul class="pager">
			<#if (tags.number>0)>
			<li class="previous">
				<a href="${rc.getContextPath()}/fav/myfav/${channel.id}/${tags.number-1}/40" title="上一页标签">&laquo;</a>
			</li>
			</#if>
			<#if (tags.number<tags.totalPages-1)>
			<li class="next">
				<a href="${rc.getContextPath()}/fav/myfav/${channel.id}/${tags.number+1}/40" title="下一页标签">&raquo;</a>
				</li>
			</#if>
		</ul>	
	</div>	
	<div id="articleDiv">
		<#if articles?? && (articles?size > 0)>
		<#list articles as article>
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
				<div class="media-body-left" style="width:85%;">
					<a href="${rc.getContextPath()}/fav/article/${channel.id}/${article.id}" class="mixLink" articleId="${article.id}">
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
					<a>
						<a href="javascript:void(0);" class="upAndDown" isUp="false" articleId="${article.id}"><span class="glyphicon glyphicon-thumbs-down"></span><span class="badge">${article.down}</span></a>
					</div>	
					<#if editable??>
				    <div class="media-body-right" style="padding-top:10px;">
					  <button type="button" class="btn btn-info btn-xs" onclick="window.location.href='${rc.getContextPath()}/fav/editarticle/${channel.id}/${article.id}'">编辑</button>
					  <button type="button" class="btn btn-danger btn-xs" onclick="if(confirm('确定删除该书签?')){window.location.href='${rc.getContextPath()}/fav/del/${channel.id}/${loginUser.id}/${tagid}/${article.id}'}">删除</button>
				    </div>	
				    </#if>
				</div>
			</div>
			</#list>
			<#if tagid??>
			<ul class="pager">
				<#if (pageNum>0)>
				<li class="previous">
					<a href="${rc.getContextPath()}/fav/userfav/${channel.id}/${loginUser.id}/${tagid}/${pageNum-1}" title="上一页链接">上一页</a>
				</li>
				</#if>
				<#if (pageNum<pages)>
				<li class="next">
					<a href="${rc.getContextPath()}/fav/userfav/${channel.id}/${loginUser.id}/${tagid}/${pageNum+1}" title="下一页链接">下一页</a>
				</li>
				</#if>
			</ul>
			</#if>
			<#else>
			  暂无任何书签。<#if editable??><button type="button" class="btn btn-info" onclick="window.location.href='${rc.getContextPath()}/fav/newarticle/${channel.id}<#if tagid??>?oldtag=${tagid}</#if>';">去创建书签</button></#if>
			</#if>
		</div>
		<script type="text/javascript" src="${rc.getContextPath()}/static/fav/js/userfav.js"></script>
	</body>
	</html>