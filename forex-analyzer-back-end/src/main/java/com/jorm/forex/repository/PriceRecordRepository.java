package com.jorm.forex.repository;

import com.jorm.forex.model.PriceRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PriceRecordRepository extends CrudRepository<PriceRecord, Long>, JpaSpecificationExecutor<PriceRecord> {
}
