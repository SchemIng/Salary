package com.scheming;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

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
        Entity salary = schema.addEntity("Salary");

        salary.addIdProperty().autoincrement();
        salary.addIntProperty("current_year");
        salary.addIntProperty("current_month");
        salary.addFloatProperty("borrow");
        salary.addFloatProperty("cut_payment");
        salary.addFloatProperty("personal_social_security");
        salary.addFloatProperty("should_pay");
        salary.addFloatProperty("real_pay");

        //工程
        Entity project = schema.addEntity("Project");

        project.addIdProperty().autoincrement();
        project.addIntProperty("type");
        project.addStringProperty("name");
        project.addFloatProperty("cut_rate");
        project.addFloatProperty("total_money");
        project.addFloatProperty("result_money");

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
        Property allowance_property = allowance.addLongProperty("salary").getProperty();
        Property attendance_property = attendance.addLongProperty("salary").getProperty();
        Property project_property = project.addLongProperty("salary").getProperty();
        Property salary_property = salary.addLongProperty("user").getProperty();

        salary.addToMany(allowance, allowance_property).setName("allowance");
        salary.addToMany(attendance, attendance_property).setName("attendance");
        user.addToMany(salary, salary_property).setName("salary");

        ToMany salaryToProjects = salary.addToMany(project, project_property);
        salaryToProjects.setName("projects");
    }
}
