package dev.piccodev.springbatch50.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    //Nessa classe, configuraremos o "Job" que queremos criar.

    //O Spring fornecerá a injeção do "JobRepository".

    @Bean
    public Job job(JobRepository jobRepository, Step step){

        return new JobBuilder("job", jobRepository)
                .start(step)
                .build();
    }


    //Abaixo, estamos criando um step do tipo "Tasklet" que imprime uma mensagem e retorna um
    //status de finalizado, utilizando um transaction manager.
    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("step", jobRepository)
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    System.out.println("Olá, mundo!");

                    return RepeatStatus.FINISHED;
        }, transactionManager)
                .build();
    }
}
