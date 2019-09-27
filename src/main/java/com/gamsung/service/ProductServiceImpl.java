package com.gamsung.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.DealMapper;
import com.gamsung.mapper.ProductMapper;
import com.gamsung.mapper.ReviewMapper;
import com.gamsung.vo.Deal;
import com.gamsung.vo.Heart;
import com.gamsung.vo.Product;
import com.gamsung.vo.ProductFile;
import com.gamsung.vo.Review;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	ReviewMapper reviewMapper;

	@Autowired
	DealMapper dealMapper;

	/*	Product	*/
	
	@Override
	public Product findProductByProductNo(int productNo) {

		Product product = productMapper.selectProductByProductNo(productNo);
		
		//
		ProductFile file = productMapper.selectProductFileByProductNo(product.getProductNo());
		product.setFile(file);
		
		return product;

	}

	@Override
	public ArrayList<Product> findProducts() {
		
		ArrayList<Product> products = productMapper.selectProducts();
		
		for(Product product : products) {
			ProductFile file = productMapper.selectProductFileByProductNo(product.getProductNo());
			product.setFile(file);
		}
		
		return products;
	}
	
	@Override
	public Integer registerProductTx(Product product) {
		System.out.println(product);
		productMapper.insertProduct(product);
		int newProductNo = product.getProductNo();
		System.out.println(product);
		
		// 대표이미지
		ProductFile titleFile = product.getFile();
		titleFile.setProductNo(newProductNo);
		productMapper.insertProductFile(titleFile);
		
		insertProductFiles(product, newProductNo);
		
		return newProductNo;
		
	}
	
	@Override
	public void insertProductFiles(Product product, int productNo) {
		
		// 이미지
		for (ProductFile file : product.getFiles()) {
			file.setProductNo(productNo);
			System.out.println(file);
			productMapper.insertProductFile(file);
			System.out.println(file);
		}
		
	}

	@Override
	public List<Product> findMyProductList(String memberId) {
		
		List<Product> products = productMapper.selectMyProductList(memberId);
		
		//
		for(Product product : products) {
			product.setFile(productMapper.selectProductFileByProductNo(product.getProductNo()));
		}
		
		return products;
	}
	
	/*	Heart	*/

	@Override
	public void insertHeart(Heart heart) {
		
		productMapper.insertHeart(heart);
		
	}

	@Override
	public void deleteHeart(String id, int productNo) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("productNo", productNo);
		
		productMapper.deleteHeart(params);
		
	}

	@Override
	public Heart findHeart(String id, int productNo) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("productNo", productNo);
		
		Heart heart = productMapper.selectHeart(params);
		
		return heart;
	}
	
	@Override
	public boolean findHeartCount(String id, int productNo) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("productNo", productNo);
		
		boolean check = productMapper.selectHeartCount(params);
		
		return check;
	}
	
	/*	Deal	*/

	@Override
	public List<Deal> findDealsByProductNo(int productNo) {
		List<Deal> deals = dealMapper.selectDealsByProductNo(productNo);
		return deals;
	}

	@Override
	public List<Deal> findDealsByBuyer(String memberId) {
		List<Deal> deals = dealMapper.selectDealsByBuyer(memberId);
		return deals;
	}
	

	/* Review	*/
	
	@Override
	public ArrayList<Review> findReviewsByProductNo(int productNo) {
		ArrayList<Review> reviewlist = reviewMapper.selectReviewsByProductNo(productNo);
		return reviewlist;
	}

	@Override
	public List<Product> findMyHeartList(String memberId) {
		
		List<Product> heartlist = productMapper.selectMyHeartList(memberId);
		
		return heartlist;
	}

}
