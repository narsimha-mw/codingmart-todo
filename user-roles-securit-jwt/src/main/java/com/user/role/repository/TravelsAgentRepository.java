package com.user.role.repository;

import com.user.role.dto.TravelsAgentDTO;
import com.user.role.models.travel.TravelsAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TravelsAgentRepository extends JpaRepository<TravelsAgent, Long> {

    Optional<TravelsAgent> findByIdAndUserId(Long agentId, Long userId);
    List<TravelsAgent> findByUserId(Long userId);
}
