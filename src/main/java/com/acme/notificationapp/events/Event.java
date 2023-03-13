package com.acme.notificationapp.events;

import lombok.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class Event {
    public UUID id = UUID.randomUUID();
    public Date created = new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(created, event.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", created=" + created +
                '}';
    }
}