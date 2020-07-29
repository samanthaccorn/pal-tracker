package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping(path="/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry newTimeEntry = timeEntryRepository.create(timeEntry);
        ResponseEntity response = new ResponseEntity(newTimeEntry,HttpStatus.CREATED);
        return response;
    }

    @GetMapping(path="/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if (timeEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(timeEntry, HttpStatus.OK);
    }

    @GetMapping(path="/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

        return new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(),HttpStatus.OK);

    }

    @PutMapping(path="/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry newTimeEntry = timeEntryRepository.update(id,timeEntry);
        if (newTimeEntry==null) {
            return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TimeEntry>(newTimeEntry,HttpStatus.OK);

    }

    @DeleteMapping(path="/time-entries/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        TimeEntry timeEntry = timeEntryRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
