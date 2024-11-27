package springs.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserDao {

    private static final Logger logger = Logger.getLogger("user dao");
    private JdbcTemplate template;

    // Query To Fetch User Data Using Username
    public Map<String, Object> getUserByUsername(String username) {
        logger.info("dao username " + username);
        String query = "SELECT u.id,u.first_name,u.last_name,u.email,u.password,u.mobile_no " +
                "FROM user_info u WHERE u.email = ? AND u.active = TRUE";
        return template.queryForMap(query, username);
    }

    // Query To Fetch User Role
    public List<Map<String, Object>> getUserRoles(String username) {
        String query = "SELECT r.role,r.abbr FROM user_info u " +
                "INNER JOIN user_roles ur ON u.id = ur.user_lid " +
                "INNER JOIN roles r ON ur.role_lid = r.id " +
                "WHERE u.email = ? AND u.active = TRUE AND ur.active = TRUE " +
                "AND r.active = TRUE";
        return template.queryForList(query, username);
    }

    // Query To Fetch User Role Type
    public Map<String, Object> getUserRoleType(String username, List<String> roleNames) {
        logger.info("dao username " + username);
        String placeholders = String.join(",", Collections.nCopies(roleNames.size(), "?"));

        String query = "SELECT " +
                " CASE WHEN r.abbr = 'ra' THEN 'admin' " +
                " WHEN r.abbr = 'ru' THEN 'user' " +
                " END AS role_type " +
                " FROM user_info u " +
                " INNER JOIN user_roles ur ON u.id = ur.user_lid " +
                " INNER JOIN roles r ON r.id = ur.role_lid " +
                " WHERE u.email = ? AND r.role IN (" + placeholders + ") " +
                " AND u.active = TRUE AND ur.active = TRUE AND r.active = TRUE";

        List<Object> params = new ArrayList<>();
        params.add(username);
        params.addAll(roleNames);

        return template.queryForMap(query, params.toArray());
    }

    // Query To Get Dashboard Modules
    public List<Map<String, Object>> getDashboardModules(String username) {
        logger.info("dashboard username " + username);
        String query = "SELECT m.id,m.modules,m.icon,m.url " +
                "FROM user_modules um INNER JOIN user_info u ON u.id = um.user_lid " +
                "INNER JOIN modules m ON m.id = um.modules_lid " +
                "WHERE u.email= ? AND um.active = TRUE AND u.active = TRUE AND m.active = TRUE";
        return template.queryForList(query, username);
    }

    

}
