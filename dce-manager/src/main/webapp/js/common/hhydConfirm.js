(function($){
	window.hhyd = window.hhyd || {};
	window.hhyd.info = function(message, options) {
		var info = {
			title: "信息",
			icon: "0 0",//蓝色i
			btn: "1"
		};
		var itype = info;//格式化输入的参数:弹窗类型
		var config = $.extend(true, {
			//属性
			title: "", //自定义的标题
			icon: "", //图标
			btn: "1", //按钮,默认单按钮
			//事件
			onOk: $.noop//点击确定的按钮回调
		}, itype, options);
		
		var $txt = $("<p>").html(message);//弹窗文本dom
		var $tt = $("<span>").addClass("tt").text(config.title);//标题
		var icon = config.icon;
		var btn = config.btn;//按钮组生成参数
		
		var popId = creatPopId();//弹窗索引
		
		var $box = $("<div>").addClass("hhydConfirm");//弹窗插件容器
		var $layer = $("<div>").addClass("hhyd_layer");//遮罩层
		var $popBox = $("<div>").addClass("popBox");//弹窗盒子
		var $txtBox = $("<div>").addClass("txtBox");//弹窗内容主体区
		var $btnArea = $("<div>").addClass("btnArea");//按钮区域
		
		var $ok = $("<a>").addClass("sgBtn").addClass("ok").text("确定");//确定按钮

		//建立按钮映射关系
		var btns = {ok: $ok	};
		
		init();
		
		function init(){
			creatDom();
			bind();
		}
		
		function creatDom(){
			$popBox.append(
				$txtBox.append($txt)
			).append(
				$btnArea.append(creatBtnGroup(btn))
			);
			$box.attr("id", popId).append($layer).append($popBox);
			$("body").append($box);
		}
		
		function bind(){
			//点击确认按钮
			$ok.click(doOk);
			
			//回车键触发确认按钮事件
			$(window).bind("keydown", function(e){
				if(e.keyCode == 13) {
					if($("#" + popId).length == 1){
						doOk();
					}
				}
			});
		}

		//确认按钮事件
		function doOk(){
			var $o = $(this);

		    config.onOk();
			$("#" + popId).remove(); 
		}
		
		
		//生成按钮组
		function creatBtnGroup(tp){
			var $bgp = $("<div>").addClass("btnGroup");
			$.each(btns, function(i, n){
				$bgp.append(n);	 
			});
			return $bgp;
		}

		//重生popId,防止id重复
		function creatPopId(){
			var i = "pop_" + (new Date()).getTime()+parseInt(Math.random()*100000);//弹窗索引
			if($("#" + i).length > 0){
				return creatPopId();
			}else{
				return i;
			}
		}
	};

})(jQuery);