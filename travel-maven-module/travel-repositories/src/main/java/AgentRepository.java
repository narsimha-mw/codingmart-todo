import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agents, Long> {

    Optional<Object> findByIdAndUserId(Long agentId, Long userId);

}
