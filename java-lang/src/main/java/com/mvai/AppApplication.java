package com.mvai;

import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import com.mvai.demo.service.PlayGroud;
import com.mvai.demo.service.PlayGround;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.w3c.dom.Document;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author fanfengshi
 */
@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {



		SpringApplication.run(AppApplication.class, args);
	}




}
