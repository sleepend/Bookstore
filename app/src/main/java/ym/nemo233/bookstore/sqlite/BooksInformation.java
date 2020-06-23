package ym.nemo233.bookstore.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity
public class BooksInformation implements Parcelable {
    @Id(autoincrement = true)
    private Long _id;
    private String name;
    private String auth;
    private String instr;
    private String imageUrl;
    private String className;
    private String status; //状态
    private String sourceUrl;//引用网页地址
    private String baseUrl;
    private String contentSize;//字数
    private String upt;//更新时间


    @ToMany(referencedJoinProperty = "biId")
    private List<Chapter> chapters;

    @Generated(hash = 1358245161)
    @Keep
    public BooksInformation() {
    }

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 192652385)
    private transient BooksInformationDao myDao;

    protected BooksInformation(Parcel in) {
        if (in.readByte() == 0) {
            _id = null;
        } else {
            _id = in.readLong();
        }
        name = in.readString();
        auth = in.readString();
        instr = in.readString();
        imageUrl = in.readString();
        className = in.readString();
        status = in.readString();
        sourceUrl = in.readString();
        baseUrl = in.readString();
        contentSize = in.readString();
        upt = in.readString();
    }

    @Generated(hash = 2085685177)
    public BooksInformation(Long _id, String name, String auth, String instr, String imageUrl, String className, String status,
            String sourceUrl, String baseUrl, String contentSize, String upt) {
        this._id = _id;
        this.name = name;
        this.auth = auth;
        this.instr = instr;
        this.imageUrl = imageUrl;
        this.className = className;
        this.status = status;
        this.sourceUrl = sourceUrl;
        this.baseUrl = baseUrl;
        this.contentSize = contentSize;
        this.upt = upt;
    }

    public static final Creator<BooksInformation> CREATOR = new Creator<BooksInformation>() {
        @Override
        public BooksInformation createFromParcel(Parcel in) {
            return new BooksInformation(in);
        }

        @Override
        public BooksInformation[] newArray(int size) {
            return new BooksInformation[size];
        }
    };

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

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getInstr() {
        return this.instr;
    }

    public void setInstr(String instr) {
        this.instr = instr;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContentSize() {
        return this.contentSize;
    }

    public void setContentSize(String contentSize) {
        this.contentSize = contentSize;
    }

    public String getUpt() {
        return this.upt;
    }

    public void setUpt(String upt) {
        this.upt = upt;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 124969432)
    public List<Chapter> getChapters() {
        if (chapters == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChapterDao targetDao = daoSession.getChapterDao();
            List<Chapter> chaptersNew = targetDao._queryBooksInformation_Chapters(_id);
            synchronized (this) {
                if (chapters == null) {
                    chapters = chaptersNew;
                }
            }
        }
        return chapters;
    }

    /**
     * 临时存储,不写入到数据库中
     *
     * @param chapters
     */
    @Keep
    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 936914273)
    public synchronized void resetChapters() {
        chapters = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    @Keep
    public void insert() {
        myDao.insert(this);
        BooksInformation bi = myDao.queryBuilder().where(
                BooksInformationDao.Properties.Name.eq(name),
                BooksInformationDao.Properties.Auth.eq(auth)
        ).unique();
        this._id = bi._id;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 2030084455)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBooksInformationDao() : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(_id);
        }
        dest.writeString(name);
        dest.writeString(auth);
        dest.writeString(instr);
        dest.writeString(imageUrl);
        dest.writeString(className);
        dest.writeString(status);
        dest.writeString(sourceUrl);
        dest.writeString(baseUrl);
        dest.writeString(contentSize);
        dest.writeString(upt);
    }
}
