package com.cbox.base.cache;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.cbox.constant.SysConstant;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @ClassName: EhcacheUtil
 * @Function: ehcache的工具类，通过这个类操作ehcache缓存
 * 
 * @author cbox
 * @date 2020年3月10日 下午7:50:52
 * @version 1.0
 */
public class EhcacheUtil {

    private static EhcacheUtil ehcacheUtil;
    private static CacheManager manager;

    public static EhcacheUtil getInstance() {
        if (manager == null) {
            ApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
            manager = ((EhCacheCacheManager) wac.getBean("cacheManager")).getCacheManager();
        }
        if (ehcacheUtil == null) {
            ehcacheUtil = new EhcacheUtil();
        }
        return ehcacheUtil;
    }

    /**
     * 在已知cache中添加键值对（element级别上设置过期时间） set:
     *
     * @date: 2018年2月23日 下午3:52:29
     * @author cbox
     * @param key
     * @param value
     * @param time 秒
     */
    public void set(String key, Object value, int time) {
        Cache cache = manager.getCache(SysConstant.EHCACHE_NAME);
        Element element = new Element(key, value);
        element.setEternal(false);
        element.setTimeToLive(time);// 设置过期时间

        cache.put(element);
    }

    /**
     * 更新过期时间
     *
     * @date: 2018年2月23日 下午3:52:29
     * @author cbox
     * @param key
     * @param time 秒
     */
    public void set(String key, int time) {
        Cache cache = manager.getCache(SysConstant.EHCACHE_NAME);
        Element element = cache.get(key);
        if (null != element) {
            element.setEternal(false);
            element.setTimeToLive(time);
            cache.put(element);
        }
    }

    /**
     * 获得已知cache的键值对
     * 
     * @param cacheName cache名称
     * @param key 键
     * @return
     */
    public Object get(String key) {
        Cache cache = manager.getCache(SysConstant.EHCACHE_NAME);
        Element element = cache.get(key);
        return element == null ? null : element.getObjectValue();
    }

    /**
     * 移除已知cache的键值对
     * 
     * @param cacheName
     * @param key
     */
    public void remove(String key) {
        Cache cache = manager.getCache(SysConstant.EHCACHE_NAME);
        cache.remove(key);
    }

    /**
     * checkValid:检查指定数据是否有效（未过期）
     *
     * @date: 2020年3月10日 下午8:48:17
     * @author cbox
     * @param key
     * @return
     */
    public boolean checkValid(String key) {
        Cache cache = manager.getCache(SysConstant.EHCACHE_NAME);
        if (cache.getKeysWithExpiryCheck().contains(key)) {
            return true;
        } else {
            return false;
        }
    }

}
