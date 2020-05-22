package ym.nemo233.bookstore.sqlite;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity
public class BooksSite {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String name;
    private String decode;
    @NotNull
    private String rootUrl;
    @NotNull
    private Integer delayMill;
    @NotNull
    private Boolean defaultSite;

    @ToMany(referencedJoinProperty = "bsId")
    private List<BookcaseClassifyCache> classifyCaches;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 698124309)
    private transient BooksSiteDao myDao;

    @Generated(hash = 1786018598)
    @Keep
    public BooksSite() {
    }

    @Generated(hash = 1468403740)
    public BooksSite(Long id, @NotNull String name, String decode, @NotNull String rootUrl,
            @NotNull Integer delayMill, @NotNull Boolean defaultSite) {
        this.id = id;
        this.name = name;
        this.decode = decode;
        this.rootUrl = rootUrl;
        this.delayMill = delayMill;
        this.defaultSite = defaultSite;
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

    public String getRootUrl() {
        return this.rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public Integer getDelayMill() {
        return this.delayMill;
    }

    public void setDelayMill(Integer delayMill) {
        this.delayMill = delayMill;
    }

    public Boolean getDefaultSite() {
        return this.defaultSite;
    }

    public void setDefaultSite(Boolean defaultSite) {
        this.defaultSite = defaultSite;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1980141476)
    public List<BookcaseClassifyCache> getClassifyCaches() {
        if (classifyCaches == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookcaseClassifyCacheDao targetDao = daoSession
                    .getBookcaseClassifyCacheDao();
            List<BookcaseClassifyCache> classifyCachesNew = targetDao
                    ._queryBooksSite_ClassifyCaches(id);
            synchronized (this) {
                if (classifyCaches == null) {
                    classifyCaches = classifyCachesNew;
                }
            }
        }
        return classifyCaches;
    }

    public void putClassifyCaches(List<BookcaseClassifyCache> data) {
        if (data == null) {
            return;
        }
        this.daoSession.getBookcaseClassifyCacheDao().insertInTx(data);
        if (classifyCaches ==null || classifyCaches.isEmpty()) {
            classifyCaches = data;
        }
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1934159512)
    public synchronized void resetClassifyCaches() {
        classifyCaches = null;
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
    @Generated(hash = 394865366)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBooksSiteDao() : null;
    }

    public String getDecode() {
        return this.decode;
    }

    public void setDecode(String decode) {
        this.decode = decode;
    }
}
