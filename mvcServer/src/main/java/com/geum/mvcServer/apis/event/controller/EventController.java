package com.geum.mvcServer.apis.event.controller;

import com.geum.mvcServer.apis.animal.model.AnimalVO;
import com.geum.mvcServer.apis.event.entity.Event;
import com.geum.mvcServer.apis.event.model.EventVO;
import com.geum.mvcServer.apis.event.service.EventService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/v1/events")
    public ResponseEntity<Result<List<Event>>> getAllEvents() {
        List<Event> eventList = eventService.getAllEvents();

        return ResponseEntity.ok().body(new Result<>(eventList, eventList.size()));
    }

    @PostMapping("/v1/events")
    public ResponseEntity<Event> addEvent(@RequestBody EventVO eventVO, HttpServletRequest request) {
        Event event = eventService.addEvent(eventVO, request);

        if(event == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().body(event);
    }

    @DeleteMapping("/v1/events/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable("id") Long id, HttpServletRequest request) {
        int process = eventService.deleteAnimal(id, request);

        if (process == 0) {
            return ResponseEntity.ok().build();
        } else if (process == 1) {
            return ResponseEntity.notFound().build();
        } else if (process == 2) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/v1/events/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") Long id, Event event, HttpServletRequest request) {
        int process = eventService.updateEvent(id, event, request);

        if (process == 0) {
            return ResponseEntity.ok().build();
        } else if (process == 1) {
            return ResponseEntity.notFound().build();
        } else if (process == 2) {
            return ResponseEntity.badRequest().build();
        } else if (process == 3) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.internalServerError().build();
            }
        }

    @Getter
    @Setter
    static class Result<T> {
        private T data;
        private int count;

        public Result(T data, int count) {
            this.data = data;
            this.count = count;
        }
    }
}
