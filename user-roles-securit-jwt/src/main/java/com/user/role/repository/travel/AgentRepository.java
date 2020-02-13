package com.user.role.repository.travel;

import com.user.role.models.travel.Agent;
import com.user.role.repository.AgentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent,Long> {

//    Optional<Agent> findById(Long userId);

    List<Agent> findByUserId(Long userId);
}
