package entitiy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DailyPricesAdjusted{
    @JsonProperty("Meta Data")
    private Map<String,String> metaData;

    public Map<String,String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String,String> metaData) {
        this.metaData = metaData;
    }

    public List<DailyTimeSeriesAdjusted> getStocks() {
        return stocks;
    }

    public void setStocks(List<DailyTimeSeriesAdjusted> stocks) {
        this.stocks = stocks;
    }

    private List<DailyTimeSeriesAdjusted> stocks = new ArrayList<>();

    @JsonProperty("Time Series (Daily)")
    private void unpackNested(Map<String,Map<String,String>>DailyTimeSeriesAdjusted){

        for(Map.Entry<String,Map<String,String>> e:DailyTimeSeriesAdjusted.entrySet()){
            DailyTimeSeriesAdjusted temp = new DailyTimeSeriesAdjusted();
            temp.setOpen(e.getValue().get("1. open"));
            temp.setHigh(e.getValue().get("2. high"));
            temp.setLow(e.getValue().get("3. low"));
            temp.setClose(e.getValue().get("4. close"));
            temp.setAdjustedClose(e.getValue().get("5. adjusted close"));
            temp.setVolume(e.getValue().get("6. volume"));
            temp.setDividendAmount(e.getValue().get("7. dividend amount"));
            temp.setSplitCoefficient(e.getValue().get("8. split coefficient"));
            temp.setTime(e.getKey());
            stocks.add(temp);


        }
    }



}
