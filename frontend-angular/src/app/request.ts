import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface Message {
  type: 'user' | 'bot';
  text: string;
  time: string;
}

@Component({
  selector: 'app-request',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './request.html',
  styleUrls: ['./request.css']
})
export class RequestComponent implements OnInit {

  @ViewChild('chatBox') chatBox!: ElementRef;

  messages: Message[] = [];
  inputValue = '';
  isTyping = false;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Auto-greet and suggest staff role request
    this.messages.push({
      type: 'bot',
      text: `Hi! 👋 If you want to request a staff role, just type your student ID or email and I’ll check for you.`,
      time: this.getCurrentTime()
    });
  }

  // =========================
  // QUICK BUTTONS
  // =========================
  quickAsk(text: string): void {
    this.inputValue = text;
    this.sendRequest();
  }

  // =========================
  // SEND MESSAGE
  // =========================
  sendRequest(): void {

    const text = this.inputValue.trim();
    if (!text) return;

    this.messages.push({
      type: 'user',
      text,
      time: this.getCurrentTime()
    });
    this.inputValue = '';
    this.isTyping = true;
    this.scrollToBottom();

    // If the user input looks like a student ID or email, call the staff role endpoint
    if (/^\d{4,}$/.test(text) || /@/.test(text)) {
      this.http.post(
        'http://localhost:8080/api/staff/request-role',
        { studentId: /^\d{4,}$/.test(text) ? text : undefined, email: /@/.test(text) ? text : undefined },
        { responseType: 'json' }
      ).subscribe({
        next: (staff: any) => {
          this.isTyping = false;
          this.messages.push({
            type: 'bot',
            text: `<b>Staff Found:</b><br>Name: ${staff.fullName || staff.staffCode}<br>Email: ${staff.email}<br>Role: ${staff.role?.name || 'N/A'}`,
            time: this.getCurrentTime()
          });
          this.scrollToBottom();
        },
        error: () => {
          this.isTyping = false;
          this.messages.push({
            type: 'bot',
            text: "Sorry, no staff record found for that ID or email.",
            time: this.getCurrentTime()
          });
          this.scrollToBottom();
        }
      });
      return;
    }

    // Otherwise, fallback to chat-search
    this.http.post(
      'http://localhost:8080/api/staff/chat-search',
      text,
      { responseType: 'text' }
    ).subscribe({
      next: (response: string) => {
        this.isTyping = false;
        this.messages.push({
          type: 'bot',
          text: response,
          time: this.getCurrentTime()
        });
        this.scrollToBottom();
      },
      error: () => {
        this.isTyping = false;
        this.messages.push({
          type: 'bot',
          text: "Sorry 😕 I couldn't process your request.",
          time: this.getCurrentTime()
        });
        this.scrollToBottom();
      }
    });
  }

  // =========================
  // TIME
  // =========================
  getCurrentTime(): string {
    return new Date().toLocaleTimeString([], {
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  // =========================
  // SCROLL CHAT
  // =========================
  private scrollToBottom(): void {
    setTimeout(() => {
      if (this.chatBox) {
        this.chatBox.nativeElement.scrollTop =
          this.chatBox.nativeElement.scrollHeight;
      }
    }, 100);
  }
}