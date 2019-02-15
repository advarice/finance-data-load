package entitiy;

public class DailyTimeSeriesAdjusted extends TimeSeriesAdjusted{

    private String splitCoefficient;

    public String getSplitCoefficient() {
        return splitCoefficient;
    }

    public void setSplitCoefficient(String splitCoefficient) {
        this.splitCoefficient = splitCoefficient;
    }

    @Override
    public String toString() {
        return "DailyTimeSeriesAdjusted{" +
                "splitCoefficient='" + splitCoefficient + '\'' +
                "} " + super.toString();
    }
}
