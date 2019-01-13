package com.zaghir.batch.batchspringmodel.service.mail.impl;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.zaghir.batch.batchspringmodel.service.mail.SendMailService;

@Component
public class SendMailServiceImpl implements SendMailService {
	private static final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}


	
	
	public void sendMail(String mailFrom , String mailsTo, String mailSubject, String templateMail  ,Map<String, InputStream> listInputfiles){ // , List<> 
		MimeMessage message = mailSender.createMimeMessage();
		 try {
			MimeMessageHelper helper = new MimeMessageHelper( message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
			
//			helper.addAttachment("logo.png", new ClassPathResource("/img/Stormtrooper-star-wors.jpg"));
//
	        Context context = new Context();
	        //context.setVariables(mail.getModel());
	        String bodyHtml = templateEngine.process(templateMail, context);

	        /*Fonction pour l'envoi de mail à une liste des personnes */
	        String[] listMail = mailsTo.split(";"); 
	        for(int i=0 ; i< listMail.length; i++){
	        	listMail[i] = listMail[i].trim();	
			}
	        
	        helper.setTo(listMail);
	        helper.setText(bodyHtml, true);
	        helper.setSubject(mailSubject);
	        helper.setFrom(mailFrom);
	        // Attachement d'un liste des fichiers de type InputStream
			if (listInputfiles != null) {
				for (String nameFile : listInputfiles.keySet()) {
					InputStream file = listInputfiles.get(nameFile);
					helper.addAttachment(nameFile, new ByteArrayResource(IOUtils.toByteArray(file)));
				}
			}	        

	        mailSender.send(message);
			
		} catch (Exception e) { 
			logger.info("Erreur sur le service d'envoi de mail ==> {}", e.getMessage());
		}
	}
	
	public void sendMail(String mailFrom , String mailsTo, String mailSubject, String templateMail  ,Map<String, InputStream> listInputfiles , Map model){
		MimeMessage message = mailSender.createMimeMessage();
		 try {
			MimeMessageHelper helper = new MimeMessageHelper( message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
			
//			helper.addAttachment("logo.png", new ClassPathResource("/img/Stormtrooper-star-wors.jpg"));
//
	        Context context = new Context();
	        context.setVariables(model);
	        String bodyHtml = templateEngine.process(templateMail, context);

	        /*Fonction pour l'envoi de mail à une liste des personnes */
	        String[] listMail = mailsTo.split(";"); 
	        for(int i=0 ; i< listMail.length; i++){
	        	listMail[i] = listMail[i].trim();	
			}
	        
	        helper.setTo(listMail);
	        helper.setText(bodyHtml, true);
	        helper.setSubject(mailSubject);
	        helper.setFrom(mailFrom);
	        // Attachement d'un liste des fichiers de type InputStream
			if (listInputfiles != null) {
				for (String nameFile : listInputfiles.keySet()) {
					InputStream file = listInputfiles.get(nameFile);
					helper.addAttachment(nameFile, new ByteArrayResource(IOUtils.toByteArray(file)));
				}
			}	        

	        mailSender.send(message);
			
		} catch (Exception e) { 
			logger.info("Erreur sur le service d'envoi de mail ==> {}", e.getMessage());
		}
	}

	@Override
	public void sendMailToList(String mailFrom, List<String> mailsTo, String mailSubject, String templateMail, 	Map<String, InputStream> listInputfiles) {

		MimeMessage message = mailSender.createMimeMessage();
		 try {
			MimeMessageHelper helper = new MimeMessageHelper( message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
			
//			helper.addAttachment("logo.png", new ClassPathResource("/img/Stormtrooper-star-wors.jpg"));
//
	        Context context = new Context();
	        //context.setVariables(mail.getModel());
	        String bodyHtml = templateEngine.process(templateMail, context);
	        
	        String[] arrayTo = new String[mailsTo.size()];
	        for(int i = 0 ; i<mailsTo.size() ; i++){
	        	arrayTo[i] = mailsTo.get(i) ;
	        }
	        
	        helper.setTo(arrayTo);
	        //helper.setCc(mailTo);
	        helper.setText(bodyHtml, true);
	        helper.setSubject(mailSubject);
	        helper.setFrom(mailFrom);
	        // Attachement d'un liste des fichiers de type InputStream
			if (listInputfiles != null) {
				for (String nameFile : listInputfiles.keySet()) {
					InputStream file = listInputfiles.get(nameFile);
					helper.addAttachment(nameFile, new ByteArrayResource(IOUtils.toByteArray(file)));
				}
			}	        

	        mailSender.send(message);
			
		} catch (Exception e) { 
			//e.printStackTrace();
			logger.info("Erreur sur le service d'envoi de mail ==> {}", e.getMessage());
		}
		
	}
	
	@Override
	public void sendMailToList(String mailFrom, List<String> mailsTo, String mailSubject, String templateMail, 	Map<String, InputStream> listInputfiles, Map model) {

		MimeMessage message = mailSender.createMimeMessage();
		 try {
			MimeMessageHelper helper = new MimeMessageHelper( message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
			
//			helper.addAttachment("logo.png", new ClassPathResource("/img/Stormtrooper-star-wors.jpg"));
//
	        Context context = new Context();
	        context.setVariables(model);
	        String bodyHtml = templateEngine.process(templateMail, context);
	        
	        String[] arrayTo = new String[mailsTo.size()];
	        for(int i = 0 ; i<mailsTo.size() ; i++){
	        	arrayTo[i] = mailsTo.get(i) ;
	        }
	        
	        helper.setTo(arrayTo);
	        //helper.setCc(mailTo);
	        helper.setText(bodyHtml, true);
	        helper.setSubject(mailSubject);
	        helper.setFrom(mailFrom);
	        // Attachement d'un liste des fichiers de type InputStream
			if (listInputfiles != null) {
				for (String nameFile : listInputfiles.keySet()) {
					InputStream file = listInputfiles.get(nameFile);
					helper.addAttachment(nameFile, new ByteArrayResource(IOUtils.toByteArray(file)));
				}
			}	        

	        mailSender.send(message);
			
		} catch (Exception e) { 
			//e.printStackTrace();
			logger.info("Erreur sur le service d'envoi de mail ==> {}", e.getMessage());
		}
		
	}

}