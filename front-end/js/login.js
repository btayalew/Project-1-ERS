let token = sessionStorage.getItem("token");

function login(){
    document.getElementById("error-div").innerHTML = "";

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let xhr = new XMLHttpRequest();
    
    xhr.open("POST", "http://localhost:7000/auth");

    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            let authToken = xhr.getResponseHeader("Authorization");
            sessionStorage.setItem("token", authToken);
            if(authToken.split(':')[1] == 2){
                window.location.href="managers.html";
            }else{
                window.location.href="employees.html";
            }
            
        } else if (xhr.readyState === 4){
            document.getElementById("error-div").innerHTML = "Unable to login.";
        }
    } 

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    let requestBody = `username=${username}&password=${password}`;
    xhr.send(requestBody);
}


