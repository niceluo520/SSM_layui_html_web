cp_list0 = {	

    tableIns : null,
    whereParam : null,
   
	/** 只触发一次初始化回调，回调内容可以在page中重置，以达到组件间的顺序调用 **/
	initCallBack : function(){        
    },
    
    /** 提供给外部组件调用触发的方法  **/
    externalCall : function(){
    	var data={};
		var formdata = $('#sform_list0').serializeArray();
		$.each(formdata,function(index, obj){
			data[obj.name] = obj.value;
		});
	
		this.loadListData(data);
    },

    // 列表初始化
    init : function(){
    	
		this.externalCall();
		
		this.initBtnEvent();

		this.initCallBack();
		this.initCallBack = function (){} //回调后清空
    },

    /** 加载列表数据 */
    loadListData : function(whereParam){
    	if(Utils.isNull(whereParam)){
    		whereParam={};
    	}else{
    		this.whereParam=whereParam;
    	}
    	
    	if(this.tableIns!=null){
    		this.tableIns.reload({
    			where : whereParam
    			,page: {
    				curr: 1 //重新从第 1 页开始
  				}
			});
			return;
    	}    	   	
        
  		var table = layui.table;  
		this.tableIns=table.render({
			elem: '#table_list0'
			,url:basePath+'/system/userlist/loadListList0'
			,method: 'post'
			,cellMinWidth: 50 //全局定义常规单元格的最小宽度，默认为60
			,toolbar: '#topbarTpl_list0' //开启头部工具栏，并为其绑定模板
			,page: true //开启分页
			,where : whereParam
			,cols:[[ //标题栏
				{type:'checkbox',fixed: 'left'} //单选
				,{field:'user_id',title:'用户ID', sort: true}
				,{field:'user_name',title:'姓名', sort: true}
                ,{field:'mobile_phone',title:'手机号码', sort: true}
				,{field:'sex',title:'性别',width:60,"templet":"#sexTpl"}
				,{field:'userbirthplace',title:'籍贯'}
				,{field:'edu_name',title:'学历'}
				,{field:'profession',title:'职业'}
				,{field:'_locked',width:60,title:'状态'}
                ,{field:'rec_time',title:'创建时间',width:160, sort: true}
				,{ title:'操作', toolbar: '#colbarTpl_list0' ,width:132}  //行尾操作列
			]]
			,done: function(res, curr, count){ //数据渲染完的回调
				//TODO..
				checkBtnPermission();//	按钮权限校验
			}
			,parseData: function(res){ //res 即为原始返回的数据
				return {
					"code": res.retCode, //解析接口状态
					"msg": res.retMsg, //解析提示文本
					"count": res.retData.total, //解析数据长度
					"data": res.retData.items //解析数据列表
				};
			}
		});
		
    },
    
    /** 初始化按钮监听事件  */
    initBtnEvent : function(){
    	layui.use('table', function(){
  			var table = layui.table;
  
			// 头部按钮监听事件
			table.on('toolbar(table_list0)', function(obj){
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				switch(obj.event){
					case 'add':
						cp_list0.add(data);
						break;
					case 'del':
						if(data.length==0){
							layer_error_shift('请选择一条记录！');
						}else if(data.length>1){
							layer_error_shift('请只选择一条记录！');
						}else{
							cp_list0.del(data);
						}
						break;
				};
			});

			// 行尾按钮监听事件
			table.on('tool(table_list0)', function(obj){
				var data = obj.data;
				switch(obj.event){
					case 'update':
						cp_list0.update(data);
						break;
					case 'view':
						cp_list0.view(data);
						break;
				};
			});

		});
    },

	/****** 列表上按钮动作  begin ******/

	// 从选中的data中获取id。@param data 选中的所有数据
	getIds : function(data){
		var idstr='';
		if(data instanceof Array){
			var ids=[];
			for (var iIndex in data) {
				ids.push(data[iIndex].rec_id);
			}
			idstr = ids.join(',');
		}else if(data instanceof Object){
			idstr = data.rec_id;
		}
		return idstr;
	},
	/****************** 头部按钮方法  Begin***************/

	// 新增
	add : function(data){
		var url = basePath + '/system/userlist/toAddList0';
		var param=encodeURI(JSON.stringify(cp_list0.whereParam));
		url=url + "?prePageParam="+param+"&hasPrePage=true";
		window.location = url;
	},

	// 删除
	del : function(data){
			layer.confirm('您确定删除选中的'+data.length+'条记录吗', {
				icon : 3,
				title : '删除确认'
			}, function(index) {
				cp_list0.delCommit(data);
				layer.close(index);
			});
	},

	// 删除提交方法
	delCommit : function(data){
		var url = basePath + '/system/userlist/toDeleteList0';
		var ids=this.getIds(data);
		$.ajax({
			type : 'post',
			url : url,
			data : {
				rec_id : ids
			},
			success : function(res) {
				if (res.retCode == 0) {
					layer_success('删除成功！');
					cp_list0.loadListData(cp_list0.whereParam);
				} else {
					layer_error_offset('删除失败：'+res.retMsg);
				}
			}
		});
	},
	/****************** 头部按钮方法  End***************/

	/****************** 行尾按钮方法  Begin***************/

	// 修改
	update : function(data){
		var url = basePath + '/system/userlist/toUpdateList0';
		var param=encodeURI(JSON.stringify(cp_list0.whereParam));
		url=url + "?pageRecId=" + data.rec_id+"&prePageParam="+param+"&hasPrePage=true";
		
		window.location = url ;
//		popupIndex = layer_show_noBtn(url, cp_list0,"修改");
	},

	// 查看
	view : function(data){
		var url = basePath + '/system/userlist/toViewList0';
		url += "?pageRecId=" + data.rec_id;
		popupIndex = layer_show_noBtn(url, cp_list0,"查看");
	},
	/****************** 行尾按钮方法  End***************/

	/****** 列表上按钮动作  end ******/
	
    /** 弹出页的保存回调 **/
    layerCallBack : function() {
        cp_list0.loadListData(); //刷新列表
        layer_close(popupIndex); //关闭弹窗

    },

    /** 弹出页的返回回调 **/
    layerCallBackCancel : function() {
        layer_close(popupIndex);
    },
    
}

