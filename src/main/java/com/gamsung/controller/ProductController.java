package com.gamsung.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gamsung.common.Util;
import com.gamsung.service.DealService;
import com.gamsung.service.MemberService;
import com.gamsung.service.ProductService;
import com.gamsung.vo.Heart;
import com.gamsung.vo.Member;
import com.gamsung.vo.Product;
import com.gamsung.vo.ProductFile;
import com.gamsung.vo.Report;
import com.gamsung.vo.Review;
import com.gamsung.vo.ReviewFile;


@Controller
@RequestMapping(path = "product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private DealService dealService;

	@Autowired
	private MemberService memberService;

	@GetMapping(path = "detail/{productNo}")
	public String productDetail(@PathVariable int productNo, Model model, HttpServletRequest req) {

		
		Authentication auth = (Authentication)req.getUserPrincipal();
		if( auth != null) {
			String id = auth.getName();
			
			if(id != null) {
				Heart heart = productService.findHeart(id, productNo);
				model.addAttribute("heart", heart);
				
				model.addAttribute("id", id);
			}
		}
		
		Product product = productService.findProductByProductNo(productNo);
		
	    ArrayList<Review> reviewlist = productService.findReviewsByProductNo(productNo);
	    
		Member member = memberService.findMemberById(product.getSeller());
		String addr = "";
		if( member.getJibunAddr() != null ) {
			String[] jibun = member.getJibunAddr().split(" ");
			for (int i = 0; i < jibun.length - 1; i++) {
				addr = addr + " " + jibun[i];
			}
		}
		//System.out.println(heart);
		
		model.addAttribute("addr", addr);
		model.addAttribute("product", product);
		model.addAttribute("reviewlist", reviewlist);
		 
		return "product/detail";
	}

	@GetMapping(path = "/categories")
	public String productList(Model model) {

		ArrayList<Product> products = productService.findProducts();

		model.addAttribute("products", products);
		System.out.println(products);

		return "product/list";
	}

	@GetMapping(path = "/write")
	public String showProductWrite() {

		return "product/write";
	}

	@PostMapping(path = "/write")
	public String write(Product product, Model model, HttpServletRequest Hreq, MultipartHttpServletRequest req ) {
		Authentication auth = (Authentication)Hreq.getUserPrincipal();

		product.setSeller(auth.getName());
		
		
		ServletContext application = req.getServletContext();
		String path = application.getRealPath("/files/product-files");// 최종 파일 저장 경로
		System.out.println("================================>"+path);
		String userFileName = "";
		try {

			MultipartFile titleImg = req.getFile("titleImgFile");
			if (titleImg != null) {
				userFileName = titleImg.getOriginalFilename();
				if (userFileName.contains("\\")) { // iexplore 경우

					userFileName = userFileName.substring(userFileName.lastIndexOf("\\") + 1);
				}
				if (userFileName != null && userFileName.length() > 0) { // 내용이 있는 경우
					if (userFileName.contains("\\")) { // iexplore 경우
						
						userFileName = userFileName.substring(userFileName.lastIndexOf("\\") + 1);
					}
					String uniqueFileName = Util.makeUniqueFileName(path, userFileName);// 파일이름_1.jpg
					
					titleImg.transferTo(new File(path, uniqueFileName));// 파일 저장

					ProductFile productFile = new ProductFile();
					productFile.setSaveFileName(uniqueFileName);
					productFile.setRawFileName(userFileName);
					productFile.setFlag(true);
					product.setFile(productFile);
				}
			}

			List<MultipartFile> img = req.getFiles("imgFile");

			if (img != null) {
				File file = new File(path);
				ArrayList<ProductFile> files = new ArrayList<ProductFile>();

				for (int i = 0; i < img.size(); i++) {
					userFileName = img.get(i).getOriginalFilename();
					if (userFileName.contains("\\")) { // iexplore 경우
						
						userFileName = userFileName.substring(userFileName.lastIndexOf("\\") + 1);
					}
					if (userFileName != null && userFileName.length() > 0) { // 내용이 있는 경우

						System.out.println(userFileName + " 업로드");
						// 파일 업로드 소스 여기에 삽입
						String uniqueFileName = Util.makeUniqueFileName(path, userFileName);// 파일이름_1.jpg
						file = new File(path, uniqueFileName);
						img.get(i).transferTo(file);

						ProductFile productFile = new ProductFile();
						productFile.setSaveFileName(uniqueFileName);
						productFile.setRawFileName(userFileName);
						productFile.setFlag(false);
						files.add(productFile);
						product.setFiles(files);

						System.out.println(files);
					}
				}
			}
			System.out.println(product);
			productService.registerProductTx(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("product", product);

		return "redirect:/product/categories";
	}
	
	//window창
	@GetMapping(path = "/report")
	public String reportForm(Model model, HttpServletRequest req ) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		auth.getPrincipal();
		
		
		return "/product/report"; 
	}
	
	@PostMapping(path="/report")
	public String report(Model model,HttpServletRequest req, Report report) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		auth.getPrincipal();
		
		productService.registerReport(report);
		model.addAttribute("report", report);
			
		//return "/coding.do";
		return "/product/report";
	}
	
	
	@GetMapping(path = "/delete/{productNo}")
	public String delete(@PathVariable int productNo) {
	      
	      productService.deleteProduct(productNo);
	          
	      return "redirect:/product/categories"; 
	    
	}
	
	
	@GetMapping(path = "/delete-file")
	@ResponseBody //return값을 스트링 형태로 받아옴
	public String deletefile(int productFileNo) {
		
		productService.deleteProductFile(productFileNo);

		return "success" ; 
	}
	
	@GetMapping(path = "/update/{productNo}")
	public String updateForm(@PathVariable  int productNo, Model model) {

		
		
		Product product = productService.findProductByProductNo(productNo);
		
		if(product == null) {
			return "redirect:/product/categories";
		}
		
		model.addAttribute("product", product);
		
		return "/product/update";
	}
	
	@PostMapping(path = "/update")
	public String update(MultipartHttpServletRequest req, Product product, Model model) {

		ServletContext application = req.getServletContext();
		String path = application.getRealPath("/files/product-files");// 최종 파일 저장 경로
		String userFileName = "";
		try {
			
			MultipartFile titleImg = req.getFile("titleImgFile");
			if (titleImg != null) {
				userFileName = titleImg.getOriginalFilename();
				if (userFileName.contains("\\")) { // iexplore 경우
					
					userFileName = userFileName.substring(userFileName.lastIndexOf("\\") + 1);
				}
				if (userFileName != null && userFileName.length() > 0) { // 내용이 있는 경우
					if (userFileName.contains("\\")) { 
						userFileName = userFileName.substring(userFileName.lastIndexOf("\\") + 1);
					}
					String uniqueFileName = Util.makeUniqueFileName(path, userFileName);// 파일이름_1.jpg
					
					titleImg.transferTo(new File(path, uniqueFileName));// 파일 저장

					ProductFile productFile = new ProductFile();
					productFile.setSaveFileName(uniqueFileName);
					productFile.setRawFileName(userFileName);
					productFile.setFlag(true);
					productFile.setProductNo(product.getProductNo());
					
					productService.updateProductFile(productFile);
					
					product.setFile(productFile);
					
				}
			}

			List<MultipartFile> img = req.getFiles("imgFile");

			if (img != null) {
				File file = new File(path);
				ArrayList<ProductFile> files = new ArrayList<ProductFile>();

				for (int i = 0; i < img.size(); i++) {
					userFileName = img.get(i).getOriginalFilename();
					if (userFileName.contains("\\")) { // iexplore 경우
						// C:\AAA\BBB\CCC.png -> CCC.png
						userFileName = userFileName.substring(userFileName.lastIndexOf("\\") + 1);
					}
					if (userFileName != null && userFileName.length() > 0) { // 내용이 있는 경우

						System.out.println(userFileName + " 업로드");
						// 파일 업로드 소스 여기에 삽입
						String uniqueFileName = Util.makeUniqueFileName(path, userFileName);// 파일이름_1.jpg
						file = new File(path, uniqueFileName);
						img.get(i).transferTo(file);

						ProductFile productFile = new ProductFile();
						productFile.setSaveFileName(uniqueFileName);
						productFile.setRawFileName(userFileName);
						productFile.setFlag(false);
						productFile.setProductNo(product.getProductNo());
						files.add(productFile);
						
						product.setFiles(files);
						
						productService.insertProductFiles(product, product.getProductNo());
					}
				}
			}
			// 데이터 저장
			productService.updateProduct(product);
			model.addAttribute("product", product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/product/detail/" + product.getProductNo();
	}
	

	// 찜하기
	@GetMapping(path = "/heart")
	// @RequestMapping(path = "/addheart?productNo={productNo}", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String heart(Heart heart, int productNo, HttpServletRequest req) {

		Authentication auth = (Authentication) req.getUserPrincipal();
		
		if (auth != null) {
			String id = auth.getName();

			// 찜 했는지 아닌지 확인
			boolean check = productService.findHeartCount(id, productNo);

			heart.setId(id);
			if (check) {
				productService.deleteHeart(id, productNo);
				return "removeheart";
			} else {
				productService.insertHeart(heart);
				return "success";
			}
		} else {
			return "error";
		}

	}
	
	@GetMapping(path = "reviewWrite/{dealNo}")
	public String reviewWriteForm(@PathVariable int dealNo, Model model) {
		model.addAttribute("dealNo", dealNo);
		return "product/reviewwrite";
	}
	
	@PostMapping(path = "reviewWrite")
	public String reviewWrite(MultipartHttpServletRequest req, Review review, Model model) {
		
		Authentication auth = (Authentication) req.getUserPrincipal();
		
		review.setBuyer(auth.getName());
		
		
		ServletContext application = req.getServletContext();
		String path = application.getRealPath("/files/review-files");// 최종 파일 저장 경로
		System.out.println("================================>"+path);
		String userFileName = "";
		try {
			List<MultipartFile> img = req.getFiles("imgFile");

			if (img != null) {
				File file = new File(path);
				ArrayList<ReviewFile> files = new ArrayList<ReviewFile>();

				for (int i = 0; i < img.size(); i++) {
					userFileName = img.get(i).getOriginalFilename();
					if (userFileName.contains("\\")) { // iexplore 경우
						// C:\AAA\BBB\CCC.png -> CCC.png
						userFileName = userFileName.substring(userFileName.lastIndexOf("\\") + 1);
					}
					if (userFileName != null && userFileName.length() > 0) { // 내용이 있는 경우

						System.out.println(userFileName + " 업로드");
						// 파일 업로드 소스 여기에 삽입
						String uniqueFileName = Util.makeUniqueFileName(path, userFileName);// 파일이름_1.jpg
						file = new File(path, uniqueFileName);
						img.get(i).transferTo(file);

						ReviewFile reviewFile = new ReviewFile();
						reviewFile.setSaveFileName(uniqueFileName);
						reviewFile.setRawFileName(userFileName);
						files.add(reviewFile);
						review.setFiles(files);

						System.out.println(files);
					}
				}
			}
			System.out.println(review);
			productService.insertReview(review);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("review", review);

		
		return "redirect:/product/categories";
	}


	
//
//	@RequestMapping(value = "/coding.do")
//    public String coding() {
//        return "redirect:/product/black";
//    }
//	  
//	
//	@PostMapping(path="/insertBoard.do")
//    public String insertBoard(String editor) {
//        System.err.println("저장할 내용 : " + editor);
//        return "redirect:/coding.do";
//    }
	
//	@PostMapping(path="/product/editor-image-upload", produces = "text/plain;charset=utf-8")
//		@ResponseBody
//		public String editorImageUpload(HttpServletRequest req) {
//			
//			try {
//				String sFileInfo = "";
//				//파일명 - 싱글파일업로드와 다르게 멀티파일업로드는 HEADER로 넘어옴 
//				String name = req.getHeader("file-name");
//				String ext = name.substring(name.lastIndexOf(".") + 1);
//				//파일 기본경로
//				String defaultPath = req.getServletContext().getRealPath("/files/product-files");
//				//파일 기본경로 _ 상세경로
//				String path = defaultPath + File.separator;
//				File file = new File(path);
//				if(!file.exists()) {
//				    file.mkdirs();
//				}
//				String realname = UUID.randomUUID().toString() + "." + ext;
//				InputStream is = req.getInputStream();
//				OutputStream os=new FileOutputStream(path + realname);
//				int numRead;
//				// 파일쓰기
//				byte b[] = new byte[Integer.parseInt(req.getHeader("file-size"))];
//				while((numRead = is.read(b,0,b.length)) != -1){
//				    os.write(b,0,numRead);
//				}
//				if(is != null) {
//				    is.close();
//				}
//				os.flush();
//				os.close();
//				sFileInfo += "&bNewLine=true&sFileName="+ name+"&sFileURL="+"/files/product-files"+realname;
//				
//				return sFileInfo;
//			} catch (Exception ex) {
//				return "error upload file";
//			}
//		} 

	
}
