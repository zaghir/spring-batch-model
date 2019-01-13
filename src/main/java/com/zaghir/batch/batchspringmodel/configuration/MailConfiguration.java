package com.zaghir.batch.batchspringmodel.configuration;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import com.zaghir.batch.batchspringmodel.service.mail.EnvoiMailService;
import com.zaghir.batch.batchspringmodel.service.mail.SendMailService;
import com.zaghir.batch.batchspringmodel.service.mail.impl.EnvoiMailServiceImpl;
import com.zaghir.batch.batchspringmodel.service.mail.impl.SendMailServiceImpl;



@Configuration
@PropertySources({	
	@PropertySource("/batchConfiguration.properties"),
	@PropertySource("/datasource.properties"),
	@PropertySource("/application.properties"),
	@PropertySource("/application.properties"),
	@PropertySource("/mailConfiguration.properties")
})
public class MailConfiguration {
	
	@Value("${spring.mail.host}")
	private String smtpServerHost;

	@Value("${spring.mail.username}")
	private String smtpServerUsername;

	@Value("${spring.mail.password}")
	private String smtpServerPassword;

	@Value("${spring.mail.properties.mail.transport.protocol}")
	private String smtpServerPropertiesTransportProtocol;

	@Value("${spring.mail.properties.mail.smtp.port}")
	private String smtpServerPropertiesPort;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String smtpServerPropertiesAuth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String smtpServerPropertiesStarttlsEnable;

	@Value("${spring.mail.properties.mail.smtp.starttls.required}")
	private String smtpServerPropertiesStarttlsRequired;
	
	@Value("${spring.mail.port}")
	private String smtpPort;
	
	
	/*
	 * On construit le bean JavaMailSenderImpl qui sera utiliser comme dependance dans le servie SendMailService
	 */
	@Bean
   	public SendMailService sendMailService(){			
   		return new SendMailServiceImpl();
   	}
	@Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol(smtpServerPropertiesTransportProtocol);
        javaMailSender.setHost(smtpServerHost);        
        javaMailSender.setUsername(smtpServerUsername);
        javaMailSender.setPassword(smtpServerPassword);
        javaMailSender.setPort(Integer.parseInt(smtpPort));
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", smtpServerPropertiesTransportProtocol);
        properties.put("mail.smtp.port", Integer.parseInt(smtpServerPropertiesPort));
        properties.put("mail.smtp.auth", Boolean.parseBoolean(smtpServerPropertiesAuth));
        properties.put("mail.smtp.starttls.enable", Boolean.parseBoolean(smtpServerPropertiesStarttlsEnable));
        properties.put("mail.smtp.starttls.required", Boolean.parseBoolean(smtpServerPropertiesStarttlsRequired));
        properties.put("mail.smtp.socketFactory.port", 465);
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory" );
        properties.put("mail.smtp.socketFactory.fallBack",false );
        
        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }
	
	
	@Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver htmlTemplateResolver(){
        SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
        emailTemplateResolver.setOrder(Integer.valueOf(1));        
//        emailTemplateResolver.setResolvablePatterns(Collections.singleton("mail/*"));
        emailTemplateResolver.setPrefix("/templates/");
        emailTemplateResolver.setSuffix(".html");
        //emailTemplateResolver.setTemplateMode(  StandardTemplateModeHandlers.HTML5.getTemplateModeName());
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        emailTemplateResolver.setCacheable(false);        
        return emailTemplateResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource(){
    	ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
    	bundleMessageSource.setDefaultEncoding("UTF-8");
    	bundleMessageSource.setBasename("classpath:listePremierVersementEligibleEasirisConfiguration");
    	bundleMessageSource.setDefaultEncoding("UTF-8");
    	bundleMessageSource.setFallbackToSystemLocale(true);
    	bundleMessageSource.setCacheSeconds(3600);
    	return bundleMessageSource ;
    }
    
    
    @Bean
	public EnvoiMailService envoiMailService(){
		return new EnvoiMailServiceImpl();
	}

}
