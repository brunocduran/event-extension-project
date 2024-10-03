package br.com.eventextensionproject.MainExtensionProject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.eventextensionproject.MainExtensionProject.entity.Course;
import br.com.eventextensionproject.MainExtensionProject.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {


    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAll() {
        return courseRepository.findByOrderByNameCourseAsc();
    }

    public Course save(Course course) {
        if(validateCourse(course)) {
            return courseRepository.saveAndFlush(course);
        } else {
            throw new DataIntegrityViolationException("Campo Nome não pode ser vazio!");
        }
    }


    public HashMap<String, Object> delete(Long idCourse) {
        String status = "";

        Optional<Course> course =
                Optional.ofNullable(courseRepository.findById(idCourse).
                        orElseThrow(() -> new ObjectnotFoundException("Curso não encontrado!")));

        //courseRepository.delete(course.get());
        if (course.get().getSituation() == Situation.ATIVO) {
            course.get().setSituation(Situation.INATIVO);
            status = "inativado";
        } else {
            course.get().setSituation(Situation.ATIVO);
            status = "ativado";
        }

        courseRepository.saveAndFlush(course.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Curso " + course.get().getNameCourse() + " " + status + " com sucesso!");
        return result;
    }

    public Optional<Course> findById(Long idCourse) {
        if (idCourse != null) {
            return Optional.ofNullable(courseRepository.findById(idCourse)
                    .orElseThrow(() -> new ObjectnotFoundException("Curso não encontrado!")));
        } else {
            throw new ObjectnotFoundException("ID do Curso não pode ser nulo!");
        }
    }

    public Course findBy(Long idCourse) {
        Optional<Course> obj = courseRepository.findById(idCourse);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Curso não encontrado!"));
    }

    public Course update(Course course) {
        if (validateCourse(course)) {
            if (findById(course.getIdCourse()) != null) {
                return courseRepository.saveAndFlush(course);
            } else {
                return null;
            }
        } else {
            throw new ObjectnotFoundException("Campo Nome não pode ser vazio!");
        }
    }

    private boolean validateCourse(Course course) {
        if (course.isValidName()) {
            return true;
        }
        return false;
    }

}
