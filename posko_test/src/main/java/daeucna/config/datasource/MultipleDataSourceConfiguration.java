package daeucna.config.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MultipleDataSourceConfiguration {


    @Bean
    @Primary
    @Qualifier("primaryHikariConfig")
    @ConfigurationProperties(prefix="spring.datasource.hikari.primary")
    public HikariConfig primaryHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Primary
    @Qualifier("primaryDataSource")
    public DataSource primaryDataSource() throws Exception {
        return new HikariDataSource(primaryHikariConfig());
    }
    
    @Bean
    @Qualifier("secondaryHikariConfig")
    @ConfigurationProperties(prefix="spring.datasource.hikari.secondary")
    public HikariConfig secondaryHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Qualifier("secondaryDataSource")
    public DataSource secondaryDataSource() throws Exception {
        return new HikariDataSource(secondaryHikariConfig());
    }

}
