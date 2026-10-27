package fr.freshperf.pve4j.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ProxmoxAsyncTaskManager Tests")
class ProxmoxAsyncTaskManagerTest {

    @Test
    @DisplayName("Should ramp checks up to the check interval")
    void shouldRampChecksUpToInterval() {
        long[] checkTimes = new long[7];
        long elapsed = 0;
        for (int i = 0; i < checkTimes.length; i++) {
            elapsed += ProxmoxAsyncTaskManager.pollDelayMillis(1000, elapsed);
            checkTimes[i] = elapsed;
        }

        assertThat(checkTimes).containsExactly(125, 250, 500, 1000, 2000, 3000, 4000);
    }

    @Test
    @DisplayName("Should not let slow checks delay the following ones")
    void shouldNotShiftScheduleAfterSlowChecks() {
        // A check started at 500ms that took 100ms: the next one is still due at 1000ms
        assertThat(ProxmoxAsyncTaskManager.pollDelayMillis(1000, 600)).isEqualTo(400);
        // A check started at 2000ms that took 100ms: the next one is due at 3000ms
        assertThat(ProxmoxAsyncTaskManager.pollDelayMillis(1000, 2100)).isEqualTo(900);
        // Check times already missed are skipped rather than caught up on
        assertThat(ProxmoxAsyncTaskManager.pollDelayMillis(1000, 300)).isEqualTo(200);
        assertThat(ProxmoxAsyncTaskManager.pollDelayMillis(1000, 3500)).isEqualTo(500);
    }

    @Test
    @DisplayName("Should land ramped checks on the fixed schedule from the first interval on")
    void shouldMatchFixedScheduleAfterRamp() {
        for (long interval : new long[]{1, 7, 8, 100, 1000, 1001, 1500, 5000, 60_000}) {
            long elapsed = 0;
            int checks = 0;
            while (elapsed < interval) {
                long delay = ProxmoxAsyncTaskManager.pollDelayMillis(interval, elapsed);
                assertThat(delay).isBetween(1L, interval);
                elapsed += delay;
                checks++;
            }
            assertThat(checks).as("checks before the first interval for %d", interval).isLessThanOrEqualTo(4);
            assertThat(elapsed).as("first full-interval check for interval %d", interval).isEqualTo(interval);
            assertThat(ProxmoxAsyncTaskManager.pollDelayMillis(interval, elapsed)).isEqualTo(interval);
        }
    }

    @Test
    @DisplayName("Should return non-positive intervals unchanged")
    void shouldReturnNonPositiveIntervalsUnchanged() {
        for (long elapsed = 0; elapsed < 10; elapsed++) {
            assertThat(ProxmoxAsyncTaskManager.pollDelayMillis(0, elapsed)).isZero();
        }
        assertThat(ProxmoxAsyncTaskManager.pollDelayMillis(-5, 0)).isEqualTo(-5);
    }
}
