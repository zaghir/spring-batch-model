package com.zaghir.batch.batchspringmodel.dao.compteDao;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;
import com.zaghir.batch.batchspringmodel.rowmapper.CompteRowMapper;

@Component
public class CompteDaoImpl implements CompteDao{

	@Autowired
	@Qualifier("jdbctemplateDatabase1")
	private JdbcTemplate jdbcTemplate1;
	
	
	@Autowired
	@Qualifier("jdbctemplateDatabase2")
	private NamedParameterJdbcTemplate namedJdbcTemplate2;	
	
	@Autowired
	Environment env;
	
	@Override
	public List<CompteBean> retrieveListCompte() {
		List<CompteBean> list = jdbcTemplate1.query(env.getProperty("retrieveComptes"), new CompteRowMapper());
		return list;
	}
	
	public int insertCompte(CompteBean compte){
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", compte.getId() ,Types.INTEGER);
		params.addValue("nom", compte.getNom(), Types.VARCHAR);
		params.addValue("prenom", compte.getPrenom(), Types.VARCHAR);
		params.addValue("numero_compte", compte.getNumeroCompte() , Types.VARCHAR);
		int index = namedJdbcTemplate2.update(env.getProperty("insertCompte"),params);
		return index ;
	}

}
