package org.scheming.salary.entity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "ALLOWANCE".
 */
public class Allowance {

    private Long id;
    private Float social_security;
    private Float accident_insurance;
    private Float life_allowance;
    private Float other;
    private Float special;
    private Float post;
    private Long salary;

    public Allowance() {
    }

    public Allowance(Long id) {
        this.id = id;
    }

    public Allowance(Long id, Float social_security, Float accident_insurance, Float life_allowance, Float other, Float special, Float post, Long salary) {
        this.id = id;
        this.social_security = social_security;
        this.accident_insurance = accident_insurance;
        this.life_allowance = life_allowance;
        this.other = other;
        this.special = special;
        this.post = post;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getSocial_security() {
        return social_security;
    }

    public void setSocial_security(Float social_security) {
        this.social_security = social_security;
    }

    public Float getAccident_insurance() {
        return accident_insurance;
    }

    public void setAccident_insurance(Float accident_insurance) {
        this.accident_insurance = accident_insurance;
    }

    public Float getLife_allowance() {
        return life_allowance;
    }

    public void setLife_allowance(Float life_allowance) {
        this.life_allowance = life_allowance;
    }

    public Float getOther() {
        return other;
    }

    public void setOther(Float other) {
        this.other = other;
    }

    public Float getSpecial() {
        return special;
    }

    public void setSpecial(Float special) {
        this.special = special;
    }

    public Float getPost() {
        return post;
    }

    public void setPost(Float post) {
        this.post = post;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

}
