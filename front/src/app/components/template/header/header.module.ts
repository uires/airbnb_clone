import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { HeaderComponent } from './header.component';
import { SidebarModule } from 'primeng/sidebar';

@NgModule({
  declarations: [HeaderComponent],
  imports: [CommonModule, SidebarModule],
  exports: [HeaderComponent],
})
export class HeaderModule {}
