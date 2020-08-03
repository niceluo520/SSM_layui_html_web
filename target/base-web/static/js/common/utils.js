var Utils = {
	/** 判断是否为空 */
    isNotNull : function(str){
        if(typeof(str) != "undefined" && null != str && "" != str && "null" != str){
            return true;
        }else{
            return false;
        }
    },
    isNull : function(str){
        return !this.isNotNull(str);
    },
    
    /** cache操作 */
    setCache : function(key,str){
    	var storage=window.localStorage;
    	storage.setItem(key,str);
    },
    getCache : function(key){
    	var storage=window.localStorage;
    	return storage.getItem(key);
    },
    delCache : function(key){
    	var storage=window.localStorage;
    	storage.removeItem(key);
    }
}