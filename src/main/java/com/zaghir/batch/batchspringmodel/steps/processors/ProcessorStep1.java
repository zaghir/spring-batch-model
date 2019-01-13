package com.zaghir.batch.batchspringmodel.steps.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;
import com.zaghir.batch.batchspringmodel.dao.compteDao.CompteDao;

@Scope("step")
public class ProcessorStep1 implements ItemProcessor<CompteBean, CompteBean> {

	private static Logger logger = LoggerFactory.getLogger(ProcessorStep1.class);
	
	@Autowired
	private CompteDao compteDao;
	
	@Override
	public CompteBean process(CompteBean compte) throws Exception {
		logger.info("processor1 --- insert dans database2 ---> {}", compte);
		compteDao.insertCompte(compte);
		return compte;
	}

}
