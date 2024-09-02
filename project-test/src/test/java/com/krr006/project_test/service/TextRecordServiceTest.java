package com.krr006.project_test.service;

import com.krr006.project_test.entity.TextRecord;
import com.krr006.project_test.exception.TextRecordNotFoundException;
import com.krr006.project_test.repository.TextRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TextRecordServiceTest {

    @Mock
    private TextRecordRepository textRecordRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @InjectMocks
    private TextRecordService textRecordService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user1");
    }

    @Test
    void testCreateTextRecord() {
        when(sequenceGeneratorService.getNextSequence("texts_seq")).thenReturn(1L);
        TextRecord textRecord = new TextRecord(1L, "user1", "Test data");
        when(textRecordRepository.save(any(TextRecord.class))).thenReturn(textRecord);

        TextRecord createdRecord = textRecordService.createTextRecord("Test data");

        assertNotNull(createdRecord);
        assertEquals(1L, createdRecord.getId());
        assertEquals("Test data", createdRecord.getData());
        assertEquals("user1", createdRecord.getUserId());

        verify(textRecordRepository, times(1)).save(any(TextRecord.class));
    }

    @Test
    void testGetAllTextsByUser() {
        List<TextRecord> textRecords = List.of(
                new TextRecord(1L, "user1", "Data 1"),
                new TextRecord(2L, "user1", "Data 2")
        );
        when(textRecordRepository.findByUserId("user1")).thenReturn(textRecords);

        List<TextRecord> result = textRecordService.getAllTextsByUser();

        assertEquals(2, result.size());
        assertEquals("Data 1", result.get(0).getData());
        assertEquals("Data 2", result.get(1).getData());

        verify(textRecordRepository, times(1)).findByUserId("user1");
    }

    @Test
    void testGetTextById() {
        TextRecord textRecord = new TextRecord(1L, "user1", "Test data");
        when(textRecordRepository.findById(1L)).thenReturn(Optional.of(textRecord));

        TextRecord result = textRecordService.getTextById(1L);

        assertNotNull(result);
        assertEquals("Test data", result.getData());

        verify(textRecordRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTextById_NotFound() {
        when(textRecordRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TextRecordNotFoundException.class, () -> textRecordService.getTextById(1L));
    }

    @Test
    void testDeleteTextById() {
        TextRecord textRecord = new TextRecord(1L, "user1", "Test data");
        when(textRecordRepository.findById(1L)).thenReturn(Optional.of(textRecord));
        doNothing().when(textRecordRepository).deleteById(1L);

        textRecordService.deleteTextById(1L);

        verify(textRecordRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateText() {
        TextRecord existingRecord = new TextRecord(1L, "user1", "Old data");
        when(textRecordRepository.findById(1L)).thenReturn(Optional.of(existingRecord));
        when(textRecordRepository.save(existingRecord)).thenReturn(existingRecord);

        TextRecord updatedRecord = textRecordService.updateText(1L, "New data");

        assertEquals("New data", updatedRecord.getData());
        verify(textRecordRepository, times(1)).save(existingRecord);
    }
}
