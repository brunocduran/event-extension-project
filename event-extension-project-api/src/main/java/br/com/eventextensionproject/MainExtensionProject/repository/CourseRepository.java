package br.com.eventextensionproject.MainExtensionProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.eventextensionproject.MainExtensionProject.entity.Course;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>{
    List<Course> findByOrderByNameCourseAsc();
}
