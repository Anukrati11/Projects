package com.batch.flux_batch_consumer.model;

import com.batch.flux_batch_consumer.enumClasses.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tbl_suvi_elimentation")
public class SuviElimentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String filePath;
    private Long totalRecords;
    private Long correctRecords;
    private Long duplicateRecords;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String statusDescription;
    private Instant createdAt;
    private String batchName;
    private String shsum;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "elimentationId")
    private List<SuviElimentationDetails> suviElimentationDetailsList;
}
