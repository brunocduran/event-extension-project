package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attendance")
    private Long idAttendance;

    @ManyToOne
    private Enrollment enrollment;

    @ManyToOne
    private EventActivity eventActivity;

    @Column(name = "enrollment_status", nullable = false)
    private AttendanceStatus attendanceStatus;

    @PrePersist
    private void prePersist() {
        this.setAttendanceStatus(AttendanceStatus.AUSENTE);
    }

    public Attendance(Enrollment enrollment, EventActivity eventActivity){
        this.enrollment = enrollment;
        this.eventActivity = eventActivity;
    }


}
