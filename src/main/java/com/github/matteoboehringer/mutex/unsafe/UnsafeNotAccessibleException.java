package com.github.matteoboehringer.mutex.unsafe;

import java.util.Objects;

/**
 * Indicates that the {@link sun.misc.Unsafe} class is not accessible.
 *
 * @see sun.misc.Unsafe
 * @see UnsafeProvider
 */
public final class UnsafeNotAccessibleException extends RuntimeException {
  private UnsafeNotAccessibleException(Exception cause) {
    super(cause);
  }

  static UnsafeNotAccessibleException create(Exception cause) {
    Objects.requireNonNull(cause);
    return new UnsafeNotAccessibleException(cause);
  }
}
