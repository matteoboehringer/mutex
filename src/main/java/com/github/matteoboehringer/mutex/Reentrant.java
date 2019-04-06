package com.github.matteoboehringer.mutex;

import java.lang.annotation.*;

/**
 * Marks a Mutex implementation to be Reentrant.
 *
 * <p>A reentrant mutex allows a thread to acquire it while
 * already having the ownership. This is, in most situations,
 * critical to prevent deadlocks.
 *
 * @see Mutex
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Reentrant { }
