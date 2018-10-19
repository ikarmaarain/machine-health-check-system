package com.example.machinehealthchecksystem.config;

import com.example.machinehealthchecksystem.model.Machine;
import com.example.machinehealthchecksystem.processor.MachineItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Machine> reader() {
        return new FlatFileItemReaderBuilder<Machine>()
                .name("machineItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"machineName", "ipAddress"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Machine>() {{
                    setTargetType(Machine.class);
                }})
                .build();
    }

    @Bean
    public MachineItemProcessor processor() {
        return new MachineItemProcessor();
    }

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Machine, Machine> chunk(10)
                .reader(reader())
                .processor(processor())
                .build();
    }
}
