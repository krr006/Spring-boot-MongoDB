package com.krr006.project_test.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "texts")
@AllArgsConstructor
@NoArgsConstructor
public class TextRecord {
    @Id
    private Long id;

    private String userId;

    private String data;
}

