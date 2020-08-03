package com.cbox.business.common.page.bean;

/**
 * @ClassName: PageLayuiVO
 * @Function: layui列表的分页参数
 * 
 * @author cbox
 * @date 2019年11月3日 上午10:10:18
 * @version 1.0
 */
public class PageLayuiVO {

    private int page = 1;// 起始页
    private int limit = 12;// 单页显示的数据的条数

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
