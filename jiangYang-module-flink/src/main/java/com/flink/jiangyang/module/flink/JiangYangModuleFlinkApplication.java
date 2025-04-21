package com.flink.jiangyang.module.flink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.flink.jiangyang.module.flink.mapper"})
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		RedisAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class,
		ThymeleafAutoConfiguration.class
})
public class JiangYangModuleFlinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(JiangYangModuleFlinkApplication.class, args);
	}

}
