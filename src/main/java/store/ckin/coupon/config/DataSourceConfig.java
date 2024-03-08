package store.ckin.coupon.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import store.ckin.coupon.keymanager.KeyManager;

import javax.sql.DataSource;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 20
 */
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final DbProperties dbProperties;
    private final KeyManager keyManager;

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setDriverClassName(keyManager.keyStore(dbProperties.getDriver()));
        basicDataSource.setUrl(keyManager.keyStore(dbProperties.getUrl()));
        basicDataSource.setUsername(keyManager.keyStore(dbProperties.getUserName()));
        basicDataSource.setPassword(keyManager.keyStore(dbProperties.getPassword()));

        basicDataSource.setInitialSize(dbProperties.getInitialSize());
        basicDataSource.setMaxTotal(dbProperties.getMaxTotal());
        basicDataSource.setMaxIdle(dbProperties.getMaxIdle());
        basicDataSource.setMinIdle(dbProperties.getMinIdle());

        basicDataSource.setTestOnBorrow(true);
        basicDataSource.setValidationQuery("SELECT 1");

        basicDataSource.setMaxWaitMillis(dbProperties.getMaxWaitMillis());
        return basicDataSource;
    }
}
