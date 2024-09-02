package com.krr006.project_test.repository;

import com.krr006.project_test.entity.TextRecord;
import com.krr006.project_test.service.SequenceGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class TextRecordRepositoryTest {

    @Autowired
    private TextRecordRepository textRecordRepository;

    @MockBean
    private SequenceGeneratorService sequenceGeneratorService;


    @Test
    public void testFindByUserId() {
        TextRecord record = new TextRecord();
        record.setId(sequenceGeneratorService.getNextSequence("texts_seq"));
        record.setUserId("user1");
        record.setData("Some test data");
        textRecordRepository.save(record);

        List<TextRecord> foundRecords = textRecordRepository.findByUserId("user1");

        assertThat(foundRecords).hasSize(1);
        assertThat(foundRecords.get(0).getUserId()).isEqualTo("user1");
    }

    @Test
    public void testFindByDataContaining() {
        TextRecord record = new TextRecord();
        record.setId(sequenceGeneratorService.getNextSequence("texts_seq"));
        record.setUserId("user2");
        record.setData("Some test data with a special word");
        textRecordRepository.save(record);

        List<TextRecord> foundRecords = textRecordRepository.findByDataContaining("special");

        assertThat(foundRecords).hasSize(1);
        assertThat(foundRecords.get(0).getData()).contains("special");
    }

    @Test
    public void testFindByIdAndDataContaining() {
        TextRecord record = new TextRecord();
        record.setId(sequenceGeneratorService.getNextSequence("texts_seq"));
        record.setUserId("user3");
        record.setData("Another test data with a keyword");
        textRecordRepository.save(record);

        List<TextRecord> foundRecords = textRecordRepository.findByIdAndDataContaining(record.getId(), "keyword");

        assertThat(foundRecords).hasSize(1);
        assertThat(foundRecords.get(0).getData()).contains("keyword");
    }
}
