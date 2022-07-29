package tn.esprit.banque.service.compte;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Operation;
import tn.esprit.banque.model.Retrait;
import tn.esprit.banque.model.Versement;
import tn.esprit.banque.model.Virement;

public class CompteExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Operation> operations;

	public CompteExcelExporter(List<Operation> operations) {
		this.operations = operations;
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("operations");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "Numéro Operation", style);
		createCell(row, 1, "Statut Compte", style);
		createCell(row, 2, "Date Opération", style);
		createCell(row, 3, "Montant", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else if(value instanceof Long) {
			cell.setCellValue((Long) value);
		}else if(value instanceof Date) {
			cell.setCellValue((Date) value);
		}else if(value instanceof BigDecimal) {
			cell.setCellValue((String) ((BigDecimal)value).toString());
		}else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (Operation operations : operations) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, operations.getId_operation(), style);
			createCell(row, columnCount++, operations.getStatut(), style);
			createCell(row, columnCount++, operations.getDate_operation(), style);
			if( operations instanceof Versement) {
				createCell(row, columnCount++, ((Versement)operations).getMontant(), style);

			}
			if( operations instanceof Virement) {
				createCell(row, columnCount++, ((Virement)operations).getMontant(), style);

			}
			if( operations instanceof Retrait) {
				createCell(row, columnCount++, ((Retrait)operations).getMontant(), style);

			}


		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
}
