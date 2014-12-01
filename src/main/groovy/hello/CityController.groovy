package hello

//import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
//import org.springframework.web.context.request.WebRequest
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus
//import org.apache.log4j.Logger
//import groovy.util.logging.Log4j
//import groovy.json.*
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse
import groovy.util.logging.Slf4j

// Use annotation to inject log field into the class.
@Slf4j
@RestController
class CityController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value( '${sample.property}' )
    String stringTemplate

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletResponse response) {
        response.setHeader("X-Frame-Options", "SAMEORIGIN")
        log.info 'inside index method...'
        return String.format(stringTemplate, 'world')
    }

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public List list(HttpServletResponse response) {

        List<City> results = jdbcTemplate.query(
            "select * from cities",
            new RowMapper<City>() {
                @Override
                public City mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new City(id: rs.getInt("gid"), name: rs.getString("name"));
                }
            });
        return results
    }

    @RequestMapping(value = "/cities/{name}", method = RequestMethod.GET)
    public List show(@PathVariable name) {
        Object[] args = [name]

        List<City> results = jdbcTemplate.query(
                "select * from cities where name = ?", args,
                new RowMapper<City>() {
                    @Override
                    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new City(id: rs.getInt("gid"), name: rs.getString("name"));
                    }
                });

        return results
    }

}



