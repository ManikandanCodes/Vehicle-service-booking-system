import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData = { email: '', password: '' };

  constructor(private http: HttpClient, private router: Router) {}

  onLogin() {
    this.http.post<any>('http://localhost:8080/api/users/login', this.loginData)
      .subscribe({
        next: (res) => {
          if (res.role === 'admin') {
            alert( res.message);
            this.router.navigate(['/admin']);
          } else if (res.role === 'user') {
            alert(res.message);
            this.router.navigate(['/booking']);
          } else {
            alert('Invalid credentials');
          }
        },
        error: (err) => {
          alert('Login failed. Server error.');
          console.error(err);
        }
      });
  }
}
