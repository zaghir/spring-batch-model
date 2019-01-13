package com.zaghir.batch.batchspringmodel.service.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.zaghir.batch.batchspringmodel.bean.CompteBean;

@Component
public class ExcelServiceImpl implements ExcelService {
	
	//private SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	private static Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);
	
	@Autowired
    private Environment env;

	public static String NAME_SHEET_MA = "Compte MA";
	public static String NAME_SHEET_US = "Compte US";
	private Font getFontHeader(Workbook workbook){
		Font fontHeader = workbook.createFont();
		fontHeader.setBold(true);
		fontHeader.setFontName("Arial");
		fontHeader.setFontHeightInPoints((short)10);
		return fontHeader ;
	}
	
	private Font getFontBody(Workbook workbook){
		Font fontBody = workbook.createFont();
		fontBody.setBold(false);
		fontBody.setItalic(false);
		fontBody.setFontName("Arial");
		fontBody.setFontHeightInPoints((short)10);
		return fontBody ;
	}
	
	private CellStyle getCellStyleHeader(Workbook workbook){
		CellStyle cellStyleHeader =workbook.createCellStyle();		
		cellStyleHeader.setBorderBottom(BorderStyle.THIN); //BorderStyle.THIN => ne fonctionne pas dans la version 3.14 de poi donc en le remplace par une short 
		cellStyleHeader.setBorderTop(BorderStyle.THIN );
		cellStyleHeader.setBorderLeft(BorderStyle.THIN );
		cellStyleHeader.setBorderRight(BorderStyle.THIN );
		cellStyleHeader.setFillForegroundColor((short) 73); // IndexedColors.LIGHT_ORANGE.getIndex()
		cellStyleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND); // FillPatternType.SOLID_FOREGROUND n'est pas supporté dans la version 3.14 on utilise une valeur short
		cellStyleHeader.setAlignment(HorizontalAlignment.LEFT);		
		cellStyleHeader.setFont(getFontHeader(workbook));		
		return cellStyleHeader ;
	}
	
	private CellStyle getCellStylePaire(Workbook workbook){
		CellStyle cellStylePaire =workbook.createCellStyle();
		cellStylePaire.setBorderBottom(BorderStyle.THIN);
		cellStylePaire.setBorderTop(BorderStyle.THIN);
		cellStylePaire.setBorderLeft(BorderStyle.THIN);
		cellStylePaire.setBorderRight(BorderStyle.THIN);
		cellStylePaire.setFillForegroundColor((short)41); // IndexedColors.ORANGE.getIndex()
		cellStylePaire.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStylePaire.setAlignment(HorizontalAlignment.LEFT);
		cellStylePaire.setFont(getFontBody(workbook));
		return cellStylePaire ;
	}
	
	private CellStyle getCellStyleImpaire(Workbook workbook){
		CellStyle cellStyleImpaire =workbook.createCellStyle();
		cellStyleImpaire.setBorderBottom(BorderStyle.THIN);
		cellStyleImpaire.setBorderTop(BorderStyle.THIN);
		cellStyleImpaire.setBorderLeft(BorderStyle.THIN);
		cellStyleImpaire.setBorderRight(BorderStyle.THIN);
		cellStyleImpaire.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellStyleImpaire.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyleImpaire.setAlignment(HorizontalAlignment.LEFT);
		cellStyleImpaire.setFont(getFontBody(workbook));
		return cellStyleImpaire ;
	}
	
	private Map<Integer , String> getListLabelFieldXls(){
		Map<Integer , String> header = new HashMap<>();
		header.put(0, "Id") ;
		header.put(1, "Nom") ;
		header.put(2, "Prenom") ;
		header.put(3, "Compte") ;
		return header ;
	}
	
	private void addHeader(Sheet sheet){
		Map <Integer, String > header = getListLabelFieldXls();
		Row rowHeader = sheet.createRow(0); 
		CellStyle cellStyleHeader = getCellStyleHeader(sheet.getWorkbook());
		
		header.forEach( (k ,v) -> {			
			rowHeader.createCell(k).setCellValue(v); 
			rowHeader.getCell(k).setCellStyle(cellStyleHeader);			
		});
	}
	
	private void writeExcelFile(Workbook workbook) {
		try {

			File file = new File("C:/Users/yzaghir/Downloads/fichier-compte-client.xls");
			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			outputStream.close();			
		} catch (Exception e) {
			logger.info("probleme de generation de fichier excel --> {} ", e.getMessage());
		}
	}
	
	private void addRowToSheet(Row row ,CellStyle cellStyle, CompteBean cp){
		Map<Integer ,String> header = getListLabelFieldXls();
		header.forEach( (k ,v) -> {
			
			if(v.equals("Id")){
				row.createCell(k).setCellValue((cp.getId() != null) ? cp.getId().toString() : "");
				row.getCell(k).setCellStyle(cellStyle);
			}
			
			if(v.equals("Nom")){
				row.createCell(k).setCellValue(cp.getNom()!=null ? cp.getNom() :"");
				row.getCell(k).setCellStyle(cellStyle);
			}
			
			if(v.equals("Prenom")){
				row.createCell(k).setCellValue(cp.getPrenom() !=null ? cp.getPrenom() : "");
				row.getCell(k).setCellStyle(cellStyle);
			}

			if(v.equals("Compte")){
				row.createCell(k).setCellValue(cp.getNumeroCompte() != null ? cp.getNumeroCompte() :"");
				row.getCell(k).setCellStyle(cellStyle);				
			}			
			
		});
		
	}
		
	private void addSheetsToWorkbook(Workbook workbook){
		
		workbook.createSheet(WorkbookUtil.createSafeSheetName(NAME_SHEET_MA));
		addHeader(workbook.getSheet(NAME_SHEET_MA));
		
		workbook.createSheet(WorkbookUtil.createSafeSheetName(NAME_SHEET_US));
		addHeader(workbook.getSheet(NAME_SHEET_US));
		
	}
	
	private void traitementFichier(Workbook workbook , List<CompteBean> comptes){
		Sheet sheetMa = workbook.getSheet(NAME_SHEET_MA);
		Sheet sheetUs = workbook.getSheet(NAME_SHEET_US);
		
		CellStyle cellStyleImpaire = getCellStyleImpaire(workbook);
		CellStyle cellStylePaire = getCellStylePaire(workbook);		
		
		// coloriser les lignes par exemple
		// MA
		for( CompteBean cp:  comptes){
			Row row = sheetMa.createRow(sheetMa.getLastRowNum()+1);
			CellStyle cellStyle ;
            if(sheetMa.getLastRowNum() % 2 > 0 ){
                cellStyle = cellStylePaire;
            }else{
                cellStyle = cellStyleImpaire ;
            }
			addRowToSheet(row, cellStyle, cp);						
		}
		//US
		for( CompteBean cp:  comptes){
			Row row = sheetUs.createRow(sheetUs.getLastRowNum()+1);
			CellStyle cellStyle ;
            if(sheetMa.getLastRowNum() % 2 > 0 ){
                cellStyle = cellStylePaire;
            }else{
                cellStyle = cellStyleImpaire ;
            }
			addRowToSheet(row, cellStyle, cp);						
		}
		
	}

	@Override
	public Workbook generateExcel(List<CompteBean> comptes) {
		logger.info("debut de creation de fichier excel dans le batch liste des compte ");
		// create workbook
		Workbook workbook = new HSSFWorkbook() ;
		// add sheet MA et US
		addSheetsToWorkbook(workbook);

		// charger le workbook avec les deux feuilles [ MA et US] plus les données des comptes 
		traitementFichier(workbook , comptes);
		
		// test pour generer le fichier excel en local 
		writeExcelFile(workbook);
		
		return workbook;
		
	}
	
	

}
