import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ApiService } from '../api';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [FormsModule, CommonModule,RouterModule],
  templateUrl: './user-form.html',
  styleUrls: ['./user-form.css']
})
export class UserForm {

  user = {
    studentId: '',
    fullName: '',
    email: '',
    password: '',
    confirmPassword: ''
  };

  constructor(private api: ApiService) {}

  saveUser() {

    // basic validation
    if (this.user.password !== this.user.confirmPassword) {
      console.error("Passwords do not match");
      return;
    }

    // send ONLY backend fields
    const payload = {
      studentId: this.user.studentId,
      fullName: this.user.fullName,
      email: this.user.email,
      password: this.user.password
    };

    this.api.createUser(payload).subscribe({
      next: (res) => {
        console.log("User saved successfully:", res);

        // reset form
        this.user = {
          studentId: '',
          fullName: '',
          email: '',
          password: '',
          confirmPassword: ''
        };
      },
      error: (err) => {
        console.error("Error saving user:", err);
      }
    });
  }
}