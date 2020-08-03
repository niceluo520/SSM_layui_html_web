package com.cbox.base.cache;

import java.util.ArrayList;
import java.util.List;

public class ProjectCache {

    private volatile static ProjectCache projectCache;// 缓存实例对象
    private volatile static List<String> listCacheAllMenu = new ArrayList<String>();// 缓存所有菜单

    /**
     * 采用单例模式获取缓存对象实例
     */
    public static ProjectCache getInstance() {
        if (null == projectCache) {
            synchronized (ProjectCache.class) {
                if (null == projectCache) {
                    projectCache = new ProjectCache();
                }
            }
        }
        return projectCache;
    }

    public List<String> getListCacheAllMenu() {

        return listCacheAllMenu;
    }

    public void setListCacheAllMenu(List<String> cacheAllMenu) {
        listCacheAllMenu.clear();
        listCacheAllMenu.addAll(cacheAllMenu);
    }

}
