//package com.free.badmod.blackhole.plusgen;
//
//import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * <p>
// * mysql 代码生成器演示例子
// * </p>
// *
// * @author jobob
// * @since 2018-09-12
// */
//public class MysqlGenerator {
//
//    /**
//     * <p>
//     * 读取控制台内容
//     * </p>
//     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入").append(tip).append("：");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
//            if (StringUtils.isNotBlank(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "！");
//    }
//
//    /**
//     * RUN THIS
//     */
//    public static void main(String[] args) {
//
//
//
//        //todo 修改以下3项
//        String moduleName = "black-hole-app";//配置哪个module文件生成在哪个module下
//        String baseEntityPath = "com.free.badmood.blackhole.base.entity.BaseEntity";
//        String baseControlPath = "com.free.bodmood.blackhole.base.controller.BaseController";
//        // 数据源配置   根据那个数据库生产文件
//        DataSourceConfig dsc = new DataSourceConfig();
//        //todo 修改数据库配置
//        dsc.setUrl("jdbc:mysql://localhost:3306/black_hole?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true");
//        // dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("Wdx123123123.");//1q2w3e4r
//
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig.Builder builder = new GlobalConfig.Builder();
//        //生成在什么地方？
//        String projectPath = System.getProperty("user.dir");
//        builder.outputDir(projectPath + "/" + moduleName +
//                "/src/main/java");
//        //生成的文件作者写谁
//        builder.author("wadexi");
//        //是否打开文件夹
//        builder.openDir(false);
//        mpg.global(builder.build());
//
//
//
//        // 包配置 生成在java目录下的哪个包里边
//        PackageConfig.Builder packageBuilder = new PackageConfig.Builder();
//        //todo 修改生成文件的目录
//        packageBuilder.parent("com.free.badmood.blackhole.web");
//        packageBuilder.moduleName("");//和上边的路径最终组合成生成文件的路径
////        packageBuilder.moduleName(scanner("模块名"));
//        PackageConfig pc = packageBuilder.build();
//        mpg.packageInfo(pc);
//
//        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//        List<FileOutConfig> focList = new ArrayList<>();
//        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
//            @Override
//            public File outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return new File(projectPath + "/" + moduleName +
//                        "/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML);
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//        mpg.setTemplate(new TemplateConfig().setXml(null));
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass(baseEntityPath);
//        strategy.setEntityLombokModel(true);
//        strategy.setSuperControllerClass(baseControlPath);
//        strategy.setInclude(scanner("表名"));
//        strategy.setSuperEntityColumns("id");
//        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
//        mpg.setStrategy(strategy);
//        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.execute();
//    }
//
//}
