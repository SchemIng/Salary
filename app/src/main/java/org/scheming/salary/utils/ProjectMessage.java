package org.scheming.salary.utils;

/**
 * Created by Scheming on 2015/10/20.
 */
public class ProjectMessage {
    public Integer type;
    public String name;
    public Float cut_rate;
    public Float total_money;

    public ProjectMessage(Integer type, String name, Float cut_rate, Float total_money) {
        this.type = type;
        this.name = name;
        this.cut_rate = cut_rate;
        this.total_money = total_money;
    }
}
