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

import org.scheming.salary.entity.Project;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PROJECT".
*/
public class ProjectDao extends AbstractDao<Project, Long> {

    public static final String TABLENAME = "PROJECT";

    /**
     * Properties of entity Project.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Type = new Property(1, Integer.class, "type", false, "TYPE");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Cut_rate = new Property(3, Float.class, "cut_rate", false, "CUT_RATE");
        public final static Property Total_money = new Property(4, Float.class, "total_money", false, "TOTAL_MONEY");
        public final static Property Salary = new Property(5, Long.class, "salary", false, "SALARY");
    };

    private Query<Project> salary_ProjectsQuery;

    public ProjectDao(DaoConfig config) {
        super(config);
    }
    
    public ProjectDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PROJECT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TYPE\" INTEGER," + // 1: type
                "\"NAME\" TEXT," + // 2: name
                "\"CUT_RATE\" REAL," + // 3: cut_rate
                "\"TOTAL_MONEY\" REAL," + // 4: total_money
                "\"SALARY\" INTEGER);"); // 5: salary
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PROJECT\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Project entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(2, type);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        Float cut_rate = entity.getCut_rate();
        if (cut_rate != null) {
            stmt.bindDouble(4, cut_rate);
        }
 
        Float total_money = entity.getTotal_money();
        if (total_money != null) {
            stmt.bindDouble(5, total_money);
        }
 
        Long salary = entity.getSalary();
        if (salary != null) {
            stmt.bindLong(6, salary);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Project readEntity(Cursor cursor, int offset) {
        Project entity = new Project( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // type
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3), // cut_rate
            cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4), // total_money
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5) // salary
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Project entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setType(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCut_rate(cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3));
        entity.setTotal_money(cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4));
        entity.setSalary(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Project entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Project entity) {
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
    
    /** Internal query to resolve the "projects" to-many relationship of Salary. */
    public List<Project> _querySalary_Projects(Long salary) {
        synchronized (this) {
            if (salary_ProjectsQuery == null) {
                QueryBuilder<Project> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Salary.eq(null));
                salary_ProjectsQuery = queryBuilder.build();
            }
        }
        Query<Project> query = salary_ProjectsQuery.forCurrentThread();
        query.setParameter(0, salary);
        return query.list();
    }

}
