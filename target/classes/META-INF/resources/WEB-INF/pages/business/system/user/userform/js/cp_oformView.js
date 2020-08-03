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
							$('#user_id').html(item.user_id);
							$('#user_name').html(item.user_name);
							$('[name="sex"][value="' + item.sex + '"]').show();
							$('#mobile_phone').html(item.mobile_phone);
							$('#email').html(item.email);
							$('#area_code').val(item.area_code);
							$('[name="is_admin"][value="' + item.is_admin + '"]').show();
							$('#is_admin').val(item.is_admin).attr('data-edit', item.is_admin);
							$('[name="locked"][value="' + item.locked + '"]').show();
							$('#locked').val(item.locked).attr('data-edit', item.locked);
							$('#role_id').val(item.role_id).attr('data-edit', item.role_id);
							$('#h_img').val(item.h_img); //图片上传-用户头像-h_img
							$('#person_sign').val(item.person_sign);//文件上传-附件-person_sign

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
		       
        this.initPageAsyncDom();
    },       
    
    /** 【初始化Step2-3】表单内各元素初始化（包含异步、相互调用） **/
    initPageAsyncDom : function(){

		this.initTreeArea_code();  //选择树-籍贯-area_code

		this.initSelectRole_ids(); //下拉框-用户角色-role_id
		this.initImgH_img(); //图片上传-用户头像-h_img

		this.initFilePerson_sign(); //文件上传-附件-person_sign

    },
    
    /** 【初始化】数据加载或特殊动作  Begin**/

	/** 初始化：选择树-籍贯-area_code*/
	initTreeArea_code : function() {
		var dataVal=$('#area_code').val();
		if(Utils.isNotNull(dataVal)){
			$.ajax({
				type : 'post',
				url : basePath + '/dict/tree/getTreeNodeAreaTree',
				success : function(json) {
					//这里兼容dtree的数据结构。关键的key为：code,msg,data。data的格式与dtree返回数据格式一致
					var res=JSON.parse(json);
					if (res.code == 0) {
						var items = res.data;
						var dataVals=dataVal.split(',');
						if(Utils.isNotNull(items)){
							var names = [];
							$.each(items, function(index, item) {
								for(var i=0;i<dataVals.length;i++){
									if(dataVals[i]==item.id){
										names.push(item.title);
										break;
									}
								}
							});
							$('#view_area_code').html(names.join(','));
						}
					} else {
						layer_error(res.msg);
					}
				}
			});
		}
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
							$.each(items, function(index, item) {
								if(dataEdit == item.value){
									html='<label class="layui-form-mid"  >'+ item.title +'</label>';
									return false;
								}
							});
							$('#role_id').html(html);
						}
					} else {
						layer_error(res.retMsg);
					}
				}
			});
		} else {
		}
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
			,viewSource:'view'
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
		//appendHtml+="  <span class='ext-file-delete' data-edit='"+url+"' onclick='cp_oform.deleteFilePerson_sign(this)' > 删除  </span>";
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

}