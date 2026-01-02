package com.example.ratelimiter.model;

// import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plan")
public class Plan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "planId")
    private UUID planId;

    @Column(name = "planName", nullable = false)
    private String planName;

    public UUID getPlanId() {
        return planId;
    }
    public void setPlanId(UUID planId) {
        this.planId = planId;
    }
    public String getPlanName() {
        return planName;
    }
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    public String toString() {
        return "Plan [planId=" + planId + ", planName=" + planName + "]";
    }
}