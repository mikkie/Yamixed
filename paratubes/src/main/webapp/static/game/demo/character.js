window.com.paratubes.initGame = function(window,canvasId){
   var canvas = document.getElementById(canvasId);
   var ctx = canvas.getContext('2d');
   var img = new Image();
   var imgReady = false;
   //加载资源
   img.onload = function() {
   	imgReady = true;
      ctx.drawImage(img, 0, 0, 30, 43, 50, 50, 30, 43);
   };
   img.src = window.ctx + '/static/images/demo/supermarry.png';
   //游戏角色
   var role = {
      speed : 48,
      x : 50,
      y : 50,
      state : {
         x : 30
      }
   };
   var raf;
   //更新对象位置
   var update = function(interval){
       if (38 in keysDown) { // Player holding up
         role.y -= role.speed * interval;
         role.state.x = 135;
       }
       else if (40 in keysDown) { // Player holding down
         role.y += role.speed * interval;
         role.state.x = 278;
       }
       else if (37 in keysDown) { // Player holding left
         role.x -= role.speed * interval;
         role.state.x = 26;
       }
       else if (39 in keysDown){
         role.x += role.speed * interval; 
         role.state.x = 0;
       }
       else{
         role.state.x = 26;
       }
   };
   //显示
   var render = function(){
     if(!imgReady){
        return;  
     }
     ctx.clearRect(0,0,1000,500);
     ctx.drawImage(img, role.state.x, 0, 30, 43, role.x, role.y, 30, 43);
   };
   //游戏主循环
   var main = function(){
      var now = Date.now();
      update((now - then)/1000);
      then = now;
      render();
      raf = window.requestAnimationFrame(main);
   };
   var keysDown = {};
   //事件
   addEventListener("keydown", function(event){
   	  keysDown[event.keyCode] = true;
   });
   addEventListener("keyup",function(event){
      delete keysDown[event.keyCode];
   });
   var then = Date.now();
   main();
};