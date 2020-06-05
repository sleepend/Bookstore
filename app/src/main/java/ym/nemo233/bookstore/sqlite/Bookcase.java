package ym.nemo233.bookstore.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Bookcase implements Parcelable {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String tag;
    private String name;
    private Integer sort;
    private String newest;
    private Integer chapter;//章节
    private Integer pageNumber;//页码
    private Integer cacheState;//缓存

    private Long biId;

    @ToOne(joinProperty = "biId")
    private BooksInformation book;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 465305063)
    private transient BookcaseDao myDao;

    @Generated(hash = 1181227989)
    @Keep
    public Bookcase() {
    }

    @Generated(hash = 9321458)
    public Bookcase(Long id, String tag, String name, Integer sort, String newest,
                    Integer chapter, Integer pageNumber, Integer cacheState, Long biId) {
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.sort = sort;
        this.newest = newest;
        this.chapter = chapter;
        this.pageNumber = pageNumber;
        this.cacheState = cacheState;
        this.biId = biId;
    }

    protected Bookcase(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        tag = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            sort = null;
        } else {
            sort = in.readInt();
        }
        newest = in.readString();
        if (in.readByte() == 0) {
            chapter = null;
        } else {
            chapter = in.readInt();
        }
        if (in.readByte() == 0) {
            pageNumber = null;
        } else {
            pageNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            cacheState = null;
        } else {
            cacheState = in.readInt();
        }
        if (in.readByte() == 0) {
            biId = null;
        } else {
            biId = in.readLong();
        }
        book = in.readParcelable(BooksInformation.class.getClassLoader());
    }

    public static final Creator<Bookcase> CREATOR = new Creator<Bookcase>() {
        @Override
        public Bookcase createFromParcel(Parcel in) {
            return new Bookcase(in);
        }

        @Override
        public Bookcase[] newArray(int size) {
            return new Bookcase[size];
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

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getBiId() {
        return this.biId;
    }

    public void setBiId(Long biId) {
        this.biId = biId;
    }

    @Generated(hash = 893611298)
    private transient Long book__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 70238390)
    public BooksInformation getBook() {
        Long __key = this.biId;
        if (book__resolvedKey == null || !book__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BooksInformationDao targetDao = daoSession.getBooksInformationDao();
            BooksInformation bookNew = targetDao.load(__key);
            synchronized (this) {
                book = bookNew;
                book__resolvedKey = __key;
            }
        }
        return book;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1052646862)
    public void setBook(BooksInformation book) {
        synchronized (this) {
            this.book = book;
            biId = book == null ? null : book.get_id();
            book__resolvedKey = biId;
        }
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
    @Generated(hash = 1431562392)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBookcaseDao() : null;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNewest() {
        return this.newest;
    }

    public void setNewest(String newest) {
        this.newest = newest;
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

    public Integer getCacheState() {
        return this.cacheState;
    }

    public void setCacheState(Integer cacheState) {
        this.cacheState = cacheState;
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
        dest.writeString(tag);
        dest.writeString(name);
        if (sort == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sort);
        }
        dest.writeString(newest);
        if (chapter == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(chapter);
        }
        if (pageNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pageNumber);
        }
        if (cacheState == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cacheState);
        }
        if (biId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(biId);
        }
        dest.writeParcelable(book, flags);
    }
}
