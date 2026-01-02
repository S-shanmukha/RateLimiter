package com.example.ratelimiter.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
// import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Service;

import com.example.ratelimiter.Dto.RateLimiterRequestDto;
import com.example.ratelimiter.Dto.RateLimiterResponseDto;
import com.example.ratelimiter.model.EndpointNormalizer;
import com.example.ratelimiter.model.RateLimitConfigStore;
import com.example.ratelimiter.model.RateLimitRule;
import com.example.ratelimiter.model.RuleKey;
import com.example.ratelimiter.model.Tanent;



@Service
public class RateLimiterService {

    RedisTemplate<String, String> redisTemplate;
    DefaultRedisScript<List<Long>> tokenBucketScript;
    RateLimitConfigStore configStore;

    public RateLimiterService(RedisTemplate<String, String> redisTemplate,DefaultRedisScript<List<Long>> tokenBucketScript,RateLimitConfigStore configStore) {
        this.redisTemplate = redisTemplate;
        this.tokenBucketScript = tokenBucketScript;
        this.configStore = configStore;
    }


    public RateLimiterResponseDto rateLimiter(RateLimiterRequestDto requestDto) {
        String apiKey = requestDto.getApiKey();
        // System.out.println("API Key: " + apiKey);
        String userId = requestDto.getUserId();
        // System.out.println("User ID: " + userId);
        String ipaddress = requestDto.getIpaddress();
        // System.out.println("IP Address: " + ipaddress);
        String endpoint = requestDto.getEndpoint();
        endpoint = EndpointNormalizer.normalize(endpoint);
        // System.out.println("Normalized Endpoint: " + endpoint);
        // configStore.printall();
        UUID planId = configStore.getTenantByApiKey(apiKey).getPlan().getPlanId();
        // System.out.println("Plan ID: " + planId);
        int Companylimit = configStore.getTenantByApiKey(apiKey).getMaxRequestsPerMinute();

        RuleKey Userrule= new RuleKey(planId, "USER", endpoint);
        RuleKey IPrule= new RuleKey(planId, "IP", endpoint);
        RuleKey Apikeyrule= new RuleKey(planId, "API_KEY", endpoint);

        RateLimitRule UserRateLimitRule=configStore.getRuleByRuleKey(Userrule);
        // if(UserRateLimitRule==null){
        //     UserRateLimitRule=new RateLimitRule();
        //     UserRateLimitRule.setLimit(Companylimit);
        //     // System.out.println("User Rate Limit Rule not found, using company limit: " + Companylimit);
        // }
        RateLimitRule IPRateLimitRule=configStore.getRuleByRuleKey(IPrule);
        // if(IPRateLimitRule==null){
        //     // IPRateLimitRule=new RateLimitRule();
        //     IPRateLimitRule.setLimit(Companylimit);
        //     // System.out.println("IP Rate Limit Rule not found, using company limit: " + Companylimit);
        // }
        RateLimitRule ApiKeyRateLimitRule=configStore.getRuleByRuleKey(Apikeyrule);
        // if(ApiKeyRateLimitRule==null){
        //     ApiKeyRateLimitRule=new RateLimitRule();
        //     ApiKeyRateLimitRule.setLimit(Companylimit);
        //     // System.out.println("API Key Rate Limit Rule not found, using company limit: " + Companylimit);
        // }

        String hashTag = "tenant:" + apiKey;

        String CompanyKey="rate:{" + hashTag + "}:company";
        
        String userKey = "rate:{" + hashTag + "}:USER:" + userId + ":" + endpoint;
                    
        
        String ipKey ="rate:{" + hashTag + "}:IP:" + ipaddress + ":" + endpoint;
                    
        String apiKeyKey="rate:{" + hashTag + "}:API_KEY:" + endpoint;


       List<Long> result1 = redisTemplate.execute(
            tokenBucketScript,
            List.of(CompanyKey),     
            String.valueOf(Companylimit),                               // refill rate (1/sec)
            String.valueOf(System.currentTimeMillis()),
            "120",                             // ttl_seconds
            "30"                               // ttl_refresh_threshold
        );

        List<Long> result2 = redisTemplate.execute(
            tokenBucketScript,
            List.of(userKey),     
            String.valueOf(UserRateLimitRule.getLimit()),                               // refill rate (1/sec)
            String.valueOf(System.currentTimeMillis()),
            "120",                             // ttl_seconds
            "30"                               // ttl_refresh_threshold
        );

        // List<Long> result3 = redisTemplate.execute(
        //     tokenBucketScript,
        //     List.of(ipKey),     
        //     String.valueOf(IPRateLimitRule.getLimit()),                               // refill rate (1/sec)
        //     String.valueOf(System.currentTimeMillis()),
        //     "120",                             // ttl_seconds
        //     "30"                               // ttl_refresh_threshold
        // );

        // List<Long> result4 = redisTemplate.execute(
        //     tokenBucketScript,
        //     List.of(apiKeyKey),     
        //     String.valueOf(ApiKeyRateLimitRule.getLimit()),                               // refill rate (1/sec)
        //     String.valueOf(System.currentTimeMillis()),
        //     "120",                             // ttl_seconds
        //     "30"                               // ttl_refresh_threshold
        // );

        RateLimiterResponseDto responseDto = new RateLimiterResponseDto();
        if(result1.get(0)==1 && result2.get(0)==1 /*&& result3.get(0)==1 && result4.get(0)==1*/){
            responseDto.setAllowed(true);
            responseDto.setRetryAfter(-1);
            return responseDto;
        }
        else{
            responseDto.setAllowed(false);
            long retryAfterCompany = result1.get(1);
            long retryAfterUser = result2.get(1);
            // long retryAfterIP = result3.get(1);
            // long retryAfterApiKey = result4.get(1);

            long maxRetryAfter = /*Math.max*/(Math.max(retryAfterCompany, retryAfterUser)/*, Math.max(retryAfterIP, retryAfterApiKey)*/);
            responseDto.setRetryAfter((int) maxRetryAfter);
            return responseDto;
        }  
    }
}
