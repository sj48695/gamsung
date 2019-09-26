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
import com.gamsung.vo.Heart;
import com.gamsung.vo.Product;
import com.gamsung.vo.ProductFile;


@Controller
@RequestMapping(path = "product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private MemberService memberService;

	@GetMapping(path = "detail/{productNo}")
	public String productDetail(@PathVariable int productNo, Model model, HttpServletRequest req) {
		Product product = productService.findProductByProductNo(productNo);
		model.addAttribute("product", product);

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
					// C:\AAA\BBB\CCC.png -> CCC.png
					userFileName = userFileName.substring(userFileName.lastIndexOf("\\") + 1);
				}
				if (userFileName != null && userFileName.length() > 0) { // 내용이 있는 경우
					if (userFileName.contains("\\")) { // iexplore 경우
						// C:\AAA\BBB\CCC.png -> CCC.png
						userFileName = userFileName.substring(userFileName.lastIndexOf("\\") + 1);
					}
					String uniqueFileName = Util.makeUniqueFileName(path, userFileName);// 파일이름_1.jpg
					// String uniqueFileName=Util.makeUniqueFileName(fileName);//고유한 파일이름.jpg
					titleImg.transferTo(new File(path, uniqueFileName));// 파일 저장

					ProductFile productFile = new ProductFile();
					productFile.setSaveFileName(uniqueFileName);
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
		
		//productService.writeProduct(product);
		model.addAttribute("product", product);

		return "redirect:/product/categories";
	}

	// 찜하기
	@GetMapping(path = "/addheart")
	// @RequestMapping(path = "/addheart?productNo={productNo}", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String addToHeart(Heart heart, int productNo, HttpServletRequest req) {

		Authentication auth = (Authentication) req.getUserPrincipal();

		if (auth != null) {
			heart.setId(auth.getName());
			productService.insertHeart(heart);
			return "success";
		} else {
			return "redirect:/";
		}

	}

}
