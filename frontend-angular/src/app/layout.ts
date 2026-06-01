import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet,CommonModule, FormsModule, RouterModule],

  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class Layout {}