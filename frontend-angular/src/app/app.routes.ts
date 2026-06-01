import { Routes } from '@angular/router';
import { Layout } from './layout';
import { DashboardComponent } from './dashboard';
import { UserForm } from './user-form/user-form';
import { UserList } from './user-list/user-list';
import { Settings } from './settings';
import { LoginComponent } from './login';
import { AdminRegister } from './admin-register';
import { RequestComponent } from './request';

export const routes: Routes = [

  {
    path: '',
    component: Layout,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'create', component: UserForm },
      { path: 'users', component: UserList },
    { path: 'login', component: LoginComponent },
    { path: 'admin', component: AdminRegister },
    { path: 'request', component: RequestComponent},

      { path: 'settings', component: Settings }
    ]
  }
];