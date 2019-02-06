package action;

import dao.FinancialDataDao;
import entitiy.WeeklyPrices;
import entitiy.WeeklyPricesAdjusted;
import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LoadDataAction implements IAction{

    @Autowired
    private ActionModel actionModel;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FinancialDataDao financialDataDao;

    @Override
    public void action() {
        ThreadFactory tf = new ThreadFactoryBuilder().setNameFormat("Thread --%d").build();
        ExecutorService executorService= Executors.newFixedThreadPool(4,tf);
        for(String url:actionModel.getUrls()){
            Runnable r =()->{
                log.info(url);
                //WeeklyPrices wp= restTemplate.getForObject(url, WeeklyPrices.class);
                //financialDataDao.insert(wp);
                try{
                    WeeklyPricesAdjusted wp=restTemplate.getForObject(url,WeeklyPricesAdjusted.class);
                    System.out.println(wp);
                    financialDataDao.insertAdjusted(wp);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(65000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            };
            executorService.submit(r);

        }
        executorService.shutdown();
        try{
            executorService.awaitTermination(200, TimeUnit.MINUTES);
        }
        catch(java.lang.InterruptedException e){
            e.printStackTrace();
        }
    }
}
