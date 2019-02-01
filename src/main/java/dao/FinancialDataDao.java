package dao;

import entitiy.WeeklyPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class FinancialDataDao {

    private JdbcTemplate jdbcTemplate;

    private static final String BATCHI_NSERT_WEEKLY="INSERT INTO STOCK_WEEKLY_DATA (STOCK_SYMBOL,TIME, OPEN,HIGH,LOW,CLOSE,VOLUME) VALUES (?,?,?,?,?,?,?)";
    private static final String GET_RANDO_MSYMBOL="SELECT STOCK_SYMBOL FROM STOCK ORDER BY RAND() LIMIT 10";

    public FinancialDataDao(DriverManagerDataSource datasource){
        jdbcTemplate=new JdbcTemplate(datasource);
    }

    public void testConnection(){

        System.out.println(jdbcTemplate.queryForMap("select 1 from dual").get("1"));

    }

    public List<String> getRandStockSymbol(int n){
        //List<String> stockSymbolList= this.jdbcTemplate.queryForList(getRandomSymbol,n,String.class);
        List<String> stockSymbolList= this.jdbcTemplate.queryForList(GET_RANDO_MSYMBOL,String.class);
        return stockSymbolList;
    }

    public void insert(WeeklyPrices wp){
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
            }

            @Override
            public int getBatchSize() {
                return wp.getStocks().size();
            }
        });
    }


}

