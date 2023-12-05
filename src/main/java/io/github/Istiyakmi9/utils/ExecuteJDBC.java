package io.github.Istiyakmi9.utils;

import io.github.Istiyakmi9.models.JDBCParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExecuteJDBC {
    @Autowired
    ExecuteJDBC(DatabaseConfiguration databaseConfiguration) {
        TemplateJDBC template = new TemplateJDBC();
        jdbcTemplate = template.getTemplate(databaseConfiguration);
    }

    @Autowired
    DatabaseConfiguration databaseConfiguration;
    private final JdbcTemplate jdbcTemplate;

    public <T> Map<String, Object> executeProcedure(String procedureName, List<JDBCParameters> sqlParams) throws Exception {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(databaseConfiguration.getDatabase())
                .withProcedureName(procedureName);

        Map<String, Object> paramSet = new HashMap<>();
        try {
            for (JDBCParameters dbParameters : sqlParams) {
                paramSet.put(dbParameters.parameter, dbParameters.value);
                simpleJdbcCall.addDeclaredParameter(
                        new SqlParameter(
                                dbParameters.parameter,
                                dbParameters.type
                        ));
            }

            return simpleJdbcCall.execute(paramSet);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
