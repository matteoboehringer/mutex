package com.github.matteoboehringer.mutex;

import com.github.matteoboehringer.mutex.unsafe.UnsafeProvider;

import java.util.Objects;

@Reentrant
public final class IntrinsicMutex implements Mutex {
  private IntrinsicMutex() {}

  @Override
  public boolean tryAcquire() {
    return UnsafeProvider.getUnsafe().tryMonitorEnter(this);
  }

  @Override
  public void acquire() {
    UnsafeProvider.getUnsafe().monitorEnter(this);
  }

  @Override
  public void release() {
    UnsafeProvider.getUnsafe().monitorExit(this);
  }

  @Override
  public int hashCode() {
    return Objects.hash(System.identityHashCode(this));
  }

  @Override
  public String toString() {
    return "IntrinsicMutex{}";
  }

  public static IntrinsicMutex create() {
    return new IntrinsicMutex();
  }
}
