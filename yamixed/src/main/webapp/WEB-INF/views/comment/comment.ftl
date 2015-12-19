<!DOCTYPE html>
<html>
<head>
	<title>${mix.title}</title>
	<link rel="stylesheet" href="${rc.getContextPath()}/static/tree/css/jquery.treegrid.css">
	<link rel="stylesheet" href="${rc.getContextPath()}/static/comment/css/comment.css">
	<link rel="stylesheet" href="${rc.getContextPath()}/static/emt/css/smohan.face.css">
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
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
					</div>
				</div>
			</div>
		</div>
		<table class="tree table">
			<tr class="treegrid">
				<td>
					<form action="${rc.getContextPath()}/comment/new" method="post" id="commentform">
						<textarea class="form-control" rows="3" name="content" id="commentarea"></textarea></br>
						<input type="hidden" value="${mix.id}" name="mixId"/>
						<a href="javascript:void(0)" class="face" title="表情"></a>
						<button class="btn btn-primary btn-xs submitComment" type="button">评论</button>
					</form>
				</td>
			</tr>
			<#if comments??>
			<#list comments as comment>
			<#if comment.parent??>
			<tr class="treegrid-${comment.pos} treegrid-parent-${comment.parent_pos}"> 
				<#else>
				<tr class="treegrid-${comment.pos}">
					</#if>
					<td>
						<span class="commentTD">${comment.pos+1}楼 : ${comment.content}</span></br>
						<a href="javascript:void(0);" class="showCommentInput">回复</a>
						<form action="${rc.getContextPath()}/comment/new" method="post" style="display:none;">
							<textarea class="form-control" rows="3" name="content"></textarea></br>
							<input type="hidden" value="${mix.id}" name="mixId"/>
							<input type="hidden" value="${comment.id}" name="commentId"/>
							<button class="btn btn-primary btn-xs submitComment" type="button">评论</button>
						</form>
					</td>
				</tr>
				</#list>
				</#if>
			</table>
		</div>
		<script type="text/javascript" src="${rc.getContextPath()}/static/tree/js/jquery.treegrid.js"></script> 
		<script type="text/javascript" src="${rc.getContextPath()}/static/tree/js/jquery.treegrid.bootstrap3.js"></script>
		<script type="text/javascript">
		$('.tree').treegrid({
			expanderExpandedClass: 'glyphicon glyphicon-minus',
			expanderCollapsedClass: 'glyphicon glyphicon-plus'
		});
		</script>
		<script type="text/javascript" src="${rc.getContextPath()}/static/emt/js/smohan.face.js" charset="utf-8"></script>
		<script type="text/javascript" src="${rc.getContextPath()}/static/comment/js/comment.js"></script>
	</body>
	</html>	