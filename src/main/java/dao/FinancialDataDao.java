package dao;

import entitiy.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FinancialDataDao {

    private JdbcTemplate jdbcTemplate;

    private static final String BATCHI_NSERT_WEEKLY="insert into stock_weekly_data (stock_symbol,time, open,high,low,close,volume,financial_data_load_id) values (?,?,?,?,?,?,?,?)";
    private static final String BATCHI_NSERT_WEEKLY_ADJUSTED="insert into stock_weekly_data_adjusted (stock_symbol,time, open,high,low,close,volume,adjusted_close,dividend_amount,financial_data_load_id) values (?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_FINANCIAL_DATA_LOAD_META_DATA="insert into financial_data_load (load_type,load_status) values (?,?)";
    private static final String BATCH_INSERT_DAILY_ADJUSTED="insert into stock_daily_data_adjusted(stock_symbol,time,open,close,high,low,adjusted_close,dividend_amount,volume,split_coefficient,financial_data_load_id) values (?,?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_RANDO_MSYMBOL="select stock_symbol from stock order by rand() limit ?";
    private static final String GET_LOAD_ID="select max(data_load_id) from financial_data_load where load_status='processing'";

    private static final String GET_LAST_PROCESSED_TIME="select max(time) from stock_daily_data_adjusted where stock_symbol= ?";

    public FinancialDataDao(DriverManagerDataSource datasource){
        jdbcTemplate=new JdbcTemplate(datasource);
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void testConnection(){

        System.out.println(jdbcTemplate.queryForMap("select 1 from dual").get("1"));

    }

    public List<String> getRandStockSymbol(int n){
        //List<String> stockSymbolList= this.jdbcTemplate.queryForList(getRandomSymbol,n,String.class);
        List<String> stockSymbolList= this.jdbcTemplate.queryForList(GET_RANDO_MSYMBOL,String.class,n);
        return stockSymbolList;
    }

    public void insertFinancialDataLoadMetaData(String type){
        this.jdbcTemplate.update(INSERT_FINANCIAL_DATA_LOAD_META_DATA,type,"PROCESSING");
    }

    public int getLoadId(){
        return this.jdbcTemplate.queryForObject(GET_LOAD_ID,Integer.class);
    }

    public void insert(WeeklyPrices wp,int loadId){
        this.jdbcTemplate.batchUpdate(BATCHI_NSERT_WEEKLY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,wp.getMetaData().get("2. Symbol"));
                preparedStatement.setString(2,wp.getStocks().get(i).getTime());
                preparedStatement.setString(3,wp.getStocks().get(i).getOpen());
                preparedStatement.setString(4,wp.getStocks().get(i).getHigh());
                preparedStatement.setString(5,wp.getStocks().get(i).getLow());
                preparedStatement.setString(6,wp.getStocks().get(i).getClose());
                preparedStatement.setString(7,wp.getStocks().get(i).getVolume());
                preparedStatement.setInt(8,loadId);
            }

            @Override
            public int getBatchSize() {
                return wp.getStocks().size();
            }
        });

    }
    public void insertAdjusted(WeeklyPricesAdjusted wp,int loadId){
        Object[] array = {wp.getMetaData().get("2. Symbol")};
        Date lastInsert = this.jdbcTemplate.queryForObject(GET_LAST_PROCESSED_TIME,array,Date.class);

        if(lastInsert!=null){
            List<TimeSeriesAdjusted> series = wp.getStocks().stream().filter( s -> {
                try {
                    return sdf.parse(s.getTime()).after(lastInsert);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }).collect(Collectors.toList());
            log.info(wp.getMetaData().get("2. Symbol") +"|SIZE=" + wp.getStocks().size()+"|FILTERED_SIZE=" + series.size());
            wp.setStocks(series);
        }


        this.jdbcTemplate.batchUpdate(BATCHI_NSERT_WEEKLY_ADJUSTED, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,wp.getMetaData().get("2. Symbol"));
                preparedStatement.setString(2,wp.getStocks().get(i).getTime());
                preparedStatement.setString(3,wp.getStocks().get(i).getOpen());
                preparedStatement.setString(4,wp.getStocks().get(i).getHigh());
                preparedStatement.setString(5,wp.getStocks().get(i).getLow());
                preparedStatement.setString(6,wp.getStocks().get(i).getClose());
                preparedStatement.setString(7,wp.getStocks().get(i).getVolume());
                preparedStatement.setString(8,wp.getStocks().get(i).getAdjustedClose());
                preparedStatement.setString(9,wp.getStocks().get(i).getDividendAmount());
                preparedStatement.setInt(10,loadId);
            }

            @Override
            public int getBatchSize() {
                return wp.getStocks().size();
            }
        });
    }


    public void insertDailyAdjujsted(DailyPricesAdjusted wp,int loadId){

        Object[] array = {wp.getMetaData().get("2. Symbol")};
        Date lastInsert = this.jdbcTemplate.queryForObject(GET_LAST_PROCESSED_TIME,array,Date.class);

        if(lastInsert!=null){
            List<DailyTimeSeriesAdjusted> series = wp.getStocks().stream().filter( s -> {
                try {
                    return sdf.parse(s.getTime()).after(lastInsert);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }).collect(Collectors.toList());
            log.info(wp.getMetaData().get("2. Symbol") +"|SIZE=" + wp.getStocks().size()+"|FILTERED_SIZE=" + series.size());
            wp.setStocks(series);
        }


        this.jdbcTemplate.batchUpdate(BATCH_INSERT_DAILY_ADJUSTED, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,wp.getMetaData().get("2. Symbol"));
                preparedStatement.setString(2,wp.getStocks().get(i).getTime());
                preparedStatement.setString(3,wp.getStocks().get(i).getOpen());
                preparedStatement.setString(4,wp.getStocks().get(i).getClose());
                preparedStatement.setString(5,wp.getStocks().get(i).getHigh());
                preparedStatement.setString(6,wp.getStocks().get(i).getLow());
                preparedStatement.setString(7,wp.getStocks().get(i).getAdjustedClose());
                preparedStatement.setString(8,wp.getStocks().get(i).getDividendAmount());
                preparedStatement.setString(9,wp.getStocks().get(i).getVolume());
                preparedStatement.setString(10,wp.getStocks().get(i).getSplitCoefficient());
                preparedStatement.setInt(11,loadId);
            }

            @Override
            public int getBatchSize() {
                return wp.getStocks().size();
            }
        });
    }

}

