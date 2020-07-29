package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntry {
    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry() {
        this.id = 1L;
        this.projectId = 0;
        this.userId = 0;
        this.date = LocalDate.now();
        this.hours = 0;
    }

    public long getId() { return id; }

    public long getProjectId() { return projectId; }

    public long getUserId() { return userId; }

    public LocalDate getDate() { return date; }

    public int getHours() { return hours; }

    public void setId(Long id) { this.id = id; }

    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setHours(int hours) { this.hours = hours; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeEntry timeEntry = (TimeEntry) o;

        if (id != timeEntry.id) { return false; }
        if (projectId != timeEntry.projectId) { return false; }
        if (userId != timeEntry.userId) { return false; }
        if (hours != timeEntry.hours) { return false; }
        return date != null ? date.equals(timeEntry.date) : timeEntry.date == null;
    }

}
