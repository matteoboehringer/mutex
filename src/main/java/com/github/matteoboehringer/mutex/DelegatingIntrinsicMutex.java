package com.github.matteoboehringer.mutex;

import com.github.matteoboehringer.mutex.unsafe.UnsafeProvider;

import java.util.Objects;

@Reentrant
public final class DelegatingIntrinsicMutex implements Mutex {
  private Object delegate;
  private DelegatingIntrinsicMutex(Object mutex) {
    this.delegate = mutex;
  }

  @Override
  public void acquire() {
    UnsafeProvider.getUnsafe().monitorEnter(delegate);
  }

  @Override
  public void release() {
    UnsafeProvider.getUnsafe().monitorExit(delegate);
  }

  @Override
  public boolean tryAcquire() {
    return UnsafeProvider.getUnsafe().tryMonitorEnter(delegate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(System.identityHashCode(this));
  }

  @Override
  public String toString() {
    return String.format("DelegatingIntrinsicMutex{%s}", delegate);
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof DelegatingIntrinsicMutex)) {
      return false;
    }
    // Checks for the equality of the mutexes instances, since the
    // lock flag is in their header. Do not replace with 'equals()'.
    return ((DelegatingIntrinsicMutex)object).delegate == delegate;
  }

  public static DelegatingIntrinsicMutex create() {
    return new DelegatingIntrinsicMutex(new Object());
  }

  public static DelegatingIntrinsicMutex of(Object mutex) {
    Objects.requireNonNull(mutex);
    return new DelegatingIntrinsicMutex(mutex);
  }
}
