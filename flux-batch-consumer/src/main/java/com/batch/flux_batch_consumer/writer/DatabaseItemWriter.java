package com.batch.flux_batch_consumer.writer;

import com.batch.flux_batch_consumer.model.SuviElimentation;
import com.batch.flux_batch_consumer.repository.SuviElimentationRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.batch.flux_batch_consumer.model.SuviElimentationDetails;
import com.batch.flux_batch_consumer.repository.SuviElimentationDetailsRepository;

import java.util.List;

@Component
public class DatabaseItemWriter implements ItemWriter<SuviElimentation> {

    @Autowired
    private SuviElimentationRepository suviElimentationRepository;

//    @Override
//    public void write(List<? extends SuviElimentationDetails> items) throws Exception {
//        // Save each item to the database
//        suviElimentationDetailsRepository.saveAll(items);
//    }

    @Override
    public void write(Chunk<? extends SuviElimentation> chunk) throws Exception {
        // Save the chunks of item to the database
        suviElimentationRepository.saveAll(chunk.getItems());
    }
}
