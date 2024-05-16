package daeucna.config.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import daeucna.mapper.primary.batch.MatchingInnerDelingMapper;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class BatchMatchingConfig {

    final JobRepository jobRepository;
    final PlatformTransactionManager batchTransactionManager;
    private static final int BATCH_SIZE = 50;
    
    @Autowired
    MatchingInnerDelingMapper matchingInnerDelingMapper;
    @Autowired
    MatchingItemReader customItemReader;
    @Autowired
    MatchingItemWriter<Map<String, Object>> matchingItemWriter;
    
    public BatchMatchingConfig(JobRepository jobRepository, PlatformTransactionManager batchTransactionManager) {
        this.jobRepository = jobRepository;
        this.batchTransactionManager = batchTransactionManager;
    }

    /**
     * Job which contains multiple steps
     */
    @Bean
    public Job matchingInnerDelng() {
        return new JobBuilder("자료매칭 job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .build();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	@JobScope
	public Step chunkStep() {
        log.debug("chunkStep");
        return new StepBuilder("자료매칭 step", jobRepository)
                .<Map, Map>chunk(BATCH_SIZE, batchTransactionManager)
                .reader(reader())
                .processor(compositeProcessor())
                .writer(writer())
                .build();
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public CompositeItemProcessor compositeProcessor() {
        log.debug("CompositeItemProcessor");
        
    	List<ItemProcessor> delegates = new ArrayList<>(2);
    	MatchingItemProcessorAuto customItemProcessorAuto = new MatchingItemProcessorAuto();
    	customItemProcessorAuto.setMapper(matchingInnerDelingMapper);
    	delegates.add(customItemProcessorAuto);

    	CompositeItemProcessor processor = new CompositeItemProcessor();

    	processor.setDelegates(delegates);

    	return processor;
    }    
	
	@Bean
	@JobScope
	public ItemReader<Map> reader() {
    	
        log.debug("run ItemReader");
        
        List<Map> lmData = customItemReader.customRead();
        return new ListItemReader<>(lmData);
    }

    @Bean
    public ItemWriter<Map> writer() {
    	
        ItemWriterAdapter<Map> writer = new ItemWriterAdapter<>();
        writer.setTargetObject(matchingItemWriter); // 대상 클래스
        writer.setTargetMethod("customWrite"); // 대상 메서드
        return writer;
    }
    
   
}