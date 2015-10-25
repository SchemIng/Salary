package org.scheming.salary.entity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PROJECT".
 */
public class Project {

    private Long id;
    private Integer type;
    private String name;
    private Float cut_rate;
    private Float total_money;
    private Long salary;

    public Project() {
    }

    public Project(Long id) {
        this.id = id;
    }

    public Project(Long id, Integer type, String name, Float cut_rate, Float total_money, Long salary) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.cut_rate = cut_rate;
        this.total_money = total_money;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCut_rate() {
        return cut_rate;
    }

    public void setCut_rate(Float cut_rate) {
        this.cut_rate = cut_rate;
    }

    public Float getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Float total_money) {
        this.total_money = total_money;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

}
