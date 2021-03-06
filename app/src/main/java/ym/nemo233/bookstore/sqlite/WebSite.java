package ym.nemo233.bookstore.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

@Entity
public class WebSite implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String url;
    private String decode;//解码方式
    private Integer delayMill;//延迟
    private Integer isDefault;
    private String searchUrl;
    private String hotUrl;
    private Long parent;

    @Transient
    public List<BookInformation> books;

    @Generated(hash = 1521659554)
    public WebSite(Long id, String name, String url, String decode, Integer delayMill,
                   Integer isDefault, String searchUrl, String hotUrl, Long parent) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.decode = decode;
        this.delayMill = delayMill;
        this.isDefault = isDefault;
        this.searchUrl = searchUrl;
        this.hotUrl = hotUrl;
        this.parent = parent;
    }

    @Generated(hash = 121794805)
    public WebSite() {
    }

    /**
     * 历史记录-临时数据
     *
     * @param name
     */
    public WebSite(String name) {
        this.id = -1L;
        this.name = name;
    }

    protected WebSite(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        url = in.readString();
        decode = in.readString();
        if (in.readByte() == 0) {
            delayMill = null;
        } else {
            delayMill = in.readInt();
        }
        if (in.readByte() == 0) {
            isDefault = null;
        } else {
            isDefault = in.readInt();
        }
        searchUrl = in.readString();
        hotUrl = in.readString();
        if (in.readByte() == 0) {
            parent = null;
        } else {
            parent = in.readLong();
        }
        books = in.createTypedArrayList(BookInformation.CREATOR);
    }

    public static final Creator<WebSite> CREATOR = new Creator<WebSite>() {
        @Override
        public WebSite createFromParcel(Parcel in) {
            return new WebSite(in);
        }

        @Override
        public WebSite[] newArray(int size) {
            return new WebSite[size];
        }
    };

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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDecode() {
        return this.decode;
    }

    public void setDecode(String decode) {
        this.decode = decode;
    }

    public Integer getDelayMill() {
        return this.delayMill;
    }

    public void setDelayMill(Integer delayMill) {
        this.delayMill = delayMill;
    }

    public Integer getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Long getParent() {
        return this.parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getSearchUrl() {
        return this.searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getHotUrl() {
        return this.hotUrl;
    }

    public void setHotUrl(String hotUrl) {
        this.hotUrl = hotUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(decode);
        if (delayMill == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(delayMill);
        }
        if (isDefault == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isDefault);
        }
        dest.writeString(searchUrl);
        dest.writeString(hotUrl);
        if (parent == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(parent);
        }
        dest.writeTypedList(books);
    }
}
