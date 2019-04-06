package com.github.matteoboehringer.mutex;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ExtrinsicMutex implements Mutex {
  private Lock lock;

  private ExtrinsicMutex(Lock lock) {
    this.lock = lock;
  }

  @Override
  public void acquire() {
    lock.lock();
  }

  @Override
  public void release() {
    lock.unlock();
  }

  @Override
  public boolean tryAcquire() {
    return lock.tryLock();
  }

  @Override
  public int hashCode() {
    return Objects.hash(lock);
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof ExtrinsicMutex)) {
      return false;
    }
    return ((ExtrinsicMutex)object).lock.equals(lock);
  }

  @Override
  public String toString() {
    return String.format("ExtrinsicMutex{%s}", lock);
  }

  public static ExtrinsicMutex create() {
    return new ExtrinsicMutex(new ReentrantLock());
  }

  public static ExtrinsicMutex of(Lock lock) {
    Objects.requireNonNull(lock);
    return new ExtrinsicMutex(lock);
  }
}
