package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{
    private HashMap<Long,TimeEntry> timeEntryList = new HashMap<>();
    private Long currentId = 1L;

    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry newTimeEntry = new TimeEntry(
                currentId,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
        timeEntryList.put(currentId,newTimeEntry);
        currentId = currentId + 1;
        return newTimeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return timeEntryList.get(id);
    }


    public TimeEntry update(long id,TimeEntry timeEntry) {
        TimeEntry oldEntry = timeEntryList.get(id);
        timeEntryList.remove(oldEntry);
        if (oldEntry==null) {
            return oldEntry;
        }
        oldEntry.setProjectId(timeEntry.getProjectId());
        oldEntry.setUserId(timeEntry.getUserId());
        oldEntry.setDate(timeEntry.getDate());
        oldEntry.setHours(timeEntry.getHours());
        timeEntryList.put(id,oldEntry);
        return oldEntry;

    }

    public TimeEntry delete(long id) {
        TimeEntry timeEntry = timeEntryList.get(id);
        timeEntryList.remove(id);
        return timeEntry;
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntryList.values());
    }
}
