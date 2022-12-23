package com.me.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.me.entity.CustomerDetails;
import com.me.entity.SearchRequest;
import com.me.service.CustomerDetailsService;

@RestController

public class CustomerDetailscontroller {

	@Autowired
	private CustomerDetailsService customerDetailsService;
	
	@GetMapping("/{plan}")
	public ResponseEntity<String> getCustomerDetailsByPlan(@PathVariable String plan){
		  List<String> plansName = customerDetailsService.getPlansName();
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	@GetMapping("/status")
	public ResponseEntity<String> getCustomerDetailsByStatus(){
		  List<String> plansstatus = customerDetailsService.getPlanesStatus();
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<String> getCustomerDetailsBySearch(SearchRequest request){
		  List<CustomerDetails> searchPlane = customerDetailsService.SearchPlane(request);
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	
	@GetMapping("/excel")
	public void generateExcel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename=CustomerDetail.xls";
		response.setHeader(headerKey, headerValue);
		customerDetailsService.exportExcel(response);
	}
 	
	 @GetMapping("/pdf")
	    public void exportToPDF(HttpServletResponse response) throws  Exception {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=CustomerDetails.pdf";
	        response.setHeader(headerKey, headerValue);
	         
	         customerDetailsService.exportPdf(response);
	             
	     
	         
	    }
	
	
	
}
