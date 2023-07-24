package zerobase.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate; // 템플릿

    @Autowired
    public JdbcMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 메모 저장 함수
    public Memo save(Memo memo) {
        String sql = "insert into memo values(?, ?)";
        jdbcTemplate.update(sql, memo.getId(), memo.getText());
        return memo;
    }

    // 전체 메모 조회 memoRowMapper 결과로 조회
    public List<Memo> findAll() {
        String sql = "select * from memo";
       return jdbcTemplate.query(sql, memoRowMapper());
    }

    public Optional<Memo> findById(int id) {
        String sql = "select * from memo where id = ?";
        return jdbcTemplate.query(sql, memoRowMapper(), id).stream().findFirst();
    }

    // 가져온 데이터 Set 값을 Memo 라는 형식으로 매핑해 주는 것
    private RowMapper<Memo> memoRowMapper() {
        // ResultSet
        // { id = 1, test = "~~~~~" }
        return (rs, rowNum) -> new Memo(
               rs.getInt("id"),
               rs.getString("text")
        );// result set, rowNum
    }
}
