
document.getElementById("table_space").appendChild(getUserTable(["UserA", "UserB"]));
console.log("asdfg");

function getRow (user) {
    var userRow = document.createElement("tr");
    
    var nameCell = document.createElement("td");
    nameCell.innerHTML = user;
    userRow.appendChild(nameCell);

    var adminCell = document.createElement("td");
    adminCell.innerHTML = "Admin";
    userRow.appendChild(adminCell);

    return userRow;
}

function getUserTable (users) {
    var table = document.createElement("table");

    table.appendChild(getRow("Namen"));
    users.forEach(function(element) {
        table.appendChild(getRow(element));
    }, this);

    return table;
}