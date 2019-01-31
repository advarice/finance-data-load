package config;

import action.ActionModel;
import action.BuildRestUrlAction;
import action.LoadDataAction;
import dao.FinancialDataDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public String testString(){
        return "ddsaf";
    }

    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource ds=new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/stock_analysis");
        ds.setUsername("root");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public ActionModel actionModel(){
        return new ActionModel();
    }

    @Bean
    public FinancialDataDao financialDataDao(){
        return new FinancialDataDao(dataSource());
    }

    @Bean
    public BuildRestUrlAction buildRestUrlAction(){
        return new BuildRestUrlAction();
    }

    @Bean
    public LoadDataAction loadDataAction(){
        return new LoadDataAction();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
