package source.code.oneclickbooking.service.implementation

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Service

@Service
class BeanProvider : ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        BeanProvider.applicationContext = applicationContext
    }

    companion object {
        private lateinit var applicationContext: ApplicationContext

        fun <T> getBean(beanClass: Class<T>): T = applicationContext.getBean(beanClass)
    }
}