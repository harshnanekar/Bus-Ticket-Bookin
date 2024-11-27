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

