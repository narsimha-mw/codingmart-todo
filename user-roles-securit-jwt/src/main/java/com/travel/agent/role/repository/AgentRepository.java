package com.travel.agent.role.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.agent.role.models.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
	Optional<Agent> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
