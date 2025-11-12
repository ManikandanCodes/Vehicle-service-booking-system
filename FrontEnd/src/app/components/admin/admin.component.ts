import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  bookings: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadBookings();
  }

  
  loadBookings() {
    this.http.get<any[]>('http://localhost:8080/api/bookings/all').subscribe({
      next: (data) => (this.bookings = data),
      error: (err) => console.error('Failed to fetch bookings', err)
    });
  }

 
  markCompleted(id: number) {
   this.http.put(`http://localhost:8080/api/bookings/${id}/complete`, {}, { responseType: 'text' }).subscribe({
      next: () => {

        const booking = this.bookings.find(b => b.id === id);
        if (booking) booking.status = 'Completed';
        alert('Booking marked as completed');
      },
      error: (err) => {
        console.error('Error marking completed:', err);
        alert('Something went wrong while updating status.');
      }
    });
  }


  getCompletedCount() {
    return this.bookings.filter(b => b.status === 'Completed').length;
  }

  getPendingCount() {
    return this.bookings.filter(b => b.status === 'Pending').length;
  }

  getTotalCount() {
    return this.bookings.length;
  }
}
