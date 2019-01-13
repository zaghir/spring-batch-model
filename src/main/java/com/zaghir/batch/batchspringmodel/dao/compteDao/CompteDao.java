package com.zaghir.batch.batchspringmodel.dao.compteDao;

import java.util.List;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;

public interface CompteDao {
	public List<CompteBean> retrieveListCompte();
	public int insertCompte(CompteBean compte);

}
