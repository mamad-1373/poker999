package com.poker999.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Valloon Project
 * @version 1.0 @2019-07-04
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Role {
	public static final int ROLE_MASTER = 1;
	public static final int ROLE_EMPLOYEE = 2;

	int value() default 0;
}
