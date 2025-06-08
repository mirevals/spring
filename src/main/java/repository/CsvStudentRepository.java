package repository;

import model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Repository
public class CsvStudentRepository implements StudentRepository {

    @Value("${students.file-path}")
    private String filePath;

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return students; // файл отсутствует — возвращаем пустой список
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            reader.readLine(); // пропускаем заголовок
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    students.add(new Student(
                            Integer.parseInt(parts[0].trim()),
                            parts[1].trim(),
                            parts[2].trim(),
                            Integer.parseInt(parts[3].trim())
                    ));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read students", e);
        }
        return students;
    }

    @Override
    public void saveAll(List<Student> students) {
        File file = new File(filePath);

        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.println("id,firstName,lastName,tokens");
            for (Student s : students) {
                writer.printf("%d,%s,%s,%d%n",
                        s.getId(),
                        s.getFirstName(),
                        s.getLastName(),
                        s.getTokens());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to save students", e);
        }
    }

}
