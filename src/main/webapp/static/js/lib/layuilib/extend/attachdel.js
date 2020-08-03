/*!
 * 封装附件删除的操作 
 */

layui.define(['jquery', 'layer'], function (exports) {
        var $ = layui.jquery
            , layer = layui.layer;
        
        var obj = {
            render: function (e) {
                var self = this,      
                    currUrl= e.currUrl,//当前点击的附件文件名
                    url = e.url, //服务端的地址
                    eleId = e.eleId, //对应的页面元素id，存放所有附件id的元素
                    fileGroupId = e.fileGroupId, //当前附件的分组id          
                    callback = e.callback;
               
                layer.confirm('确定删除当前文件?', {icon: 3, title:'删除提示'}, function(index){            
                    var formData = new FormData();
                    formData.append('fileName',currUrl);//当前文件名称
                    $.ajax({
                        method: "post",
                        url: url, //用于文件上传的服务器端请求地址
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function (result) {                    
                            if (result.retCode == 0) {//成功
                                
                                //删除页面的元素附件
                                $('#'+fileGroupId).remove();
                                
                                //删除hidden中的值
                                var fileVal=$('#'+eleId).val();
                                if(fileVal===currUrl){//两者相同，则清空            
                                    fileVal='';
                                }else if(fileVal.indexOf(currUrl)==0){//在开头位置           
                                    fileVal=fileVal.replace(currUrl+',','');
                                }else{
                                    fileVal=fileVal.replace(','+currUrl,'');
                                }       
                                $('#'+eleId).val(fileVal);
                                
                                layer.close(index);
                                layer.msg("删除成功",{icon:1});    
                                if($.isFunction(callback)){
                                    callback();
                                }
                            } else{
                                layer.alert(result.retMsg, { icon: 2 });
                            }
                        }
                    });
                });     
            }
        };
        
        
        exports('attachdel', obj);
});