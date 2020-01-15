package action;


import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ActionModel {

    @Value("${alpha.vantantage.api.keys}")
    private List<String> apiKeys;

    private List<String> urls=new ArrayList<>();

    public ActionModel (){
    }

    public List<String> getUrls() {

        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getKeys(){
        return apiKeys;
    }

}
