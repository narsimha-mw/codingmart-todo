package com.user.role.repository;

import com.user.role.dto.AgentDTO;
import com.user.role.models.travel.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Object> findByIdAndUserId(Long agentId, Long userId);

    List<AgentDTO> findByAgentName(String name);
}
