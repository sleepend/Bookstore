package ym.nemo233.bookstore.sqlite;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class BookcaseClassifyCache {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private Long bsId;
    private String name;
    private String url;

    @Generated(hash = 1987884798)
    @Keep
    public BookcaseClassifyCache(Long id, @NotNull Long bsId, String name,
                                 String url) {
        this.id = id;
        this.bsId = bsId;
        this.name = name;
        this.url = url;
    }

    @Generated(hash = 1619949787)
    @Keep
    public BookcaseClassifyCache() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBsId() {
        return this.bsId;
    }

    public void setBsId(Long bsId) {
        this.bsId = bsId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
