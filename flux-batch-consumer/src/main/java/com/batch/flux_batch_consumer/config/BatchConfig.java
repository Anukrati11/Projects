package com.batch.flux_batch_consumer.config;

import com.batch.flux_batch_consumer.model.SuviElimentation;
import com.batch.flux_batch_consumer.model.SuviElimentationDetails;
import com.batch.flux_batch_consumer.processor.ChecksumProcessor;
import com.batch.flux_batch_consumer.reader.FileItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * Job definition for processing the flux file.
     * @param checksumStep
     * @param processFluxFileStep
     * @return
     */
    @Bean
    public Job processFluxFileJob(@Qualifier("checksumCheckStep") Step checksumStep, @Qualifier ("processFluxFileStep") Step processFluxFileStep) {
        return new JobBuilder("processFileJob", jobRepository)
                .incrementer(new RunIdIncrementer())  // Ensures each run gets a unique ID
                .start(checksumStep) //The job starts with checksumStep
                .next(processFluxFileStep) // Next step is step1
                .build();
    }


    /**
     * Step 1 - Step for validating the checksum of the file.
     * @param checksumProcessor
     * @return
     */
    @Bean
    public Step checksumCheckStep(ChecksumProcessor checksumProcessor) {
        return new StepBuilder("checksumStep", jobRepository)
                .<File, File>chunk(1, transactionManager)
                .reader(checksumFileReader())
                .processor(checksumProcessor)
                .writer(writer -> {}) // No-op writer
                .listener(checksumProcessor) // Register ChecksumProcessor as a listener
                .build();
    }


    /**
     * Step 2 - Step for processing and validating the flux file.
     * @param reader
     * @param processor
     * @param writer
     * @return
     */
    @Bean
    public Step processFluxFileStep(ItemReader<SuviElimentation> reader, ItemProcessor<SuviElimentation, SuviElimentation> processor,
                                    ItemWriter<SuviElimentation> writer) {
        return new StepBuilder("step2", jobRepository)
                .<SuviElimentation, SuviElimentation>chunk(10, transactionManager) // Chunk-oriented processing
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener((StepExecutionListener) reader) // Register FileItemReader as a listener
                .build();
    }


    /**
     * This method defines a custom file reader that reads data from a file, on given location.
     * @return
     * @throws IOException
     */
    @Bean
    public FileItemReader fileItemReader() throws IOException {
        // Specify the path to the input file here
        String filePath = "C:\\Users\\anuverma\\Downloads\\NA076008.0684.SRRXMS.2227.2301121637";
        return new FileItemReader(filePath);
    }

    @Bean
    public ItemReader<File> checksumFileReader() {
        return new ItemReader<File>() {
            private boolean fileRead = false;

            @Override
            public File read() {
                if (!fileRead) {
                    fileRead = true;
                    return new File("C:\\Users\\anuverma\\Downloads\\NA076008.0684.SRRXMS.2227.2301121637");
                } else {
                    return null;
                }
            }
        };
    }

}
