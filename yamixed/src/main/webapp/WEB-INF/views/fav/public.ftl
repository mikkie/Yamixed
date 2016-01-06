<!DOCTYPE html>
<html>
<head>
	<title>${channel.name}</title>
	<link href="${rc.getContextPath()}/static/fav/css/fav.css" rel="stylesheet">
	<style type="text/css">
	.navbar-inverse .navbar-nav>li:nth-child(1)>a {
		color: #ffffff;
	}
	</style>
</head>
<body>
	<div>
		<input type="hidden" id="tagid" value="${tagid}"/>
		<ul id="tagUl" class="nav nav-pills" role="tablist">
			<#if tags??>
			<#list tags as tag>
			<li role="presentation" class="active"><button type="button" class="tag btn btn-warning btn-xs" data="${tag.id}" onclick="javascript:window.location.href='${rc.getContextPath()}/fav/${channel.id}/${tag.id}/0';">${tag.name}<span class="badge">${tag.articleCount}</span></button></li>
			</#list>
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
					<a href="${rc.getContextPath()}/fav/userfav/${channel.id}/${article.user.id}/${article.tag.id}/0">
						<img src="${article.user.avatar}" alt="..." class="img-circle"/>
					</a>
				</div>	
			</div>
		</div>
		</#list>
		<ul class="pager">
			<#if (pageNum>0)>
			<li class="previous">
				<a href="${rc.getContextPath()}/fav/${channel.id}/${tagid}/${pageNum-1}">上一页</a>
			</li>
			</#if>
			<#if (pageNum<pages)>
			<li class="next">
				<a href="${rc.getContextPath()}/fav/${channel.id}/${tagid}/${pageNum+1}">下一页</a>
			</li>
			</#if>
		</ul>
		<#else>
		该标签暂无任何文章。
		</#if>
	</div>
	<div class="modal fade" id="loginDialog" tabindex="-1" role="dialog" aria-labelledby="loginLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title" id="loginLabel">登录</h4>
				</div>
				<div class="modal-body" style="text-align:center;">
					<form id="qqLoginForm" class="form-horizontal" role="form" method="post" action="${rc.getContextPath()}/fav/login/${channel.id}">
						<input type="hidden" name="openId" id="openId"/>
						<input type="hidden" name="accessToken" id="accessToken"/>
						<input type="hidden" name="avatar" id="avatar"/>
						<input type="hidden" name="nickname" id="nickname"/>
						<span id="qqLoginBtn"></span>
					</form>	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${rc.getContextPath()}/static/fav/js/public.js"></script>
	<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101202925" data-redirecturi="http://www.yamixed.com${rc.getContextPath()}/fav/qqcallback?channelid=${channel.id}&tagid=${tagid}&cb=cb" data-callback="true" charset="utf-8"></script>
	<script type="text/javascript">
	<#if op??>
	if('${op}' == 'logout'){
	   QC.Login.signOut();
	}  
	</#if>
	<#if cb?? || op??>
    //QQ登录
	(function qqLogin(){
		var a = QC.Login({
		 	btnId:"qqLoginBtn",
		 	size: "A_L"	
		},function(reqData, opts){
		 	var figureurl = reqData.figureurl;
		 	var nickname = reqData.nickname;
		 	if(QC.Login.check()){
		 		QC.Login.getMe(function(openId, accessToken){
		 			$('#openId').val(openId);
		 			$('#accessToken').val(accessToken);	
		 			$('#nickname').val(nickname);
		 			QC.api("get_user_info", {}).success(function(s){
		 			  $('#avatar').val(s.data.figureurl_2);
		 			  $('#qqLoginForm').submit();  
		 			});
		 		}); 
		 	}
		 }); 
	})();
	</#if>
	<#if op??>
	if('${op}' == 'login'){
	   $('#loginDialog').modal('show');
	}  
	</#if>
	</script>
	</body>
	</html>