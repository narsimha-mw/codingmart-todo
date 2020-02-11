package com.travel.agent.role.security.services;

import com.travel.agent.role.models.Agent;
import com.travel.agent.role.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgentDetailsServiceImpl implements UserDetailsService {
	@Autowired
	AgentRepository agentRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Agent user = agentRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return AgentDetailsImpl.build(user);
	}

}
