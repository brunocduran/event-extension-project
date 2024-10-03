package br.com.eventextensionproject.MainExtensionProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "event_organizer")
@AllArgsConstructor
@NoArgsConstructor
public class EventOrganizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event_organizer")
    private Long idEventOrganizer;

    @JsonIgnore
    @ManyToOne
    private Event event;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Function function;
}
