package com.notifyme.configs;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {

	private final DataSourceProperties dataSourceProperties;

	@Bean(initMethod = "migrate")
	public Flyway flyway() {
		Flyway flyway = Flyway.configure()
				.dataSource(dataSourceProperties.getUrl(),
						dataSourceProperties.getUsername(),
						dataSourceProperties.getPassword())
				.baselineDescription("Migração do banco de dados da NotifyMe").baselineOnMigrate(true)
				.load();
		flyway.repair();
		flyway.baseline();
		return flyway;
	}
}
