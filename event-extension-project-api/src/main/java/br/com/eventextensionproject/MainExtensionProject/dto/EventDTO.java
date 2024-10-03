package br.com.eventextensionproject.MainExtensionProject.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Long idEvent;
    private String nameEvent;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private String descriptionEvent;
    private Long idEventCategory;
    private MultipartFile pictureEvent;
    private Set<Long> courses = new HashSet<>();
    private Set<Long> institutions = new HashSet<>();
}
