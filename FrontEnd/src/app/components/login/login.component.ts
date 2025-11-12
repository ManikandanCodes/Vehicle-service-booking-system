import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginData = { email: '', password: '' };

  
  showForgotPassword = false;
  forgotEmail = '';
  otp: string = '';
  newPassword = '';
  confirmPassword = '';
  otpSent = false;
  message = '';
  loading = false;
  passwordMismatch = false;
  resetSuccess = false;

  constructor(private http: HttpClient, private router: Router) {}

  
  onLogin() {
    this.loading = true;
    this.http.post<any>('http://localhost:8080/api/users/login', this.loginData)
      .subscribe({
        next: (res) => {
          this.loading = false;
          if (res.role === 'admin') {
            alert('Admin Login Successful!');
            this.router.navigate(['/admin']);
          } else if (res.role === 'user') {
            alert('User Login Successful!');
            this.router.navigate(['/booking']);
          } else {
            alert('Invalid Credentials');
          }
        },
        error: () => {
          this.loading = false;
          alert('Login failed. Please try again.');
        }
      });
  }

  

  sendOtp() {
    this.loading = true;
    this.message = '';
    this.resetSuccess = false;

    this.http.post('http://localhost:8080/api/users/forgot-password',
      { email: this.forgotEmail },
      { responseType: 'text' })
      .subscribe({
        next: (res) => {
          this.loading = false;
          this.otpSent = true;
          this.message = res || 'OTP sent to your registered email!';
        },
        error: (err) => {
          this.loading = false;
          console.error('OTP Error:', err);
          this.message = 'Error sending OTP. Try again.';
        }
      });
  }

  

  onPasswordInput() {
    this.passwordMismatch = this.newPassword !== this.confirmPassword && this.confirmPassword.length > 0;
  }

  

  resetPassword() {
    if (this.passwordMismatch) {
      this.message = 'Passwords do not match!';
      return;
    }

    this.loading = true;
    this.message = '';

    const payload = {
      email: this.forgotEmail,
      otp: this.otp,
      newPassword: this.newPassword
    };

    this.http.post('http://localhost:8080/api/users/reset-password', payload, { responseType: 'text' })
      .subscribe({
        next: (res) => {
          this.loading = false;
          this.resetSuccess = true;
          this.message = res || 'Password reset successfully!';

          
          this.otp = '';
          this.newPassword = '';
          this.confirmPassword = '';

          
          setTimeout(() => {
            this.resetSuccess = false;
          }, 3000);
        },
        error: (err) => {
          this.loading = false;
          console.error('Reset Error:', err);
          this.message = 'Invalid OTP or email.';
        }
      });
  }

  
  closeForgotPopup() {
    this.showForgotPassword = false;
    this.otpSent = false;
    this.resetSuccess = false;
    this.forgotEmail = '';
    this.otp = '';
    this.newPassword = '';
    this.confirmPassword = '';
    this.message = '';
    this.passwordMismatch = false;
  }
}
