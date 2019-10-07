package com.gamsung.service;

import java.util.ArrayList;
import java.util.List;

import com.gamsung.vo.Heart;
import com.gamsung.vo.Member;
import com.gamsung.vo.Product;
import com.gamsung.vo.ProductFile;

public interface ProductService {

	/*	Product	*/
	
	Product findProductByProductNo(int productNo);

	ArrayList<Product> findProducts(String type, String category, String keyword);
	
	Integer registerProductTx(Product product);

	void insertProductFiles(Product product, int productNo);

	List<Product> findMyProductList(String memberId);
	
	List<Product> findMyRequestProductList(String memberId);
	
	void updateProductCount(int productNo);

	void deleteProductFile(int productFileNo);
	
	void updateProductFile(ProductFile productFile);
	
	void deleteProduct(int productNo);
	
	void updateProduct(Product product);
	
	List<Product> findMain();
	
	//메인 검색
	ArrayList<Product> findProductSearch(String keyword);
	
	/*	Heart	*/

	void insertHeart(Heart heart);
	
	void deleteHeart(String id, int productNo);

	Heart findHeart(String id, int productNo);
	
	boolean findHeartCount(String id, int productNo);

	List<Product> findMyHeartList(String memberId);

	Integer findHeartCountByProductNo(int productNo);


}
