package com.user.role.repository;

import com.user.role.models.travel.TravelsAgentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelsAgentFileRepository extends JpaRepository<TravelsAgentFile, String> {

}