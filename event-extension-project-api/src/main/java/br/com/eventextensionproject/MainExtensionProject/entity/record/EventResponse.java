package br.com.eventextensionproject.MainExtensionProject.entity.record;

import java.time.LocalDateTime;
import java.util.List;
import br.com.eventextensionproject.MainExtensionProject.entity.*;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.StatusEvent;

public record EventResponse(
    Long idEvent,
    String nameEvent,
    LocalDateTime eventStartDate,
    LocalDateTime eventEndDate,
    String descriptionEvent,
    EventCategory eventCategory,
    String nameImage,
    String pictureEvent,
    String typeImage,
    StatusEvent statusEvent,
    List<Course> courses,
    List<Institution> institutions,
    List<EventOrganizer> eventOrganizer,
    List<EventActivity> eventActivity,
    List<Lot> lot
) {
}
