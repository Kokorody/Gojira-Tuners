// Toggle class active for hamburger menu
const navbarNav = document.querySelector('nav ul');

document.querySelector('#hamburger-menu').onclick = (event) => {
    event.preventDefault(); // Prevent default action of the anchor tag
    navbarNav.classList.toggle('active');
};

// SLIDE SHOW //
const slideshows = {
    brands: {
        slideIndex: 1,
        timer: null,
        containerClass: "brands-slideshow",
    },
    r32: {
        slideIndex: 1,
        timer: null,
        containerClass: "r32-slideshow",
    },
    r33: {
        slideIndex: 1,
        timer: null,
        containerClass: "r33-slideshow",
    },
    r34: {
        slideIndex: 1,
        timer: null,
        containerClass: "r34-slideshow",
    },
    r35: {
        slideIndex: 1,
        timer: null,
        containerClass: "r35-slideshow",
    },
};

function plusSlides(n, slideshowName) {
    const slideshow = slideshows[slideshowName];
    showSlides(slideshow.slideIndex += n, slideshowName);
    resetTimer(slideshowName);
}

function currentSlide(n, slideshowName) {
    const slideshow = slideshows[slideshowName];
    showSlides(slideshow.slideIndex = n, slideshowName);
    resetTimer(slideshowName);
}

function showSlides(n, slideshowName) {
    const slideshow = slideshows[slideshowName];
    let i;
    const slides = document.querySelectorAll(`.${slideshow.containerClass} .mySlides`);
    const dots = document.querySelectorAll(`.${slideshow.containerClass} .dot`);

    if (n > slides.length) {
        slideshow.slideIndex = 1;
    }

    if (n < 1) {
        slideshow.slideIndex = slides.length;
    }

    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }

    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }

    slides[slideshow.slideIndex - 1].style.display = "block";
    if(dots.length > 0) {
        dots[slideshow.slideIndex - 1].className += " active";
    }
}

function showSlidesAutomatically(slideshowName) {
    if (slideshowName === 'brands') {
        const slideshow = slideshows[slideshowName];
        slideshow.slideIndex++;
        showSlides(slideshow.slideIndex, slideshowName);
        slideshow.timer = setTimeout(() => showSlidesAutomatically(slideshowName), 5000); // Change image every 5 seconds
    }
}

function resetTimer(slideshowName) {
    const slideshow = slideshows[slideshowName];
    clearTimeout(slideshow.timer);
    slideshow.timer = setTimeout(() => showSlidesAutomatically(slideshowName), 5000);
}

// Initialize slideshows
Object.keys(slideshows).forEach(name => {
    showSlides(slideshows[name].slideIndex, name);
    resetTimer(name);
});

document.addEventListener('DOMContentLoaded', () => {
  const dropdownTrigger = document.querySelector('.gojira-dropdown > a');
  const dropdownMenu = document.querySelector('.gojira-dropdown-menu');

  if (dropdownTrigger && dropdownMenu) {
      dropdownTrigger.addEventListener('click', (e) => {
          e.preventDefault(); // Prevent default anchor behavior

          // Toggle the 'show' class to trigger the animation
          dropdownMenu.classList.toggle('show');
      });

      // Close the dropdown if clicking outside of it
      document.addEventListener('click', (e) => {
          if (!dropdownTrigger.contains(e.target) && !dropdownMenu.contains(e.target)) {
              dropdownMenu.classList.remove('show');
          }
      });
  }
});

document.querySelector('#hamburger-menu').onclick = (event) => {
  event.preventDefault(); // Prevent default action of the anchor tag
  navbarNav.classList.toggle('active');
};

// Hide the hamburger menu when clicking outside of it
const hm = document.querySelector('#hamburger-menu');

document.addEventListener('click', function(e) {
  if (!hm.contains(e.target) && !navbarNav.contains(e.target)) {
      navbarNav.classList.remove('active');
  }
});
