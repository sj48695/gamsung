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
import com.gamsung.vo.ReviewFile;

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
		
		//detail 이미지
		ProductFile titleFile = productMapper.selectProductFileByProductNo(product.getProductNo());
		product.setFile(titleFile);
		List<ProductFile> files = productMapper.selectProductFilesByProductNo(product.getProductNo());
		product.setFiles(files);
		
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
		productMapper.insertProduct(product);
		int newProductNo = product.getProductNo();
		
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
			productMapper.insertProductFile(file);
		}
		
	}

	@Override
	public List<Product> findMyProductList(String memberId) {
		
		List<Product> products = productMapper.selectMyProductList(memberId);
		for(Product product : products) {
			product.setFile(productMapper.selectProductFileByProductNo(product.getProductNo()));
			product.setDeals(dealMapper.selectDealsByProductNo(product.getProductNo()));//요청받은 거래
		}
		
		return products;
	}
	

	@Override
	public List<Product> findMyRequestProductList(String memberId) {
		List<Product> products = productMapper.selectMyRequestProductList(memberId);
		for(Product product : products) {
			product.setFile(productMapper.selectProductFileByProductNo(product.getProductNo()));
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("buyer", memberId);
			params.put("productNo", product.getProductNo());

			product.setDeals(dealMapper.selectDealsByBuyer(params));//요청한 거래
		}
		
		return products;
	}

	@Override
	public void updateProductCount(int productNo) {
		productMapper.updateProductCount(productNo);
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
	
	@Override
	public List<Product> findMyHeartList(String memberId) {
		
		List<Product> heartlist = productMapper.selectMyHeartList(memberId);
		
		for(Product product : heartlist) {
			product.setFile(productMapper.selectProductFileByProductNo(product.getProductNo()));
		}
		
		return heartlist;
	}
	
	/* Review	*/
	
	@Override
	public ArrayList<Review> findReviewsByProductNo(int productNo) {
		ArrayList<Review> reviewlist = reviewMapper.selectReviewsByProductNo(productNo);
		return reviewlist;
	}

	@Override
	public Integer insertReview(Review review) {
		reviewMapper.insertReview(review);
		int newDealNo = review.getDealNo();
		
		insertReviewFiles(review, newDealNo);
		
		return newDealNo;
	}
	
	@Override
	public void insertReviewFiles(Review review, int dealNo) {
		// 이미지
		for (ReviewFile file : review.getFiles()) {
			file.setDealNo(dealNo);
			reviewMapper.insertReviewFile(file);
		}
		
	}

	@Override
	public List<Review> selectReview(String memberId) {
		List<Review> reviewlist = reviewMapper.selectReview(memberId);
		for(Review review : reviewlist) {
			Deal deal = dealMapper.selectDealByDealNo(review.getDealNo());
			review.setProductNo(deal.getProductNo());
		}
		return reviewlist;
	}

	@Override
	public void deleteProduct(int productNo) {
		productMapper.deleteProduct(productNo);
		
	}

}
