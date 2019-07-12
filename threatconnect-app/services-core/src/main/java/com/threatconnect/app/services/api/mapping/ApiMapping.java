package com.threatconnect.app.services.api.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMapping
{
	/**
	 * The path of the api request
	 *
	 * @return
	 */
	String path();
	
	Method method();
	
	String name() default "";
	
	String description() default "";
	
	Header[] headers() default {};
}
