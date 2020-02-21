import java.util.List;

public interface AgentService {
    List<Agents> allAgentDetails(Long userId);

    void saveAgentDetails(Long userId, Agents agent);
}
