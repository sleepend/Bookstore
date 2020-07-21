package ym.nemo233.bookstore.sqlite;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class HistoryQuery {
    @Id(autoincrement = true)
    private Long id;
    private String searchKey; //关键字
    private Long stamp;//时间戳&显示排序

    @Generated(hash = 2084435783)
    public HistoryQuery(Long id, String searchKey, Long stamp) {
        this.id = id;
        this.searchKey = searchKey;
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

    public Long getStamp() {
        return this.stamp;
    }

    public void setStamp(Long stamp) {
        this.stamp = stamp;
    }

}
