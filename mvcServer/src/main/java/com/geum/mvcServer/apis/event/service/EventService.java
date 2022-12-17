package com.geum.mvcServer.apis.event.service;

import com.geum.mvcServer.apis.animal.entity.Animal;
import com.geum.mvcServer.apis.animal.model.AnimalVO;
import com.geum.mvcServer.apis.event.entity.Event;
import com.geum.mvcServer.apis.event.entity.EventRepository;
import com.geum.mvcServer.apis.event.model.EventVO;
import com.geum.mvcServer.apis.user.entity.User;
import com.geum.mvcServer.config.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final SessionManager sessionManager;

    public List<Event> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events;
    }

    public Event addEvent(EventVO eventVO, HttpServletRequest request) {

        User user = (User) sessionManager.getSession(request);
        if(!user.getRole().equals("ROLE_ADMIN")) {
            return null;
        }

        Event event = new Event();
        event.setTitle(eventVO.getTitle());
        event.setBody(eventVO.getBody());
        event.setImageUrl(eventVO.getImageUrl());

        Event uploadEvent = eventRepository.save(event);

        return uploadEvent;
    }

    /** deleteEvent
     * CODE 0 : SUCCESS
     * CODE 1 : FAILED
     * CODE 2 : PROGRESSING */
    public int deleteAnimal(Long id, HttpServletRequest request) {

        User user = (User) sessionManager.getSession(request);
        if(!user.getRole().equals("ROLE_ADMIN")) {
            return 1;
        }

        Optional<Event> event = eventRepository.findById(id);
        eventRepository.delete(event);

        if(eventRepository.existsById(id)) {
            return 2; // 작업 대기
        }

        return 0;
    }

    /** updateEvent
     * CODE 0 : SUCCESS
     * CODE 1 : NOT FOUND
     * CODE 2 : BAD REQUEST
     * CODE 3 : FAILED */
    public int updateEvent(Long id, Event event, HttpServletRequest request) {

        User user = (User) sessionManager.getSession(request);
        if(!user.getRole().equals("ROLE_ADMIN")) {
            return 1;
        } else if(event.getId() != id) {
            return 2;
        }

        eventRepository.save(event);

        return 0;
    }
}
