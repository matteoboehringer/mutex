package com.github.matteoboehringer;

import com.github.matteoboehringer.mutex.Mutex;
import com.github.matteoboehringer.mutex.Mutexes;
import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ExtrinsicMutexTests {

  @Test
  public void testLock_unlocked() {
    Lock lock = new ReentrantLock();
    Mutex wrapper = Mutexes.ofLock(lock);
    wrapper.acquire();
    lock.unlock();
  }
}
