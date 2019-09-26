package com.gamsung.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamsung.mapper.ProductMapper;
import com.gamsung.vo.Product;
import com.gamsung.vo.ProductFile;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductMapper productMapper;

	public Product findProductByProductNo(int productNo) {

		Product product = productMapper.selectProductByProductNo(productNo);
		return product;

	}

	@Override
	public ArrayList<Product> findProducts() {
		
		ArrayList<Product> products = productMapper.selectProducts();
		
		for(Product product : products) {
			ProductFile file = productMapper.selectFileByProductNo(product.getProductNo());
			product.setFile(file);
		}
		
		return products;
	}

//	@Override
//	public void writeProduct(Product product) {
//		productMapper.insertProduct(product);
//		
//	}

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
	

}
