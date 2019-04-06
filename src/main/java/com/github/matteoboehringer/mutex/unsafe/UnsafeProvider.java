package com.github.matteoboehringer.mutex.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Provides a way to access the {@link Unsafe} class in a lazy and
 * thread-safe manner.
 *
 * <p>The Unsafe itself is a Singleton, and can be accessed using
 * the {@link Unsafe#getUnsafe()} method. However this is only
 * possible when the calling class has a specific class-loader,
 * which third_party classes usually do not have.
 *
 * <p>Using the Unsafe in production is not advised because it is
 * part of a proprietary API which may not be accessible in every
 * environment and is removed in latter versions of the JDK.
 * Some libraries however, even within Java stdlib, still use features
 * from this class.
 *
 * @see Unsafe
 */
public final class UnsafeProvider {
  private static final class Lazy {
    /* This field is lazily initialized using the initialization
     * on demand holder idiom. This way we can ensure that the
     * unsafe is only loaded when needed without using any
     * explicit locking. To read more about this idiom:
     * https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
     */
    static final Unsafe UNSAFE = resolveUnsafeOrThrow();
  }

  // Tries to get a reference to the Unsafe singleton or throws a exception.
  private static Unsafe resolveUnsafeOrThrow() {
    try {
      Field singleton = Unsafe.class.getDeclaredField("theUnsafe");
      if (!singleton.isAccessible()) {
        singleton.setAccessible(true);
      }
      return (Unsafe) singleton.get(null);
    } catch (IllegalAccessException | NoSuchFieldException reflectionFailure) {
      throw UnsafeNotAccessibleException.create(reflectionFailure);
    }
  }

  /**
   * @return Reference to the Unsafe's singleton.
   */
  public static Unsafe getUnsafe() {
    return Lazy.UNSAFE;
  }
}
