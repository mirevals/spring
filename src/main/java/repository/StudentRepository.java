package repository;

import model.Student;
import java.util.List;

public interface StudentRepository {
    List<Student> findAll();
    void saveAll(List<Student> students);
}
