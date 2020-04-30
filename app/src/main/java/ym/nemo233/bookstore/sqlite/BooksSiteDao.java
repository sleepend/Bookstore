package ym.nemo233.bookstore.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOKS_SITE".
*/
public class BooksSiteDao extends AbstractDao<BooksSite, Long> {

    public static final String TABLENAME = "BOOKS_SITE";

    /**
     * Properties of entity BooksSite.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property RootUrl = new Property(2, String.class, "rootUrl", false, "ROOT_URL");
        public final static Property DelayMill = new Property(3, Integer.class, "delayMill", false, "DELAY_MILL");
        public final static Property DefaultSite = new Property(4, Boolean.class, "defaultSite", false, "DEFAULT_SITE");
    }

    private DaoSession daoSession;


    public BooksSiteDao(DaoConfig config) {
        super(config);
    }
    
    public BooksSiteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOKS_SITE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT NOT NULL ," + // 1: name
                "\"ROOT_URL\" TEXT NOT NULL ," + // 2: rootUrl
                "\"DELAY_MILL\" INTEGER NOT NULL ," + // 3: delayMill
                "\"DEFAULT_SITE\" INTEGER NOT NULL );"); // 4: defaultSite
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOKS_SITE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BooksSite entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindString(3, entity.getRootUrl());
        stmt.bindLong(4, entity.getDelayMill());
        stmt.bindLong(5, entity.getDefaultSite() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BooksSite entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindString(3, entity.getRootUrl());
        stmt.bindLong(4, entity.getDelayMill());
        stmt.bindLong(5, entity.getDefaultSite() ? 1L: 0L);
    }

    @Override
    protected final void attachEntity(BooksSite entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BooksSite readEntity(Cursor cursor, int offset) {
        BooksSite entity = new BooksSite( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.getString(offset + 2), // rootUrl
            cursor.getInt(offset + 3), // delayMill
            cursor.getShort(offset + 4) != 0 // defaultSite
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BooksSite entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setRootUrl(cursor.getString(offset + 2));
        entity.setDelayMill(cursor.getInt(offset + 3));
        entity.setDefaultSite(cursor.getShort(offset + 4) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BooksSite entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BooksSite entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BooksSite entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}