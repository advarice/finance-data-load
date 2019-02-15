package dao;

import entitiy.DailyPricesAdjusted;
import entitiy.WeeklyPrices;
import entitiy.WeeklyPricesAdjusted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class FinancialDataDao {

    private JdbcTemplate jdbcTemplate;

    private static final String BATCHI_NSERT_WEEKLY="INSERT INTO STOCK_WEEKLY_DATA (STOCK_SYMBOL,TIME, OPEN,HIGH,LOW,CLOSE,VOLUME,FINANCIAL_DATA_LOAD_ID) VALUES (?,?,?,?,?,?,?,?)";
    private static final String BATCHI_NSERT_WEEKLY_ADJUSTED="INSERT INTO STOCK_WEEKLY_DATA_ADJUSTED (STOCK_SYMBOL,TIME, OPEN,HIGH,LOW,CLOSE,VOLUME,ADJUSTED_CLOSE,DIVIDEND_AMOUNT,FINANCIAL_DATA_LOAD_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_FINANCIAL_DATA_LOAD_META_DATA="INSERT INTO FINANCIAL_DATA_LOAD (LOAD_TYPE,LOAD_STATUS) VALUES (?,?)";
    private static final String BATCH_INSERT_DAILY_ADJUSTED="INSERT INTO stock_daily_data_adjusted(STOCK_SYMBOL,TIME,OPEN,CLOSE,HIGH,LOW,ADJUSTED_CLOSE,DIVIDEND_AMOUNT,VOLUME,SPLIT_COEFFICIENT,FINANCIAL_DATA_LOAD_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_RANDO_MSYMBOL="SELECT STOCK_SYMBOL FROM STOCK ORDER BY RAND() LIMIT ?";
    private static final String GET_LOAD_ID="SELECT MAX(DATA_LOAD_ID) FROM FINANCIAL_DATA_LOAD WHERE LOAD_STATUS='PROCESSING'";

    public FinancialDataDao(DriverManagerDataSource datasource){
        jdbcTemplate=new JdbcTemplate(datasource);
    }

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

