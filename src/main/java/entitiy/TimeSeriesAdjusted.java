package entitiy;

import java.sql.Time;

public class TimeSeriesAdjusted extends TimeSeries {
    private String adjustedClose;
    private String dividendAmount;

    public String getAdjustedClose() {
        return adjustedClose;
    }

    public void setAdjustedClose(String adjustedClose) {
        this.adjustedClose = adjustedClose;
    }

    public String getDividendAmount() {
        return dividendAmount;
    }

    public void setDividendAmount(String dividendAmount) {
        this.dividendAmount = dividendAmount;
    }

    @Override
    public String toString() {
        return "TimeSeriesAdjusted{" +
                "adjustedClose=" + adjustedClose +
                ", dividendAmount=" + dividendAmount +
                "} " + super.toString();
    }
}
