package ym.nemo233.bookstore.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

import java.io.Serializable;

/**
 * 热门书籍
 */
@Entity
public class PopularBooks implements Parcelable {


    @Id(autoincrement = true)
    private Long id;
    private String website;//网站名
    private Integer type;//分类id
    private String typeName;//分类名
    private String baseUrl;//域名地址 http://www.baidu.com
    private String specific;//实际地址  /book_1002.html
    private String bookName;//推荐的书名
    private String auth;//作者
    private String picture;//图片
    private String bookUrl;//图书地址
    private String instr;//介绍


    @Generated(hash = 1702118410)
    @Keep
    public PopularBooks() {
    }

    @Generated(hash = 436873857)
    @Keep
    public PopularBooks(Long id, String website, Integer type, String typeName, String baseUrl,
                        String specific, String bookName, String auth, String picture, String bookUrl,
                        String instr) {
        this.id = id;
        this.website = website;
        this.type = type;
        this.typeName = typeName;
        this.baseUrl = baseUrl;
        this.specific = specific;
        this.bookName = bookName;
        this.auth = auth;
        this.picture = picture;
        this.bookUrl = bookUrl;
        this.instr = instr;
    }

    protected PopularBooks(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        website = in.readString();
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readInt();
        }
        typeName = in.readString();
        baseUrl = in.readString();
        specific = in.readString();
        bookName = in.readString();
        auth = in.readString();
        picture = in.readString();
        bookUrl = in.readString();
        instr = in.readString();
    }

    public static final Creator<PopularBooks> CREATOR = new Creator<PopularBooks>() {
        @Override
        public PopularBooks createFromParcel(Parcel in) {
            return new PopularBooks(in);
        }

        @Override
        public PopularBooks[] newArray(int size) {
            return new PopularBooks[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSpecific() {
        return this.specific;
    }

    public void setSpecific(String specific) {
        this.specific = specific;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuth() {
        return this.auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getInstr() {
        return this.instr;
    }

    public void setInstr(String instr) {
        this.instr = instr;
    }

    public String getBookUrl() {
        return this.bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
        dest.writeString(website);
        if (type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(type);
        }
        dest.writeString(typeName);
        dest.writeString(baseUrl);
        dest.writeString(specific);
        dest.writeString(bookName);
        dest.writeString(auth);
        dest.writeString(picture);
        dest.writeString(bookUrl);
        dest.writeString(instr);
    }
}
