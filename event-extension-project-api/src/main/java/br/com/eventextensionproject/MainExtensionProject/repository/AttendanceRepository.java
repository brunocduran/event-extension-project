package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Attendance;
import br.com.eventextensionproject.MainExtensionProject.entity.Enrollment;
import br.com.eventextensionproject.MainExtensionProject.entity.EventActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEventActivityOrderByEnrollmentPersonNamePerson(EventActivity eventActivity);
    List<Attendance> findByEnrollment(Enrollment enrollment);
    Optional<Attendance> findByEventActivityAndEnrollment(EventActivity eventActivity, Enrollment enrollment);
}
