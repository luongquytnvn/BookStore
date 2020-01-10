package com.codegym.repositories;

import com.codegym.models.Publishing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishingRepository extends CrudRepository<Publishing, Long> {
}
