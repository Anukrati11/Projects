package com.batch.flux_batch_consumer.processor;

import com.batch.flux_batch_consumer.service.ChecksumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class ChecksumProcessor implements ItemProcessor<File, File>, StepExecutionListener {

    private StepExecution stepExecution;
    @Autowired
    private ChecksumService checksumService;

    @Override
    public File process(File file) throws Exception {
        String checksum = checksumService.generateChecksum(file.getPath());
        if (checksumService.checksumExists(checksum)) {
            throw new Exception("File already processed with checksum : "+checksum);
        }
        // Save checksum in the StepExecution's ExecutionContext
        stepExecution.getJobExecution().getExecutionContext().putString("checksum", checksum);
        log.info("Checksum generated for file : "+file.getName()+" is : "+stepExecution.getJobExecution().getExecutionContext().getString("checksum"));
        return file;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}