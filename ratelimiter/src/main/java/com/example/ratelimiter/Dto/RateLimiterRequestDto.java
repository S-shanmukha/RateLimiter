package com.example.ratelimiter.Dto;

public class RateLimiterRequestDto {
        private String apiKey;
        private String userId;
        private String ipaddress;
        private String endpoint;

        public String getApiKey() {
            return apiKey;
        }
        public void setApiKey(String apikey) {
            this.apiKey = apikey;
        }
        public String getUserId() {
            return userId;
        }
        public void setUserId(String userId) {
            this.userId = userId;
        }
        public String getIpaddress() {
            return ipaddress;
        }
        public void setIpaddress(String ipaddress) {
            this.ipaddress = ipaddress;
        }
        public String getEndpoint() {
            return endpoint;
        }
        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }
}
