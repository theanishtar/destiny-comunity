import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';

import { liquid } from "../../../assets/js/utils/liquidify.js";
// import { tns } from '../../../assets/js/vendor/ti';
import { avatarHexagons } from '../../../assets/js/global/global.hexagons.js';
import { tooltips } from '../../../assets/js/global/global.tooltips.js';
import { popups } from '../../../assets/js/global/global.popups.js';
import { headers } from '../../../assets/js/header/header.js';
import { sidebars } from '../../../assets/js/sidebar/sidebar.js';
import { content } from '../../../assets/js/content/content.js';
import { form } from '../../../assets/js/form/form.utils.js';
import 'src/assets/js/utils/svg-loader.js';

// 
import { ModalService } from '../service/modal.service';
import { InteractPostsService } from '../service/interact-posts.service';

import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { GetStartedComponent } from '@app/get-started/get-started.component';
import { LoginService } from '@app/service/login.service';
import '../../../assets/toast/main.js';
declare var toast: any;
@Component({
  selector: 'app-newsfeed',
  templateUrl: './newsfeed.component.html',
  styleUrls: [
    `../../css/vendor/bootstrap.min.css`,
    `../../css/styles.min.css`,
    `../../css/vendor/simplebar.css`,
    `../../css/vendor/tiny-slider.css`,
    './newsfeed.component.css'
  ]
})
export class NewsfeedComponent implements OnInit {
  userDisplayName = '';
  postId = '123'; // Mã số của bài viết (có thể là mã số duy nhất của mỗi bài viết)

  ngOnInit() {
    this.userDisplayName = this.cookieService.get('full_name');  
    this.checkSrcoll();
    this.translate();
    liquid.liquid();
    avatarHexagons.avatarHexagons();
    tooltips.tooltips();
    popups.popup();
    popups.picturePopup();
    headers.headers();
    sidebars.sidebars();
    content.contentTab();
    form.formInput();
  }

  constructor(
    public modalService: ModalService,
    public interactPostsService: InteractPostsService,
    private cookieService: CookieService,
    private loginService: LoginService,
    private router: Router,
  ) { }

  translate() {
    document.addEventListener("DOMContentLoaded", function () {
      const translateButton = document.querySelector(".translate-button") as HTMLButtonElement;
      const backButton = document.querySelector(".back-button") as HTMLButtonElement;
      const originalContent = document.querySelector(".original-content") as HTMLElement;
      const translatedContent = document.querySelector(".translated-content") as HTMLElement;
    
      translateButton.addEventListener("click", function () {
        originalContent.style.display = "none";
        translatedContent.classList.add("active");
      });
    
      backButton.addEventListener("click", function () {
        originalContent.style.display = "block";
        translatedContent.classList.remove("active");
      });
    });
  }

  @ViewChild('elementToScroll', { static: false }) elementToScroll: ElementRef;

  scrollToTop() {
    console.log(this.elementToScroll); // In ra để kiểm tra ElementRef
    if (this.elementToScroll && this.elementToScroll.nativeElement) {
      this.elementToScroll.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }
  checkSrcoll() {
    const scrollableDiv = document.getElementById('scrollableDiv')!;
    const scrollButton = document.getElementById('scrollButton')!;

    // Thêm sự kiện lắng nghe lướt cho thẻ div
    scrollableDiv.addEventListener('scroll', () => {
      // Kiểm tra vị trí cuộn
      if (scrollableDiv.scrollTop > 100) {
        // Hiển thị nút scroll khi cuộn đến vị trí cụ thể (ở đây là 100)
        scrollButton.style.display = 'block';
      } else {
        // Ẩn nút scroll nếu không đủ cuộn
        scrollButton.style.display = 'none';
      }
    });
  }
  toggleLike() {
    this.interactPostsService.toggleLike(this.postId);
  }

  isLiked(postId: string) {
    return this.interactPostsService.isLiked(postId);
  }

  isLogin(){
    return this.loginService.isLogin();
  }

  logout(){
    return this.loginService.logout();
  }

  // Modal
  openModalCreatePost() {
    this.modalService.openModalCreatePost();
  }
  closeModalCreatePost() {
    this.modalService.closeModalCreatePost();
  }
  openModalComment() {
    this.modalService.openModalComment();
  }
  closeModalComment() {
    this.modalService.closeModalComment();
  }
}