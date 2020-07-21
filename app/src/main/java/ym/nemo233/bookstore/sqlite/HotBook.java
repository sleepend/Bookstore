package ym.nemo233.bookstore.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class HotBook implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    private String siteTag;//站点
    private String name;
    private String auth;
    private String imgUrl;
    private String sourceUrl;
    private String newest;//最新
    private Long stamp;//时间戳

    @Generated(hash = 1512732916)
    public HotBook(Long id, String siteTag, String name, String auth, String imgUrl,
                   String sourceUrl, String newest, Long stamp) {
        this.id = id;
        this.siteTag = siteTag;
        this.name = name;
        this.auth = auth;
        this.imgUrl = imgUrl;
        this.sourceUrl = sourceUrl;
        this.newest = newest;
        this.stamp = stamp;
    }

    @Generated(hash = 1298229619)
    public HotBook() {
    }

    protected HotBook(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        siteTag = in.readString();
        name = in.readString();
        auth = in.readString();
        imgUrl = in.readString();
        sourceUrl = in.readString();
        newest = in.readString();
        if (in.readByte() == 0) {
            stamp = null;
        } else {
            stamp = in.readLong();
        }
    }

    public static final Creator<HotBook> CREATOR = new Creator<HotBook>() {
        @Override
        public HotBook createFromParcel(Parcel in) {
            return new HotBook(in);
        }

        @Override
        public HotBook[] newArray(int size) {
            return new HotBook[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteTag() {
        return this.siteTag;
    }

    public void setSiteTag(String siteTag) {
        this.siteTag = siteTag;
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

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNewest() {
        return this.newest;
    }

    public void setNewest(String newest) {
        this.newest = newest;
    }

    public Long getStamp() {
        return this.stamp;
    }

    public void setStamp(Long stamp) {
        this.stamp = stamp;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
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
        dest.writeString(siteTag);
        dest.writeString(name);
        dest.writeString(auth);
        dest.writeString(imgUrl);
        dest.writeString(sourceUrl);
        dest.writeString(newest);
        if (stamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(stamp);
        }
    }
}
