var HHN = HHN || {};
HHN.formatNumber = function(num) {
    num = num + '';
    var rgx = /(\d+)(\d{3})/g;
    while (rgx.test(num)) {
        num = num.replace(rgx, '$1' + ',' + '$2');
    }
    return num;
};
HHN.checkEmpty = function(val){
    return val == undefined? true : (val.replace(/^\s+|\s+$/g, "").trim() == "" ? true : false);
}
HHN.checkPhone = function(val) {
    return /^0?(13|15|18|14|17)[0-9]{9}$/.test($.trim(val));
};
HHN.checkMail = function(val){
    //return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(val);
    return /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(val);
};
HHN.checkCode = function(val) { //非数字和字母组合返回true
    var s = $.trim(val);
    if (!(/\d/.test(s))){
        return true;
    }
    if (!(/[a-zA-Z\~\)\!\$\%\*\(\_\+\-\=\{\}\[\]\|\:\;\<\>\?\,\.\/\@\#\^\"\'\`\?\&]/.test(s)))
    {
        return true;
    }
}
HHN.checkCodeLength = function(val){ //字节长度
    return $.trim(val).replace(/[^\x00-\xff]/g, '中文').length;
}
HHN.checkIdNo = function(val) { //身份证15位和18位
    //return /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test(val) || /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/.test(val);
   return /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test(val) || /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/.test(val);
};
HHN.checkRealName = function(val) { //姓名校验
     return /^[a-zA-Z\u4e00-\u9fa5]+$/.test(val);
};
HHN.checkDigit = function(val) { //纯数字，包括小数点
   return /^\+?[1-9][0-9]*$/.test(val) || /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test(val); 
};
HHN.checkAllDigit = function(val){
    return /^[0-9]*$/.test(val);
};
HHN.checkAllLetter = function(val){  //纯字母
    return /^[A-Za-z]+$/.test(val);
};
HHN.checkAllSign = function(val){  //纯符号
    return /[^a-zA-Z0-9\u4e00-\u9fa5]+$/.test(val);
};
HHN.checkTowDigit = function(val){  //最多两位小数的数字
    return /^\d+(?:\.\d{1,2})?$/.test(val);
};
HHN.checkChinese = function(val){  //纯汉字
    return /^[\u4e00-\u9fa5]+$/.test(val);
};

HHN.popup = function(content, type, time) {
    return new Modal({
        model: 'popup',
        type: type || 'danger',
        content: content,
        time:time||3000
    });
};

HHN.webPopup = function(content, options) {
    if(options != (undefined && '')){
        return new Modal({        
            title:options.title == (undefined || '') ? '' : options.title,
            content : options.content == (undefined || '') ? (content == (undefined || '') ? '系统异常 , 请稍后重试....' : content): options.content,
            cancelText: options.cancelText == (undefined || '') ? '取消' : options.cancelText ,
            okText: options.okText == (undefined || '') ? '确定' : options.okText,
            cancel : options.cancel ,
            ok : options.ok || true,
            
        });
    }else{
        return new Modal({        
            title:'',
            content : content == (undefined || '') ?  '系统异常 , 请稍后重试....' : content,
            cancelText: '',
            okText:'确定',
            cancel : false,
            ok : true
        });
    }
    
};


HHN.loading = function(msg){    
    var str = '<div class="loadingPanel">'+
              '<div>'+
              '<div class="center"><img src="http://rec.hehuayidai.com/res/images/app/loading01.gif"></div>'+              
              (HHN.checkEmpty(msg)?('<div class="center"><p>正在努力加载中...</p></div>'):('<div class="center"><p>'+msg+'</p></div>'))+         
              '</div>'+
              '</div>';

       $(document.body).append(str);
}

HHN.removeLoading = function(){
    $(".loadingPanel").remove();
}