package entitiy;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WeeklyPrices {

    @JsonProperty("Meta Data")
    private Map<String,String> metaData;

    public Map<String,String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String,String> metaData) {
        this.metaData = metaData;
    }

    @JsonProperty("Weekly Time Series")
    private LinkedHashMap<String,TimeSeries> ts;

    public LinkedHashMap<String, TimeSeries> getTs() {
        return ts;
    }

    public void setTs(LinkedHashMap<String, TimeSeries> ts) {
        this.ts = ts;
    }

    public List<TimeSeries> getStocks() {
        return stocks;
    }

    public void setStocks(List<TimeSeries> stocks) {
        this.stocks = stocks;
    }

    private List<TimeSeries> stocks = new ArrayList<>();

    @JsonProperty("Weekly Time Series")
    private void unpackNested(Map<String,Map<String,String>>weeklyTimeSeries){

        for(Map.Entry<String,Map<String,String>> e:weeklyTimeSeries.entrySet()){
            TimeSeries temp = new TimeSeries();
            temp.setOpen(e.getValue().get("1. open"));
            temp.setHigh(e.getValue().get("2. high"));
            temp.setLow(e.getValue().get("3. low"));
            temp.setClose(e.getValue().get("4. close"));
            temp.setVolume(e.getValue().get("5. volume"));
            temp.setTime(e.getKey());
            stocks.add(temp);


        }
    }


    @Override
    public String toString() {
        return "WeeklyPrices{" +
                "metaData=" + metaData +
                ", ts=" + stocks +
                '}';
    }
}
