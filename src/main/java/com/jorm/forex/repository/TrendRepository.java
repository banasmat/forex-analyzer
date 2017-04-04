package com.jorm.forex.repository;

import com.jorm.forex.model.Trend;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TrendRepository extends CrudRepository<Trend, Long>, JpaSpecificationExecutor<Trend> {
}
