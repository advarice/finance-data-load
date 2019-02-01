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

import java.util.List;

@SpringBootApplication
@ComponentScan({"config"})
public class FinancialDataLoader implements CommandLineRunner {


    @Autowired
    private List<IAction> actionList;

    public static void main(String[] args){
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
        for(IAction action:actionList){
            action.action();;
        }
        System.exit(0);
    }

}
