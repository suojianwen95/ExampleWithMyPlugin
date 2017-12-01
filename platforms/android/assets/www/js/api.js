//需要先安装插件
var theList = new Array();
////////////////////////////////////////////////////////////////////////
//测试
function example(){
   cordova.plugins.MyTest.coolMethod("hello world",success,error);
   function success(data){
     alert(JSON.stringify(data));
   };
   function error(err){
      alert(JSON.stringify(err));
   };
};
////////////////////////////////////////////////////////////////////////
//获取配对设备
function thelist(listId){
   if(listId == null || listId == undefined || listId == ""){
     alert("地址不能为空");
     return;
   }
   var listDiv = document.getElementById(listId);
   listDiv.innerHTML = "查询中......";
   cordova.plugins.MyTest.thelist(success,error);
   function success(data){
     //alert(JSON.stringify(data));
     listDiv.innerHTML = "";
     theList.length = 0;
     for(var i = 0,len = data.length;i<len;i++){
              theList.push(data[i].address);

              var li = document.createElement('li');
              var title = document.createElement('div');
              title.innerHTML = "&ensp;&ensp;名称:"+data[i].name+
                                       "<br/>&ensp;&ensp;地址:"+data[i].address+
                                       "<br/>&ensp;&ensp;类别标识:"+ (data[i].class == undefined?'无':data[i].class)+
                                       "<br/>&ensp;&ensp;状态:<span id = 'status"+i+"' style = 'color:#f00;'>未连接</span>";
              li.appendChild(title);

              var op = document.createElement('div');
              var connectBt = document.createElement('input');
              connectBt.setAttribute("type","button");
              connectBt.style.width = "100%";
              connectBt.setAttribute("value","连接打印机（请确保连接）");

              var cutpaperBt = document.createElement('input');
              cutpaperBt.setAttribute("value","打印机切纸");
              cutpaperBt.style.width = "100%";
              cutpaperBt.setAttribute("type","button");

              var sendDataBt = document.createElement('input');
              sendDataBt.setAttribute("value","打印下方数据");
              sendDataBt.style.width = "100%";
              sendDataBt.setAttribute("type","button");

              var textareaDiv  = document.createElement('textarea');
              textareaDiv.style.width = "100%";
              textareaDiv.style.height = "200px";

              op.appendChild(connectBt);
              op.appendChild(sendDataBt);
              op.appendChild(cutpaperBt);

              li.appendChild(op);
              li.appendChild(textareaDiv);

              listDiv.appendChild(li);
              addEventBle(i,connectBt,cutpaperBt,sendDataBt,textareaDiv);
      }
   };
   function error(err){
      alert(JSON.stringify(err));
   };
};
////////////////////////////////////////////////////////////////////////
//事件
function addEventBle(index,obj1,obj2,obj3,obj4){
  obj1.onclick = function (){
      //alert("要连接的设备地址为:"+theList[index]);
      connect(theList[index],index);
  };
  obj2.onclick = function(){
        cutpaper();
  };
  obj3.onclick = function(){
      printData(obj4.value);
  };
};
////////////////////////////////////////////////////////////////////////
//获取配对设备
function connect(address,index){
   cordova.plugins.MyTest.connect(address,success,error);
   document.getElementById("status"+index).innerHTML = "正在连接请稍后...";
   document.getElementById("status"+index).style.color = "#ff0";
   function success(data){
     alert(JSON.stringify(data));
     var stauts = document.getElementById("status"+index);
     stauts.innerHTML = "已连接";
     stauts.style.color = "#0f0";
   };
   function error(err){
      alert(JSON.stringify(err));
       var stauts = document.getElementById("status"+index);
       stauts.innerHTML = "未连接";
       stauts.style.color = "#f00";
   };
};
////////////////////////////////////////////////////////////////////////
//打印测试
function printData(data){
   cordova.plugins.MyTest.printData(data,success,error);
   function success(data){
     //alert(JSON.stringify(data));
   };
   function error(err){
      alert(JSON.stringify(err));
   };
};
////////////////////////////////////////////////////////////////////////
//开钱箱
function opencash(){
   cordova.plugins.MyTest.opencash("opencash",success,error);
   function success(data){
     alert(JSON.stringify(data));
   };
   function error(err){
      alert(JSON.stringify(err));
   };
};
////////////////////////////////////////////////////////////////////////
//切纸
function cutpaper(){
   cordova.plugins.MyTest.cutpaper("cutpaper",success,error);
   function success(data){
     //alert(JSON.stringify(data));
   };
   function error(err){
      alert(JSON.stringify(err));
   };
};
