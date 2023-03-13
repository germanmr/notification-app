package com.acme.notificationapp.stores;

import com.acme.notificationapp.events.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventStore {

    private Map<String, List<Event>> store = new HashMap<>();

    public void addEvent(String id, Event event) {
        List<Event> events = store.get(id);
        if (events == null) {
            events = new ArrayList<>();
            events.add(event);
            store.put(id, events);
        } else {
            events.add(event);
        }
    }

    public List<Event> getEvents(String id) {
        return store.get(id);
    }

}
