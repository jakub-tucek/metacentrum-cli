package cz.fit.metacentrum.service.action.submit.executor

import cz.fit.metacentrum.TestData
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/**
 * @author Jakub Tucek
 */
@Disabled
internal class ResolvePathExecutorTest {


    @Test
    fun testThatAbsolutePathWontChange() {
        Assertions.assertThat(ResolvePathExecutor().execute(TestData.metadata))
                .isEqualTo(TestData.metadata)
    }
}