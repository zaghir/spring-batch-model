package com.zaghir.batch.batchspringmodel.service.mail;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;

public interface EnvoiMailService {
	
	public void envoiMailDesComptesClients(Workbook workbook , List<CompteBean> comptes);

}
