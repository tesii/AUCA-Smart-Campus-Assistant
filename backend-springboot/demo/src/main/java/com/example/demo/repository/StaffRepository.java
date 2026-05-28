package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByEmail(String email);
    Staff findByStaffCode(String staffCode);
}
