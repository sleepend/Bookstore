package ym.nemo233.bookstore.sqlite;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class HistoryQuery {
    @Id(autoincrement = true)
    private Long id;
    private String searchKey; //关键字
    private String site;//站点&解析方式
    private String url;//指向地址
    private Integer sort;//结果顺序
    private Long stamp;//时间戳&显示排序

    @Generated(hash = 1565962885)
    public HistoryQuery(Long id, String searchKey, String site, String url,
                        Integer sort, Long stamp) {
        this.id = id;
        this.searchKey = searchKey;
        this.site = site;
        this.url = url;
        this.sort = sort;
        this.stamp = stamp;
    }

    @Generated(hash = 242971371)
    public HistoryQuery() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getStamp() {
        return this.stamp;
    }

    public void setStamp(Long stamp) {
        this.stamp = stamp;
    }

}
