package com.example.ignite_demo3;

import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.springframework.stereotype.Repository;

@RepositoryConfig(cacheName = "Toy")
public interface ToyRepository extends IgniteRepository<ToyModel, String> {
}
