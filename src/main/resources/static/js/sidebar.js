function toggleSidebar() {
  const sidebar = document.getElementById("sidebar");
  const overlay = document.getElementById("sidebarOverlay");

  // Toggle the 'open' class on the sidebar
  sidebar.classList.toggle("open");

  // Toggle the overlay's visibility
  overlay.classList.toggle("show");
}

function closeSidebar() {
  const sidebar = document.getElementById("sidebar");
  const overlay = document.getElementById("sidebarOverlay");

  sidebar.classList.remove("open");
  overlay.classList.remove("show");
}
