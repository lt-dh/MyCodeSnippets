package com.lt;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * @author liutong
 * @date 2024年11月13日 17:42
 */
public class GenerateMain {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/test?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai",
                        "root", "root")
                .globalConfig(builder -> {
                    builder.author("lt") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("D:\\person\\projects\\MyCodeSnippets\\mysql\\mysql-code-generate\\src\\main\\java\\com\\lt"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("test") // 设置父包名
                                .moduleName("a") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\person\\projects\\MyCodeSnippets\\mysql\\mysql-code-generate\\src\\main\\java\\com\\lt\\system")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude("a") // 设置需要生成的表名
//                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
