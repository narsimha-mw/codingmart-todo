package com.user.role.repository;

import com.user.role.models.travel.DriverInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverInfoRepository extends JpaRepository<DriverInfo, String> {

}