package ym.nemo233.bookstore.sqlite;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Chapter {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private Long biId;
    private String tag;
    private String name;
    private String url;
    private String content;

    @Generated(hash = 393170288)
    @Keep
    public Chapter() {
    }

    @Generated(hash = 32395797)
    @Keep
    public Chapter(Long id, @NotNull Long biId, String tag, String name, String url,
                   String content) {
        this.id = id;
        this.biId = biId;
        this.tag = tag;
        this.name = name;
        this.url = url;
        this.content = content;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getBiId() {
        return this.biId;
    }

    public void setBiId(Long biId) {
        this.biId = biId;
    }


}
