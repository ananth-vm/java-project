package com.example.attendancetracker.service;

import com.example.attendancetracker.model.Subject;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class AttendanceService {

    // Using a HashMap as an in-memory 'database' for simplicity
    private final Map<String, Subject> subjectRepository = new HashMap<>();

    /**
     * Initializes the subjects on application startup.
     * The @PostConstruct annotation ensures this runs after dependency injection.
     */
    @PostConstruct
    public void initializeSubjects() {
        // Data from your prompt
        addSubject(new Subject("datastructures", 45));
        addSubject(new Subject("dsd", 45));
        addSubject(new Subject("java", 45));
        addSubject(new Subject("math", 60));
        addSubject(new Subject("software", 45));
        addSubject(new Subject("ds lab", 60));
        addSubject(new Subject("dsd lab", 60));
        addSubject(new Subject("java lab", 60));
    }

    private void addSubject(Subject subject) {
        // Use subject name as the key (converted to lowercase for case-insensitivity)
        subjectRepository.put(subject.getName().toLowerCase(), subject);
    }

    /**
     * Retrieves all subjects.
     */
    public Collection<Subject> getAllSubjects() {
        return subjectRepository.values();
    }

    /**
     * Increases the 'classesLeft' (leave taken) count for a subject.
     * This mimics the action of pressing the '+' button.
     */
    public Subject takeLeave(String subjectName) {
        Subject subject = subjectRepository.get(subjectName.toLowerCase());
        
        if (subject == null) {
            // In a real app, you'd throw a NotFoundException here
            return null; 
        }

        int currentLeave = subject.getClassesLeft();
        // Prevent leave from exceeding total hours
        if (currentLeave < subject.getTotalHours()) {
            subject.setClassesLeft(currentLeave + 1);
        }
        
        return subject;
    }
}