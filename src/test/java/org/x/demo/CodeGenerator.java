package org.x.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.x.demo.sbmdemo.simple.Application;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class CodeGenerator {
    private String tableName = "sys_user";

    @Autowired
    private DataSource dataSource;

    @Test
    public void voGenerator() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        String sql = "select column_name, column_type from information_schema.columns where table_schema='xdb' and table_name=:tableName";
        Map<String, String> param = new HashMap<>();
        param.put("tableName", tableName);

        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(sql, param);
        for (Map<String, Object> columnInfo : list) {
            String columnName = "" + columnInfo.get("COLUMN_NAME");
            String columnType = "" + columnInfo.get("COLUMN_TYPE");
            System.out.println("private " + transColumnType(columnType) + " " + transColumnName(columnName) + ";");
        }
    }

    private String transColumnType(String typeStr) {
        int tagIndex = typeStr.indexOf("(");
        String type = (tagIndex <= 0) ? typeStr : typeStr.substring(0, tagIndex);
        switch (type.toLowerCase()) {
            case "bigint": return "Long";
            case "int": return "Integer";
            case "varchar": return "String";
            case "char": return "String";
            case "text": return "String";
            case "datetime": return "LocalDateTime";
            default:
                System.out.println("***warn*** " + type + " no mapper.");
                return "String";
        }
    }

    private String transColumnName(String nameStr) {
        char[] charAry = nameStr.toCharArray();

        StringBuilder sb = new StringBuilder();
        boolean needUpperCase = false;
        for (char c : charAry) {
            if ('_' == c) {
                needUpperCase = true;
            } else {
                sb.append((needUpperCase) ? ("" + c).toUpperCase() : c);
                needUpperCase = false;
            }
        }

        return sb.toString();
    }
}
