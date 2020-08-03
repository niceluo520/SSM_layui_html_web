/*!
 * Cropper v3.0.0
 * 与layui官网的不完全一致，有做部分修改调整
 */

layui.extend({
    cropper: 'layuilib/extend/cropper/cropper' 
}).define(['jquery', 'layer', 'cropper'], function (exports) {
        var $ = layui.jquery
            , layer = layui.layer;
        var html = 
            "<div class='layui-fluid showImgEdit' style='display: none'>\n" +
            "    <div class='layui-row layui-col-space15' style=' margin-top:5px; '>\n" +
            "        <div class='layui-col-xs9' style='background-color:#3c3939db'>\n" +
            "            <div class='readyimg' style='height:350px;'>\n" +
            "                <img src='' >\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div class='layui-col-xs3 layui-row' >\n" +
            "           <div class='layui-col-xs12' style='margin-left:10px;' >\n" +
            "               <div >裁剪后图片：\n" +
            "               </div>\n" +
            "           </div>\n" +
            "           <div class='layui-col-xs12'  style='margin-left:10px;'>\n" +
            "               <div class='img-preview' style='width:140px;margin-top:5px;overflow:hidden;border:1px solid gray'>\n" +
            "               </div>\n" +
            "           </div>\n" +
            "           <div class='layui-col-xs12'  style='margin-left:10px;'>\n" +
            "               <button type='button' class='layui-btn layui-bg-red' cropper-event='confirmSave' type='button' style='font-size:12px !important;width:140px;margin-top:100px;'> 保存修改</button>\n"+
            "           </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <div class='layui-row layui-col-space15'>\n" +
            "                <div >\n" +
            "                    <button type='button' class='layui-btn' cropper-event='rotate' data-option='90' title='Rotate 90 degrees' style='font-size:12px !important;'>顺90度</button>\n" +
            "                    <button type='button' class='layui-btn' cropper-event='rotate' data-option='-90' title='Rotate -90 degrees' style='font-size:12px !important;'>逆90度</button>\n" +
            "                    <button type='button' class='layui-btn' cropper-event='setDragMode' title='移动图片' style='font-size:12px !important;'>移动图片</button>\n" +
            "                    <button type='button' class='layui-btn' cropper-event='setDragMode1' title='裁剪图片' style='font-size:12px !important;'>裁剪图片</button>\n" +
            "                    <button type='button' class='layui-btn' cropper-event='zoomLarge' title='放大图片' style='font-size:12px !important;'>放大图片</button>\n" +
            "                    <button type='button' class='layui-btn' cropper-event='zoomSmall' title='缩小图片' style='font-size:12px !important;'>缩小图片</button>\n" +
            "                    <button type='button' class='layui-btn' cropper-event='reset' title='重置图片' style='font-size:12px !important;'>重置图片</button>\n" +
            "                </div>\n" +
            "    </div>\n" +
            "\n" +
            "</div>";
        var obj = {
            render: function (e) {
                var self = this,
                    //elem = e.elem,
                    saveW = e.saveW,
                    saveH = e.saveH,
                    mark = e.mark,
                    area = e.area,
                    url = e.url,
                    imgUrl = e.imgUrl,
                    imgFileName= e.imgFileName,//img在服务端的名称，为空则说明是新上传，不为空是修改
                    done = e.done;
                $('#cropperdiv').html("");
                $('#cropperdiv').append(html);
                $(".showImgEdit .readyimg img").attr('src', imgUrl);
                var content = $('.showImgEdit')
                    , image = $(".showImgEdit .readyimg img")
                    , preview = '.showImgEdit .img-preview'
                    , file = $(".showImgEdit input[name='file']")
                    , options = { aspectRatio: mark, preview: preview, viewMode: 1 };
     
                var layerIndex = layer.open({
                    title: "图片裁剪"
                    , type: 1
                    , content: content
                    , area: area
                    , success: function () {
                        image.cropper(options);
                    }
                    , cancel: function (index) {
                        layer.close(index);
                        image.cropper('destroy');
                    }
                });
                $(".layui-btn").on('click', function () {
                    var event = $(this).attr("cropper-event");
                    //监听确认保存图像
                    if (event === 'confirmSave') {
                        image.cropper("getCroppedCanvas", {
                            width: saveW,
                            height: saveH
                        }).toBlob(function (blob) {
                            var formData = new FormData();
                            var timestamp = Date.parse(new Date());
                            formData.append('file', blob, timestamp + '.jpeg');
                            formData.append('imgFileName',imgFileName);
                            $.ajax({
                                method: "post",
                                url: url, //用于文件上传的服务器端请求地址
                                data: formData,
                                processData: false,
                                contentType: false,
                                success: function (result) {
                                    //保存图片返回表示
                                    if (result.retCode == 0) {//成功
                                        layer.msg(result.retMsg, { icon: 1 });
                                        
                                        layer.close(layerIndex); //再执行关闭   
                                        
                                        return done(result.retData.src);//返回资源url
                                    } else{
                                        layer.alert(result.retMsg, { icon: 2 });
                                    }
                                }
                            });
                            }, 'image/jpeg');
                        //监听旋转
                    } else if (event === 'rotate') {
                        var option = $(this).attr('data-option');
                        image.cropper('rotate', option);
                        //重设图片
                    } else if (event === 'reset') {
                        image.cropper('reset');
                    }
                    else if (event === 'zoomLarge') {
                        image.cropper('zoom', 0.1);
                    }
                    else if (event === 'zoomSmall') {
                        image.cropper('zoom', -0.1);
                    }
                    else if (event === 'setDragMode') {
                        image.cropper('setDragMode', "move");
                    }
                    else if (event === 'setDragMode1') {
                        image.cropper('setDragMode', "crop");
                    }
                    //文件选择
                    file.change(function () {
                        var r = new FileReader();
                        var f = this.files[0];
                        r.readAsDataURL(f);
                        r.onload = function (e) {
                            image.cropper('destroy').attr('src', this.result).cropper(options);
                        };
                    });
                });
            }

        };
        exports('croppers', obj);
    });