package com.gamsung.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration // Bean 설정
@PropertySource("classpath:/application.properties") //설정 파일의 위치를 지정 (로그인 정보 등이 담긴 설정파일)
public class DatabaseConfig {
	
	@Autowired
	private ApplicationContext applicationContext; //IoC 컨테이너

	@Bean // <bean id="" 과 같은 기능
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig(); // 데이터베이스 연결 정보 설정		/ 히카리CP의 설정파일을 만듦.
		return config;
	}
	
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new HikariDataSource(hikariConfig()); //데이터 소스가 정상적으로 생성 되었는지 확인 용도
		return dataSource;
	}
	
	////////////////////////////////////////////////////////////
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig() {

		return new org.apache.ibatis.session.Configuration();		 
	
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean(); // mybatis를 사용하기위해 SqlSessionFactory를 생성.
		factoryBean.setDataSource(dataSource()); // 데이터 소스 설정
		
		factoryBean.setMapperLocations(
				applicationContext.getResources("classpath:/com/gamsung/mapper/**/*-mapper.xml")); // mapper파일의 위치 지정
		
		factoryBean.setConfiguration(mybatisConfig());
		
		return factoryBean.getObject();
	}
	
	/////////////////////////////////////
	
}










