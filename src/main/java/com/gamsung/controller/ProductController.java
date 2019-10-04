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
import com.gamsung.service.MemberService;
import com.gamsung.service.ProductService;
import com.gamsung.service.ReviewService;
import com.gamsung.vo.Heart;
import com.gamsung.vo.Member;
import com.gamsung.vo.Product;
import com.gamsung.vo.ProductFile;
import com.gamsung.vo.Review;
import com.gamsung.vo.ReviewFile;


@Controller
@RequestMapping(path = "product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
//	@Autowired
//	private DealService dealService;

	@Autowired
	private ReviewService reviewService;

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
		
	    ArrayList<Review> reviewlist = reviewService.findReviewsByProductNo(productNo);
	    
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
	
	
	@GetMapping(path = "/delete/{productNo}")
	public String delete(@PathVariable int productNo) {
	      
	      productService.deleteProduct(productNo);
	          
	      return "redirect:/product/categories"; 
	    
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
					productFile.setFlag(true);
					productFile.setProductNo(product.getProductNo());
					
					//productService.updateProductFile(productFile);
					
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
						productFile.setFlag(false);
						productFile.setProductNo(product.getProductNo());
						files.add(productFile);
						
						product.setFiles(files);
						
						productService.insertProductFiles(product, product.getProductNo());
					}
				}
			}
			// 데이터 저장
			//productService.updateProduct(product);
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
		return "review/write";
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
					}
				}
			}
			reviewService.insertReview(review);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("review", review);

		
		return "redirect:/product/categories";
	}
	
	@GetMapping(path = "/deleteReview/{dealNo}")
	public String deleteReview(@PathVariable int dealNo) {
		
	      reviewService.deleteReview(dealNo);
	          
	      return "redirect:/member/mypage"; 
	    
	}
	
	@GetMapping(path = "/updateReview/{dealNo}")
	public String reviewUpdateForm(@PathVariable int dealNo, Model model) {
		
		Review review = reviewService.findReviewByDealNo(dealNo);
		
		model.addAttribute("review", review);
		return "review/update";
	}
	
	@PostMapping(path = "/updateReview")
	public String updateReview(MultipartHttpServletRequest req, Review review, Model model) {

		ServletContext application = req.getServletContext();
		String path = application.getRealPath("/files/review-files");// 최종 파일 저장 경로
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
						
					}
				}
			}
			reviewService.updateReview(review);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/member/mypage";
	}
	
	//window창
	@GetMapping(path = "/black")
	public String blackForm(Model model, HttpServletRequest req ) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		auth.getPrincipal();
		
		List<Product> products = productService.findProducts();
		model.addAttribute("products", products);
		
		return "/product/black"; 
	}

}
