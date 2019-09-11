package me.mfathy.task.base

import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

abstract class BaseUnitTest {
    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        postSetup()
    }

    /**
     * Use this method to initialize your custom test setup
     */
    abstract fun postSetup()

}