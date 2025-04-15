package com.edwin.emsp;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {
    public static void main(String[] args) {
        // 1. 数据源配置
        DataSourceConfig dataSource = new DataSourceConfig.Builder(
                "jdbc:mysql://localhost:3306/emsp?useSSL=false&serverTimezone=UTC",
                "root",
                "123456"
        ).build();

        // 2. 全局配置
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .outputDir("D:\\dev\\emsp\\src\\main\\java") // 输出路径
                .author("jiucheng") // 作者
//                .openDir(false) // 生成后不打开文件夹
                .disableOpenDir()
//                .fileOverride() // 覆盖已有文件
                .build();

        // 3. 包配置
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent("com.edwin.emsp") // 父包名
                .entity("entity") // 实体类包名
                .mapper("mapper") // Mapper 接口包名
//                .service("service") // Service 包名
//                .controller("controller") // Controller 包名（可选）
                .build();

        // 4. 生成策略配置
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .addInclude("emsp_history") // 指定要生成的表名
                .addTablePrefix("emsp_") // 过滤表前缀（如 t_user → User）
                .entityBuilder()
                .naming(NamingStrategy.underline_to_camel) // 字段名驼峰转换
                .columnNaming(NamingStrategy.underline_to_camel)
                .enableLombok() // 启用 Lombok
//                .logicDeleteColumnName("deleted") // 逻辑删除字段
                .enableTableFieldAnnotation() // 字段添加注解 @TableField
                .mapperBuilder()
                .enableMapperAnnotation() // Mapper 接口添加 @Mapper
                .serviceBuilder()
                .formatServiceFileName("%sService") // Service 接口命名规则
                .build();

        // 5. 模板配置（可选，默认使用内置模板）
        TemplateConfig templateConfig = new TemplateConfig.Builder()
                .disable(TemplateType.CONTROLLER) // 不生成 Controller
                .build();

        // 6. 启动生成器
        AutoGenerator generator = new AutoGenerator(dataSource)
                .global(globalConfig)
                .packageInfo(packageConfig)
                .strategy(strategyConfig)
                .template(templateConfig);
        generator.execute();
    }
}