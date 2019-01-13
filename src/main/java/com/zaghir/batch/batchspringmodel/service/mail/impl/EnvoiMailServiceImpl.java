package com.zaghir.batch.batchspringmodel.service.mail.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;
import com.zaghir.batch.batchspringmodel.service.mail.EnvoiMailService;
import com.zaghir.batch.batchspringmodel.service.mail.SendMailService;

@Component
public class EnvoiMailServiceImpl implements EnvoiMailService {
	
	Logger logger = LoggerFactory.getLogger(EnvoiMailServiceImpl.class);

	@Autowired
	private SendMailService sendMailService;
	
	@Autowired
	private Environment env;
	
	@Override
	public void envoiMailDesComptesClients(Workbook workbook , List<CompteBean> comptes) {
		Map model = new HashMap<String, Object>();		
		
		try {
			if(comptes != null && comptes.size()>0){
				model.put("comptes" , comptes);
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			byte[] excelBytes = bos.toByteArray();
			Map<String, InputStream> listFiles = new HashMap<>();
			InputStream ios = new ByteArrayInputStream(excelBytes);
			listFiles.put("liste_des_comptes.xls", ios);

			Date dateEnvoi = new Date();
			SimpleDateFormat formatDateEnvoiMail = new SimpleDateFormat("dd-MM-yyyy");
			sendMailService.sendMail(
					env.getProperty("mail.mailFrom"),
					env.getProperty("mail.mailTo"),
					"Batch Comptes clients créé ! " + formatDateEnvoiMail.format(dateEnvoi).toString(),
					env.getProperty("mail.templateHtml"),
					listFiles,
					model);
			logger.info("envoi  de mail avec fichier excel Batch liste des compte");

		} catch (Exception e) {
			logger.info("probleme de generation de fichier excel --> {} ", e.getMessage());
		}

	}

}
