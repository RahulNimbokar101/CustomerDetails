package com.me.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.me.entity.CustomerDetails;
import com.me.entity.SearchRequest;

public interface CustomerDetailsService {
	
	public List<String> getPlansName();
	public List<String> getPlanesStatus();
	public List<CustomerDetails> SearchPlane(SearchRequest request);
	public void exportExcel(HttpServletResponse response) throws Exception;
	public void exportPdf(HttpServletResponse response) throws Exception;
}
