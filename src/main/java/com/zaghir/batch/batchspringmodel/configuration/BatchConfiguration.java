package com.zaghir.batch.batchspringmodel.configuration;

/**
 * 
	Ce projet est de type Maven avec l’implementation de springboot 2.1.1 pour démarrer  le projet
	Il implémente aussi :
	Poi 4.0.0 pour la creation des fichier excel 
	JavamailSender pour l’envoi de mail , le paramétrage ici c’est fait pour un smtp de google
	Spring 5 pour l’injection des dépendances 
	Spring batch 4 pour la création de batch 
	Thymeleaf 3 et Thymeleaf spring5 pour le moteur de Template et la création des gabarits html des mails
	Jdbc de type Mysql pour la connexion au base de données, c'est possible d'ajouter d'autre jdbc pour d'autre types de base de données
	    et aussi configurer les datasource
	La datasource c'est la datasource primary (@Primary) car c'est dans cette base ou se trouve les table technique de spring batch
	Dans le dossier spring-batch-model/information  se trouve une image pour trouver les script de creation des tables techniques 
	
	Le batch est composé de 
	Un seul step mais c’est possible d’ajouter d’autre 
	Service dao pour la récupération des données et l’insertion : CompteDao , la dao se base sur JdbcTemplate pour passer le requêtes 
	Un service excel pour créer un fichier excel 
	Un servie mail pour l’envoi de mail EnvoiMailService ,et un service mail pour la configuration de l’envoi SendMailService
	Le Step du batch fait :
		Dans le ReaderStep1 : la lecture de données depuis la datasource1 et passe la donnée au ProcessorStep1 
		Dans le ProcessorStep1 : récupère les données depuis le reader , insère la donnée avec la deuxième datasoure2 dans le base2 et passe la donnée au writer
		Dans le WriterStep1 : récupère la liste des données  traités dans processor ,créer un fichier excel avec la liste des données(Compte dans notre exemple) et envoie le mail par le EnvoiMailService, avec en pièce jointe le fichier Excel et les données variables qui seront injecter dans le gabarit de mail
		
		

 */

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.policy.DefaultResultCompletionPolicy;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;
import com.zaghir.batch.batchspringmodel.dao.compteDao.CompteDao;
import com.zaghir.batch.batchspringmodel.dao.compteDao.CompteDaoImpl;
import com.zaghir.batch.batchspringmodel.service.excel.ExcelService;
import com.zaghir.batch.batchspringmodel.service.excel.ExcelServiceImpl;
import com.zaghir.batch.batchspringmodel.steps.liteners.JobListener;
import com.zaghir.batch.batchspringmodel.steps.liteners.ListenerStep1;
import com.zaghir.batch.batchspringmodel.steps.processors.ProcessorStep1;
import com.zaghir.batch.batchspringmodel.steps.readers.ReaderStep1;
import com.zaghir.batch.batchspringmodel.steps.writers.WriterStep1;

@Configuration
@PropertySources({
	@PropertySource("/batchConfiguration.properties"),
	@PropertySource("/datasource.properties"),
		@PropertySource("/application.properties"),
		@PropertySource("/application.properties"),
		@PropertySource("/mailConfiguration.properties"),
		@PropertySource("/queries.xml") 
	})
public class BatchConfiguration {

	@Value("${commitInterval}")
	private String commitInterval;

	@Autowired
	private JobBuilderFactory jobBuilders;

	@Autowired
	private StepBuilderFactory stepBuilders;

	@Bean
	public JobExecutionListener getJobListener() {
		return new JobListener();
	}

	@Bean
	public PlatformTransactionManager getTransactionManager() {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public JobRepository getJobRepo() throws Exception {
		return new MapJobRepositoryFactoryBean(getTransactionManager()).getObject();
	}

	public String getCommitInterval() {
		return commitInterval;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public JobBuilderFactory getJobBuilders() {
		return jobBuilders;
	}

	public void setJobBuilders(JobBuilderFactory aJobBuilders) {
		jobBuilders = aJobBuilders;
	}

	public StepBuilderFactory getStepBuilders() {
		return stepBuilders;
	}

	public void setStepBuilders(StepBuilderFactory aStepBuilders) {
		stepBuilders = aStepBuilders;
	}

	public void setCommitInterval(String commitInterval) {
		this.commitInterval = commitInterval;
	}

	@Bean
	public ItemReader<CompteBean> getReaderStep1() {
		return new ReaderStep1();
	}

	@Bean
	public ItemProcessor<CompteBean, CompteBean> getProcessorStep1() {
		return new ProcessorStep1();
	}

	@Bean
	public ItemWriter<CompteBean> getWriterStep1() {
		return new WriterStep1();
	}

	@Bean
	public StepExecutionListener getListenerStep1() {
		return new ListenerStep1();
	}

	@Bean
	@StepScope
	public CompletionPolicy customCompletionPolicy() {
		return new CustomCompletionPolicy(this.getCommitInterval());
	}

	// annuler la regle de commit inteval
	@Bean
	@StepScope
	public CompletionPolicy completionPolicy() {
		return new DefaultResultCompletionPolicy();
	}

	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory) {

		return stepBuilderFactory.get("step1").<CompteBean, CompteBean> chunk(completionPolicy())
				.reader(getReaderStep1()).processor(getProcessorStep1()).writer(getWriterStep1())
				// .faultTolerant().skip(Exception.class).skipLimit(20000)
				.listener(getListenerStep1()).build();
	}

	/* -------------------------------------------------------------------- */
	/* ---------------------DEFINITION DES BEANS--------------------------- */
	/* -------------------------------------------------------------------- */

	@Bean
	public Job premierJob(JobBuilderFactory jobs, @Qualifier("step1") Step step1) {
		return jobs.get("premierJob").incrementer(new RunIdIncrementer()).flow(step1).end()
				.listener(getJobListener()).build();

	}
	
	@Bean
	public CompteDao compteDao(){
		return new CompteDaoImpl();
	}
	
	@Bean
	public ExcelService excelService(){
		return new ExcelServiceImpl();
	}
	
	

}
