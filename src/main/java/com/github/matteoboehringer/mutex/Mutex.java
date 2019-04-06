package com.github.matteoboehringer.mutex;

/**
 * Synchronization mechanism that serializes thread-access to a defined
 * section of code.
 *
 * <p>As opposed to a {@link java.util.concurrent.locks.Lock},
 * the {@code Mutex} does not provide a {@code Condition} and generally
 * defines a slimmer interface, which allows a simple implementation of
 * top level intrinsic locking.
 *
 * @see Mutexes
 * @see java.util.concurrent.locks.Lock
 */
public interface Mutex {

  /**
   *
   */
  void acquire();

  /**
   *
   */
  void release();

  /**
   * Tries to get ownership of the mutex without blocking the
   * requesting thread and returns the state of success.
   *
   * @return Whether the thread got ownership of the mutex.
   */
  boolean tryAcquire();
}
