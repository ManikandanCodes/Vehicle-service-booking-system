import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent {
  booking: any = {
    name: '',
    email: '',
    phone: '',
    vehicle: '',
    model: '',
    regNumber: '',
    serviceType: '',
    partService: '',
    pickupOption: '',
    date: '',
    timeSlot: '',
    remarks: ''
  };

  message = '';
  isSuccess = false;

  constructor(private http: HttpClient) {}

  submitBooking() {
    this.http.post('http://localhost:8080/api/bookings/create', this.booking).subscribe({
      next: () => {
        this.isSuccess = true;
        this.message = ' Booking successful!';
        this.booking = {};
      },
      error: () => {
        this.isSuccess = false;
        this.message = ' Booking failed. Try again.';
      }
    });
  }
}
