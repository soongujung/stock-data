package com.share.data.api.playground.jpa.sampleuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SampleUserRepository extends JpaRepository<SampleUserEntity, Long> {

    public Optional<SampleUserEntity> findByName(String name);
}
