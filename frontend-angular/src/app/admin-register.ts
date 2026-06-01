import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ApiService } from './api';

@Component({
  selector: 'app-admin-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './admin-register.html',
  styleUrls: ['./admin-register.css']
})
export class AdminRegister {

  admin = {
    username: '',
    email: '',
    password: '',
    confirmPassword: ''
  };

  constructor(private api: ApiService) {}

  saveAdmin() {
    if (this.admin.password !== this.admin.confirmPassword) {
      alert("Passwords do not match");
      return;
    }

    this.api.createAdmin(this.admin).subscribe({
      next: (res) => {
        console.log("Admin created:", res);
        this.admin = {
          username: '',
          email: '',
          password: '',
          confirmPassword: ''
        };
      },
      error: (err) => {
        console.error("Error:", err);
      }
    });
  }
}