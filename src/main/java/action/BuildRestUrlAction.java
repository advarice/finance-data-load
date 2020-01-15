package action;

import dao.FinancialDataDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BuildRestUrlAction implements IAction{

    @Autowired
    private ActionModel actionModel;

    @Autowired
    private FinancialDataDao financialDataDao;

    private List<String> symbols=new ArrayList<>();

    @Override
    public void action() {
        //load n number of stocks information to the database
        symbols= financialDataDao.getRandStockSymbol(1200);

        int apiKeysSize= actionModel.getKeys().size();
        int currentKey=0;
        for(String symbol:symbols){
            actionModel.getUrls().add(String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=%s&outputsize=full&apikey=%s", symbol,actionModel.getKeys().get(currentKey)));
            currentKey++;
            currentKey=currentKey%apiKeysSize;
        }
    }
}
