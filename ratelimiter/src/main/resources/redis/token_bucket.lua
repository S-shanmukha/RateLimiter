local key = KEYS[1]

local capacity = tonumber(ARGV[1])
local refill_rate = 1 -- TEMP only
local now = tonumber(ARGV[3])
local ttl_seconds = tonumber(ARGV[4])
local ttl_refresh_threshold = tonumber(ARGV[5]) or 0

-- HARD SAFETY GUARD
if capacity == nil or now == nil or ttl_seconds == nil then
    return {0, -800}
end

local data = redis.call("HMGET", key, "tokens", "lastRefill")
local tokens = tonumber(data[1])
local lastRefill = tonumber(data[2])

if tokens == nil or lastRefill == nil then
    tokens = capacity
    lastRefill = now
    redis.call("HMSET", key,
        "capacity", capacity,
        "tokens", tokens,
        "lastRefill", lastRefill
    )
    redis.call("EXPIRE", key, ttl_seconds)
end

local elapsed = (now - lastRefill) / 1000
if elapsed > 0 then
    tokens = math.min(capacity, tokens + elapsed * refill_rate)
    lastRefill = now
end

local allowed = 0
local retry_after = 0

if tokens >= 1 then
    tokens = tokens - 1
    allowed = 1
else
    retry_after = math.ceil((1 - tokens) / refill_rate)
end

redis.call("HMSET", key,
    "tokens", tokens,
    "lastRefill", lastRefill
)

local ttl = redis.call("TTL", key)
if ttl == nil or ttl < 0 or ttl < ttl_refresh_threshold then
    redis.call("EXPIRE", key, ttl_seconds)
end

return { allowed, retry_after }

