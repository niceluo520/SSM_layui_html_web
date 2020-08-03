/*!
 * mapselector.地图选择器，通过地图选择经纬度坐标。
 * 操作之后，通过done函数回调，调用端在done函数中处理页面逻辑。
 */

layui.define(['jquery', 'layer'], function (exports) {
        var $ = layui.jquery
            , layer = layui.layer;
        
        var obj = {
        	map : null,
     		markersArray : [],
     		mapLocal:null,
     		
            render: function (e) {
                var self = this,
                    selectPointVal = e.selectPointVal,//选中点的经纬度坐标，逗号分隔
                    mapCenter = e.mapCenter, //地图的中心点（省份）
                    done = e.done; //          
                
                var w=($(window).width() - 100);
                if(w>1000){
                    w=1000;
                }
                
                var h = ($(window).height() - 50);
                var h_map=h-180;
                
                /* 加载弹出选择框 */
                var content = 
                    "<div class='layui-fluid'>\n" +
                    "    <div class='layui-row layui-col-space15' style=' margin-top:-10px; '>\n" +
                    "        <div class='layui-col-xs6'>\n" +
                    "            <input class='layui-input ' placeholder='请输入地址搜索或点击地图' id='input_search' type='text'/>\n" +
                    "        </div>\n" +   
                    "        <div class='layui-col-xs6'>\n" +
                    "            <button id='btn_search' class='layui-btn ext-btn-radius'>\n" +                    
                    "            	<i class='layui-icon layui-icon-search layuiadmin-button-btn'></i>\n" +
                    "            </button>\n" +
                    "            <button id='btn_remove' class='layui-btn layui-btn-primary ext-btn-radius'>清除</button>\n" +
                    "        </div>\n" +                
                    "    </div>\n" +
                    "    <div class='layui-row ' style='margin:10px 0px;'>\n" +
                    "        <div id='bmap_show' style='height: "+h_map+"px;' ></div>\n" +
                    "    </div>\n" +
                    "    <div class='layui-row' style='margin-top:15px'>\n" +
                    "        <div class='layui-col-xs12' style='display:flex;'>\n" +
                    "        	<div style='margin-top:9px;'>选中地址：</div>\n" +
                    "        	<div id='select_addr' style='margin-top:9px;'><span style='color:red;'>未选择</span></div>\n" +                  
                    "        	<input type='hidden' id='select_pointval'></input>\n" +
                    "        	<button id='btn_select' class='layui-btn' style='margin-left:50px;' >确认选取</button>\n" +
                    "        </div>\n" +  
                    
                    
                    "    </div>\n" +
                	"</div>";
               
                
                
               
     
                var layerIndex = layer.open({
                    title: "地图定位"
                    , type: 1
                    , content: content
                    , area: [w+'px', h+'px']
                    , success: function () {                    
                    }
                    , cancel: function (index) {
                        layer.close(index);
                    }
                });               
                
                //搜索                
                $("#btn_search").on('click', function () {
                	var local = new BMap.LocalSearch(obj.map, {
        				onSearchComplete: function(){
        					//如果搜索的有结果
        			        if(local.getResults() != undefined) {
        			            if(local.getResults().getPoi(0)) {
        			                point = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
        			                obj.map.centerAndZoom(point, 15);
        			                obj.addMarker(point); // 将标注添加到地图中
        			                
        			                obj.transToAddr(point);
        			            } else {
        			            	layer_error("未匹配到地点!可拖动地图上的红色图标到精确位置");
        			            }
        			        } else {
        			        	layer_error("未找到搜索结果")
        			        }
        				}
        		    });
                	
        			var param = $('#input_search').val();
        		    local.search(param);
                });
                
                //清除
                $("#btn_remove").on('click', function () {
                	$('#select_addr').val('');
                	$('#select_pointval').val('');
                	$('#input_search').val('');
                	$('#select_addr').html('<span style="color:red;">未选择</span>');
        			obj.clearOverlays();
                });
 
                //确定选中
                $("#btn_select").on('click', function () {
                	var addr=$('#select_addr').html();
                	var pointVal=$('#select_pointval').val();
                	if(Utils.isNull(pointVal)){
                		layer_error_shift('您尚未在地图上选点定位！');
                	}else{
                		layer.close(layerIndex);
                		return done(pointVal,addr);
                	}
                });
                
                
                /* 初始化地图 */
                
                this.map = new BMap.Map("bmap_show",{enableMapClick: false});
    			// 添加事件
    			this.map.enableScrollWheelZoom(true);//开启鼠标滚轮缩放
    			this.map.addEventListener('click', function(e){
    				obj.clearOverlays(); 
    				var point = e.point;    				
    				obj.addMarker(point);
    				
    				obj.transToAddr(point);
    			});
    			
    			// 回显
    			if(Utils.isNotNull(selectPointVal)){
    				var points=selectPointVal.split(',');
    				
    				var point = new BMap.Point(points[0], points[1]);
    				obj.map.centerAndZoom(point, 15);    				
    				obj.addMarker(point, obj.map);
    				    				
    				obj.transToAddr(point);
    			} else {
    			    if(Utils.isNotNull(mapCenter)){
                        obj.map.centerAndZoom(mapCenter, 15);
                    }else{
                        var geolocation = new BMap.Geolocation();
                        geolocation.getCurrentPosition(function(r) {
                            if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                                obj.map.centerAndZoom(r.address.province, 15); // 初始化地图位置
                            } else {
                                obj.map.centerAndZoom('福建省', 15);
                            }
                        }, {nableHighAccuracy : true});
                    }
    			}
            },
                        
    		// 地图上标注
    		addMarker : function(point) {
    			var marker = new BMap.Marker(point);
    			this.markersArray.push(marker);
    			this.clearOverlays();
    			this.map.addOverlay(marker);
    		}, 
    		// 把坐标解析成地址，并复制
    		transToAddr : function(point) {
    			var geoc = new BMap.Geocoder(); //逆地址解析器
				geoc.getLocation(point, function(rs){
					var addComp = rs.addressComponents;
					var Adresss = addComp.province  + addComp.city  + addComp.district  + addComp.street  + addComp.streetNumber;
					
					$('#select_addr').html(Adresss);
    				$('#select_pointval').val(point.lng+","+point.lat);
				});
    		},
    		// 清除标注
    		clearOverlays : function() {
    			if (this.markersArray) {
    				for (i in this.markersArray) {
    					this.map.removeOverlay(this.markersArray[i]);
    				}
    			}
    		}

        };
        
        
        exports('mapselector', obj);
});