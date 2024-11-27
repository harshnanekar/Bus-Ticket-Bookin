//Function To Handle Alert's
function showAlert(obj) {
  if (obj.alert == "success") {
    document.getElementById("toast-success").classList.remove("hidden");
    document.getElementById("toast-success-content").innerText = obj.title;
    setTimeout(() => {
      document.getElementById("toast-success").classList.add("hidden");
    }, 5000);
  } else if (obj.alert === "error") {
    document.getElementById("toast-danger").classList.remove("hidden");
    document.getElementById("toast-danger-content").innerText = obj.title;
    setTimeout(() => {
      document.getElementById("toast-danger").classList.add("hidden");
    }, 5000);
  } else {
    document.getElementById("toast-warning").classList.remove("hidden");
    document.getElementById("toast-warning-content").innerText = obj.title;
    setTimeout(() => {
      document.getElementById("toast-warning").classList.add("hidden");
    }, 5000);
  }
}
