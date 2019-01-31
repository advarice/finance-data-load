package main;

import action.BuildRestUrlAction;
import action.IAction;
import action.LoadDataAction;
import dao.FinancialDataDao;
import entitiy.WeeklyPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan({"config"})
public class FinancialDataLoader implements CommandLineRunner {

    @Autowired
    private ApplicationContext ctx;

    public static void main(String[] args){
        System.out.println("haha");

        SpringApplication.run(FinancialDataLoader.class,args);

    }

    @Override
    public void run(String... args){
        //FinancialDataDao dao = (FinancialDataDao)ctx.getBean("financialDataDao");
        //dao.testConnection();

        //RestTemplate rt=new RestTemplate();
        //WeeklyPrices wp=rt.getForObject("https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol=MSFT&apikey=demo", WeeklyPrices.class);
        //System.out.println(wp.toString());
        //String s=(String)ctx.getBean("testString");
        //dao.insert(wp);
        IAction buildRestUrlAction = (BuildRestUrlAction)ctx.getBean("buildRestUrlAction");
        IAction loadDataAction = (LoadDataAction)ctx.getBean("loadDataAction");
        buildRestUrlAction.action();
        loadDataAction.action();
        System.exit(0);
    }

}
