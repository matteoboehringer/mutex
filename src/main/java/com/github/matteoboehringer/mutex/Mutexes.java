package com.github.matteoboehringer.mutex;

import java.util.concurrent.locks.Lock;

/**
 * @see Mutex
 */
public final class Mutexes {
  private Mutexes(){}

  public static Mutex ofLock(Lock lock) {
    return ExtrinsicMutex.of(lock);
  }

  public static Mutex ofObject(Object object) {
    return DelegatingIntrinsicMutex.of(object);
  }
}
