package com.batch.flux_batch_consumer.model;

import com.batch.flux_batch_consumer.enumClasses.LineType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tbl_suvi_elimentation_details")
public class SuviElimentationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long elimentationId;
//    private String header;
    private String fluxName;
//    private String batchName;
//    private Instant createdAt;
//    private Instant updatedAt;
//    private Instant processedTime;
//    private String tail;
//    private List<FluxData> fluxDataList;

    private LineType lineType;
    private String recordId;
    private String customerId;
    private String classificationCode;
    private String customerCode;
    private String lineCode;
    private String fileDate;
    private String amount;
    private String regionType;
    private String code1;
    private String code2;
    private String code3;
    private int num1;
    private int num2;
    private int num3;
    private int num4;
    private String dataType;
    private int dataValue;
    private int dataValue1;
    private int dataValue2;
    private int dataValue3;
    private int dataValue4;
    private int dataValue5;
    private String rechargeType;
    private int rechargeCode;
    private String rechargeId;
    private String code4;
    private int num5;
    private Long rechargeValue;
    private int code5;

    private long lineNumber;

}
