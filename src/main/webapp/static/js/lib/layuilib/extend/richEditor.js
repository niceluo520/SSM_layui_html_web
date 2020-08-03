/*!
 * 封装wangeditor富文本
 */
layui.define([ 'jquery', 'layer' ], function(exports) {
    var $ = layui.jquery, layer = layui.layer;
    var obj = {
        render : function(e) {
            var id = e.id, editorId = e.editorId, fileShowPath = e.fileShowPath, uploadImgServer = e.uploadImgServer;

            var E = window.wangEditor;
            var wEditor = new E('#' + editorId);
            
            // 配置 onchange 函数
            wEditor.customConfig.onchange = function (html) {
                if(html == '<p><br></p>'){
                    html = '';
                }
                $('#' + id).val(html);
            }
            // 自定义 onchange 触发的延迟时间，默认为 200 ms
            wEditor.customConfig.onchangeTimeout = 1000 // 单位 ms
            // 配置服务器端地址
            wEditor.customConfig.uploadImgServer = uploadImgServer;
            // 上传图片的错误提示,,自定义提示方法，默认使用alert弹出
            wEditor.customConfig.customAlert = function (msg) {
                lalert2(msg);
            }
            //自定义 fileName
            wEditor.customConfig.uploadFileName = 'file';
            var loadingIndex = null;
            // 监听函数
            wEditor.customConfig.uploadImgHooks = {
                before: function () {
                    loadingIndex = layer.msg('正在上传......', { icon: 16,shade: 0.01,time:0});
                },
                success: function () {
                    layer.close(loadingIndex);
                },
                fail: function () {
                    layer.close(loadingIndex);
                },
                error: function () {
                    layer.close(loadingIndex);
                },
                timeout: function () {
                    layer.close(loadingIndex);
                },
                customInsert: function (insertImg, result, editor) {
                    var url= fileShowPath + result.retData.src;
                    insertImg(url);
                }
            }
            // 对粘贴的文本内容进行自定义的过滤、处理等操作
            wEditor.customConfig.pasteTextHandle = function (content) {
                // 只过滤一些标签
                var stringStripper = /(\n|\r| class=(")?Mso[a-zA-Z]+(")?)/g;
                var output = content.replace(stringStripper, ' ');
                var commentSripper = new RegExp('<!--(.*?)-->','g');
                var output = output.replace(commentSripper, '');
                var badTags = ['style', 'script','applet','embed','noframes','noscript','o:p'];
                for (var i=0; i< badTags.length; i++) {
                  tagStripper = new RegExp('<'+badTags[i]+'.*?'+badTags[i]+'(.*?)>', 'gi');
                  output = output.replace(tagStripper, '');
                }
                return output;
            }
            
            wEditor.create();
            return wEditor;
        }
    };
    exports('richEditor', obj);
});