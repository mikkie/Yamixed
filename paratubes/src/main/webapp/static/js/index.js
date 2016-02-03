window.com.paratubes.index = (function() {
	var ELS_IDS = {
      MAP : 'map',
      CANVAS : 'playCanvas'
	};

	var map;

  var layer = {

  };

	var bind = {
         
	};

	var initMap = function(){
       L.mapbox.accessToken = 'pk.eyJ1IjoiYXF1YSIsImEiOiJRS3JfXzlZIn0.DgCZGsqE4SWPCvXGj77hyw';
       // Create a map in the div #map
       map = L.mapbox.map(ELS_IDS.MAP, 'aqua.obf5jnno').setView([24.494124, 118.134854], 13);
       // map = L.mapbox.map(ELS_IDS.MAP, 'aqua.occ5hn1m').setView([24.494124, 118.134854], 13);
       // map = L.mapbox.map(ELS_IDS.MAP, 'aqua.occ6oi5b').setView([24.494124, 118.134854], 13);
	};


  var gotoZone = function(e){
       $('#' + ELS_IDS.MAP).hide();
       $('#' + ELS_IDS.CANVAS).show(); 
  };

  
  var loadZones = function(){
    var zones = [
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [118.134854,24.494124]
      },
      "properties": {
        "title": "aqua's home",
        "description": "测试地区",
        "marker-color": "#F9BE18",
        "marker-size": "small",
        "marker-symbol": "star-stroked"
      }
    }
   ];
   layer = L.mapbox.featureLayer().setGeoJSON(zones).addTo(map);
   layer.on('click',function(e){
       gotoZone(e);
   }); 
  } 


  var initGame = function(){
      var $canvas = $('#' + ELS_IDS.CANVAS);
      $canvas.attr('width',$canvas.width());
      $canvas.attr('height',$canvas.height());
      window.com.paratubes.initGame(window,ELS_IDS.CANVAS,$);
  };


	var init = function(){
       com.paratubes.common.init(bind);
       // initMap();
       // loadZones();
       initGame();
	};

	return {
		init : init
	}
})();
$(document).ready(window.com.paratubes.index.init);


