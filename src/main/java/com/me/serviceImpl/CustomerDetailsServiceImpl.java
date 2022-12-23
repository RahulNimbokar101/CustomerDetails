package com.me.serviceImpl;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.me.entity.CustomerDetails;
import com.me.entity.SearchRequest;
import com.me.repo.CustomerDetailsRepo;
import com.me.service.CustomerDetailsService;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

	@Autowired
	private CustomerDetailsRepo repo;

	@Override
	public List<String> getPlansName() {

		return repo.getByPlanName();
	}

	@Override
	public List<String> getPlanesStatus() {

		return repo.getByStatus();
	}

	@Override
	public List<CustomerDetails> SearchPlane(SearchRequest request) {
		CustomerDetails details = new CustomerDetails();
		if (request.getPlaneName() != null && !request.getPlaneName().equals("")) {
			details.setPlanName(request.getPlaneName());
		}

		if (request.getPlaneStatus() != null && !request.getPlaneStatus().equals("")) {
			details.setStatus(request.getPlaneStatus());
		}

		if (request.getGender() != null && !request.getGender().equals("")) {
			details.setGender(request.getGender());
		}

		Example<CustomerDetails> example = Example.of(details);

		List<CustomerDetails> records = repo.findAll(example);

		return records;
	}

	@Override
	public void exportExcel(HttpServletResponse response) throws IOException {
		List<CustomerDetails> excel = repo.findAll();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet createSheet = workbook.createSheet("CustomerDetails");

		HSSFRow createRow = createSheet.createRow(0);
		createRow.createCell(0).setCellValue("ID");
		createRow.createCell(1).setCellValue("Name");
		createRow.createCell(2).setCellValue("Email");
		createRow.createCell(3).setCellValue("Mobile");
		createRow.createCell(4).setCellValue("Gender");
		createRow.createCell(5).setCellValue("SSn");
		createRow.createCell(6).setCellValue("Plan");
		createRow.createCell(7).setCellValue("Plan-Status");

		int dataRowIndex = 1;

		for (CustomerDetails customer : excel) {
			HSSFRow dataRow = createSheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(customer.getId());
			dataRow.createCell(1).setCellValue(customer.getName());
			dataRow.createCell(2).setCellValue(customer.getEmail());
			dataRow.createCell(3).setCellValue(customer.getMobile());
			dataRow.createCell(4).setCellValue(customer.getGender());
			dataRow.createCell(5).setCellValue(customer.getSsn());
			dataRow.createCell(6).setCellValue(customer.getPlanName());
			dataRow.createCell(7).setCellValue(customer.getStatus());
			dataRowIndex++;

			ServletOutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();

		}

	}

	@Override
	public void exportPdf(HttpServletResponse response) throws Exception {
		List<CustomerDetails> records = repo.findAll();

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("List of Users", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 3.0f, 1.5f,3.5f,3.0f,3.0f });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font f = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Customer ID", f));

		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Customer Name", f));
		table.addCell(cell);

		
		cell.setPhrase(new Phrase(" Customer E-mail", f));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Customer Mobile", f));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Customer Gender", f));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Customer SSN", f));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Customer Plan", f));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Customer Plan-Ststus", f));
		table.addCell(cell);

		for (CustomerDetails record : records) {
			table.addCell(String.valueOf(record.getId()));
			table.addCell(record.getName());
			table.addCell(record.getEmail());
			table.addCell(String.valueOf(record.getMobile()));
			table.addCell(record.getGender());
			table.addCell(record.getSsn());
			table.addCell(record.getPlanName());
			table.addCell(record.getStatus());
		}

		

		document.add(table);

		document.close();

	}

}
