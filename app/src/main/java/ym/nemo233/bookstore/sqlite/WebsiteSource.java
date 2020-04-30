package ym.nemo233.bookstore.sqlite;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class WebsiteSource {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String website;
    private Boolean selected;

    @Generated(hash = 1154167730)
    @Keep
    public WebsiteSource(Long id, String name, String website, Boolean selected) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.selected = selected;
    }

    @Generated(hash = 2022254440)
    @Keep
    public WebsiteSource() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getSelected() {
        return this.selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
