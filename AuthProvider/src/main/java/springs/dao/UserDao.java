package springs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import springs.model.UserDto;

@Repository
public class UserDao {
	
	@Autowired
    JdbcTemplate jdbctemplate;
	
	public List<Map<String,Object>> getUsername (String username) {
		String sql = "SELECT * FROM employees WHERE email=?";
		return jdbctemplate.queryForList(sql,username);		
	}

}
