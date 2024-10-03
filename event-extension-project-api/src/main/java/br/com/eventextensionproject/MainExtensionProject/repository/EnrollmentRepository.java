package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Enrollment;
import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.EventActivity;
import br.com.eventextensionproject.MainExtensionProject.entity.Person;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.EnrollmentStatus;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.StatusEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByEventOrderByPersonNamePerson(Event event);
    List<Enrollment> findByPersonOrderByEventEventStartDate(Person person);
    List<Enrollment> findByPersonAndEnrollmentStatusAndEventStatusEventOrderByEventEventStartDate(Person person, EnrollmentStatus enrollmentStatus, StatusEvent statusEvent);
    List<Enrollment> findByPersonAndEnrollmentStatusOrderByEventEventStartDate(Person person, EnrollmentStatus enrollmentStatus);
    List<Enrollment> findByEventActivitiesOrderByPersonNamePerson(EventActivity eventActivity);
    Enrollment findByEventAndPersonAndEnrollmentStatus(Event event, Person person, EnrollmentStatus enrollmentStatus);
    Optional<Enrollment> findByBarCode(String barCode);
}
