package com.krr006.project_test.controller;

import com.krr006.project_test.dto.TextRecordRequest;
import com.krr006.project_test.dto.TextRecordResponse;
import com.krr006.project_test.entity.TextRecord;
import com.krr006.project_test.service.TextRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TextRecordControllerTest {

    @Mock
    private TextRecordService textRecordService;

    @InjectMocks
    private TextRecordController textRecordController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTextRecord() {
        TextRecordRequest request = new TextRecordRequest("Test data");
        TextRecord textRecord = new TextRecord(1L, "user1", "Test data");
        when(textRecordService.createTextRecord(request.getData())).thenReturn(textRecord);

        ResponseEntity<TextRecordResponse> response = textRecordController.createTextRecord(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(textRecord.getId(), response.getBody().getId());
        assertEquals(textRecord.getData(), response.getBody().getData());

        verify(textRecordService, times(1)).createTextRecord(request.getData());
    }

    @Test
    void testGetAllTexts() {
        List<TextRecord> textRecords = List.of(
                new TextRecord(1L, "user1", "Data 1"),
                new TextRecord(2L, "user1", "Data 2")
        );
        when(textRecordService.getAllTextsByUser()).thenReturn(textRecords);

        List<TextRecordResponse> responses = textRecordController.getAllTexts();

        assertEquals(2, responses.size());
        assertEquals(textRecords.get(0).getId(), responses.get(0).getId());
        assertEquals(textRecords.get(1).getId(), responses.get(1).getId());

        verify(textRecordService, times(1)).getAllTextsByUser();
    }

    @Test
    void testGetTextById() {
        TextRecord textRecord = new TextRecord(1L, "user1", "Test data");
        when(textRecordService.getTextById(1L)).thenReturn(textRecord);

        ResponseEntity<TextRecordResponse> response = textRecordController.getTextById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(textRecord.getId(), response.getBody().getId());
        assertEquals(textRecord.getData(), response.getBody().getData());

        verify(textRecordService, times(1)).getTextById(1L);
    }

    @Test
    void testUpdateText() {
        TextRecord textRecord = new TextRecord(1L, "user1", "Updated data");
        when(textRecordService.updateText(1L, "Updated data")).thenReturn(textRecord);

        ResponseEntity<TextRecordResponse> response = textRecordController.updateText(1L, "Updated data");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(textRecord.getId(), response.getBody().getId());
        assertEquals(textRecord.getData(), response.getBody().getData());

        verify(textRecordService, times(1)).updateText(1L, "Updated data");
    }

    @Test
    void testDeleteText() {
        doNothing().when(textRecordService).deleteTextById(1L);

        textRecordController.deleteText(1L);

        verify(textRecordService, times(1)).deleteTextById(1L);
    }
}
