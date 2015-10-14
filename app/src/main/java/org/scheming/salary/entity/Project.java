package org.scheming.salary.entity;

import org.scheming.salary.dao.DaoSession;
import de.greenrobot.dao.DaoException;

import org.scheming.salary.dao.ProjectDao;
import org.scheming.salary.dao.SalaryItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PROJECT".
 */
public class Project {

    private Long id;
    private String sale_name;
    private Float sale_cut_rate;
    private Float sale_total_money;
    private String implement_name;
    private Float implement_cut_rate;
    private Float implement_total_money;
    private String service_name;
    private Float service_cut_rate;
    private Float service_total_money;
    private Long salary_item;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ProjectDao myDao;

    private SalaryItem project_salary_relation;
    private Long project_salary_relation__resolvedKey;


    public Project() {
    }

    public Project(Long id) {
        this.id = id;
    }

    public Project(Long id, String sale_name, Float sale_cut_rate, Float sale_total_money, String implement_name, Float implement_cut_rate, Float implement_total_money, String service_name, Float service_cut_rate, Float service_total_money, Long salary_item) {
        this.id = id;
        this.sale_name = sale_name;
        this.sale_cut_rate = sale_cut_rate;
        this.sale_total_money = sale_total_money;
        this.implement_name = implement_name;
        this.implement_cut_rate = implement_cut_rate;
        this.implement_total_money = implement_total_money;
        this.service_name = service_name;
        this.service_cut_rate = service_cut_rate;
        this.service_total_money = service_total_money;
        this.salary_item = salary_item;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProjectDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSale_name() {
        return sale_name;
    }

    public void setSale_name(String sale_name) {
        this.sale_name = sale_name;
    }

    public Float getSale_cut_rate() {
        return sale_cut_rate;
    }

    public void setSale_cut_rate(Float sale_cut_rate) {
        this.sale_cut_rate = sale_cut_rate;
    }

    public Float getSale_total_money() {
        return sale_total_money;
    }

    public void setSale_total_money(Float sale_total_money) {
        this.sale_total_money = sale_total_money;
    }

    public String getImplement_name() {
        return implement_name;
    }

    public void setImplement_name(String implement_name) {
        this.implement_name = implement_name;
    }

    public Float getImplement_cut_rate() {
        return implement_cut_rate;
    }

    public void setImplement_cut_rate(Float implement_cut_rate) {
        this.implement_cut_rate = implement_cut_rate;
    }

    public Float getImplement_total_money() {
        return implement_total_money;
    }

    public void setImplement_total_money(Float implement_total_money) {
        this.implement_total_money = implement_total_money;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public Float getService_cut_rate() {
        return service_cut_rate;
    }

    public void setService_cut_rate(Float service_cut_rate) {
        this.service_cut_rate = service_cut_rate;
    }

    public Float getService_total_money() {
        return service_total_money;
    }

    public void setService_total_money(Float service_total_money) {
        this.service_total_money = service_total_money;
    }

    public Long getSalary_item() {
        return salary_item;
    }

    public void setSalary_item(Long salary_item) {
        this.salary_item = salary_item;
    }

    /** To-one relationship, resolved on first access. */
    public SalaryItem getProject_salary_relation() {
        Long __key = this.salary_item;
        if (project_salary_relation__resolvedKey == null || !project_salary_relation__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SalaryItemDao targetDao = daoSession.getSalaryItemDao();
            SalaryItem project_salary_relationNew = targetDao.load(__key);
            synchronized (this) {
                project_salary_relation = project_salary_relationNew;
            	project_salary_relation__resolvedKey = __key;
            }
        }
        return project_salary_relation;
    }

    public void setProject_salary_relation(SalaryItem project_salary_relation) {
        synchronized (this) {
            this.project_salary_relation = project_salary_relation;
            salary_item = project_salary_relation == null ? null : project_salary_relation.getId();
            project_salary_relation__resolvedKey = salary_item;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}