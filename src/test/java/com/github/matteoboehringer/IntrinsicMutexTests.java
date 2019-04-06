package com.github.matteoboehringer;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.github.matteoboehringer.mutex.Mutex;
import com.github.matteoboehringer.mutex.IntrinsicMutex;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;

public final class IntrinsicMutexTests {
  @Rule
  public ExpectedException thrown;

  private ReentrantMutexTest tester;

  public IntrinsicMutexTests() {
    this.tester = ReentrantMutexTest.create(IntrinsicMutex::create);
    this.thrown = ExpectedException.none();
  }

  @Test
  public void testAcquire_unlocked() {
    Mutex mutex = IntrinsicMutex.create();
    mutex.acquire();
  }

  @Test
  public void testRelease_unlocked() {
    thrown.expect(IllegalMonitorStateException.class);
    thrown.expectMessage(is("current thread not owner"));

    Mutex mutex = IntrinsicMutex.create();
    mutex.release();
  }

  @Test
  public void testReentrancy() {
    tester.testReentrancy();
  }

  /**
   * Tests releasing the mutex, when it is locked intrinsicly.
   * This test should not throw an IllegalMonitorStateException.
   */
  @Test
  public void testRelease_lockedIntrinsicly() {
    Mutex mutex = IntrinsicMutex.create();
    synchronized (mutex) {
      mutex.release();
    }
  }

  @RunWith(ConcurrentTestRunner.class)
  public final static class RaceConditionCounter {
    private static final int WORKER_COUNT = 4; // ConcurrentTestRunner count
    private static final int INCREMENTS_PER_WORKER = 1000;

    private Mutex mutex;
    private long count;

    @Before
    public void prepare() {
      this.mutex = IntrinsicMutex.create();
    }

    @Test
    public void increment() {
      mutex.acquire();
      for (int index = 0; index < INCREMENTS_PER_WORKER; index++) {
        this.count++;
      }
      mutex.release();
    }

    @After
    public void ensureTargetCount() {
      Assert.assertEquals(count, WORKER_COUNT * INCREMENTS_PER_WORKER);
    }
  }
}
