/**
 * 
 * @Name：mainFrame 加载菜单数据
 * @Author：cbox
 * 
 */

$(function() {
    // 初始菜单
    initMenus();

    // var iframe = document.getElementById("mainFrame");// iframe的ID
    // iframe.height = $(window).height() - $("#navbar").innerHeight() - 5;
});

function initMenus() {
    $.ajax({
        type : "post",
        url : basePath + "/menus",
        timeout : 60000,
        success : function(data) {
            var json = data;
            if (json.retCode == 0) {
                if (json.retData != null) {
                    menuForHtml("LAY-system-side-menu", json.retData);
                }
            } else {
                layer.alert(json.retMsg);
            }
        }
    });
}

function menuForHtml(domId, menus) {
    var html = "";
    if (null != menus && menus.length > 0) {
        $.each(menus, function(index, item) {
            var isAppend = false;
            if ('${userInfo.isAdmin}' == '1') {
                isAppend = true;
            } else {
                if (item.is_control != "1") {
                    isAppend = true;
                }
            }
            if (isAppend) {
                /*
                 * <li data-name="component" class="layui-nav-item"> <a
                 * href="javascript:;" lay-tips="组件" lay-direction="2"> <i
                 * class="layui-icon layui-icon-component"></i> <cite>组件</cite>
                 * </a> <dl class="layui-nav-child"> <dd data-name="grid"> <a
                 * href="javascript:;">栅格</a> <dl class="layui-nav-child">
                 * <dd data-name="list"><a
                 * lay-href="component/grid/list.html">等比例列表排列</a></dd>
                 * <dd data-name="mobile"><a
                 * lay-href="component/grid/mobile.html">按移动端排列</a></dd>
                 * <dd data-name="mobile-pc"><a
                 * lay-href="component/grid/mobile-pc.html">移动桌面端组合</a></dd>
                 * <dd data-name="all"><a
                 * lay-href="component/grid/all.html">全端复杂组合</a></dd>
                 * <dd data-name="stack"><a
                 * lay-href="component/grid/stack.html">低于桌面堆叠排列</a></dd>
                 * <dd data-name="speed-dial"><a
                 * lay-href="component/grid/speed-dial.html">九宫格</a></dd>
                 * </dl> </dd> <dd data-name="button"> <a
                 * lay-href="component/button/index.html">按钮</a> </dd> </dl>
                 * <li>
                 */

                if (index == 0) {
                    // 第一个默认展开
                    html += '<li data-name="' + item.identity + '" class="layui-nav-item layui-nav-itemed">\n';
                } else {
                    html += '<li data-name="' + item.identity + '" class="layui-nav-item">\n';
                }

                if (Utils.isNull(item.url)) {
                    html += '   <a href="javascript:;" lay-tips="' + item.name + '" lay-direction="2">\n';
                } else {
                    html += '   <a lay-href="' + basePath + item.url + '" lay-tips="' + item.name + '" lay-direction="2">\n';

                }
//                html += '  <i class="layui-icon layui-icon-home"></i>';
                 if (Utils.isNotNull(item.icon)) {
                     html += ' <i class="layui-icon ' + item.icon + '"></i>\n';
                 } else {
                     html += '  <i class="layui-icon layui-icon-home"></i>';
                 }
                html += '	  <cite>' + item.name + '</cite>\n';
                html += '   </a>\n';

                if (null != item.childMenus && item.childMenus.length > 0) {
                    html += '   <dl class="layui-nav-child">';
                    html += menuForSonHtml(item.childMenus);
                    html += '   </dl>\n';
                }

                html += '<li>\n'
            }
        });
    }
    $("#" + domId).append(html);

    //重新对导航进行渲染
    layui.use('element', function() {
        var element = layui.element;
        element.render("nav");
    });
}

function menuForSonHtml(childMenus) {
    var childHtml = '';
    var isBtn = false;
    $.each(childMenus, function(index, item) {
        if (item.type == 'button') {
            isBtn = true;
        }
    });
    if (isBtn) {
        return childHtml;
    }

    $.each(childMenus, function(index, item) {

        childHtml += '      <dd data-name="' + item.identity + '">\n';

        if (Utils.isNull(item.url)) {
            childHtml += '          <a href="javascript:;" >' + item.name + '</a>\n';
        } else {
            childHtml += '          <a lay-href="' + basePath + item.url + '">' + item.name + '</a>\n';

        }

        if (null != item.childMenus && item.childMenus.length > 0) {
            // 最多只支持三级目录。TODO:如需支持更多级，在这里用递归调用方式来实现。

            childHtml += '          <dl class="layui-nav-child">\n';
            $.each(item.childMenus, function(index2, item2) {
                childHtml += '              <dd data-name="' + item.identity + '">';
                childHtml += '<a lay-href="' + basePath + item.url + '">' + item.name + '</a>';
                childHtml += '</dd>\n';
            });

            childHtml += '          </dl>\n';
        }

        childHtml += '      <dd>\n'
    });

    return childHtml;
}
