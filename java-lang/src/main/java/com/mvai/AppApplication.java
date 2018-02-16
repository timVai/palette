package com.mvai;

import com.mvai.demo.service.PlayGroud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author fanfengshi
 */
@SpringBootApplication
public class AppApplication {


	@Autowired
	PlayGroud playGroud;


	public static void main(String[] args) {


		System.out.print("first run SpringApplication");



		SpringApplication.run(AppApplication.class, args);


		System.out.print("first run SpringApplication");
		System.out.print("first run SpringApplication");
		System.out.print("first run SpringApplication");
		System.out.print("first run SpringApplication");
		System.out.print("first run SpringApplication");

		
	}
}
