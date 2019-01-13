package com.zaghir.batch.batchspringmodel.service.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;

public interface ExcelService {
	public Workbook generateExcel(List<CompteBean> comptes);
}
