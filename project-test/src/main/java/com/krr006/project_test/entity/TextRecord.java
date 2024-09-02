package com.krr006.project_test.entity;
import com.krr006.project_test.service.SequenceGeneratorService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;

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


//    @Transient
//    @Autowired
//    private SequenceGeneratorService sequenceGeneratorService;

//    @PostConstruct
//    public void init() {
//        if (id == null) {
//            this.id = sequenceGeneratorService.getNextSequence("texts_seq");
//        }
//    }
}

