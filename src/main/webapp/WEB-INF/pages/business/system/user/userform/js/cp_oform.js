cp_oform = {
    rec_id : '',//表单页面主键，用来获取表单数据，以及判断新增or修改操作
    
	/** 只触发一次初始化回调，回调内容可以在page中重置，以达到组件间的顺序调用 **/
	initCallBack : function(){        
    },
    
    /** 提供给外部组件调用触发的方法  **/
    externalCall : function(){
    	var data={};
	
		rec_id=data.rec_id;
		this.initFormData();
    },
    
	/** 表单初始化总入口 **/
    init : function(){
		this.rec_id = recId;


		//【初始化Step0】初始化各元素事件
        this.initEvent();

        //【初始化Step1】初始化元素设置
        this.initSetting();
        
        //【初始化Step2】初始化表单
    	if(Utils.isNotNull(this.rec_id)){            
            this.initFormData();// step2-1 获取表单数据
        } else {            
            this.initDefaultData();// step2-2 获取表单默认值
        }      
        
		//【初始化Step3】初始化完成后触发回调，并清空回调
        this.initCallBack();
        this.initCallBack = function (){} //回调后清空
    },
    
    /** 【初始化Step0】初始化各元素事件 **/
    initEvent : function(){    	 		
		
    },
    
    /** 【初始化Step1】初始化元素设置 **/
    initSetting : function(){

		// 图片上传-用户头像-h_img
		var element = layui.element
			,layer=layui.layer
			,upload = layui.upload;

		var uploadRender =upload.render({
			elem: '.ext-uploadkey-h_img' //关联样式，支持多个元素触发
			,accept:'images' //指定允许上传时校验的文件类型：图片
			,acceptMime:'.jpg,image/bmp' //规定打开文件选择框时，筛选出的文件类型。jpg的要特殊处理成.jpg，不然不生效
			,exts:'jpg|bmp' //允许上传的文件后缀
			,auto:false
			,choose: function(obj){
				var currImgId=this.imgId;//this.imgId从html的lay-data中获取
				obj.preview(function(index, file, result){
					cp_oform.croppers_h_img(result,currImgId);
				});
				//解决choose只触发一次的问题
				uploadRender.config.elem.next()[0].value = '';
			}
		});

		// 文件上传-附件-person_sign
		var element = layui.element
			,layer=layui.layer
			,upload = layui.upload;

		var uploadRender =upload.render({
			elem: '#fileUpload_person_sign'
			,accept:'file'
			,url: basePath +'/attach/upload'
			,auto: false //为进行总数量控制，只能屏蔽自动提交，采用绑定提交的方式
			,bindAction: '#btnUpload_person_sign'
			,choose: function(obj){
				var files= this.files = obj.pushFile(); //将每次选择的文件追加到文件队列

				/* 限制上传图片总数的逻辑控制 */
				//获取页面已经选择的文件数量
				var currFileCount=0;
				var fileVal=$('#person_sign').val();
				if(Utils.isNotNull(fileVal)){
					currFileCount=fileVal.split(',').length;
				}

				//获取当前选择的文件数量
				var upFileCount=Object.getOwnPropertyNames(files).length;
				if((currFileCount+upFileCount)>2){
					//清空当前选择的队列
					for(var key in files){
						delete files[key];
					}
					layer_error_shift('同时最多只能上传的数量为：2');
					return false;
				}else{
					$('#btnUpload_person_sign').click();//直接触发上传的按钮
				}
			}
			,done: function (res, index, upload) { //当文件全部被提交后，才触发
				if(res.retCode==0){
					cp_oform.showFilePerson_sign(res.retData,true);  //调用附件展示方法
				}
				delete this.files[index]; //不管成功与否，都删除已提交上传的文件队列
			}
		});

    },
    
    /** 【初始化Step2-1】获取表单数据 **/
    initFormData : function(){
        if(Utils.isNotNull(cp_oform.rec_id)){
            $.ajax({
                type : "post",
                url : basePath + '/system/userform/loadFormOform',
                data : {rec_id : cp_oform.rec_id},
                success : function(res) {
                    if (res.retCode == 0) {
                        var item = res.retData;
                        if(Utils.isNotNull(item)){
							$('#user_id').val(item.user_id);
							$('#user_name').val(item.user_name);
							$('[name="sex"][value="' + item.sex + '"]').prop("checked", "checked");
							$('#mobile_phone').val(item.mobile_phone);
							$('#email').val(item.email);
							$('#area_code').val(item.area_code);
							$('#is_admin').val(item.is_admin).attr('data-edit', item.is_admin);
							$('#locked').val(item.locked).attr('data-edit', item.locked);
							$('#role_id').val(item.role_id).attr('data-edit', item.role_id);
							$('#h_img').val(item.h_img); //图片上传-用户头像-h_img
							$('#person_sign').val(item.person_sign);//文件上传-附件-person_sign
							$('#education').val(item.education).attr('data-edit', item.education);
							$('#profession').val(item.profession);

							layui.form.render();
                        }
                        cp_oform.initPageAsyncDom();
                    } else {
                        layer.msg(res.retMsg, {icon : 2, time : 2000 });
                    }
                }
            });
        }
    },
    
    /** 【初始化Step2-2】获取表单默认值 **/
    initDefaultData : function(){
		$('#sex').val('男').attr('data-edit', '男');
		$('#imgShow_h_img').parent().hide();
		$('#imgUpload_h_img').show();
		$('#imgUpload_h_img').attr('src',basePath + '/static/images/nopic_01.png');
		       
        this.initPageAsyncDom();
    },       
    
    /** 【初始化Step2-3】表单内各元素初始化（包含异步、相互调用） **/
    initPageAsyncDom : function(){

		this.initTreeArea_code();  //选择树-籍贯-area_code

		this.initSelectRole_ids(); //下拉框-用户角色-role_id
		this.initImgH_img(); //图片上传-用户头像-h_img

		this.initFilePerson_sign(); //文件上传-附件-person_sign

        this.initSelectEdus();
    },
    
    /** 【初始化】数据加载或特殊动作  Begin**/

	/** 初始化：选择树-籍贯-area_code*/
	initTreeArea_code : function() {
		var dtree=layui.dtree;
		bShowAjaxLoading=false;//dtree默认有loading，这里把全局的加载屏蔽
		var DTreeNode=dtree.renderSelect({
			width: '100%', // 可以在这里指定树的宽度来填满div
			selectTips: '请选择籍贯',
			elem: '#show_area_code',
			url : basePath + '/dict/tree/getTreeNodeAreaTree',
			dataStyle: 'layuiStyle',  //使用layui风格的数据格式
			dataFormat: 'list',  //配置data的风格为list
			response: {statusCode: 0}, //默认成功的状态码是200
			done:function(data,obj){
				//设置默认值
				var defaultVal=$('#area_code').val();
				if(Utils.isNotNull(defaultVal)){
					dtree.selectVal('show_area_code', defaultVal);
				}
				bShowAjaxLoading=true;
			}
		});
		// 绑定节点的单击
		dtree.on("node('show_area_code')", function(obj){
			var basicData = obj.param.basicData;
			$('#area_code').val(basicData.code);
		});
	},
	/** 下拉-用户角色 **/
	initSelectRole_ids : function(){
		var dataEdit =  $('#role_id').attr('data-edit');
		var dataLoaded = $('#role_id').attr('data-loaded');
		if(Utils.isNull(dataLoaded) || dataLoaded !== 'true'){
			$.ajax({
				type : 'post',
				url : basePath + '/dict/select/getOptionUserRole',
				data : {},
				success : function(res) {
					if (res.retCode == 0) {
						var items = res.retData;
						if(Utils.isNotNull(items)){
							var html = '';
							html = '<option value="">请选择</option>';
							$.each(items, function(index, item) {
								var selected = '';
								if(dataEdit == item.value){
									selected = 'selected = "selected"';
								}
								html += '<option ' + selected + ' value="' + item.value + '">' + item.title + '</option>';
							});
							$('#role_id').html(html);
							layui.form.render('select');
							$('#role_id').attr('data-loaded', 'true');
						}
					} else {
						layer_error(res.retMsg);
					}
				}
			});
		} else {
		}
	},
	/** 下拉-用户学历 **/
    initSelectEdus : function(){
        var dataEdit =  $('#education').attr('data-edit');
        var dataLoaded = $('#education').attr('data-loaded');
        if(Utils.isNull(dataLoaded) || dataLoaded !== 'true'){
            $.ajax({
                type : 'post',
                url : basePath + '/dict/select/getOptionUserEdu',
                data : {},
                success : function(res) {
                    if (res.retCode == 0) {
                        var items = res.retData;
                        if(Utils.isNotNull(items)){
                            var html = '';
                            html = '<option value="">请选择</option>';
                            $.each(items, function(index, item) {
                                var selected = '';
                                if(dataEdit == item.value){
                                    selected = 'selected = "selected"';
                                }
                                html += '<option ' + selected + ' value="' + item.value + '">' + item.title + '</option>';
                            });
                            $('#education').html(html);
                            layui.form.render('select');
                            $('#education').attr('data-loaded', 'true');
                        }
                    } else {
                        layer_error(res.retMsg);
                    }
                }
            });
        } else {
        }
    },

	/** 裁剪图片 - 用户头像
	* @param imgId为<img>对应的id
	*/
	doCroppers_h_img : function(imgId){
		var src = $('#'+imgId).attr('src');
		cp_oform.croppers_h_img(src,imgId);
	},

	/** 裁剪图片 - 用户头像
	* @param fullurl为完整图片路径，imgId为<img>对应的id
	*/
	croppers_h_img : function(fullurl,imgId){
		var croppers = layui.croppers;
		var fileName;
		if(Utils.isNotNull(imgId)){
			fileName = $('#'+imgId).attr('data-edit');
		}
		croppers.render({
			area: ['700px', '500px']  //弹窗宽度
			,imgUrl: fullurl
			,saveW:150     //保存宽度
			,saveH:150     //保存宽度
			,mark:3/4   //裁剪框比例
			,imgFileName:fileName   //图片名称，为空则为新增
			,url: basePath +'/attach/upload' //图片上传接口
			,done: function (url) { //上传完毕回调，只有成功才会返回这里
				$('#'+imgId).attr('src',fileShowPath +url);
				$('#'+imgId).attr('data-edit',url);//通过属性data-edit来传递图片名
				$('#h_img').val(url);

				//show展示图片div，hide上传的按钮
				$('#'+imgId).parent().show();
				$('#imgUpload_h_img').hide();
			}
		});
	},


	/** 初始化图片展示-图片上传-用户头像-h_img**/
	initImgH_img : function() {
		var imageVal=$('#h_img').val();
		if(Utils.isNotNull(imageVal)){
			$('#imgShow_h_img').attr('src', fileShowPath + imageVal);
			$('#imgShow_h_img').attr('data-edit', imageVal);
			$('#imgShow_h_img').parent().show();
			$('#imgUpload_h_img').hide();
		}
	},
	/** 查看及删除图片 - 用户头像
	*  @param obj  img的对象引用，通过 $(obj).attr('') 来获取属性值
	*/
	imgviewer_h_img : function(obj){
		var imgviewer=layui.imgviewer;
		var currSrc= $(obj).attr('data-edit');

		//获取所有的图片链接url
		var imgUrlArray=[];
		var imageVal=$('#h_img').val();
		if(Utils.isNotNull(imageVal)){
			imgUrlArray=imageVal.split(',');
		}

		imgviewer.render({
			showBasePath: fileShowPath
			,imgUrlArray: imgUrlArray
			,currUrl:currSrc
			,url: basePath +'/attach/delete' //图片删除接口
			,done: function(imgUrlArray){
				if(imgUrlArray==null || imgUrlArray.length==0){
					$('#h_img').val('');
					$('#h_img').attr('data-edit','');
					$('#imgShow_h_img').parent().hide();
					$('#imgUpload_h_img').show();
				}
			}
		});
	},


	/** 初始化附件展示：文件上传-附件-person_sign */
	initFilePerson_sign :function(){
		var fileVal=$('#person_sign').val();
		if(Utils.isNotNull(fileVal)){
			var fileUrlArray=fileVal.split(',');
			for(var i=0;i<fileUrlArray.length;i++){
				var url=fileUrlArray[i];
				var formData = new FormData();
				formData.append('fileName',url);
				$.ajax({
					method: 'post',
					url: basePath+'/attach/info',
					data: formData,
					processData: false,
					contentType: false,
					success: function (result) {
						if (result.retCode == 0) {
							cp_oform.showFilePerson_sign(result.retData,false);  //调用附件展示方法
						}
					}
				});
			}
		}
	},

	/** 单个附件展示方法
	* @param retData 单个附件的相关数据；bAssign true-需要赋值，否则不需要赋值
	*/
	showFilePerson_sign :function(retData,bAssign){
		var url=retData.src;
		var fileSuffix=retData.fileSuffix;
		var orgFileName=retData.orgFileName;
		var urlKey=url.replace(/[.]/g,'');//全部替换掉.，不然jquery会取不到

		//拼接新增的文件html
		var appendHtml="<div class='ext-file-showgroup' id='group_"+urlKey+"'>";
		appendHtml+="  <span class='formatmin " + fileSuffix + "'></span>";
		appendHtml+="  <span class='ext-file-show' > "+orgFileName +" </span>";
		appendHtml+="  <a class='ext-file-btn' href='"+fileDownloadPath+url+"'> 下载  </a>";
		appendHtml+="  <span class='ext-file-delete' data-edit='"+url+"' onclick='cp_oform.deleteFilePerson_sign(this)' > 删除  </span>";
		appendHtml+="</div>";
		$('#fileShow_person_sign').append(appendHtml);

		//赋值
		if(bAssign){
			var fileVal=$('#person_sign').val();
			if(Utils.isNull(fileVal)){
				$('#person_sign').val(url);
			}else{
				$('#person_sign').val(fileVal+','+url);
			}
		}
	},

	/** 删除单个附件方法
	* @param obj 删除的按钮对象
	*/
	deleteFilePerson_sign :function(obj){
		var currUrl=$(obj).attr('data-edit');
		var fileGroupId='group_'+currUrl.replace(/[.]/g,'');
		var attachdel=layui.attachdel;
		attachdel.render({
			currUrl: currUrl
			,eleId: 'person_sign'
			,fileGroupId: fileGroupId
			,url: basePath+'/attach/delete'
		});
	},

	/** 【初始化】数据加载或特殊动作 End **/

	/** 表单提交 **/
    submit : function(){
        var url = "";
        if(Utils.isNull(cp_oform.rec_id)){
            url = basePath + "/system/userform/doAddOform";
        }else{
            url = basePath + "/system/userform/doEditOform";
        }
        
        //表单提交的方法、比如ajax提交
        $.ajax({
            type : "post",
            traditional : true,
            url : url,
            data : $.param({"rec_id" : cp_oform.rec_id}) + "&" + $('#oform').serialize(),
            success : function(res) {
            	if (res.retCode == 0) {           
            		
            		if(Utils.isNotNull(sourceDomForPopup)){
            			//不为空说明当前是弹出页面
            			sourceDomForPopup.layerCallBack(); //调用上一个页面的方法
            		}else{
            			//为空则需要追踪当前页面的来源
            			var hasPrePage=$("#hasPrePage").val();
            			if(hasPrePage == 'true'){            				
            				var preUrl=document.referrer;
            				var param=encodeURI($("#prePageParam").val());
            				var splitFlag="?";            				
            				if(preUrl.indexOf("?")>-1){
            					splitFlag="&";
            				}
            				
            				preUrl=preUrl+splitFlag+"prePageParam="+param;
            				window.location=preUrl;
            			}else{
            				window.location.reload();
            			}
                    }            		
            		
            		layer_success(res.retMsg);
            	} else {
            		layer_alert("操作失败，原因：" + res.retMsg + "<br>" + res.retData);
            	}
            }
        });
    },
    
    /** 重置表单 **/
    reset : function(){
    	$('#cp_oform')[0].reset();
    	layui.form.render();
    },

    /** 返回 **/
    goback : function(){
        if(Utils.isNotNull(sourceDomForPopup)){
        	//不为空说明当前是弹出页面
        	sourceDomForPopup.layerCallBackCancel();
        }else{
        	var hasPrePage=$("#hasPrePage").val();
			if(hasPrePage == 'true'){
				history.go(-1);
			}else{
				layer_alert("当前已经是第一个页面不能再返回了");
			}            
        }
    }
}