package org.scheming.salary.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import org.scheming.salary.entity.Salary;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SALARY".
*/
public class SalaryDao extends AbstractDao<Salary, Long> {

    public static final String TABLENAME = "SALARY";

    /**
     * Properties of entity Salary.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Current_month = new Property(1, Integer.class, "current_month", false, "CURRENT_MONTH");
        public final static Property Borrow = new Property(2, Float.class, "borrow", false, "BORROW");
        public final static Property Cut_payment = new Property(3, Float.class, "cut_payment", false, "CUT_PAYMENT");
        public final static Property Personal_social_security = new Property(4, Float.class, "personal_social_security", false, "PERSONAL_SOCIAL_SECURITY");
        public final static Property User = new Property(5, Long.class, "user", false, "USER");
    };

    private DaoSession daoSession;

    private Query<Salary> user_SalaryQuery;

    public SalaryDao(DaoConfig config) {
        super(config);
    }
    
    public SalaryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SALARY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CURRENT_MONTH\" INTEGER," + // 1: current_month
                "\"BORROW\" REAL," + // 2: borrow
                "\"CUT_PAYMENT\" REAL," + // 3: cut_payment
                "\"PERSONAL_SOCIAL_SECURITY\" REAL," + // 4: personal_social_security
                "\"USER\" INTEGER);"); // 5: user
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SALARY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Salary entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer current_month = entity.getCurrent_month();
        if (current_month != null) {
            stmt.bindLong(2, current_month);
        }
 
        Float borrow = entity.getBorrow();
        if (borrow != null) {
            stmt.bindDouble(3, borrow);
        }
 
        Float cut_payment = entity.getCut_payment();
        if (cut_payment != null) {
            stmt.bindDouble(4, cut_payment);
        }
 
        Float personal_social_security = entity.getPersonal_social_security();
        if (personal_social_security != null) {
            stmt.bindDouble(5, personal_social_security);
        }
 
        Long user = entity.getUser();
        if (user != null) {
            stmt.bindLong(6, user);
        }
    }

    @Override
    protected void attachEntity(Salary entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Salary readEntity(Cursor cursor, int offset) {
        Salary entity = new Salary( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // current_month
            cursor.isNull(offset + 2) ? null : cursor.getFloat(offset + 2), // borrow
            cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3), // cut_payment
            cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4), // personal_social_security
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5) // user
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Salary entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCurrent_month(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setBorrow(cursor.isNull(offset + 2) ? null : cursor.getFloat(offset + 2));
        entity.setCut_payment(cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3));
        entity.setPersonal_social_security(cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4));
        entity.setUser(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Salary entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Salary entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "salary" to-many relationship of User. */
    public List<Salary> _queryUser_Salary(Long user) {
        synchronized (this) {
            if (user_SalaryQuery == null) {
                QueryBuilder<Salary> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.User.eq(null));
                user_SalaryQuery = queryBuilder.build();
            }
        }
        Query<Salary> query = user_SalaryQuery.forCurrentThread();
        query.setParameter(0, user);
        return query.list();
    }

}
