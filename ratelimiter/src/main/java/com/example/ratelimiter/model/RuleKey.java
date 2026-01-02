package com.example.ratelimiter.model;

import java.util.Objects;
import java.util.UUID;

public class RuleKey {

    private final UUID companyId;
    private final String scope;      // IP / USER / API_KEY
    private final String endpoint;  // "/login", "/orders"

    public RuleKey(UUID companyId, String scope, String endpoint) {
        this.companyId = companyId;
        this.scope = scope;
        this.endpoint = endpoint;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public String getScope() {
        return scope;
    }

    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public String toString() {
        return "RuleKey [companyId=" + companyId + ", scope=" + scope + ", endpoint=" + endpoint + "]";
    }

    // 🔑 VERY IMPORTANT (Map uses this)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleKey)) return false;
        RuleKey ruleKey = (RuleKey) o;
        return Objects.equals(companyId, ruleKey.companyId)
                && Objects.equals(scope, ruleKey.scope)
                && Objects.equals(endpoint, ruleKey.endpoint);
    }

    // 🔑 VERY IMPORTANT (Map uses this)
    @Override
    public int hashCode() {
        return Objects.hash(companyId, scope, endpoint);
    }
}

