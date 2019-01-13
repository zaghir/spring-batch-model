package com.zaghir.batch.batchspringmodel.steps.writers;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;
import com.zaghir.batch.batchspringmodel.service.excel.ExcelService;
import com.zaghir.batch.batchspringmodel.service.mail.EnvoiMailService;

@Scope("step")
public class WriterStep1 implements ItemWriter<CompteBean>{

	Logger logger = LoggerFactory.getLogger(WriterStep1.class);
	
	@Autowired
	private ExcelService excelService ;
	
	@Autowired
	private EnvoiMailService envoiMailService ;
	
	@Autowired
	private Environment env ;
	
	public ExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ExcelService excelService) {
		this.excelService = excelService;
	}

	public void write(List<? extends CompteBean> comptes) throws Exception {
		logger.info("WriterStep1 --->");
		Workbook workbook =  excelService.generateExcel((List<CompteBean>)comptes);
		envoiMailService.envoiMailDesComptesClients(workbook, (List<CompteBean>)comptes);
		
		
	}
	
	

}
