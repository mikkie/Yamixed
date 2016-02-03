<script type="text/javascript">
window.ctx = '${rc.getContextPath()}';
window.com = {};
com.paratubes = {};
com.paratubes.common = {
	init : function(bind) {
	   for(var m in bind){
          bind[m]();
	   }
	}
};
</script>