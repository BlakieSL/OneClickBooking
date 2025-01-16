package source.code.oneclickbooking.config

import org.hibernate.dialect.MySQLDialect
import org.hibernate.boot.model.FunctionContributions
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.query.ReturnableType
import org.hibernate.query.spi.QueryEngine
import org.hibernate.query.sqm.function.*
import org.hibernate.type.StandardBasicTypes

import org.hibernate.query.sqm.produce.function.StandardArgumentsValidators
import org.hibernate.query.sqm.produce.function.StandardFunctionReturnTypeResolvers
import org.hibernate.sql.ast.SqlAstTranslator
import org.hibernate.sql.ast.tree.SqlAstNode
import org.hibernate.sql.ast.spi.SqlAppender
import org.hibernate.type.spi.TypeConfiguration

class CustomMySQLDialect : MySQLDialect() {
    override fun initializeFunctionRegistry(contributions: FunctionContributions) {
        super.initializeFunctionRegistry(contributions)
        val functionRegistry: SqmFunctionRegistry = contributions.functionRegistry
        val typeConfiguration: TypeConfiguration = contributions.typeConfiguration

        functionRegistry.register(
            "custom_timestampdiff",
            CustomTimestampDiffFunction(typeConfiguration)
        )
    }
}

class CustomTimestampDiffFunction(typeConfiguration: TypeConfiguration) :
    AbstractSqmSelfRenderingFunctionDescriptor(
        "custom_timestampdiff",
        FunctionKind.NORMAL,
        StandardArgumentsValidators.exactly(2),
        StandardFunctionReturnTypeResolvers.invariant(typeConfiguration.basicTypeRegistry.resolve(StandardBasicTypes.INTEGER)),
        null
    ) {

    override fun render(
        sqlAppender: SqlAppender?,
        sqlAstArguments: MutableList<out SqlAstNode>?,
        returnType: ReturnableType<*>?,
        walker: SqlAstTranslator<*>?
    ) {
        if (sqlAppender == null || sqlAstArguments == null || walker == null) {
            throw IllegalArgumentException("sqlAppender, sqlAstArguments, and walker cannot be null")
        }

        sqlAppender.appendSql("TIMESTAMPDIFF(")
        sqlAppender.appendSql("SECOND")
        sqlAppender.appendSql(", ")
        sqlAstArguments[0].accept(walker)
        sqlAppender.appendSql(", ")
        sqlAstArguments[1].accept(walker)
        sqlAppender.appendSql(")")
    }
}