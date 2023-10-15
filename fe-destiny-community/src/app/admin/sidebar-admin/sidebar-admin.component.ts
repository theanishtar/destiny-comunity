import { Component, HostListener, Renderer2, ElementRef, QueryList, ViewChildren } from '@angular/core';

@Component({
  selector: 'app-sidebar-admin',
  templateUrl: './sidebar-admin.component.html',
  styleUrls: [`../css/sb-admin-2.min.css`, `../css/home.css`],
})
export class SidebarAdminComponent {
  constructor(private renderer: Renderer2) {}

  @ViewChildren('mainColorLink')
  mainColorLinks!: QueryList<ElementRef>;

  ngAfterViewInit(): void {
    const bodyElement = document.querySelector('.body-admin');
    const sidebarElement = document.querySelector('.sidebarD');
    const colortheme = localStorage.getItem("colortheme");
    if (bodyElement) {
      this.renderer.addClass(bodyElement, String(colortheme));
      this.renderer.addClass(sidebarElement, String(colortheme));
    }

    const sideLinks: NodeListOf<HTMLElement> = document.querySelectorAll(
      ".sidebarD .side-menu li a:not(.logout):not(.clickSetting)"
    );

    sideLinks.forEach((item: HTMLElement) => {
      item.addEventListener("click", () => {
        sideLinks.forEach((i: HTMLElement) => {
          this.renderer.removeClass(i.parentElement, "active");
        });
        localStorage.setItem("sidebarActive", item.className);
      });
    });

    const sideBarItemActive = localStorage.getItem("sidebarActive");
    sideLinks.forEach((i: HTMLElement) => {
      if(sideBarItemActive?.match(i.className)){
        this.renderer.addClass(i.parentElement, "active");
      }
      else{
        this.renderer.removeClass(i.parentElement, "active");
      }
    });

    this.mainColorLinks.forEach((item: ElementRef) => {
      const li = item.nativeElement;
      li.addEventListener('click', () => {
        this.mainColorLinks.forEach((i: ElementRef) => {
          i.nativeElement.classList.remove('border-active');
        });
        li.classList.add('border-active');
      });
    });
  }

  mainColor = ["pink", "blue", "green", "orange", "cyan"];

  setMainColor(value: string) {
    const bodyElement = document.querySelector('.body-admin'); // Select the div with class 'body'
    const sidebarElement = document.querySelector('.sidebarD');
    this.mainColor.forEach((item: string) => {
      if (!item.match(value)) {
        if (bodyElement) {
          this.renderer.removeClass(bodyElement, item);
          this.renderer.removeClass(sidebarElement, item);
          localStorage.removeItem("colortheme");
        }
      }
    });
    if (bodyElement) {
      this.renderer.addClass(bodyElement, value);
      this.renderer.addClass(sidebarElement, value);
      localStorage.setItem("colortheme", value);
    }
  }

  isNavOpen = false;
  open = 0;
  openNav(): void {
    this.open++;
    this.isNavOpen = true;
    if(this.open > 1){
      this.closeNav();
      this.open = 0;
    }
  }

  closeNav(): void {
    this.isNavOpen = false;
    this.open = 0;
  }

  @HostListener('window:load', ['$event'])
  onWindowLoad(event: Event): void {
    this.toggleSidebar();
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize(event: Event): void {
    this.toggleSidebar();
  }

  private toggleSidebar(): void {
    const sideBar = document.querySelector('.sidebarD');
    const contentClose = document.querySelector('.content');
    if (sideBar) {
      if (window.innerWidth < 1300) {
          this.renderer.addClass(sideBar, 'closeD');
          this.renderer.addClass(contentClose, 'contentCloseD');
      } else {
          this.renderer.removeClass(sideBar, 'closeD');
          this.renderer.removeClass(contentClose, 'contentCloseD');
      }
    }
  }




}
