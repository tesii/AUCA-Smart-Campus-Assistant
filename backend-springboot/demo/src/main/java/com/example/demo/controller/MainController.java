package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.User;
import com.example.demo.model.Admin;
import com.example.demo.model.Department;
import com.example.demo.model.Request;
import com.example.demo.model.Role;
import com.example.demo.model.Staff;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.RequestRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StaffRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RequestRepository requestRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

@Autowired
    private AdminRepository adminRepo;

    @Autowired
private DepartmentRepository departmentRepo;

@Autowired
private RoleRepository roleRepo;

@Autowired
private StaffRepository staffRepo;
    // =========================
    // USERS CRUD
    // =========================

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {

        User existing = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setStudentId(user.getStudentId());
        existing.setFullName(user.getFullName());
        existing.setEmail(user.getEmail());

        // IMPORTANT: hash password again
        existing.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(existing);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
    }
    @PostMapping("/login")
    public User login(@RequestBody User user) {

        User existingUser = userRepo.findByStudentId(user.getStudentId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean passwordMatch = passwordEncoder.matches(
                user.getPassword(),
                existingUser.getPassword()
        );

        if (!passwordMatch) {
            throw new RuntimeException("Invalid credentials");
        }

        return existingUser;
    }
    // =========================
    // REQUESTS CRUD
    // =========================

    @GetMapping("/requests")
    public List<Request> getRequests() {
        return requestRepo.findAll();
    }

    @GetMapping("/requests/{id}")
    public Request getRequestById(@PathVariable Long id) {
        return requestRepo.findById(id).orElse(null);
    }

    @PostMapping("/requests")
    public Request createRequest(@RequestBody Request req) {
        return requestRepo.save(req);
    }

    @PutMapping("/requests/{id}")
    public Request updateRequest(@PathVariable Long id, @RequestBody Request req) {

        Request existing = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        existing.setTitle(req.getTitle());
        existing.setDescription(req.getDescription());

        return requestRepo.save(existing);
    }

    @DeleteMapping("/requests/{id}")
    public void deleteRequest(@PathVariable Long id) {
        requestRepo.deleteById(id);
    }
    @PostMapping("/admin/register")
    public Admin registerAdmin(@RequestBody Admin admin) {

        // hash password before saving
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        return adminRepo.save(admin);
    }
@PostMapping("/admin/login")
public Admin loginAdmin(@RequestBody Admin admin) {

    Admin existing = adminRepo.findByUsername(admin.getUsername())
            .orElseThrow(() -> new RuntimeException("Admin not found"));

    boolean match = passwordEncoder.matches(
            admin.getPassword(),
            existing.getPassword()
    );

    if (!match) {
        throw new RuntimeException("Invalid credentials");
    }

    return existing;
}
// =========================
// DEPARTMENTS CRUD
// =========================

@PostMapping("/departments")
public Department createDepartment(@RequestBody Department dept) {
    return departmentRepo.save(dept);
}

@GetMapping("/departments")
public List<Department> getDepartments() {
    return departmentRepo.findAll();
}

@GetMapping("/departments/{id}")
public Department getDepartment(@PathVariable Long id) {
    return departmentRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found"));
}

@PutMapping("/departments/{id}")
public Department updateDepartment(@PathVariable Long id, @RequestBody Department dept) {

    Department existing = departmentRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found"));

    existing.setName(dept.getName());
    existing.setCode(dept.getCode());
    existing.setDescription(dept.getDescription());

    return departmentRepo.save(existing);
}

@DeleteMapping("/departments/{id}")
public void deleteDepartment(@PathVariable Long id) {
    departmentRepo.deleteById(id);
}
// =========================
// ROLES CRUD
// =========================

@PostMapping("/roles")
public Role createRole(@RequestBody Role role) {
    return roleRepo.save(role);
}

@GetMapping("/roles")
public List<Role> getRoles() {
    return roleRepo.findAll();
}

@GetMapping("/roles/{id}")
public Role getRole(@PathVariable Long id) {
    return roleRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found"));
}

@PutMapping("/roles/{id}")
public Role updateRole(@PathVariable Long id, @RequestBody Role role) {

    Role existing = roleRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found"));

    existing.setName(role.getName());

    return roleRepo.save(existing);
}

@DeleteMapping("/roles/{id}")
public void deleteRole(@PathVariable Long id) {
    roleRepo.deleteById(id);
}
// =========================
// STAFF CRUD
// =========================

@PostMapping("/staff")
public Staff createStaff(@RequestBody Staff staff) {
    return staffRepo.save(staff);
}

@GetMapping("/staff")
public List<Staff> getStaff() {
    return staffRepo.findAll();
}

@GetMapping("/staff/{id}")
public Staff getStaffById(@PathVariable Long id) {
    return staffRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Staff not found"));
}

@PutMapping("/staff/{id}")
public Staff updateStaff(@PathVariable Long id, @RequestBody Staff staff) {

    Staff existing = staffRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Staff not found"));

    existing.setStaffCode(staff.getStaffCode());
    existing.setFullName(staff.getFullName());
    existing.setEmail(staff.getEmail());
    existing.setPhone(staff.getPhone());
    existing.setPositionTitle(staff.getPositionTitle());
    existing.setStatus(staff.getStatus());

    existing.setDepartment(staff.getDepartment());
    existing.setRole(staff.getRole());

    return staffRepo.save(existing);
}

@DeleteMapping("/staff/{id}")
public void deleteStaff(@PathVariable Long id) {
    staffRepo.deleteById(id);
}
// =========================
// STAFF ROLE REQUEST
// =========================
@PostMapping("/staff/chat-search")
public String chatSearch(@RequestBody String message) {

    String input = message.replace("\"", "").toLowerCase();

    Request req = new Request();
    req.setTitle(message);
    req.setDescription(message);

    // =========================
    // 1. GREETING CASE
    // =========================
    if (input.contains("hello") || input.contains("hi")) {

        req.setStatus("GREETING");
        requestRepo.save(req);

        return "Hi 👋 Ask me about staff by department or role.";
    }

    List<Staff> results = staffRepo.findAll();

    List<Staff> filtered = results.stream()
        .filter(s -> s.getDepartment() != null && s.getRole() != null)
        .filter(s -> {

            String dept = ((String) s.getDepartment().getName()).toLowerCase();
            String role = ((String) s.getRole().getName()).toLowerCase();

            return input.contains(dept) || input.contains(role);
        })
        .toList();

    // =========================
    // 2. NO RESULT CASE
    // =========================
    if (filtered.isEmpty()) {

        req.setStatus("NO_RESULT");
        requestRepo.save(req);

        return "❌ I couldn't find any staff matching your request.";
    }

    // =========================
    // 3. SUCCESS CASE
    // =========================
    req.setStatus("SUCCESS");
    requestRepo.save(req);

    StringBuilder response = new StringBuilder("Here is what I found 👇<br><br>");

    for (Staff s : filtered) {
        response.append("👤 <b>")
                .append(s.getFullName())
                .append("</b><br>")
                .append("📞 ")
                .append(s.getPhone())
                .append("<br><br>");
    }

    return response.toString();
}}