if (!token) {
  window.location.href = "../pages/login.html";
} else {
  let col_map = {
    reimbId: "REIMB ID",
    reimbAmount: "REIMB AMOUNT",
    reimbSubmitted: "REIMB SUBMITTED",
    reimbResolved: "REIMB RESOLVED",
    reimbDescription: "REIMB DESCRIPTION",
    reimbReceipt: "REIMB RECEIPT",
    reimbAuthor: "REIMB AUTHOR",
    reimbResolver: "REIMB RESOLVER",
    reimbStatusId: "REIMB STATUS ID",
    reimbTypeId: "REIMB TYPE ID",
    ersUserId: "USER ID",
    ersUsername: "USERNAME",
    ersPassword: "PASSWORD",
    userFirstName: "FIRST NAME",
    userLastName: "LAST NAME",
    userEmail: "EMAIL",
    userRoleId: "ROLE ID",
  };

  document
    .getElementById("myReimbursements")
    .addEventListener("click", tableMyReimbursements);

  document
    .getElementById("pending")
    .addEventListener("click", tableMyPendingReimbursements);

  document
    .getElementById("resolved")
    .addEventListener("click", tableMyResolvedReimbursements);

  document
    .getElementById("viewMyAccount")
    .addEventListener("click", getMyAccount);

  document
    .getElementById("updateMyAccount")
    .addEventListener("click", setAccountForUpdate);

  document
    .getElementById("fileReimbursement")
    .addEventListener("click", fileReimbursement);

  let reimbUrl = "http://localhost:7000/reimbursements";
  let empUrl = "http://localhost:7000/ersUsers";
  let empId = token.split(":")[0];

  async function commitUpdates() {
    let data = collectUpdates();
    if (Object.keys(data).length == 5) {
      let response = await fetch(`${empUrl}/${empId}`, {
        method: "PUT",
        headers: {
          Authorization: token,
        },
        body: JSON.stringify(data),
      });
      if (response.status == 200) {
        window.location.reload();
        document.getElementById("error-div").innerHTML =
          "Successfully submit reimbursement.";
      }
    } else {
      let userInput = "?reimbAuthId=" + empId;
      let response = await fetch(`${reimbUrl}${userInput}`, {
        method: "POST",
        headers: {
          Authorization: token,
        },
        body: JSON.stringify(data),
      });
      if (response.status == 200) {
        document.getElementById("error-div").innerHTML =
          "Successfully submit reimbursement.";
        window.location.reload();
      }
    }
  }

  function collectUpdates() {
    let cells = document.getElementById("updatable").rows[1].cells;
    let str = "";
    for (let i = 0; i < cells.length; i++) {
      str = str + cells.item(i).innerHTML + ":";
    }
    if (typeof Storage !== "undefined") {
      sessionStorage.setItem("update", str);
    }
    let savedUpdate = sessionStorage.getItem("update");
    let usersTh = [
      "ersUsername",
      "ersPassword",
      "userFirstName",
      "userLastName",
      "userEmail",
    ];
    let reimbTh = [
      "reimbAmount",
      "reimbSubmitted",
      "reimbResolved",
      "reimbDescription",
      "reimbReceipt",
      "reimbAuthor",
      "reimbResolver",
      "reimbStatusId",
      "reimbTypeId",
    ];
    let th = [];
    if (savedUpdate.slice(0, -1).split(":").length > 5) {
      th = reimbTh;
    } else {
      th = usersTh;
    }
    let tr = savedUpdate.slice(0, -1).split(":");
    let result = tr.reduce(function (result, field, index) {
      result[th[index]] = field;
      return result;
    }, {});
    return result;
  }

  async function setAccountForUpdate() {
    let response = await fetch(`${empUrl}/${empId}`);
    if (response.status >= 200 && response.status < 300) {
      data = await response.json();
      delete data.ersUserId;
      delete data.userRoleId;
      populateData(data);
      if (!document.getElementById("editBtn"))
        editBtn = document.createElement("button");
      editBtn.setAttribute("class", "btn btn-outline-primary");
      editBtn.setAttribute("id", "editBtn");
      editBtn.innerHTML = "Save Changes";
      document.body.appendChild(editBtn);
      document
        .getElementById("editBtn")
        .addEventListener("click", commitUpdates);
    }
  }

  function populateData(response) {
    // document.getElementById("newButton").innerHTML="";
    function generateTableHead(table, data) {
      let thead = table.createTHead();
      let row = thead.insertRow();
      for (let key of data) {
        let th = document.createElement("th");
        if (Object.keys(col_map).includes(key)) {
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
    table.setAttribute("class", "table table-hover");
    table.setAttribute("id", "updatable");
    if (!Array.isArray(response)) {
      let listResponse = [];
      listResponse.push(response);
      let data1 = Object.keys(listResponse[0]);
      generateTableHead(table, data1);
      generateTable(table, listResponse);
    } else {
      let data = Object.keys(response[0]);
      generateTableHead(table, data);
      generateTable(table, response);
    }
  }

  function convertDate(miliseconds) {
    let oldFormat = new Date(miliseconds).toLocaleDateString();
    let dateList = oldFormat.split("/");
    let month = dateList[0];
    let day = dateList[1];
    let year = dateList[2];
    let newFormat = year + "-";
    if (month.length < 2) {
      newFormat = newFormat + "0" + month + "-";
    } else {
      newFormat = newFormat + month + "-";
    }
    if (day.length < 2) {
      newFormat = newFormat + "0" + day;
    } else {
      newFormat = newFormat + day;
    }
    return newFormat;
  }

  function fileReimbursement() {
    let newReimbursement = {
      reimbAmount: 0,
      reimbSubmitted: convertDate(+new Date()),
      reimbResolved: null,
      reimbDescription: null,
      reimbReceipt: null,
      reimbAuthor: empId,
      reimbResolver: null,
      reimbStatusId: 1,
      reimbTypeId: 0,
    };
    populateData(newReimbursement);
    if (!document.getElementById("editBtn"))
      editBtn = document.createElement("button");
    editBtn.setAttribute("class", "btn btn-outline-primary");
    editBtn.setAttribute("id", "editBtn");
    editBtn.innerHTML = "Save Changes";
    document.body.appendChild(editBtn);
    console.log("button created");
    document.getElementById("editBtn").addEventListener("click", commitUpdates);
  }

  function tableMyReimbursements() {
    // document.getElementById("newButton").innerHTML="";
    let authId = empId;
    let data = getReimbursementsByAuthorId(authId);
  }

  function tableMyResolvedReimbursements() {
    // document.getElementById("newButton").innerHTML="";
    let authId = empId;
    let status = "resolved";
    getMyReimbursementsByStatus(authId, status);
  }

  function tableMyPendingReimbursements() {
    // document.getElementById("newButton").innerHTML="";
    let status = "pending";
    let authId = empId;
    getMyReimbursementsByStatus(authId, status);
  }

  async function getMyAccount() {
    id = empId;
    let response = await fetch(`${empUrl}/${id}`);
    if (response.status >= 200 && response.status < 300) {
      let data = await response.json();
      populateData(data);
    }
  }

  async function getMyReimbursementsByStatus(authId, status) {
    let reimbs = [];
    authorId = empId;
    let userInput = "?reimbAuthId=" + authorId;
    let response = await fetch(`${reimbUrl}${userInput}`);
    if (response.status >= 200 && response.status < 300) {
      let data = await response.json();
      if (data != null) {
        for (const reimb of data) {
          reimb.reimbSubmitted = new Date(
            reimb.reimbSubmitted
          ).toLocaleDateString();
          if (reimb.reimbResolved != null) {
            reimb.reimbResolved = new Date(
              reimb.reimbResolved
            ).toLocaleDateString();
          }
          if (status == "pending" && reimb.reimbStatusId == 1) {
            reimbs.push(reimb);
          } else if (status == "resolved" && reimb.reimbStatusId != 1) {
            reimbs.push(reimb);
          }
        }
        populateData(reimbs);
      }
    }
  }

  async function getReimbursementsByAuthorId(authorId) {
    let reimbs = [];
    authorId = empId;
    let userInput = "?reimbAuthId=" + authorId;
    let response = await fetch(`${reimbUrl}${userInput}`);
    if (response.status >= 200 && response.status < 300) {
      let data = await response.json();
      if (data != null) {
        for (const reimb of data) {
          reimb.reimbSubmitted = new Date(
            reimb.reimbSubmitted
          ).toLocaleDateString();
          if (reimb.reimbResolved != null) {
            reimb.reimbResolved = new Date(
              reimb.reimbResolved
            ).toLocaleDateString();
          }
          reimbs.push(reimb);
          populateData(reimbs);
        }
      }
    }

    async function getAllReimbursements() {
      let reimbs = [];
      let response = await fetch(reimbUrl);
      if (response.status >= 200 && response.status < 300) {
        let data = await response.json();
        if (data != null) {
          for (const reimb of data) {
            reimb.reimbSubmitted = new Date(
              reimb.reimbSubmitted
            ).toLocaleDateString();
            if (reimb.resolved != null) {
              reimb.reimbResolved = new Date(
                reimb.reimbSubmitted
              ).toLocaleDateString();
            }
            reimbs.push(reimb);
          }
          return reimbs;
        }
      }
    }
  }
}
