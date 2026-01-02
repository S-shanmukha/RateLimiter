package com.example.ratelimiter.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ratelimiter.model.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, UUID> {
}
