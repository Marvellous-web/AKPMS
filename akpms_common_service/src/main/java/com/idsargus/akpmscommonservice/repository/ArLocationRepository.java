package com.idsargus.akpmscommonservice.repository;

import com.idsargus.akpmscommonservice.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArLocationRepository extends JpaRepository<Location, Integer> {
}
