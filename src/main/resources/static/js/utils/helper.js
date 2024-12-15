//Collapsable Sidebar
document.getElementById("header-btn").addEventListener("click", () => {
  let style = getComputedStyle(document.body);
  let asideValue = style.getPropertyValue("--sidebar-width");

  if (asideValue === "300px") {
    document.documentElement.style.setProperty("--sidebar-width", "0px");
  } else {
    document.documentElement.style.setProperty("--sidebar-width", "300px");
  }
});

//Set Sidebar Active
let items = document.querySelectorAll(".sidebar-items li a");
console.log(location.pathname);
items.forEach((item) => {
  let path = item.getAttribute("data-sidebar");
  if (location.pathname === path) {
    item.classList.add("active");
  }
});

//Responsive Sidebar
window.addEventListener("resize", function () {
  if (window.innerWidth < 786) {
    document.documentElement.style.setProperty("--sidebar-width", "0px");
  } else if (window.innerWidth <= 1000 && window.innerWidth > 786) {
    document.documentElement.style.setProperty("--sidebar-width", "200px");
  } else {
    document.documentElement.style.setProperty("--sidebar-width", "300px");
  }
});

function toggleDropdown(event) {
  // Close all other dropdowns
  document.querySelectorAll(".dropdown").forEach((dropdown) => {
    dropdown.classList.add("hidden");
  });

  // Toggle the dropdown next to the clicked button
  const button = event.currentTarget;
  const dropdown = button.nextElementSibling;

  if (dropdown && dropdown.classList.contains("dropdown")) {
    dropdown.classList.toggle("hidden");
  }
}

document.addEventListener("click", function (event) {
  const dropdowns = document.querySelectorAll(".dropdown");
  dropdowns.forEach((dropdown) => {
    if (
      !dropdown.contains(event.target) &&
      !dropdown.previousElementSibling.contains(event.target)
    ) {
      dropdown.classList.add("hidden");
    }
  });
});

function showLoader(value) {
  if (value) {
    document.querySelector(".loader").classList.remove("hidden");
  } else {
    document.querySelector(".loader").classList.add("hidden");
  }
}


function toggleAccordion() {
  const content = document.getElementById(`content-2`);
  const icon = document.getElementById(`icon-2`);

  // SVG for Minus icon
  const minusSVG = `
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4">
      <path d="M3.75 7.25a.75.75 0 0 0 0 1.5h8.5a.75.75 0 0 0 0-1.5h-8.5Z" />
    </svg>
  `;

  // SVG for Plus icon
  const plusSVG = `
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4">
      <path d="M8.75 3.75a.75.75 0 0 0-1.5 0v3.5h-3.5a.75.75 0 0 0 0 1.5h3.5v3.5a.75.75 0 0 0 1.5 0v-3.5h3.5a.75.75 0 0 0 0-1.5h-3.5v-3.5Z" />
    </svg>
  `;

  // Toggle the content's max-height for smooth opening and closing
  if (content.style.maxHeight && content.style.maxHeight !== '0px') {
    content.style.maxHeight = '0';
    icon.innerHTML = plusSVG;
    document.querySelector(".accordion-btn").classList.remove("border-b");
    document.querySelector(".accordion-btn").classList.remove("border-slate-200");
    document.querySelector(".accordion-btn").classList.remove("bg-[#EAF1FB]");
  } else {
    content.style.maxHeight = content.scrollHeight + 'px';
    icon.innerHTML = minusSVG;
    document.querySelector(".accordion-btn").classList.add("border-b");
    document.querySelector(".accordion-btn").classList.add("border-slate-200");
    document.querySelector(".accordion-btn").classList.add("bg-[#EAF1FB]");
  }
}


function toggleDroppingAccordion() {
  const content = document.getElementById(`content-3`);
  const icon = document.getElementById(`icon-3`);

  // SVG for Minus icon
  const minusSVG = `
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4">
      <path d="M3.75 7.25a.75.75 0 0 0 0 1.5h8.5a.75.75 0 0 0 0-1.5h-8.5Z" />
    </svg>
  `;

  // SVG for Plus icon
  const plusSVG = `
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4">
      <path d="M8.75 3.75a.75.75 0 0 0-1.5 0v3.5h-3.5a.75.75 0 0 0 0 1.5h3.5v3.5a.75.75 0 0 0 1.5 0v-3.5h3.5a.75.75 0 0 0 0-1.5h-3.5v-3.5Z" />
    </svg>
  `;

  // Toggle the content's max-height for smooth opening and closing
  if (content.style.maxHeight && content.style.maxHeight !== '0px') {
    content.style.maxHeight = '0';
    icon.innerHTML = plusSVG;
    document.querySelector(".dropping-btn").classList.remove("border-b");
    document.querySelector(".dropping-btn").classList.remove("border-slate-200");
    document.querySelector(".dropping-btn").classList.remove("bg-[#EAF1FB]");
  } else {
    content.style.maxHeight = content.scrollHeight + 'px';
    icon.innerHTML = minusSVG;
    document.querySelector(".dropping-btn").classList.add("border-b");
    document.querySelector(".dropping-btn").classList.add("border-slate-200");
    document.querySelector(".dropping-btn").classList.add("bg-[#EAF1FB]");
  }
}
