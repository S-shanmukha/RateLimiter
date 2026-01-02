package com.example.ratelimiter.model;

// import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.ratelimiter.Repository.PlanRepository;
import com.example.ratelimiter.Repository.RateLimitRuleRepository;
import com.example.ratelimiter.Repository.TenantRepository;

import jakarta.annotation.PostConstruct;

@Component
public class RateLimitConfigLoader {

    private final TenantRepository tenantRepo;
    private final PlanRepository planRepo;
    private final RateLimitRuleRepository ruleRepo;
    private final RateLimitConfigStore store;

    public RateLimitConfigLoader(
            TenantRepository tenantRepo,
            PlanRepository planRepo,
            RateLimitRuleRepository ruleRepo,
            RateLimitConfigStore store) {

        this.tenantRepo = tenantRepo;
        this.planRepo = planRepo;
        this.ruleRepo = ruleRepo;
        this.store = store;
    }

    @PostConstruct
    public void loadAtStartup() {
        loadFromDb();
    }

    // 🔹 Runs EVERY 5 minutes after startup
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void refreshPeriodically() {
        loadFromDb();
    }

    public void loadFromDb() {
        var tenants = tenantRepo.findAll();
        var plans = planRepo.findAll();
        var rules = ruleRepo.findAll();

         Map<String, Tanent> tenant =
                tenants.stream()
                        .collect(Collectors.toMap(
                                Tanent::getApiKey,
                                t -> t
                        ));

        // 2️⃣ plans: planId → Plan
        Map<UUID, Plan> plan=
                plans.stream()
                        .collect(Collectors.toMap(
                                Plan::getPlanId,
                                p -> p
                        ));



        // 3️⃣ rules: planId → List<Rule>
        Map<RuleKey, RateLimitRule> rule =
                rules.stream()
                        .collect(Collectors.toMap(
                                r -> new RuleKey(
                                        r.getPlan().getPlanId(),
                                        r.getDimension(),
                                        r.getUrlendpoint()
                                ),
                                r -> r
                        ));

        store.replace(tenant, plan, rule);
        
    }
    
}
