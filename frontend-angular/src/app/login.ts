import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from './api';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  user = {
    studentId: '',
    password: ''
  };

  constructor(
    private api: ApiService,
    private router: Router
  ) {}

  login() {
    this.api.login(this.user).subscribe({
      next: (res: any) => {

        console.log("Login successful:", res);

        // store session
        localStorage.setItem("user", JSON.stringify(res));

        alert("Welcome " + res.fullName);

        // ✅ REDIRECT TO REQUEST PAGE
        this.router.navigate(['/request']);

      },
      error: (err) => {
        console.error("Login failed:", err);
        alert("Invalid Student ID or Password");
      }
    });
  }
}