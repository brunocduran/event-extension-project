package br.com.eventextensionproject.MainExtensionProject.entity.record;

import br.com.eventextensionproject.MainExtensionProject.entity.Attendance;
import br.com.eventextensionproject.MainExtensionProject.entity.EventActivity;
import br.com.eventextensionproject.MainExtensionProject.entity.Lot;
import br.com.eventextensionproject.MainExtensionProject.entity.Person;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.EnrollmentFinancialStatus;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.EnrollmentStatus;
import java.util.List;

public record EnrollmentResponse(
        Long idEnrollment,
        String barCode,
        Person person,
        EventResponse event,
        List<EventActivity> eventActivities,
        Lot lot,
        EnrollmentStatus enrollmentStatus,
        EnrollmentFinancialStatus enrollmentFinancialStatus,
        List<Attendance> attendances) {
}
