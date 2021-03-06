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

import org.scheming.salary.entity.Allowance;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALLOWANCE".
*/
public class AllowanceDao extends AbstractDao<Allowance, Long> {

    public static final String TABLENAME = "ALLOWANCE";

    /**
     * Properties of entity Allowance.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Social_security = new Property(1, Float.class, "social_security", false, "SOCIAL_SECURITY");
        public final static Property Accident_insurance = new Property(2, Float.class, "accident_insurance", false, "ACCIDENT_INSURANCE");
        public final static Property Life_allowance = new Property(3, Float.class, "life_allowance", false, "LIFE_ALLOWANCE");
        public final static Property Other = new Property(4, Float.class, "other", false, "OTHER");
        public final static Property Special = new Property(5, Float.class, "special", false, "SPECIAL");
        public final static Property Post = new Property(6, Float.class, "post", false, "POST");
        public final static Property Salary = new Property(7, Long.class, "salary", false, "SALARY");
    };

    private Query<Allowance> salary_AllowanceQuery;

    public AllowanceDao(DaoConfig config) {
        super(config);
    }
    
    public AllowanceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALLOWANCE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"SOCIAL_SECURITY\" REAL," + // 1: social_security
                "\"ACCIDENT_INSURANCE\" REAL," + // 2: accident_insurance
                "\"LIFE_ALLOWANCE\" REAL," + // 3: life_allowance
                "\"OTHER\" REAL," + // 4: other
                "\"SPECIAL\" REAL," + // 5: special
                "\"POST\" REAL," + // 6: post
                "\"SALARY\" INTEGER);"); // 7: salary
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALLOWANCE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Allowance entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Float social_security = entity.getSocial_security();
        if (social_security != null) {
            stmt.bindDouble(2, social_security);
        }
 
        Float accident_insurance = entity.getAccident_insurance();
        if (accident_insurance != null) {
            stmt.bindDouble(3, accident_insurance);
        }
 
        Float life_allowance = entity.getLife_allowance();
        if (life_allowance != null) {
            stmt.bindDouble(4, life_allowance);
        }
 
        Float other = entity.getOther();
        if (other != null) {
            stmt.bindDouble(5, other);
        }
 
        Float special = entity.getSpecial();
        if (special != null) {
            stmt.bindDouble(6, special);
        }
 
        Float post = entity.getPost();
        if (post != null) {
            stmt.bindDouble(7, post);
        }
 
        Long salary = entity.getSalary();
        if (salary != null) {
            stmt.bindLong(8, salary);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Allowance readEntity(Cursor cursor, int offset) {
        Allowance entity = new Allowance( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getFloat(offset + 1), // social_security
            cursor.isNull(offset + 2) ? null : cursor.getFloat(offset + 2), // accident_insurance
            cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3), // life_allowance
            cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4), // other
            cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5), // special
            cursor.isNull(offset + 6) ? null : cursor.getFloat(offset + 6), // post
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7) // salary
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Allowance entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSocial_security(cursor.isNull(offset + 1) ? null : cursor.getFloat(offset + 1));
        entity.setAccident_insurance(cursor.isNull(offset + 2) ? null : cursor.getFloat(offset + 2));
        entity.setLife_allowance(cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3));
        entity.setOther(cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4));
        entity.setSpecial(cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5));
        entity.setPost(cursor.isNull(offset + 6) ? null : cursor.getFloat(offset + 6));
        entity.setSalary(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Allowance entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Allowance entity) {
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
    
    /** Internal query to resolve the "allowance" to-many relationship of Salary. */
    public List<Allowance> _querySalary_Allowance(Long salary) {
        synchronized (this) {
            if (salary_AllowanceQuery == null) {
                QueryBuilder<Allowance> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Salary.eq(null));
                salary_AllowanceQuery = queryBuilder.build();
            }
        }
        Query<Allowance> query = salary_AllowanceQuery.forCurrentThread();
        query.setParameter(0, salary);
        return query.list();
    }

}
