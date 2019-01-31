package action;

import dao.FinancialDataDao;
import entitiy.WeeklyPrices;
import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class LoadDataAction implements IAction{

    @Autowired
    private ActionModel actionModel;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FinancialDataDao financialDataDao;

    @Override
    public void action() {
        ThreadFactory tf = new ThreadFactoryBuilder().setNameFormat("Rest Call--TPool --%d").build();
        ExecutorService executorService= Executors.newFixedThreadPool(4,tf);
        for(String url:actionModel.getUrls()){
            Runnable r =()->{
                System.out.println(url);
                WeeklyPrices wp= restTemplate.getForObject(url, WeeklyPrices.class);
                financialDataDao.insert(wp);
                System.out.println("daaaa");
                System.out.println("end");

            };
            executorService.submit(r);
            System.out.println("hey");

        }
        System.out.println("dsdsad");
        executorService.shutdown();
        try{
            executorService.awaitTermination(20, TimeUnit.MINUTES);
        }
        catch(java.lang.InterruptedException e){
            e.printStackTrace();
        }
    }
}
