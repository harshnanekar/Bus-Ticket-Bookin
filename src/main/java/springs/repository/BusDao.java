package springs.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import springs.dto.BusMaster;
import springs.dto.Seats;
import springs.exceptions.DbExceptionHandler;

@Repository
public class BusDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createBus(BusMaster bus) {

        System.out.println("Bus Dao Called");

        String sql = "INSERT INTO bus_master (bus_name,bus_type_lid,capacity,seats_per_row,total_rows,created_by) " +
                " VALUES (?,?,?,?,?,?) RETURNING id";

        return jdbcTemplate.query(sql, ps -> {
            ps.setString(1, bus.getBus_name());
            ps.setInt(2,bus.getBus_type_id());
            ps.setInt(3, bus.getCapacity());
            ps.setInt(4, bus.getSeats_per_row());
            ps.setInt(5, bus.getTotal_rows());
            ps.setString(6, bus.getCreated_by());
        }, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }else{
                throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Bus Not Created");
            }
                        
        });
    }

    public int[] createBusSeats(List<Seats> seats) {
        String sql = "INSERT INTO seats(seat_position,bus_lid,seat_number,created_by) " +
                "VALUES (?,?,?,?)";
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Seats seat = seats.get(i);
                ps.setString(1, seat.getSeat_position().toString());
                ps.setInt(2, seat.getBusId());
                ps.setInt(3, seat.getSeat_number());
                ps.setString(4, seat.getCreated_by());
            }

            @Override
            public int getBatchSize() {
                return seats.size();
            }

        });
    }

}
