/**
 * 项目地址
 */
var basePath;

/**
 * Created by Administrator on 2017/09/28.
 * 
 * 实现类似jsp中<%=basePath%>的功能，获取项目根目录有助于url的填写
 * 
 * 使用方法，用类似引用jQuery的方法引入本插件
 * 
 * 定义一个全局的变量，然后的ready方法内部掉用本方法，获得basePath
 */
var getBasePath = function (){
    //获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPath = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/ems
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    //获取项目的basePath   http://localhost:8080/ems/
    var basePath=localhostPath+projectName+"/";
    return basePath;
};

/**
 * Created by Administrator on 2017/09/28.
 * 填充url
 */
var fillPath = function (pagePath){
    return basePath + pagePath;
};

$(document).ready(function() {
	basePath = getBasePath();
});