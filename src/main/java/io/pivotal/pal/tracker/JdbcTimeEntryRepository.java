package io.pivotal.pal.tracker;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlStatement = "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                "VALUES ( ? , ? , ? , ?);";

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sqlStatement, new String[]{"id"});
                    ps.setLong(1, timeEntry.getProjectId());
                    ps.setLong(2, timeEntry.getUserId());
                    ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                    ps.setInt(4,timeEntry.getHours());
                    return ps;
                }, keyHolder);
        Number key = keyHolder.getKey();
        return find(key.longValue());
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        String sqlStatement = "Select * from time_entries where id = ? ;";
        List<TimeEntry> timeEntryList = jdbcTemplate.query(
                sqlStatement,
                new Object[]{timeEntryId}, (resultSet, i) -> new TimeEntry(
                        resultSet.getLong("id"),
                        resultSet.getLong("project_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getInt("hours")));

        if (timeEntryList.size()>0) { return timeEntryList.get(0); }
        return null;
    }

    @Override
    public List<TimeEntry> list() {
        String sqlStatement = "Select * from time_entries;";
        List<TimeEntry> timeEntryList = jdbcTemplate.query(
                sqlStatement,
                new Object[]{}, (resultSet, i) -> new TimeEntry(
                        resultSet.getLong("id"),
                        resultSet.getLong("project_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getInt("hours")));

        return timeEntryList;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        String sqlStatement = "UPDATE time_entries SET project_id = ? , user_id = ? , date = ? " +
                ", hours = ? WHERE id = ?";

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sqlStatement, new String[]{"id"});
                    ps.setLong(1, timeEntry.getProjectId());
                    ps.setLong(2, timeEntry.getUserId());
                    ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                    ps.setInt(4,timeEntry.getHours());
                    ps.setLong(5,id);
                    return ps;
                });

        return find(id);
    }

    @Override
    public TimeEntry delete(long id) {
        String sqlStatement = "DELETE FROM time_entries WHERE id = ?";

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sqlStatement, new String[]{"id"});
                    ps.setLong(1,id);
                    return ps;
                });

        return find(id);
    }
}
