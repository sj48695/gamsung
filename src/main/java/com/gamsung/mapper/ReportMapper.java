package com.gamsung.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.Report;

@Mapper
public interface ReportMapper {

	List<Report> findList();

	void insertReport(Report report);

	void reportAnswer(Report report);

}
