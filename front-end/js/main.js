let token = sessionStorage.getItem("token");

if (!token) {
   window.location.href = "../pages/login.html";
}

document.getElementById('logout-button').addEventListener('click', logout);

function logout(){
   sessionStorage.clear();
   location.reload();
}
