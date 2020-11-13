package com.dragon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//配置扫描包
@MapperScan("com.dragon.mapper")
@ComponentScan({"com.dragon.web", "com.dragon.service"})
//@EnableCaching//配置开启缓存
public class SpringbootdemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringbootdemoApplication.class, args);
		//相对完整的spring+mybatis项目部署配置
		//http://blog.csdn.net/winter_chen001/article/details/77249029
	}
}
