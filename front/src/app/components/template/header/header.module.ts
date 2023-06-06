import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { HeaderComponent } from './header.component';
import { SidebarModule } from 'primeng/sidebar';
import { LoginComponent } from './login/login.component';
import { DialogModule } from 'primeng/dialog';

@NgModule({
  declarations: [HeaderComponent, LoginComponent],
  imports: [CommonModule, SidebarModule, DialogModule],
  exports: [HeaderComponent],
})
export class HeaderModule {}
