package com.zaghir.batch.batchspringmodel.steps.readers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;
import com.zaghir.batch.batchspringmodel.dao.compteDao.CompteDao;

@Component
public class ReaderStep1 implements ItemReader<CompteBean>{

	private static Logger logger = LoggerFactory.getLogger(ReaderStep1.class);
	
	private int nextLineATraiter;
	
	@Autowired
	private CompteDao compteDao ;
	
	private List<CompteBean> listCompte ;
	
	@BeforeStep
	public void beforeStep(){
		listCompte = compteDao.retrieveListCompte();
	}
	
	@Override
	public CompteBean read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		logger.info("Reader1 ---->");
		CompteBean elementATraiter = null ;
		if (listCompte != null && !listCompte.isEmpty() && nextLineATraiter < listCompte.size()) {

			elementATraiter = listCompte.get(nextLineATraiter);
			logger.info("Reader1 --read--> {}" , elementATraiter);
			nextLineATraiter++;

		}
		return elementATraiter;
	}

}
