package com.threatconnect.app.apps.service.webhook.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Webhook
{
	/**
	 * The URI of the webhook
	 *
	 * @return
	 */
	String uri();
	
	Method method();
	
	String name() default "";
	
	String description() default "";
}
