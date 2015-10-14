package org.scheming.salary;

import android.app.Application;

import org.scheming.salary.dao.DaoMaster;
import org.scheming.salary.dao.DaoSession;
import org.scheming.salary.dao.OpenHelper;

/**
 * Created by Scheming on 2015/10/9.
 */
public class SalaryApplication extends Application {
    private static SalaryApplication application;
    private DaoMaster daoMaster;
    private DaoSession session;


    @Override
    public void onCreate() {
        super.onCreate();
        if (application == null)
            application = this;

        if (daoMaster == null) {
            daoMaster = new DaoMaster(new OpenHelper(this, "salary-db", null).getWritableDatabase());
            session = daoMaster.newSession();
        }


    }

    public static SalaryApplication getApplication() {
        return application;
    }

    public DaoSession getSession() {
        return session;
    }
}
