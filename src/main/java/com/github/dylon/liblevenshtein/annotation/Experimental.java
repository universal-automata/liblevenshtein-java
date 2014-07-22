package com.github.dylon.liblevenshtein.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks an entity as experimental, in that it may change or be removed in the
 * future.  An experimental entity will typically be one that has not been
 * proven correct or one in which there are multiple ways to implement it and I
 * haven't decided yet which I prefer.
 * @author Dylon Edwards
 * @since 2.1.0
 */
@Inherited
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Experimental {
	String value() default "This is experimental and may change or be removed.";
}
