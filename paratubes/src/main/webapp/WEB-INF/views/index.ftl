<!DOCTYPE html>
<html>
<head>
	<title></title>
    <!--<link href='https://api.mapbox.com/mapbox.js/v2.2.3/mapbox.css' rel='stylesheet' />-->
</head>
<body>
	<div>
		<div id="map" style="display:none;"></div>
	    <div class="container-fluid">
	      <div class="row">
       		<div class="col-sm-3 col-md-2 sidebar">
       		   <ul class="nav nav-sidebar">
                  <li>
                  	<input type="text" class="form-control" placeholder="搜索...">
                  </li>  
       		   </ul>
       		</div>
       		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
       		   <div id="toolbar">
       		   	<ul id="toolbar_ul">
       		   		<li>
       		   		    <button type="button" class="btn btn-default btn-xs painter" name="pen">
                          <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </button>
       		   		</li>
       		   		<li>
       		   		    <button type="button" class="btn btn-default btn-xs painter" name="eraser">
                          <span class="glyphicon glyphicon-stop" aria-hidden="true"></span>
                        </button>
       		   		</li>
       		   		<li>
       		   			<label>
                          <input type="checkbox" id="gridCheck"> 网格
                        </label>
       		   		</li>
       		   	</ul>
       		   </div>
       		   <canvas id="playCanvas">您的浏览器不支持Html5</canvas>
       		</div>
	      </div>
	    </div>
	</div>
	<!--<script src='https://api.mapbox.com/mapbox.js/v2.2.3/mapbox.js'></script>-->
	<script src='${rc.getContextPath()}/static/js/index.js'></script>
	<script src='${rc.getContextPath()}/static/game/shared/Interface.js'></script>
	<script src='${rc.getContextPath()}/static/game/shared/Library.js'></script>
	<script src='${rc.getContextPath()}/static/game/shared/requestNextAnimationFrame.js'></script>
	<script src='${rc.getContextPath()}/static/game/shared/sprites.js'></script>
	<script src='${rc.getContextPath()}/static/game/shared/gameEngine.js'></script>
	<script src='${rc.getContextPath()}/static/game/paratubes.js'></script>
</body>
</html>