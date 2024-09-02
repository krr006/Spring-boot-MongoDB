package com.krr006.project_test.exception;

public class TextRecordNotFoundException extends RuntimeException {
    public TextRecordNotFoundException(Long id) {
            super("TextRecord with id=" + id + " not found");
        }
    }

