package com.share.data.api.playground.jpa.sampleuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleUserRepository extends JpaRepository<SampleUserEntity, Long> {

    public SampleUserEntity findByName(String name);
}
