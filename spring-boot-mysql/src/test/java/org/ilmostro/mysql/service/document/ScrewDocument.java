package org.ilmostro.mysql.service.document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

/**
 * @author li.bowei
 */
public class ScrewDocument {

	@Test
	public void document(){
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikariConfig.setJdbcUrl("jdbc:mysql://124.236.102.162:3306/core_data");
		hikariConfig.setUsername("dev");
		hikariConfig.setPassword("dev");
		//设置可以获取tables remarks信息
		hikariConfig.addDataSourceProperty("useUnicode", "true");
		hikariConfig.addDataSourceProperty("characterEncoding", "utf-8");
		hikariConfig.addDataSourceProperty("useSSL", "false");
		hikariConfig.addDataSourceProperty("serverTimezone", "Asia/Shanghai");
		hikariConfig.addDataSourceProperty("useInformationSchema", "true");
		hikariConfig.setMinimumIdle(2);
		hikariConfig.setMaximumPoolSize(5);
		DataSource dataSource = new HikariDataSource(hikariConfig);
		//生成配置
		EngineConfig engineConfig = EngineConfig.builder()
				//生成文件路径
				.fileOutputDir("./database/document")
				//打开目录
				.openOutputDir(true)
				//文件类型
				.fileType(EngineFileType.WORD)
				//生成模板实现
				.produceType(EngineTemplateType.freemarker)
				//自定义文件名称
				.fileName("document").build();

		//忽略表
		ArrayList<String> ignoreTableName = new ArrayList<>();
		ignoreTableName.add("test_user");
		ignoreTableName.add("test_group");
		//忽略表前缀
		ArrayList<String> ignorePrefix = new ArrayList<>();
		ignorePrefix.add("test_");
		//忽略表后缀
		ArrayList<String> ignoreSuffix = new ArrayList<>();
		ignoreSuffix.add("_test");
		ProcessConfig processConfig = ProcessConfig.builder()
				//指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
				//根据名称指定表生成
				.designatedTableName(List.of("device_package",
						"device_region_config",
						"device_region",
						"isp_region_config",
						"region_migrate",
						"product_package_history",
						"product_package",
						"product_region",
						"province_city_region",
						"region_config",
						"system_config"))
				//根据表前缀生成
				.designatedTablePrefix(new ArrayList<>())
				//根据表后缀生成
				.designatedTableSuffix(new ArrayList<>())
				//忽略表名
				.ignoreTableName(ignoreTableName)
				//忽略表前缀
				.ignoreTablePrefix(ignorePrefix)
				//忽略表后缀
				.ignoreTableSuffix(ignoreSuffix).build();
		//配置
		Configuration config = Configuration.builder()
				//版本
				.version("1.0.0")
				//描述
				.description("数据库设计文档生成")
				//数据源
				.dataSource(dataSource)
				//生成配置
				.engineConfig(engineConfig)
				//生成配置
				.produceConfig(processConfig)
				.build();
		//执行生成
		new DocumentationExecute(config).execute();
	}
}
