if (!token) {
  window.location.href = '../pages/login.html';
}else if(token.split(":")[1] == 1){
  window.location.href = '../pages/employees.html';
}else{
  
  let reimbUrl = 'http://localhost:7000/reimbursements';
  let empUrl = 'http://localhost:7000/ersUsers';
  let empId = token.split(":")[0];

  let col_map = {
      reimbId:"REIMB ID",
      reimbAmount:"REIMB AMOUNT",
      reimbSubmitted:"REIMB SUBMITTED",
      reimbResolved:"REIMB RESOLVED",
      reimbDescription:"REIMB DESCRIPTION",
      reimbReceipt:"REIMB RECEIPT",
      reimbAuthor:"REIMB AUTHOR",
      reimbResolver:"REIMB RESOLVER",
      reimbStatusId:"REIMB STATUS ID",
      reimbTypeId:"REIMB TYPE ID",
      ersUserId:"USER ID",
      ersUsername:"USERNAME",
      ersPassword:"PASSWORD",
      userFirstName:"FIRST NAME",
      userLastName: "LAST NAME",
      userEmail: "EMAIL",
      userRoleId: "ROLE ID"
    }

    document.getElementById('viewAllReimbursements')
    .addEventListener("click", getAllReimbursements);

    document.getElementById('underReview')
    .addEventListener("click", getPendingReimbursements);

    document.getElementById('approved')
    .addEventListener("click", getApprovedReimbursements);

    document.getElementById('rejected')
    .addEventListener("click", getRejectedReimbursements);

    document.getElementById('reimbursementAuthorId')
    .addEventListener("input", getReimbursementByAuthorId);

    document.getElementById('reimbursementToReview')
    .addEventListener("input", setReimbursementForUpdate);

    document.getElementById('viewAllEmployees')
    .addEventListener("click", getAllEmployees);

    document.getElementById('viewEmployeeById')
    .addEventListener("input", getEmployeeById);

    async function commitUpdates(){
      let data = collectUpdates();
      let reimbId = document.getElementById('reimbursementToReview').value;
      let response = await fetch(`${reimbUrl}/${reimbId}`, {
          method:'PUT',
          headers:{
            "Authorization":token
          },
          body: JSON.stringify(data)
        });
        document.getElementById('error-div').innerHTML= "Update was successful!";
      if(response.status == 200){
        
        window.location.reload();
        
        } else {
          
      }
    }

    function collectUpdates() {
      let cells = document.getElementById("updatable").rows[1].cells;
      let str = "";
      for(let i=0; i<cells.length; i++){
        str = str + cells.item(i).innerHTML + ":";
      }      
      if(typeof(Storage) !== "undefined"){
        sessionStorage.setItem("update", str);
      }
      let savedUpdate = sessionStorage.getItem("update");
      let th = ["reimbId","reimbAmount","reimbResolved","reimbReceipt","reimbResolver","reimbStatusId","reimbTypeId"]    
      let tr = savedUpdate.slice(0,-1).split(":");
      let result =  tr.reduce(function(result, field, index) {
          result[th[index]] = field;
          return result;
        }, {})
      return result;
    }
    
    async function setReimbursementForUpdate() {
      let reimbId = document.getElementById('reimbursementToReview').value;
      let response = await fetch(`${reimbUrl}/${reimbId}`); 
      if(response.status >= 200 && response.status < 300){
       
          data = await response.json();
          // delete data.reimbId;
          delete data.reimbSubmitted;
          delete data.reimbDescription;
          delete data.reimbAuthor;
          if(data.reimbResolved != null){
            data.reimbResolved= convertDate(data.reimbResolved);
          }
          populateData(data);
          editBtn = document.createElement("button");
          editBtn.setAttribute("class","btn btn-outline-primary");
          editBtn.setAttribute("id", "editBtn");
          editBtn.innerHTML = 'Save Changes';
          document.body.appendChild(editBtn);
          document.getElementById('editBtn')
          .addEventListener("click", commitUpdates);
      }
    }

    function populateData(response) {
      if(document.getElementById('editBtn')){
        document.getElementById('editBtn').remove();
      }
      function generateTableHead(table, data) {
          let thead = table.createTHead();
          let row = thead.insertRow();
          for (let key of data) {
            let th = document.createElement("th");
            if(Object.keys(col_map).includes(key)){
              let text = document.createTextNode(col_map[key]);
              th.appendChild(text);
              row.appendChild(th);
            }         
          }
        }

      function generateTable(table, data) {
        for (let element of data) {
          let row = table.insertRow();
          for (key in element) {
            let cell = row.insertCell();
            let text = document.createTextNode(element[key]);
            cell.appendChild(text);
            cell.setAttribute("contenteditable", "true");
          }
        }
      } 
      let table = document.querySelector("table");
      table.innerHTML = "";
      table.setAttribute("class", "table table-hover")
      table.setAttribute("id", "updatable")
      if(!Array.isArray(response)){
        let listResponse = [];
        listResponse.push(response);
        let data1 = Object.keys(listResponse[0]);
        generateTableHead(table, data1);
        generateTable(table, listResponse);
        }else{
            let data = Object.keys(response[0]);
            generateTableHead(table, data);
            generateTable(table, response);
        }
    }

    async function getAllEmployees() {
      let response = await fetch(empUrl);
      if(response.status >= 200 && response.status < 300){
          let data = await response.json();
          populateData(data);
      }
    }

    async function getEmployeeById() {
      let userInput = document.getElementById('viewEmployeeById').value;
      let response = await fetch(`${empUrl}/${userInput}`);
      if(response.status >= 200 && response.status < 300){
          let data = await response.json();
          populateData(data);
      }
    }

    async function getAllReimbursements() {
      let reimbs = [];
      let response = await fetch(reimbUrl);
      if(response.status >= 200 && response.status < 300){
          let data = await response.json();
          if(data != null){
            for (const reimb of data) {
              reimb.reimbSubmitted = new Date(reimb.reimbSubmitted)
              .toLocaleDateString();
              if(reimb.resolved != null){
                reimb.reimbResolved = new Date(reimb.reimbSubmitted)
                .toLocaleDateString();
              }
              reimbs.push(reimb);
            }
            populateData(reimbs);
          }
      }
    }

    async function getPendingReimbursements() {
      let unerReview = [];
      let response = await fetch(reimbUrl);
      if(response.status >= 200 && response.status < 300){
        let data = await response.json();
        for (const reimb of data) {
          if(reimb != null && reimb.reimbStatusId == 1){ 
            reimb.reimbSubmitted = new Date(reimb.reimbSubmitted)
            .toLocaleDateString();
            if(reimb.reimbResolved !=null){
              reimb.reimbResolved = new Date(reimb.reimbResolved)
              .toLocaleDateString();
            }
            unerReview.push(reimb);
            populateData(unerReview);
          }
        }
      }
    }

    async function getApprovedReimbursements() {
      let approved = [];
      let response = await fetch(reimbUrl);
      if(response.status >= 200 && response.status < 300){
        let data = await response.json();
        for (const reimb of data) {
          if(reimb != null && reimb.reimbStatusId == 2){ 
            reimb.reimbSubmitted = new Date(reimb.reimbSubmitted)
            .toLocaleDateString();
            reimb.reimbResolved = new Date(reimb.reimbResolved)
            .toLocaleDateString();
            approved.push(reimb);
            populateData(approved);
          }
        }
      }
    }
    async function getRejectedReimbursements() {
      let rejected = [];
      let response = await fetch(reimbUrl);
      if(response.status >= 200 && response.status < 300){
        let data = await response.json();
        for (const reimb of data) {
          if(reimb != null && reimb.reimbStatusId == 3){ 
            reimb.reimbSubmitted = new Date(reimb.reimbSubmitted)
            .toLocaleDateString();
            reimb.reimbResolved = new Date(reimb.reimbResolved)
            .toLocaleDateString();
            rejected.push(reimb);
            populateData(rejected);
          }
        }
      }
    }

    async function getReimbursementByAuthorId() {
    
      let userInput = "?reimbAuthId=" + document.getElementById('reimbursementAuthorId').value;
      let response = await fetch(`${reimbUrl}${userInput}`);
    
      if(response.status >= 200 && response.status < 300){
          let data = await response.json();
          populateData(data);
      }
    }

    function convertDate(miliseconds){
      let oldFormat = new Date(miliseconds).toLocaleDateString();
      let dateList = oldFormat.split('/');
      let month = dateList[0];
      let day = dateList[1];
      let year = dateList[2];
      let newFormat = year+"-";
      if(month.length<2){
        newFormat=newFormat+"0"+month+"-";
      }else{
        newFormat=newFormat+month+"-";
      }
      if(day.length<2){
        newFormat=newFormat+"0"+day;
      }else{
        newFormat=newFormat+day;
      }
      return newFormat;
    }

  //   async function reviewReimbursement(){
  //     let reimbId = document.getElementById("reimbursementToReview").value;
  //     console.log(reimbId);
  //     let response = await fetch(`${reimbUrl}/${reimbId}`);
  //     if(response.status >= 200 && response.status < 300){
  //       let data = await response.json();
      
  //       populateData(data);
  //   }
  // }
}
