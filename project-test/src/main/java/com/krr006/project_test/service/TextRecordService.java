package com.krr006.project_test.service;

import com.krr006.project_test.entity.TextRecord;
import com.krr006.project_test.exception.TextRecordNotFoundException;
import com.krr006.project_test.repository.TextRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextRecordService {

    private final TextRecordRepository textRecordRepository;

    private final SequenceGeneratorService sequenceGeneratorService;


    public TextRecord createTextRecord(String data) {
        String userId = getCurrentUserId();

        Long id = sequenceGeneratorService.getNextSequence("texts_seq");

        TextRecord textRecord = TextRecord.builder()
                .id(id)
                .userId(userId)
                .data(data)
                .build();

        return textRecordRepository.save(textRecord);
    }

    public List<TextRecord> getAllTextsByUser() {
        String userId = getCurrentUserId();
        return textRecordRepository.findByUserId(userId);
    }

    public TextRecord getTextById(Long id) {
        String userId = getCurrentUserId();
        TextRecord textRecord = textRecordRepository.findById(id)
                .orElseThrow(() -> new TextRecordNotFoundException(id));

        if (!textRecord.getUserId().equals(userId)) {
            throw new SecurityException("You do not have permission to access this record.");
        }


        return textRecord;
    }

    public void deleteTextById(Long id) {
        String userId = getCurrentUserId();
        TextRecord textRecord = textRecordRepository.findById(id)
                .orElseThrow(() -> new TextRecordNotFoundException(id));

        if (!textRecord.getUserId().equals(userId)) {
            throw new SecurityException("You do not have permission to delete this record.");
        }

        textRecordRepository.deleteById(id);
    }

    @Transactional
    public TextRecord updateText(Long id, String data) {
        String userId = getCurrentUserId();
        TextRecord existingText = textRecordRepository.findById(id)
                .orElseThrow(() -> new TextRecordNotFoundException(id));

        if (!existingText.getUserId().equals(userId)) {
            throw new SecurityException("You do not have permission to update this record.");
        }

        existingText.setData(data);

        return textRecordRepository.save(existingText);

    }

    public List<TextRecord> searchTexts(Long id, String data) {
        String userId = getCurrentUserId();

        if (id != null && data != null) {
            return textRecordRepository.findByIdAndDataContaining(id, data).stream()
                    .filter(record -> record.getUserId().equals(userId))
                    .toList();
        } else if (data != null) {
            return textRecordRepository.findByDataContaining(data).stream()
                    .filter(record -> record.getUserId().equals(userId))
                    .toList();
        } else if (id != null) {
            TextRecord textRecord = textRecordRepository.findById(id).orElseThrow(() -> new TextRecordNotFoundException(id));
            if (textRecord.getUserId().equals(userId)) {
                return List.of(textRecord);
            } else throw new SecurityException("You do not have permission to update this record.");
        } else {
            return textRecordRepository.findByUserId(userId);
        }
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
