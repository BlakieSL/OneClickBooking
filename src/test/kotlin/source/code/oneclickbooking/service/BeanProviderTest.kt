package source.code.oneclickbooking.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.ApplicationContext
import source.code.oneclickbooking.service.implementation.util.BeanProvider

@ExtendWith(MockitoExtension::class)
class BeanProviderTest {
    private lateinit var applicationContext: ApplicationContext

    @BeforeEach
    fun setUp() {
        applicationContext = mock(ApplicationContext::class.java)
        BeanProvider().setApplicationContext(applicationContext)
    }

    @Test
    fun `should get bean successfully`() {
        val bean = Any()
        whenever(applicationContext.getBean(Any::class.java)).thenReturn(bean)

        val result = BeanProvider.getBean(Any::class.java)

        assertNotNull(result)
        assertEquals(bean, result)
        verify(applicationContext).getBean(Any::class.java)
    }

    @Test
    fun `should throw exception when bean not found`() {
        whenever(applicationContext.getBean(Any::class.java))
            .thenThrow(NoSuchBeanDefinitionException::class.java)

        assertThrows<NoSuchBeanDefinitionException> {
            BeanProvider.getBean(Any::class.java)
        }

        verify(applicationContext).getBean(Any::class.java)
    }
}