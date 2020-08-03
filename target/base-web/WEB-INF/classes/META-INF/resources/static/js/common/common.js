/** 弹出框之前的页面对象*/
var sourceDomForPopup;
/** 弹出框执行成功后回调 源页面DOM*/
function layer_load_success(dom) {
	sourceDomForPopup = dom;
}

function refresh(){
    window.location.reload();
}
// 验证重复元素，有重复返回true；否则返回false
function isRepeat(arr) {
    var hash = {};
    for(var i in arr) {
        if(hash[arr[i]]) {
            return true;
        }
        // 不存在该元素，则赋值为true，可以赋任意值，相应的修改if判断条件即可
        hash[arr[i]] = true;
    }
    return false;
}

/** 展示弹出提示 **/
function showPopupTip(event){
    var tips = $(this).attr('ext-tipinfo');
    var id = $(this).attr('id');
    if(event.type == "mouseover"){ //鼠标悬浮
        layer.tips(tips, "#"+id, {tips: [3, '#3595CC'], time:0});
    } else if(event.type == "mouseout"){ //鼠标离开
        layer.close(layer.index)
    }
}

/** 获取工程路径 */
function getRootPath() {
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    var host = window.location.host;
    var host2 = document.domain;
    return (prePath + postPath);
}

/** ***** 按钮权限校验 ************************** */
function checkBtnPermission() {
    var btns = new Array;
    
    //获取页面需要权限控制的authid
    $("[authid]").each(function() {
        var btnCode = $(this).attr("authid");
        btns.push(btnCode);
    });
    
    if (btns.length > 0) {
    	//如果存在，则进行权限控制    	
        $.ajax({
            type : "post",
            traditional : true,
            url : basePath + "/auth/getPageBtnAuth",
            data : {
                "btns" : btns
            },
            success : function(data) {
                var json = JSON.parse(data);
                for (var i = 0; i < btns.length; i++) {
                    var btnCode = btns[i];
                    var flag = json[btnCode]
                    
                    if(!flag){
                    	$("[authid='"+btnCode+"']").hide();
                    }
                    
                }
            }
        });
    }
}

//文件图标类型
function getFileIconClass(format) {
    var formatArr = [ "doc", "docx", "dotx", "dot", "docm", "dotm", "xps", "mht", "mhtml", "rtf", "xml", "odt", 
        "pdf", "ppt", "pptx", 
        "xls", "xlsx", "xlsb", "xlsm", "xlst", 
        "jpg", "jpeg", "png", "gif", "bmp", "jpeg2000",
        "rar", "zip", 
        "swf", "flic", "flc", "fli", "flash", "mp4", "avi", "rm", "rmvb", "wmv", "f4v", "flv", "mid", "mpeg", "mpg", "dat" ];

    if (typeof format != "undefined") {
        format = $.trim(format).toLowerCase();
        if ($.inArray(format, formatArr) == -1) {
            return "unknown";
        } else {
            return format;
        }
    } else {
        return "unknown";
    }
}
