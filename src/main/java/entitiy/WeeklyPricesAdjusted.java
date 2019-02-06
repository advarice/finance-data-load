package entitiy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WeeklyPricesAdjusted {

    @JsonProperty("Meta Data")
    private Map<String,String> metaData;

    public Map<String,String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String,String> metaData) {
        this.metaData = metaData;
    }

    public List<TimeSeriesAdjusted> getStocks() {
        return stocks;
    }

    public void setStocks(List<TimeSeriesAdjusted> stocks) {
        this.stocks = stocks;
    }

    private List<TimeSeriesAdjusted> stocks = new ArrayList<>();

    @JsonProperty("Weekly Adjusted Time Series")
    private void unpackNested(Map<String,Map<String,String>>weeklyTimeSeries){

        for(Map.Entry<String,Map<String,String>> e:weeklyTimeSeries.entrySet()){
            TimeSeriesAdjusted temp = new TimeSeriesAdjusted();
            temp.setOpen(e.getValue().get("1. open"));
            temp.setHigh(e.getValue().get("2. high"));
            temp.setLow(e.getValue().get("3. low"));
            temp.setClose(e.getValue().get("4. close"));
            temp.setAdjustedClose(e.getValue().get("5. adjusted close"));
            temp.setVolume(e.getValue().get("6. volume"));
            temp.setDividendAmount(e.getValue().get("7. dividend amount"));
            temp.setTime(e.getKey());
            stocks.add(temp);


        }
    }


    @Override
    public String toString() {
        return "WeeklyPricesAdjusted{" +
                "metaData=" + metaData +
                ", stocks=" + stocks +
                '}';
    }
}
