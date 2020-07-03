package ym.nemo233.bookstore.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

@Entity
public class BookInformation implements Parcelable {
    @Id(autoincrement = true)
    private Long id; //id,有id表示书架打开,无id表示搜索打开
    @Unique
    private String identifyCode;//识别码=书名+作者+引用地址
    private String name;//书名
    private String auth;//作者
    private String instr;//简介
    private String imageUrl;//封面
    private String className;//标签
    private String status; //状态
    private String sourceUrl;//引用网页地址
    private String siteName;//来源
    private Integer currentChapter;//当前阅读章节
    private Integer sort;//排序索引
    private String newest;//最新章节
    private String upt;//更新时间

    @ToMany(referencedJoinProperty = "biId")
    private List<Chapter> chapters;

    @Transient
    public List<Chapter> latest;

    @Generated(hash = 2061895590)
    public BookInformation(Long id, String identifyCode, String name, String auth,
                           String instr, String imageUrl, String className, String status,
                           String sourceUrl, String siteName, Integer currentChapter, Integer sort,
                           String newest, String upt) {
        this.id = id;
        this.identifyCode = identifyCode;
        this.name = name;
        this.auth = auth;
        this.instr = instr;
        this.imageUrl = imageUrl;
        this.className = className;
        this.status = status;
        this.sourceUrl = sourceUrl;
        this.siteName = siteName;
        this.currentChapter = currentChapter;
        this.sort = sort;
        this.newest = newest;
        this.upt = upt;
    }

    @Generated(hash = 315621338)
    public BookInformation() {
    }

    /**
     * 搜索结果临时对象
     *
     * @param name
     * @param auth
     * @param imageUrl
     * @param sourceUrl
     */
    @Keep
    public BookInformation(String name, String auth, String imageUrl, String sourceUrl) {
        this.id = null;
        this.identifyCode = null;
        this.name = name;
        this.auth = auth;
        this.instr = null;
        this.imageUrl = imageUrl;
        this.className = null;
        this.status = null;
        this.sourceUrl = sourceUrl;
        this.siteName = null;
        this.currentChapter = -1;
        this.sort = -1;
        this.newest = null;
        this.upt = null;
    }

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1484693217)
    private transient BookInformationDao myDao;

    protected BookInformation(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        identifyCode = in.readString();
        name = in.readString();
        auth = in.readString();
        instr = in.readString();
        imageUrl = in.readString();
        className = in.readString();
        status = in.readString();
        sourceUrl = in.readString();
        siteName = in.readString();
        if (in.readByte() == 0) {
            currentChapter = null;
        } else {
            currentChapter = in.readInt();
        }
        if (in.readByte() == 0) {
            sort = null;
        } else {
            sort = in.readInt();
        }
        newest = in.readString();
        upt = in.readString();
        chapters = in.createTypedArrayList(Chapter.CREATOR);
        latest = in.createTypedArrayList(Chapter.CREATOR);
    }

    public static final Creator<BookInformation> CREATOR = new Creator<BookInformation>() {
        @Override
        public BookInformation createFromParcel(Parcel in) {
            return new BookInformation(in);
        }

        @Override
        public BookInformation[] newArray(int size) {
            return new BookInformation[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifyCode() {
        return this.identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
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

    public String getInstr() {
        return this.instr;
    }

    public void setInstr(String instr) {
        this.instr = instr;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getCurrentChapter() {
        return this.currentChapter;
    }

    public void setCurrentChapter(Integer currentChapter) {
        this.currentChapter = currentChapter;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getNewest() {
        return this.newest;
    }

    public void setNewest(String newest) {
        this.newest = newest;
    }

    public String getUpt() {
        return this.upt;
    }

    public void setUpt(String upt) {
        this.upt = upt;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 686204133)
    public List<Chapter> getChapters() {
        if (chapters == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChapterDao targetDao = daoSession.getChapterDao();
            List<Chapter> chaptersNew = targetDao._queryBookInformation_Chapters(id);
            synchronized (this) {
                if (chapters == null) {
                    chapters = chaptersNew;
                }
            }
        }
        return chapters;
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

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1128218966)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBookInformationDao() : null;
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
        dest.writeString(identifyCode);
        dest.writeString(name);
        dest.writeString(auth);
        dest.writeString(instr);
        dest.writeString(imageUrl);
        dest.writeString(className);
        dest.writeString(status);
        dest.writeString(sourceUrl);
        dest.writeString(siteName);
        if (currentChapter == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(currentChapter);
        }
        if (sort == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sort);
        }
        dest.writeString(newest);
        dest.writeString(upt);
        dest.writeTypedList(chapters);
        dest.writeTypedList(latest);
    }
}
