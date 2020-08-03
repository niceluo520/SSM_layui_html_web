/*!
 * layer弹出框的公共定义，以保障整个项目的统一风格。

 */

/**************************** 弹窗 Begin *************************
 **@param  url-请求的url,dom-弹框前的对象,title-标题, w-弹出层宽度（缺省调默认值）, h-弹出层高度（缺省调默认值）, isMaxMin-是否最大化 true/false
 ****************************************************************/
//自带默认按钮：提交和取消两个按钮
function layer_show(url,dom,title, w, h, isMaxMin) {    
    if (Utils.isNull(title)) {
        title = false;
    }
    if (Utils.isNull(url)) {
        url = "404.html";
    }
    if (Utils.isNull(w)) {
        w=($(window).width() - 100);
        if(w>1000){
            w=1000;
        }
    }
    if (Utils.isNull(h)) {
        h = ($(window).height() - 50);
    }
    
    
    var area=[ w + 'px', h + 'px' ];
    var offset='5px';
    if ((w + "").indexOf("%") > -1) {
    	area=[ w, h ];
    	offset='t';
    }
    
    var index = layer.open({
        type : 2,
        btn : [ "提交", "取消" ],
        area : area,
        shade : 0.1,
        offset : offset,
        maxmin : isMaxMin,
        title : title,
        content : url,
        success  : function(layero, index) {
          //把弹窗前的对象dom参数，传给目标窗口，以便目标窗口业务处理完成后回调。
            var iframeWin = layero.find('iframe');
            $(iframeWin)[0].contentWindow.layer_load_success(dom);//目标窗口提供layer_load_success方法来接收（在common.js中公共定义了）
        },
        yes : function(index, layero) {
            var iframeWin = layero.find('iframe');
            $(iframeWin)[0].contentWindow.layer_submit(dom);//调用目标窗口的layer_submit方法，dom作为参数传入
        }
    });
    
    if (isMaxMin) {
        layer.full(index);
    }
    
    return index;
}

//无默认按钮
function layer_show_noBtn(url,dom,title, w, h, isMaxMin) {
    if (Utils.isNull(title)) {
        title = false;
    }
    if (Utils.isNull(url)) {
        url = "404.html";
    }
    if (Utils.isNull(w)) {
        w=($(window).width() - 100);
        if(w>1000){
            w=1000;
        }
    }
    if (Utils.isNull(h)) {
        h = ($(window).height() - 50);
    }
    
    
    var area=[ w + 'px', h + 'px' ];
    if ((w + "").indexOf("%") > -1) {
    	area=[ w, h ];
    }
    
    var index = layer.open({
        type : 2,
        area : area,
        shade : 0.1,
        offset : 'auto',
        maxmin : isMaxMin,
        title : title,
        content : url,
        success : function(layero, index) {
            //把弹窗前的对象dom参数，传给目标窗口，以便目标窗口业务处理完成后回调。
            var iframeWin = layero.find('iframe');
            $(iframeWin)[0].contentWindow.layer_load_success(dom);//目标窗口提供layer_load_success方法来接收（在common.js中公共定义了）
        }
    });
    
    if (isMaxMin) {
        layer.full(index);
    }
    
    return index;

}


/**************************** 弹窗 End *************************/



/**************************** 提示信息 自定义icon 显示时间如{icon:1,2000}适用于弹出框的提示信息 */
function layer_msg(msg, params) {
    layer.msg(msg, params);
}

/**************************** 正确信息 Begin *************************/
//正确信息1(页面正中间 适用于弹出框的提示信息)
function layer_success(msg) {
    layer.msg(msg, {
        icon : 1,
        time : 2000
    });
}

//正确信息2(屏幕中间范围-适用于无弹窗的情况下的提示信息)
function layer_success_offset(msg) {
    var sheight = window.screen.availHeight;
    var offset = sheight / 2 - 10 ;
    layer_success(msg,offset);
}

// 正确信息3(自定义高度) 
function layer_success(msg, offset) {
    layer.msg(msg, {
        icon : 1,
        time : 2000,
        offset : offset + 'px'
    });
}
/**************************** 正确信息 End *************************/

/**************************** 错误信息 Begin *************************/
// 错误信息1(页面正中间 适用于弹出框的提示信息)
function layer_error(msg) {
    layer.msg(msg, {
        icon : 2,
        time : 2000
    });
}

//错误提示，带抖动
function layer_error_shift(msg) {
    layer.msg(msg, {
        icon : 2,
        shift : 6
    });
}

// 错误信息2(屏幕中间范围-适用于无弹窗的情况下的提示信息) 
function layer_error_offset(msg) {
    var sheight = window.screen.availHeight;
    var offset = sheight / 2 - 10;
    
    layer_error(msg,offset);
}

// 错误信息3(自定义高度 ) 
function layer_error(msg, offset) {
    layer.msg(msg, {
        icon : 2,
        time : 2000,
        offset : offset + 'px'
    });
}
/**************************** 错误信息 End *************************/

/**************************** 警示信息 Begin *************************/
//无标题的警示信息 
function layer_alert(msg) {
    var sheight = window.screen.availHeight;
    var offset = sheight / 2 - 10;
    layer.msg(msg, {
        icon : 2,
        title : false,
        area : offset + 'px'
    });
}
/**************************** 警示信息 End *************************/


/***************************** confirm方法 *************************/
function layer_confirm(msg, params, Func) {
    layer.confirm(msg, params, function() {
        Func();
    });
};

/**************************** 显示加载动图... *************************/
function layer_loading() {
    layer.load(0, {
        shadeClose : true
    });
}

/**************************** 关闭弹出框口 Begin *************************/
//关闭弹层
//弹窗 iframe层.当你在iframe页面关闭自身时
function closePLayer() {
    var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
    parent.layer.close(index); // 再执行关闭
}
// 关闭指定弹层 
function layer_close(index) {
    layer.close(index);
}
//关闭所有弹层
function layer_closeAll() {
    layer.closeAll();
}
/**************************** 关闭弹出框口 End *************************/
