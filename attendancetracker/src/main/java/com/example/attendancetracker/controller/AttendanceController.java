package com.example.attendancetracker.controller;

import com.example.attendancetracker.model.Subject;
import com.example.attendancetracker.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins="*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * GET /api/attendance
     * Retrieves the list of all subjects with their current status.
     */
    @GetMapping
    public Collection<Subject> getAllSubjects() {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        return attendanceService.getAllSubjects();
    }

    /**
     * PUT /api/attendance/leave/{subjectName}
     * Increases the leave count for a specific subject.
     * This simulates the '+' button click.
     */
    @PutMapping("/leave/{subjectName}")
    public ResponseEntity<Subject> takeLeave(@PathVariable String subjectName) {
        Subject updatedSubject = attendanceService.takeLeave(subjectName);
        
        if (updatedSubject == null) {
            // System.out.println("Subject not found: " + subjectName);
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(updatedSubject);
    }

     @PutMapping("/remove/{subjectName}")
    public ResponseEntity<Subject> removeLeave(@PathVariable String subjectName) {
        Subject updatedSubject = attendanceService.removeLeave(subjectName);
        
        if (updatedSubject == null) {
            // System.out.println("Subject not found: " + subjectName);
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(updatedSubject);
    }
}