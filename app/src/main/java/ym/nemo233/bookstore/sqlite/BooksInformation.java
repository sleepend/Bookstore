package ym.nemo233.bookstore.sqlite;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class BooksInformation {
    @Id(autoincrement = true)
    private Long _id;
    private String name;
    private String auth;
    private Boolean state; //书籍是否完结
    private Integer chapter;//章节
    private Integer pageNumber;//页码
    private String sourceUrl;//引用网页地址
    private Boolean isCache;//是否缓存整书

    @Generated(hash = 515606093)
    public BooksInformation(Long _id, String name, String auth, Boolean state,
                            Integer chapter, Integer pageNumber, String sourceUrl,
                            Boolean isCache) {
        this._id = _id;
        this.name = name;
        this.auth = auth;
        this.state = state;
        this.chapter = chapter;
        this.pageNumber = pageNumber;
        this.sourceUrl = sourceUrl;
        this.isCache = isCache;
    }

    @Generated(hash = 1358245161)
    @Keep
    public BooksInformation() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuth() {
        return this.auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Boolean getState() {
        return this.state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getChapter() {
        return this.chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Boolean getIsCache() {
        return this.isCache;
    }

    public void setIsCache(Boolean isCache) {
        this.isCache = isCache;
    }
}