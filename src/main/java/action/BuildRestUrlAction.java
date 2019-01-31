package action;

import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BuildRestUrlAction implements IAction{

    @Autowired
    ActionModel actionModel;

    private List<String> symbols=new ArrayList<>();

    public BuildRestUrlAction(){
        symbols.add("FB");
        symbols.add("AMZN");
        symbols.add("NVDA");
        symbols.add("BABA");
    }

    @Override
    public void action() {

        for(String symbol:symbols){
            actionModel.getUrls().add(String.format("https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol=%s&apikey=placeholder", symbol));
        }
    }
}
