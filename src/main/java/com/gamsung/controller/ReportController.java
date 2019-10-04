package com.gamsung.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamsung.service.MemberService;
import com.gamsung.service.ReportService;
import com.gamsung.vo.Report;

@Controller
@RequestMapping(path="/report")
public class ReportController {
	
	@Autowired 
	ReportService reportService;
	
	@Autowired 
	MemberService memberService;
	
	@GetMapping(path= {"/list"})
	@ResponseBody
	public List<Report> reportList(){
		List<Report> reports = reportService.findList();
		
		return reports;
	}
	
	//신고
	@GetMapping(path = "/{seller}")
	public String reportForm(Model model, @PathVariable String seller) {
		
		model.addAttribute("seller", seller);
		
		return "/product/report"; 
	}
	
	@PostMapping(path="/")
	public String report(Model model,HttpServletRequest req, Report report) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		auth.getPrincipal();
		
		reportService.registerReport(report);
		model.addAttribute("report", report);
			
		return "redirect:/report/coding.do";
		//return "/reports/report";
	}
		

	@RequestMapping(value = "/coding.do")
    public String coding() {
        return "redirect:/reports/report";
    }
	  
	
	@PostMapping(path="/insertBoard.do")
    public String insertBoard(String editor) {
        System.err.println("저장할 내용 : " + editor);
        return "redirect:/report/coding.do";
    }
	
	@PostMapping(path="/editor-image-upload", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String editorImageUpload(HttpServletRequest req) {
		
		try {
			String sFileInfo = "";
			//파일명 - 싱글파일업로드와 다르게 멀티파일업로드는 HEADER로 넘어옴 
			String name = req.getHeader("file-name");
			String ext = name.substring(name.lastIndexOf(".") + 1);
			//파일 기본경로
			String defaultPath = req.getServletContext().getRealPath("/files/product-files");
			//파일 기본경로 _ 상세경로
			String path = defaultPath + File.separator;
			File file = new File(path);
			if(!file.exists()) {
			    file.mkdirs();
			}
			String realname = UUID.randomUUID().toString() + "." + ext;
			InputStream is = req.getInputStream();
			OutputStream os=new FileOutputStream(path + realname);
			int numRead;
			// 파일쓰기
			byte b[] = new byte[Integer.parseInt(req.getHeader("file-size"))];
			while((numRead = is.read(b,0,b.length)) != -1){
			    os.write(b,0,numRead);
			}
			if(is != null) {
			    is.close();
			}
			os.flush();
			os.close();
			sFileInfo += "&bNewLine=true&sFileName="+ name+"&sFileURL="+"/files/product-files"+realname;
			
			return sFileInfo;
		} catch (Exception ex) {
			return "error upload file";
		}
	} 
}
