package com.example.ratelimiter.model;

import java.util.HashMap;
// import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class RateLimitConfigStore {

        private Map<String, Tanent> tanentMap = new HashMap<>();
        private Map<UUID, Plan> plansMap = new HashMap<>();
        private Map<RuleKey, RateLimitRule> ruleMap = new HashMap<>();

        public synchronized void replace(Map<String, Tanent> tanentMap, Map<UUID, Plan> plansMap, Map<RuleKey, RateLimitRule> ruleMap) {
                this.tanentMap = tanentMap;
                this.plansMap = plansMap;
                this.ruleMap = ruleMap;
        }

        public Tanent getTenantByApiKey(String apiKey) {
        return tanentMap.get(apiKey);
        }
        public Plan getPlanById(UUID planId) {
                return plansMap.get(planId);
        }
        public RateLimitRule getRuleByRuleKey(RuleKey ruleKey) {
                return ruleMap.get(ruleKey);
        }

        public void printall() {
                System.out.println("Tenants:");
                tanentMap.forEach((k, v) -> System.out.println(k + " -> " + v));
                System.out.println("Plans:");
                plansMap.forEach((k, v) -> System.out.println(k + " -> " + v));
                System.out.println("Rules:");
                ruleMap.forEach((k, v) -> System.out.println(k + " -> " + v));
        }



}