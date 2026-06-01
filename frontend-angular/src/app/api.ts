import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ApiService {

  private baseUrl = "http://localhost:8080/api/users";

  constructor(private http: HttpClient) {}

  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  createUser(user: any): Observable<any> {
    return this.http.post(this.baseUrl, user);
  }
  login(user: any) {
  return this.http.post("http://localhost:8080/api/login", user);
}
createAdmin(admin: any) {
  return this.http.post("http://localhost:8080/api/admin/register", admin);
}
}