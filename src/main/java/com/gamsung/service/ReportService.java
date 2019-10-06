package com.gamsung.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.ReportMapper;
import com.gamsung.vo.Report;

@Service
public class ReportService {
	
	@Autowired ReportMapper reportMapper;

	public List<Report> findList() {
		List<Report> reports = reportMapper.findList();
		return reports;
	}
	
	public void registerReport(Report report) {
		reportMapper.insertReport(report);
	}

	public void reportAnswer(Report report) {
		reportMapper.reportAnswer(report);
		
	}



}
