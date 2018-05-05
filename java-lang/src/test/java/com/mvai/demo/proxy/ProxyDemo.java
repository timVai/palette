package com.mvai.demo.proxy;

import com.mvai.demo.service.PlayGroud;
import com.mvai.demo.service.PlayGround;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author fanfengshi
 */
public class ProxyDemo {


	private static void testJVMProxy(){
		final PlayGroud real = new PlayGroud();

		PlayGround proxy = (PlayGround) Proxy.newProxyInstance(real.getClass().getClassLoader(),real.getClass().getInterfaces(), new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("method name is : "+method.getName());
				System.out.println("before");
				if(args!=null){
					for(Object arg : args){
						System.out.println("arg"+arg.toString());
					}
				}
				Object returnVal = method.invoke(real,args);
				System.out.println("after");
				return returnVal;
			}
		});

		proxy.test2();
	}


	private void testCGLibProxy(){
		PlayGround real = new PlayGroud();
		PlayGround proxy = (PlayGround) new ProxyFactory(real).getProxyInstance();
		proxy.test2();
	}


	public class ProxyFactory implements MethodInterceptor {
		//维护目标对象
		private Object target;

		public ProxyFactory(Object target) {
			this.target = target;
		}

		//给目标对象创建一个代理对象
		public Object getProxyInstance(){
			//1.工具类
			Enhancer en = new Enhancer();
			//2.设置父类
			en.setSuperclass(target.getClass());
			//3.设置回调函数
			en.setCallback(this);
			//4.创建子类(代理对象)
			return en.create();

		}

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			System.out.println("开始事务...");

			//执行目标对象的方法
			Object returnValue = method.invoke(target, args);

			System.out.println("提交事务...");

			return returnValue;
		}
	}

}
