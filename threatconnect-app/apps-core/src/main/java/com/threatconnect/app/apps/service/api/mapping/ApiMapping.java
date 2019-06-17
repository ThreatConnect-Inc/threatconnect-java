package com.threatconnect.app.apps.service.api.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMapping
{
	/**
	 * The URI of the api request
	 *
	 * @return
	 */
	String uri();
	
	Method method();
	
	String name() default "";
	
	String description() default "";
}
