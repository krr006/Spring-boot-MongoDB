package com.krr006.project_test.controller;

import com.krr006.project_test.dto.TextRecordRequest;
import com.krr006.project_test.dto.TextRecordResponse;
import com.krr006.project_test.service.TextRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/texts")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class TextRecordController {

    private final TextRecordService textRecordService;

    @PostMapping
    public ResponseEntity<TextRecordResponse> createTextRecord(@RequestBody TextRecordRequest textRecordRequest) {
        var data = textRecordRequest.getData();

        var textRecord = textRecordService.createTextRecord(data);

        TextRecordResponse response = new TextRecordResponse(textRecord.getId(), textRecord.getData());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<TextRecordResponse> getAllTexts() {
        return textRecordService.getAllTextsByUser().stream()
                .map(record -> new TextRecordResponse(record.getId(), record.getData()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TextRecordResponse> getTextById(@PathVariable Long id) {
        var textRecord = textRecordService.getTextById(id);

        TextRecordResponse response = new TextRecordResponse(textRecord.getId(), textRecord.getData());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TextRecordResponse> updateText(@PathVariable Long id, @RequestParam String data) {
        var textRecord = textRecordService.updateText(id, data);

        TextRecordResponse response = new TextRecordResponse(textRecord.getId(), textRecord.getData());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<TextRecordResponse> searchTexts(
            @RequestParam(required = false) Long id,
            @RequestBody(required = false) TextRecordRequest textRecordRequest) {

        String data = (textRecordRequest != null) ? textRecordRequest.getData() : null;

        return textRecordService.searchTexts(id, data).stream()
                .map(record -> new TextRecordResponse(record.getId(), record.getData()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteText(@PathVariable Long id) {
        textRecordService.deleteTextById(id);
    }
}
