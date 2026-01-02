-- =========================
-- PLAN TABLE
-- =========================
create table if not exists plan (
    planId UUID primary key,
    planName VARCHAR(100) not null
);

-- =========================
-- TENANT TABLE
-- =========================
create table if not exists tanent (
    tanentId UUID primary key,
    tanentName VARCHAR(100) not null,
    ApiKey VARCHAR(255) not null,
    planStatus VARCHAR(50) not null,
    maxRequestsPerMinute INT not null,
    planId UUID not null,
    foreign key (planId) references plan(planId)
);

-- =========================
-- RATE LIMIT RULE TABLE
-- =========================
create table if not exists ratelimitrule (
    ruleId UUID primary key,
    Apikey VARCHAR(255) not null,
    urlendpoint VARCHAR(255) not null,
    dimension VARCHAR(50) not null,
    limitValue INT not null,
    failStrategy VARCHAR(50) not null,
    planId UUID not null,
    foreign key (planId) references plan(planId)
);

-- =========================
-- PLAN DATA
-- =========================
insert into plan (planId, planName)
select
    '11111111-1111-1111-1111-111111111111',
    'Basic'
where not exists (
    select 1 from plan where planId = '11111111-1111-1111-1111-111111111111'
);

insert into plan (planId, planName)
select
    '22222222-2222-2222-2222-222222222222',
    'Premium'
where not exists (
    select 1 from plan where planId = '22222222-2222-2222-2222-222222222222'
);

insert into plan (planId, planName)
select
    '33333333-3333-3333-3333-333333333333',
    'Enterprise'
where not exists (
    select 1 from plan where planId = '33333333-3333-3333-3333-333333333333'
);

-- =========================
-- TENANT DATA
-- =========================
insert into tanent (tanentId, tanentName, ApiKey, planStatus, maxRequestsPerMinute, planId)
select
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'TenantA',
    'APIKEY123456',
    'Active',
    1000,
    '11111111-1111-1111-1111-111111111111'
where not exists (
    select 1 from tanent where tanentId = 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'
);

insert into tanent (tanentId, tanentName, ApiKey, planStatus, maxRequestsPerMinute, planId)
select
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    'TenantB',
    'APIKEY654321',
    'Active',
    5000,
    '22222222-2222-2222-2222-222222222222'
where not exists (
    select 1 from tanent where tanentId = 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'
);

insert into tanent (tanentId, tanentName, ApiKey, planStatus, maxRequestsPerMinute, planId)
select
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    'TenantC',
    'APIKEY112233',
    'Active',
    20000,
    '33333333-3333-3333-3333-333333333333'
where not exists (
    select 1 from tanent where tanentId = 'cccccccc-cccc-cccc-cccc-cccccccccccc'
);

-- =========================
-- RATE LIMIT RULE DATA
-- =========================
insert into ratelimitrule (ruleId, Apikey, urlendpoint, dimension, limitValue, failStrategy, planId)
select
    'aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee',
    'APIKEY123456',
    '/api/v1/resourceA',
    'API',
    10,
    'REJECT',
    '11111111-1111-1111-1111-111111111111'
where not exists (
    select 1 from ratelimitrule
    where ruleId = 'aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee'
);

insert into ratelimitrule (ruleId, Apikey, urlendpoint, dimension, limitValue, failStrategy, planId)
select
    'aaaaaaaa-bbcb-cccc-dddd-eeeeeeeeeeee',
    'APIKEY123456',
    '/api/v1/resourceA',
    'USER',
    10,
    'REJECT',
    '11111111-1111-1111-1111-111111111111'
where not exists (
    select 1 from ratelimitrule
    where ruleId = 'aaaaaaaa-bbcb-cccc-dddd-eeeeeeeeeeee'
);

insert into ratelimitrule (ruleId, Apikey, urlendpoint, dimension, limitValue, failStrategy, planId)
select
    'ffffffff-1111-2222-3333-444444444444',
    'APIKEY654321',
    '/api/v1/resourceB',
    'USER',
    10,
    'THROTTLE',
    '22222222-2222-2222-2222-222222222222'
where not exists (
    select 1 from ratelimitrule
    where ruleId = 'ffffffff-1111-2222-3333-444444444444'
);

insert into ratelimitrule (ruleId, Apikey, urlendpoint, dimension, limitValue, failStrategy, planId)
select
    '55555555-6666-7777-8888-999999999999',
    'APIKEY112233',
    '/api/v1/resourceC',
    'IP',
    50,
    'REJECT',
    '33333333-3333-3333-3333-333333333333'
where not exists (
    select 1 from ratelimitrule
    where ruleId = '55555555-6666-7777-8888-999999999999'
);