package source.code.oneclickbooking.integration.annotation

import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@SqlGroup(
    Sql(
        scripts = ["classpath:testcontainers/insert-data.sql"],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    ),
    Sql(
        scripts = ["classpath:testcontainers/remove-data.sql"],
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
)
annotation class SqlSetup()
