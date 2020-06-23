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
 * DAO for table "BOOKS_INFORMATION".
*/
public class BooksInformationDao extends AbstractDao<BooksInformation, Long> {

    public static final String TABLENAME = "BOOKS_INFORMATION";

    /**
     * Properties of entity BooksInformation.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Auth = new Property(2, String.class, "auth", false, "AUTH");
        public final static Property Instr = new Property(3, String.class, "instr", false, "INSTR");
        public final static Property ImageUrl = new Property(4, String.class, "imageUrl", false, "IMAGE_URL");
        public final static Property ClassName = new Property(5, String.class, "className", false, "CLASS_NAME");
        public final static Property Status = new Property(6, String.class, "status", false, "STATUS");
        public final static Property SourceUrl = new Property(7, String.class, "sourceUrl", false, "SOURCE_URL");
        public final static Property BaseUrl = new Property(8, String.class, "baseUrl", false, "BASE_URL");
        public final static Property ContentSize = new Property(9, String.class, "contentSize", false, "CONTENT_SIZE");
        public final static Property Upt = new Property(10, String.class, "upt", false, "UPT");
    }

    private DaoSession daoSession;


    public BooksInformationDao(DaoConfig config) {
        super(config);
    }
    
    public BooksInformationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOKS_INFORMATION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"NAME\" TEXT," + // 1: name
                "\"AUTH\" TEXT," + // 2: auth
                "\"INSTR\" TEXT," + // 3: instr
                "\"IMAGE_URL\" TEXT," + // 4: imageUrl
                "\"CLASS_NAME\" TEXT," + // 5: className
                "\"STATUS\" TEXT," + // 6: status
                "\"SOURCE_URL\" TEXT," + // 7: sourceUrl
                "\"BASE_URL\" TEXT," + // 8: baseUrl
                "\"CONTENT_SIZE\" TEXT," + // 9: contentSize
                "\"UPT\" TEXT);"); // 10: upt
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOKS_INFORMATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BooksInformation entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String auth = entity.getAuth();
        if (auth != null) {
            stmt.bindString(3, auth);
        }
 
        String instr = entity.getInstr();
        if (instr != null) {
            stmt.bindString(4, instr);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(5, imageUrl);
        }
 
        String className = entity.getClassName();
        if (className != null) {
            stmt.bindString(6, className);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(7, status);
        }
 
        String sourceUrl = entity.getSourceUrl();
        if (sourceUrl != null) {
            stmt.bindString(8, sourceUrl);
        }
 
        String baseUrl = entity.getBaseUrl();
        if (baseUrl != null) {
            stmt.bindString(9, baseUrl);
        }
 
        String contentSize = entity.getContentSize();
        if (contentSize != null) {
            stmt.bindString(10, contentSize);
        }
 
        String upt = entity.getUpt();
        if (upt != null) {
            stmt.bindString(11, upt);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BooksInformation entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String auth = entity.getAuth();
        if (auth != null) {
            stmt.bindString(3, auth);
        }
 
        String instr = entity.getInstr();
        if (instr != null) {
            stmt.bindString(4, instr);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(5, imageUrl);
        }
 
        String className = entity.getClassName();
        if (className != null) {
            stmt.bindString(6, className);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(7, status);
        }
 
        String sourceUrl = entity.getSourceUrl();
        if (sourceUrl != null) {
            stmt.bindString(8, sourceUrl);
        }
 
        String baseUrl = entity.getBaseUrl();
        if (baseUrl != null) {
            stmt.bindString(9, baseUrl);
        }
 
        String contentSize = entity.getContentSize();
        if (contentSize != null) {
            stmt.bindString(10, contentSize);
        }
 
        String upt = entity.getUpt();
        if (upt != null) {
            stmt.bindString(11, upt);
        }
    }

    @Override
    protected final void attachEntity(BooksInformation entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BooksInformation readEntity(Cursor cursor, int offset) {
        BooksInformation entity = new BooksInformation( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // auth
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // instr
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // imageUrl
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // className
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // status
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // sourceUrl
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // baseUrl
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // contentSize
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // upt
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BooksInformation entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAuth(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setInstr(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setImageUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setClassName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setStatus(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSourceUrl(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setBaseUrl(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setContentSize(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setUpt(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BooksInformation entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BooksInformation entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BooksInformation entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
