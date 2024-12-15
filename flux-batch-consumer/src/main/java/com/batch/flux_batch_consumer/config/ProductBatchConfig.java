//package com.batch.flux_batch_consumer.config;
//
//import com.batch.flux_batch_consumer.listener.MyJobListener;
//import com.batch.flux_batch_consumer.model.Product;
//import com.batch.flux_batch_consumer.processor.ProductProcessor;
//import com.batch.flux_batch_consumer.repository.ProductRepository;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecutionListener;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//@EnableBatchProcessing
//public class ProductBatchConfig {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    // 1. Reader class object
//    @Bean
//    public FlatFileItemReader<Product> reader() {
//        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
//        reader.setResource(new ClassPathResource("products.csv"));
//
//        reader.setLineMapper(new DefaultLineMapper<>() {{
//            setLineTokenizer(new DelimitedLineTokenizer() {{
//                setDelimiter(DELIMITER_COMMA);
//                setNames("prodId", "prodName", "prodCost");
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
//                setTargetType(Product.class);
//            }});
//        }});
//
//        return reader;
//    }
//
//
//    // 2. Processor class object
//    @Bean
//    public ItemProcessor<Product, Product> processor(){
//        return new ProductProcessor();
//    }
//
//
//    // 3. Writer class
//    @Bean
//    public RepositoryItemWriter<Product> writer(){
//        RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
//        writer.setRepository(productRepository);
//        writer.setMethodName("save");
//        return writer;
//    }
//
//
//    // 4. listener class object
//    @Bean
//    public JobExecutionListener listener(){
//        return new MyJobListener();
//    }
//
//    // 5. Step builder factory class object
//    @Bean
//    public Step step(
//            JobRepository jobRepository){
//        return new StepBuilder("jobStep", jobRepository)
//                .<Product, Product>chunk(10, transactionManager())
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//    }
//
//    // 7. job builder factory class object
//    @Bean
//    public Job job(JobRepository jobRepository){
//        return new JobBuilder("job", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .listener(listener())
//                .start(step(jobRepository))
//                .build();
//    }
//
//
//
//
//
//
//}
