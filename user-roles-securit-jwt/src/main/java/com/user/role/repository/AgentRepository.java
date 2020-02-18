package com.user.role.repository;

import com.user.role.models.travel.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    Object findByUserId(Long userId);

    Optional<Object> findByIdAndUserId(Long agentId, Long userId);
}
