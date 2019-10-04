package com.gamsung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamsung.service.ReportService;
import com.gamsung.vo.Report;

@Controller
@RequestMapping(path="/report")
public class ReportController {
	@Autowired ReportService reportService;
	
	@GetMapping(path= {"/list"})
	@ResponseBody
	public List<Report> reportList(){
		List<Report> reports = reportService.findList();
		
		return reports;
	}
}
