package br.com.eventextensionproject.MainExtensionProject.entity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.eventextensionproject.MainExtensionProject.dto.EventDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.StatusEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")
    private Long idEvent;

    @Column(name = "name_event", nullable = false)
    private String nameEvent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-3")
    @Column(name = "start_date_event", nullable = false)
    private LocalDateTime eventStartDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-3")
    @Column(name = "final_date_event", nullable = false)
    private LocalDateTime eventEndDate;

    @Column(name = "description_event", nullable = false)
    private String descriptionEvent;

    @JoinColumn(name = "id_event_category", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private EventCategory eventCategory;

    @Column(name = "name_image")
    private String nameImage;

    @Column(name = "type_image")
    private String typeImage;

	@ManyToMany(cascade = CascadeType.ALL)
    private List<Course> courses;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Institution> institutions;

    @Column(name = "status_event", nullable = false)
    private StatusEvent statusEvent;

    @JsonIgnore
    public boolean isValidName() {
        return this.getNameEvent() != null && !this.getNameEvent().isBlank();
    }

    public Event(EventDTO obj) throws IOException {
        this.idEvent = obj.getIdEvent();
        this.nameEvent = obj.getNameEvent();
        this.eventStartDate = obj.getEventStartDate();
        this.eventEndDate = obj.getEventEndDate();
        this.descriptionEvent = obj.getDescriptionEvent();
        if(!obj.getPictureEvent().isEmpty()){
            this.typeImage = obj.getPictureEvent().getContentType();
            this.nameImage = StringUtils.cleanPath(obj.getPictureEvent().getOriginalFilename());
        }
    }
    @PrePersist
    private void prePersist() {
        this.setStatusEvent(StatusEvent.ATIVO);
    }
}
