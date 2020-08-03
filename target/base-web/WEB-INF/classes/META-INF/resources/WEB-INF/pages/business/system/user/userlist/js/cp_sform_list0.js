cp_sform_list0 = {
	bShowMore : false,
	
	// 只触发一次初始化回调
	initCallBack : function(){
        
    },
    
    /** 提供给外部组件调用触发的方法  **/
    externalCall : function(){
	
    },

    init : function(){    
		
        this.initDefaultData();// 表单内各元素初始化（包含异步、相互调用）
        
        this.initSetting();// 初始化元素插件
		
        this.initEvent();// 初始化各元素事件
        
        //回车事件
		$('#locked,#user_name').keypress(function(e) {
			if (e.which == 13) {
				cp_sform_list0.search();
			}
		});

        
		this.initCallBack();
		this.initCallBack = function (){} //回调后清空
    },
    
    /** 获取默认值，并调用元素初始化 **/
    initDefaultData : function(){
    	//加载配置的默认值
    	
    	//加载之前的查询条件
    	this.loadPreSearchData();
    	//调用元素初始化
        this.initPageAsyncDom();
    },  
    
    /** 加载上一次的查询条件数据（如果有） **/
    loadPreSearchData:function(){
    	var prePageParam=$("#prePageParam").val();
    	if(Utils.isNotNull(prePageParam)){
    		var objPreParam=JSON.parse(prePageParam);
        	if(objPreParam!=null){
        		if(Utils.isNotNull(objPreParam['user_name'])){
        			$('#user_name').val(objPreParam.user_name);
        		}
        		if(Utils.isNotNull(objPreParam['locked'])){
        			$('#locked').val(objPreParam.locked);
        		}
        	}  
    	}    	  	
    },
    
    /** 表单内各元素初始化（包含异步、相互调用）**/
    initPageAsyncDom : function(){
        this.initSelectEdus();
    },

    /** 初始化元素插件 **/
    initSetting : function(){

    },

    /** 初始化各元素事件 **/
    initEvent : function(){
    
    },

    /** 下拉-用户学历 **/
    initSelectEdus : function(){
        var dataEdit =  $('#edu_code').attr('data-edit');
        var dataLoaded = $('#edu_code').attr('data-loaded');
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
                            $('#edu_code').html(html);
                            layui.form.render('select');
                            $('#edu_code').attr('data-loaded', 'true');
                        }
                    } else {
                        layer_error(res.retMsg);
                    }
                }
            });
        } else {
        }
    },

    /** 查询 **/
    search : function(){
		cp_list0.externalCall();

    },

    /** 重置 **/
    resetSearch : function() {
        $('#sform_list0')[0].reset();
        layui.form.render();
        this.search();
    },
   

	
}