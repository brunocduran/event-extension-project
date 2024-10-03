package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Enrollment;
import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.EventActivity;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventActivityRepository extends JpaRepository<EventActivity, Long> {
    List<EventActivity> findByOrderByActivityNameAsc();
    List<EventActivity> findByEventOrderByActivityName(Event event);

    @Query("select distinct ea from EventActivity ea, Attendance a where a.eventActivity = ea and a.enrollment = ?1 and a.attendanceStatus = ?2 order by ea.activityName")
    List<EventActivity> findEventActivityCertificate(Enrollment enrollment, AttendanceStatus attendanceStatus);

    @Query("SELECT ea from EventActivity ea where ea.event = :event and ea.activityDate = :date")
     List<EventActivity> findAllByActivityEventDate(Event event, LocalDate date);

    @Query("SELECT DISTINCT activityDate FROM EventActivity where event = :event  ORDER BY activityDate DESC")
    List<LocalDate> findDistinctRecentActivityDates(@Param("event") Event event);

}
