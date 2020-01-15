package config;

import action.ActionModel;
import action.BuildRestUrlAction;
import action.IAction;
import action.LoadDataAction;
import dao.FinancialDataDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

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
        ds.setUrl("jdbc:mysql://fde.czduruoyy9zb.us-east-2.rds.amazonaws.com/fde?autoReconnect=true&useSSL=false");
        ds.setUsername("admin");
        ds.setPassword("Abc-12345Abc-12345");
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

    @Bean
    public List<IAction> actionList(){
        List<IAction> actionList=new ArrayList<>();
        actionList.add(buildRestUrlAction());
        actionList.add(loadDataAction());
        return actionList;
    }

    @Bean public ConversionService conversionService() {
        return new DefaultConversionService();
    }

}
