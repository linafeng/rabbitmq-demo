package com.fiona.mq.rabbitmq.util.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component 
@Lazy(false) 
public class AppCtxRegister implements ApplicationContextAware { 
	private static final Logger LOGGER = LoggerFactory.getLogger(AppCtxRegister.class);   
	private static ApplicationContext APPLICATION_CONTEXT;   
	/**  
	 * set spring context
	 * @param applicationContext spring context
	 * @throws BeansException  
	 */  
	@Override  
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		LOGGER.debug("ApplicationContext registed-->{}", applicationContext);  
		APPLICATION_CONTEXT = applicationContext;  
	} 
	//获取applicationContext
	public static ApplicationContext getCtx() { 
		return APPLICATION_CONTEXT;  
	}
	// 通过name获取 Bean.
	public static Object getBean(String name) {
		return getCtx().getBean(name);
	}

	// 通过class获取Bean.
	public static <T> T getBean(Class<T> clazz) {
		return getCtx().getBean(clazz);
	}

	// 通过name,以及Clazz返回指定的Bean
	public static <T> T getBean(String name,Class<T> clazz){  
		return getCtx().getBean(name, clazz);
	}
}
