package com.gamsung.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gamsung.common.Util;
import com.gamsung.service.MemberService;
import com.gamsung.service.ProductService;
import com.gamsung.vo.Member;
import com.gamsung.vo.Product;
import com.gamsung.vo.Review;

@Controller
@RequestMapping(value= "/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/login")
	public ModelAndView loginpage() {
		return new ModelAndView("/member/login");
	}
	
	@GetMapping("/register")
	public ModelAndView registerpage() {
		return new ModelAndView("member/register");
	}
	
	@PostMapping("/registerIn")
	public String register(Member member) {
		
		memberService.save(member);
		
		return "redirect:/member/login";
	}
	
	@GetMapping("/admin")
	@ResponseBody
	public ModelAndView adminpage(HttpServletRequest req) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		auth.getPrincipal();
		
		return new ModelAndView("/member/admin");
	}
	
	// 마이페이지
	@GetMapping(path = "mypage", produces="text/plain;charset=utf-8")
	//@ResponseBody
	public String mypage(HttpServletRequest req, Model model) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		String memberId = auth.getName();
		
		Member profile = memberService.findProfileImgById(memberId);
		
		Member member = memberService.findMemberById(memberId);
		
		List<Product> products = productService.findMyProductList(memberId);
		List<Product> requestProducts = productService.findMyRequestProductList(memberId);
		
		List<Review> reviews = productService.selectReview(memberId);
		
		//내가 찜한 목록
		List<Product> hearts = productService.findMyHeartList(memberId);

		model.addAttribute("member", member);
		model.addAttribute("profile", profile);
		model.addAttribute("products", products);
		model.addAttribute("hearts", hearts);		
		model.addAttribute("requestProducts", requestProducts);
		model.addAttribute("reviews", reviews);
		
		return "member/mypage";
	}
	
	@GetMapping(path = "mypage/fileUpload")
	public String fileUpload() {
		return "mypage";
	}
	
	//프로필 사진 업로드
	@PostMapping(path = "/mypage/fileUpload", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String fileUpload(MultipartHttpServletRequest req, HttpServletRequest request){
		
		Member member = new Member();
		Authentication auth = (Authentication)request.getUserPrincipal();
		String memberId = auth.getName();
		
		ServletContext application = req.getServletContext();
		String path = application.getRealPath("/files/profile-files");// 최종 파일 저장 경로
		String userFileName = "";
		try {

			MultipartFile profileImg = req.getFile("profileImgFile");
			if (profileImg != null) {
				userFileName = profileImg.getOriginalFilename();
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
					profileImg.transferTo(new File(path, uniqueFileName));// 파일 저장
					
					member.setId(memberId);
					member.setImgFileName(uniqueFileName);
					
					//데이터 저장
					memberService.updateProfileImg(member);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return member.getImgFileName();
	}
	
	@GetMapping(path = "/mypage/introduction")
	@ResponseBody
	public String writeIntroduction(HttpServletRequest req, Member member) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		String memberId = auth.getName();
	
		member.setId(memberId);
		memberService.updateIntroduction(member);
		
		return member.getIntroduction();
	}
	
	@GetMapping(path = "/mypage/products")
	@ResponseBody
	public List<Product> mypageProducts(HttpServletRequest req) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		String memberId = auth.getName();
		
		List<Product> products = productService.findMyProductList(memberId);
		return products;
	}
	
	@GetMapping(path = "/mypage/requestProducts")
	@ResponseBody
	public List<Product> mypageRequestProducts(HttpServletRequest req) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		String memberId = auth.getName();
		
		List<Product> requestProducts = productService.findMyRequestProductList(memberId);
		return requestProducts;
	}
	
	@GetMapping(path= {"/list"})
	@ResponseBody
	public List<Member> userList() {
			
		List<Member> members = memberService.findUserList();
		return members;
	}
	
	@PostMapping(path= {"/delete"})
	@ResponseBody
	public String userDelete(@RequestBody Member member) {
		
		memberService.deleteById(member,member.getId());
		
		return "{ \"result\": \"sucess\"}";
	}
	
	@PostMapping(path= {"/blacklist"})
	@ResponseBody
	public String activeBlackList(@RequestBody Member member) {
		memberService.activateBlackList(member, member.getId());
		
		return "{ \"result\": \"sucess\"}";
	}
	
	@GetMapping(path= {"openEditor"})
	@ResponseBody
	public String openEditor() {
		
		return "{ \"result\": \"sucess\"}";
	}
	
	@PostMapping(path= {"/mypage/confirmpwd"})
	@ResponseBody
	public String confrimPwd(@RequestBody HashMap<String, String> pwd, HttpServletRequest req) throws Exception{
		Authentication auth = (Authentication) req.getUserPrincipal();
		String id =  auth.getName();
		
		Member member = memberService.findMemberById(id);
		
		String password = member.getPwd();
		
		boolean confirmpwd = passwordEncoder.matches(pwd.get("pwd"), password);
			
		if(confirmpwd != true) {
			return "failure";
			
		}
		return "success";
	}
	
	@GetMapping(path= {"/mypage/userData"})
	@ResponseBody
	public Member userData(HttpServletRequest req) {
		Authentication auth = (Authentication)req.getUserPrincipal();
		String id = auth.getName();
		Member member = memberService.findMemberById(id);
		
		return member; 
	}
	
	@PostMapping(path = { "/mypage/userUpdate" })
	@ResponseBody
	public String userUpdate(@RequestBody Member member) {
		 memberService.UpdateUser(member);
		return "success";
	}

}
