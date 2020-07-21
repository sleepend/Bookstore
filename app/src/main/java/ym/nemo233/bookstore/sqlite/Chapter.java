package ym.nemo233.bookstore.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Chapter implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private Long biId;
    private Integer index;
    private String name;
    private String url;
    private String content;

    /**
     * 临时最新章节
     *
     * @param name
     * @param url
     */
    @Keep
    public Chapter(String name, String url) {
        this.id = null;
        this.name = name;
        this.url = url;
    }

    @Generated(hash = 173098371)
    public Chapter(Long id, @NotNull Long biId, Integer index, String name,
                   String url, String content) {
        this.id = id;
        this.biId = biId;
        this.index = index;
        this.name = name;
        this.url = url;
        this.content = content;
    }

    @Generated(hash = 393170288)
    public Chapter() {
    }

    protected Chapter(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            index = null;
        } else {
            index = in.readInt();
        }
        name = in.readString();
        url = in.readString();
        content = in.readString();
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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
        if (index == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(index);
        }
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(content);
    }

    public Long getBiId() {
        return this.biId;
    }

    public void setBiId(Long biId) {
        this.biId = biId;
    }
}
