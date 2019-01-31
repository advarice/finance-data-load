package dao;

import entitiy.WeeklyPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class FinancialDataDao {

    private JdbcTemplate jdbcTemplate;

    private static final String batchInsertWeekly="INSERT INTO STOCK_WEEKLY_DATA (STOCK_SYMBOL,TIME, OPEN,HIGH,LOW,CLOSE,VOLUME) VALUES (?,?,?,?,?,?,?)";

    public FinancialDataDao(DriverManagerDataSource datasource){
        jdbcTemplate=new JdbcTemplate(datasource);
    }

    public void testConnection(){

        System.out.println(jdbcTemplate.queryForMap("select 1 from dual").get("1"));

    }

    public void insert(WeeklyPrices wp){
        this.jdbcTemplate.batchUpdate(batchInsertWeekly, new BatchPreparedStatementSetter() {
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

