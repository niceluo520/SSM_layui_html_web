/*!
 * 图片查看器，支持查看上一张、下一张，前移、后移，以及删除功能
 * 操作之后，通过done函数回调，调用端在done函数中处理页面逻辑。
 */

layui.define(['jquery', 'layer'], function (exports) {
        var $ = layui.jquery
            , layer = layui.layer;
        
        var obj = {
            render: function (e) {
                var self = this,      
                    showBasePath = e.showBasePath,//图片展示的路径前缀
                    imgUrlArray = e.imgUrlArray,//所有的图片url，传入数组
                    currUrl= e.currUrl,//当前点击的图片url
                    url = e.url, //服务端的地址
                    viewSource = e.viewSource,//来源：edit(默认)/view，为view时必传
                    done = e.done; //返回数据为imgUrlArray，图片名数组                
                
                var content = 
                    "<div class='layui-fluid showImgEdit'>\n" +
                    "    <div class='layui-row layui-col-space15' style=' margin-top:5px; '>\n" +
                    "        <div class='layui-col-xs12'>\n" +
                    "            <div class='readyimg' style='height:350px;text-align:center;'>\n" +
                    "                <img src='"+showBasePath+currUrl+"' style='height:100%;'>\n" +
                    "                <div id='readyimg_desc' style='height:100%;display:none;'>当前没有图片了！</div>\n" +
                    "            </div>\n" +
                    "        </div>\n" +                
                    "    </div>\n" +
                    "    <div class='layui-row layui-col-space15'>\n" +
                    "        <div style='text-align:center;'>\n" +
                    "             <button type='button' class='layui-btn' imgview-event='previous' style='font-size:12px !important;'>上一张</button>\n" +
                    "             <button type='button' class='layui-btn' imgview-event='next' style='font-size:12px !important;'>下一张</button>\n" +
                    "             <button type='button' class='layui-btn' imgview-event='move_previous' style='font-size:12px !important;'>往前移动</button>\n" +
                    "             <button type='button' class='layui-btn' imgview-event='move_next' style='font-size:12px !important;'>往后移动</button>\n" ;
                    
                    if(viewSource==='view'){                    	
                    }else{
                    	content=content+"             <button type='button' class='layui-btn layui-bg-red' imgview-event='delete' style='font-size:12px !important;'>删除图片</button>\n";
                    }
                    
                    content=content+"        </div>\n" +
                    "    </div>\n" +
                    "\n" +
                    "</div>";
               
     
                var layerIndex = layer.open({
                    title: "图片浏览"
                    , type: 1
                    , content: content
                    , area: ['700px', '500px']
                    , success: function () {                    
                    }
                    , cancel: function (index) {
                        layer.close(index);
                    }
                });
                
                if(imgUrlArray!=null && imgUrlArray.length==1){
                    //只有一张图片，则disable前面四个按钮
                    $(".layui-btn[imgview-event='previous']").addClass('ext-btn-disable'); 
                    $(".layui-btn[imgview-event='next']").addClass('ext-btn-disable'); 
                    $(".layui-btn[imgview-event='move_previous']").addClass('ext-btn-disable'); 
                    $(".layui-btn[imgview-event='move_next']").addClass('ext-btn-disable'); 
                }
                
                $(".layui-btn").on('click', function () {
                    var event = $(this).attr("imgview-event");
                    
                    if(imgUrlArray==null || imgUrlArray.length==0){
                        //传入数组为空
                        $(".showImgEdit .readyimg img").hide();
                        $("#readyimg_desc").show();
                        
                    }else{
                        /* 处理不同的监听事件 */
                        
                        var iIndex=0;
                        for(var i=0;i<imgUrlArray.length;i++){
                            if(imgUrlArray[i]===currUrl){
                                iIndex=i;
                            }
                        }                       
                        
                        if (event === 'previous') {                        
                            //上一张
                            if(iIndex==0){
                                layer.msg("已经是第一张了",{icon:5,time:1000});
                            }else{
                                currUrl=imgUrlArray[iIndex-1];
                                $(".showImgEdit .readyimg img").attr('src', showBasePath+currUrl);
                            }
                            
                        } else if (event === 'next') {
                            //下一张
                            if(iIndex==imgUrlArray.length-1){
                                layer.msg("已经是最后一张了",{icon:5,time:1000});
                            }else{
                                currUrl=imgUrlArray[iIndex+1];
                                $(".showImgEdit .readyimg img").attr('src', showBasePath+currUrl);
                            }
                        } else if (event === 'move_previous') {
                            //往前移动
                            if(iIndex==0){
                                layer.msg("已经是第一张了",{icon:5,time:1000});
                            }else{
                                imgUrlArray[iIndex]=imgUrlArray[iIndex-1];
                                imgUrlArray[iIndex-1]=currUrl;
                                
                                layer.msg("当前图片前移一位",{icon:1});
                                return done(imgUrlArray);
                            }
                        } else if (event === 'move_next') {
                            //往后移动
                            if(iIndex==imgUrlArray.length-1){
                                layer.msg("已经是最后一张了",{icon:5,time:1000});
                            }else{
                                imgUrlArray[iIndex]=imgUrlArray[iIndex+1];
                                imgUrlArray[iIndex+1]=currUrl;
                                layer.msg("当前图片后移一位",{icon:1});
                                
                                return done(imgUrlArray);
                            }
                        } else if (event === 'delete') {
                            //删除
                            layer.confirm('确定删除当前图片?', {icon: 3, title:'删除提示'}, function(index){
                                
                                var formData = new FormData();
                                formData.append('fileName',currUrl);//当前图片名称
                                $.ajax({
                                    method: "post",
                                    url: url, //用于文件上传的服务器端请求地址
                                    data: formData,
                                    processData: false,
                                    contentType: false,
                                    success: function (result) {
                                        //保存图片返回表示
                                        if (result.retCode == 0) {//成功
                                            imgUrlArray.splice(iIndex,1); //从数组中删除当前值
                                            if(imgUrlArray.length==0 || iIndex==imgUrlArray.length){
                                                iIndex=iIndex-1;
                                            }
                                            
                                            if(iIndex>-1){
                                                currUrl=imgUrlArray[iIndex];
                                                $(".showImgEdit .readyimg img").attr('src', showBasePath+currUrl);
                                            }else{
                                                $(".showImgEdit .readyimg img").hide();
                                                $("#readyimg_desc").show();
                                            }
                                            
                                            layer.close(index);
                                            layer.msg("删除成功",{icon:1});
                                            
                                            //图片全部都删除时，自动关闭浏览弹窗
                                            if(iIndex<0){
                                                layer.close(layerIndex);
                                            }
                                            
                                            return done(imgUrlArray);                                            
                                        } else{
                                            layer.alert(result.retMsg, { icon: 2 });
                                        }
                                    }
                                });
                            });                         
                        }
                    }
                    
                });
            }

        };
        
        
        exports('imgviewer', obj);
});