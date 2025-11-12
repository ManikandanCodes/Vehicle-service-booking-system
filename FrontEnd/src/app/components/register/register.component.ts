import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerData = { name: '', email: '', password: '' };
  confirmPassword: string = '';
  message = '';
  isSuccess = false;

  constructor(private http: HttpClient) {}

  onRegister() {
    
    
    if (this.registerData.password !== this.confirmPassword) {
      this.message = 'Passwords do not match!';
      this.isSuccess = false;
      return;
    }

    this.http.post('http://localhost:8080/api/users/register', this.registerData)
      .subscribe({
        next: () => {
          this.message = 'Registration Successful!';
          this.isSuccess = true;
          this.registerData = { name: '', email: '', password: '' };
          this.confirmPassword = '';
        },
        error: () => {
          this.message = 'Registration Failed! Please try again.';
          this.isSuccess = false;
        }
      });
  }
}
