package com.zaghir.batch.batchspringmodel.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;

public class CompteRowMapper implements RowMapper<CompteBean> {

	@Override
	public CompteBean mapRow(ResultSet rs, int index) throws SQLException {
		CompteBean c = new CompteBean();
		c.setId(rs.getInt("id"));
		c.setNom(rs.getString("nom"));
		c.setPrenom(rs.getString("prenom"));		
		c.setNumeroCompte(rs.getString("numero_compte"));
		
		return c;
	}

}
