package com.codingallen;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql:///xdb";
        String username = "root";
        String password = "imstrong1989";
        String moduleName = "sys";
        //数据库操作语句（SQL）与 Java 对象之间的映射关系
        String mapperLocationg ="/Users/wenchengjiang/Documents/x-admin/src/main/resources/mapper/" + moduleName;
        String tables = "x_user,x_role,x_menu,x_user_role,x_role_menu";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("CodingAllen")
                            //.enableSwagger()
                            //.fileOverride()
                            .outputDir("/Users/wenchengjiang/Documents/x-admin/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.codingallen")
                            .moduleName(moduleName)
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocationg));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            .addTablePrefix("x_");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
