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
@Table(name = "tanent")
public class Tanent {
    
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "tanentId")
        private UUID tanentId;

        @Column(name = "tanentName",nullable = false)
        private String tanentName;

        @Column(name ="ApiKey", nullable = false)
        private String ApiKey;

        @Column(name = "planStatus", nullable = false)
        private String planStatus;

        @Column(name = "maxRequestsPerMinute", nullable = false)
        private int maxRequestsPerMinute;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "planId", nullable = false)
        private Plan planId;

        public UUID getTanentId() {
            return tanentId;
        }
        public void setTanentId(UUID tanentId) {
            this.tanentId = tanentId;
        }
        public String getApiKey() {
            return ApiKey;
        }
        public void setApiKey(String apiKey) {
            ApiKey = apiKey;
        }
        public String getPlanStatus() {
            return planStatus;
        }
        public void setPlanStatus(String planStatus) {
            this.planStatus = planStatus;
        }
        public Plan getPlan() {
            return planId;
        }
        public void setPlan(Plan planId) {
            this.planId = planId;
        }
        public int getMaxRequestsPerMinute() {
            return maxRequestsPerMinute;
        }
        public void setMaxRequestsPerMinute(int maxRequestsPerMinute) {
            this.maxRequestsPerMinute = maxRequestsPerMinute;
        }
        public String getTanentName() {
            return tanentName;
        }
        public void setTanentName(String tanentName) {
            this.tanentName = tanentName;
        }

        @Override
        public String toString() {
            return "Tanent [tanentId=" + tanentId + ", tanentName=" + tanentName + ", ApiKey=" + ApiKey
                    + ", planStatus=" + planStatus + ", maxRequestsPerMinute=" + maxRequestsPerMinute
                    + ", planId=" + planId + "]";
        }
}