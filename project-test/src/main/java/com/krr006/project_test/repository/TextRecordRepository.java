package com.krr006.project_test.repository;

import com.krr006.project_test.entity.TextRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TextRecordRepository extends MongoRepository<TextRecord, Long> {
    List<TextRecord> findByUserId(String userId);
    List<TextRecord> findByDataContaining(String data);
    List<TextRecord> findByIdAndDataContaining(Long id, String data);
}