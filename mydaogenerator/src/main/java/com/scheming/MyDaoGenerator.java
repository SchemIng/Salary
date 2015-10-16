package com.scheming;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "org.scheming.salary.entity");

        schema.setDefaultJavaPackageDao("org.scheming.salary.dao");

        initBean(schema);

        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

    private static void initBean(Schema schema) {
        //用户
        Entity user = schema.addEntity("User");

        user.addIdProperty().autoincrement();
        user.addStringProperty("name").notNull();
        user.addDateProperty("join_date").notNull();
        user.addFloatProperty("base_salary").notNull();

        //工资
        Entity salary_item = schema.addEntity("SalaryItem");

        salary_item.addIdProperty().autoincrement();
        salary_item.addIntProperty("current_month");
        salary_item.addFloatProperty("borrow");
        salary_item.addFloatProperty("cut_payment");
        salary_item.addFloatProperty("personal_social_security");

        //工程
        Entity project = schema.addEntity("Project");

        project.addIdProperty().autoincrement();
        project.addStringProperty("sale_name");
        project.addFloatProperty("sale_cut_rate");
        project.addFloatProperty("sale_total_money");

        project.addStringProperty("implement_name");
        project.addFloatProperty("implement_cut_rate");
        project.addFloatProperty("implement_total_money");

        project.addStringProperty("service_name");
        project.addFloatProperty("service_cut_rate");
        project.addFloatProperty("service_total_money");

        //补助
        Entity allowance = schema.addEntity("Allowance");

        allowance.addIdProperty().autoincrement();
        allowance.addFloatProperty("social_security");
        allowance.addFloatProperty("accident_insurance");
        allowance.addFloatProperty("life_allowance");
        allowance.addFloatProperty("other");
        allowance.addFloatProperty("special");
        allowance.addFloatProperty("post");

        //出勤
        Entity attendance = schema.addEntity("Attendance");

        attendance.addIdProperty().autoincrement();
        attendance.addIntProperty("business_leave");
        attendance.addIntProperty("sick_leave");
        attendance.addIntProperty("late");
        attendance.addIntProperty("leave_early");
        attendance.addIntProperty("absenteeism");

        //add foreign key
        Property allowance_property = allowance.addLongProperty("salary_item").getProperty();
        Property attendance_property = attendance.addLongProperty("salary_item").getProperty();
        Property project_property = project.addLongProperty("salary_item").getProperty();
        Property salary_item_property = salary_item.addLongProperty("salary_item").getProperty();

        allowance.addToOne(salary_item, allowance_property, "allowance_salary_relation");
        attendance.addToOne(salary_item, attendance_property, "attendance_salary_relation");
        project.addToOne(salary_item, project_property, "project_salary_relation");
        salary_item.addToOne(user, salary_item_property, "salary_user_relation");
    }
}
