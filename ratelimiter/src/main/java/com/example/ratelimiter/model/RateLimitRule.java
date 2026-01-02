package com.example.ratelimiter.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "ratelimitrule")
public class RateLimitRule{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ruleId")
    private UUID ruleId;

    @Column(name = "Apikey")
    private String Apikey;

    @Column(name = "urlendpoint")
    private String urlendpoint;

    @Column(name = "dimension")
    private String dimension;

    @Column(name = "limitValue")
    private int limitvalue;

    @Column(name = "failStrategy")
    private String failstatergy;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "planId", nullable = false)
    private Plan planId;

    

    public UUID getRuleId() {
        return ruleId;
    }
    public void setRuleId(UUID ruleId) {
        this.ruleId = ruleId;
    }
    public String getUrlendpoint() {
        return urlendpoint;
    }
    public void setUrlendpoint(String urlendpoint) {
        this.urlendpoint = urlendpoint;
    }
    public String getDimension() {
        return dimension;
    }
    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
    public int getLimit() {
        return limitvalue;
    }
    public void setLimit(int limitvalue) {
        this.limitvalue = limitvalue;
    }
    public String getFailstatergy() {
        return failstatergy;
    }
    public void setFailstatergy(String failstatergy) {
        this.failstatergy = failstatergy;
    }
    public Plan getPlan() {
        return planId;
    }
    public void setPlan(Plan planId) {
        this.planId = planId;
    }
    public String getApikey() {
        return Apikey;
    }
    public void setApikey(String apikey) {
        Apikey = apikey;
    }

    @Override
    public String toString() {
        return "RateLimitRule [ruleId=" + ruleId + ", Apikey=" + Apikey + ", urlendpoint=" + urlendpoint
                + ", dimension=" + dimension + ", limitvalue=" + limitvalue + ", failstatergy=" + failstatergy
                + ", planId=" + planId + "]";
    }

}
