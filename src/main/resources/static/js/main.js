const requestURL = 'http://localhost:8080/admin/users/';

// html-блок, локальная таблица всех пользователей
let usersTable = '';

// html-блок, локальная таблица текущего пользователя (самого себя)
let singleUserTable = '';

// html-блок из option-ов, все возможные роли с сервера
let roleOptions = '';

// json object, все возможные роли с сервера
let roles;


// -----------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------
// МЕТОДЫ ДЛЯ ЗАПРОСОВ К СЕРВЕРУ
// -----------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------

// Просто GET-запрос
function sendGet(url) {
    return fetch(url, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
}

// Просто POST-запрос
function sendPost(url, data = null) {
    return fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
}

// Просто PUT-запрос
function sendPut(url, data = null) {
    return fetch(url, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
}

// Просто DELETE-запрос
function sendDelete(url) {
    return fetch(url, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    })
}

// -----------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------
// ПОЛУЧЕНИЕ РЕСУРСОВ И СБОРКА СТРАНИЦЫ
// -----------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------

// Превращаем объект юзера в его строку в таблице
function jsonToRow(jsonObject) {
    // let newRow = document.createElement("tr");
    // newRow.id = jsonObject.id
    let newRow =
        "<tr id='tr"+jsonObject.id+"'>" +
        "<td>" + jsonObject.id + "</td>" +
        "<td>" + jsonObject.firstName + "</td>" +
        "<td>" + jsonObject.lastName + "</td>" +
        "<td>" + jsonObject.age + "</td>" +
        "<td>" + jsonObject.username + "</td>" +
        "<td>" + rolesToString(jsonObject.roles) + "</td>" +
        "<td><button class='btn btn-info eBtn' data-toggle='modal' onclick='editUserModal("+jsonObject.id+")'>Edit</button></td>" +
        "<td><button class='btn btn-danger eBtn' data-toggle='modal' onclick='deleteUserModal("+jsonObject.id+")' id='deleteButton'>Delete</button></td>"+
        "</tr>";
    return newRow;
}

// Заполнение таблицы пользователей
// json object -> html-блок -> в переменную usersTable
function fetchUsersToTable(data) {
    usersTable = document.getElementById("allUsersTableFetch")
    data.forEach((dataItem) => {
        usersTable.insertAdjacentHTML('afterBegin', jsonToRow(dataItem))
        // usersTable.append(jsonToRow(dataItem))
    })
}

// Пользовательская панель: наполняем таблицу текущего пользователя (себя)
function fetchYourselfToTable(data) {
    singleUserTable = document.getElementById("singleUserTableFetch")
    //эту строку надо будет убрать
    singleUserTable.innerHTML = ''
    let userRow = document.createElement("tr");
    userRow.innerHTML =
        "<td>" + data.id + "</td>" +
        "<td>" + data.firstName + "</td>" +
        "<td>" + data.lastName + "</td>" +
        "<td>" + data.age + "</td>" +
        "<td>" + data.username + "</td>" +
        "<td>" + rolesToString(data.roles) + "</td>";
    singleUserTable.append(userRow)
}

// Запуск и получение нужных ресурсов
function onStartup() {
    $(document).ready(
        () => {
            // Все пользователи с сервера
            // json object -> пихаем в метод заполнения таблицы пользователей
            sendGet(requestURL).then(response => fetchUsersToTable(response))
            // Текущий пользователь с сервера
            // json object
            sendGet(requestURL + 'current').then(response => fetchYourselfToTable(response))
            // Все возможные роли с сервера
            // json object -> в переменную roles
            sendGet(requestURL + 'roles').then(data => {
                roles = data
            })
            // Все возможные роли с сервера
            // html-блок из option-ов -> в переменную roleOptions
            roleOptions = '';
            sendGet(requestURL + 'roles').then(response => {
                response.forEach((role) => {
                    roleOptions += "<option id="
                    roleOptions += role.id
                    roleOptions += " class=\"optionItem"
                    roleOptions += role.id
                    roleOptions += "\">"
                    roleOptions += role.name
                    roleOptions += "</option>"
                })
            })
            // Отрисовка и заполнение статус-бара
            sendGet(requestURL + 'current').then(response => {
                    let headerInfoLine = document.getElementById("headerInfoLine");
                    headerInfoLine.innerHTML = ''
                    let headerBlock = document.createElement("div")
                    headerBlock.className = "row"
                    headerBlock.innerHTML =
                        "<div class='col-md-9 text-left bg-dark text-light align-middle pt-2'>" + response.username + ' with roles: ' + rolesToString(response.roles) + "</div>" +
                        "<div class='col-md-3 text-right bg-dark'><form method='POST' action='/logout'><button class='btn btn-link text-muted text-decoration-none' type='submit'>Logout</button></form></div>";
                    headerInfoLine.append(headerBlock)
                })
        })
}

// -----------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------
// CRUD-ОПЕРАЦИИ (ЛОКАЛЬНО)
// -----------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------


// Админ панель: модальное окно удаления пользователя - список ролей юзера
function rolesToString(roles) {
    let rolesAsString = '';
    roles.forEach((singleRole) => {
        rolesAsString += singleRole.name + " ";
    })
    return rolesAsString;
}

// Админ панель: модальное окно создания пользователя
function newUserModal() {
    $("#newUserModal").modal('show')
    let roleSelector = document.getElementById("newModalRoleSelector")
    roleSelector.innerHTML = roleOptions
}

// Создаём пользователя
function newUser() {
    let rolesArray = []
    let allOptions = document.getElementById("newModalRoleSelector").options
    let body = {}
    for (let i = 0; i < allOptions.length; i++) {
        if (allOptions[i].selected) {
            rolesArray.push({
                id: allOptions[i].id,
                name: allOptions[i].value
            })
        }
    }
    body = {
        firstName: $("#newUserFirstName").val(),
        lastName: $("#newUserLastName").val(),
        age: $("#newUserAge").val(),
        username: $("#newUserEmail").val(),
        password: $("#newUserPassword").val(),
        roles: rolesArray
    }
    sendPost(requestURL, body)
        .then(() => {
            document.getElementById("allUsersTableFetch").innerHTML = ''
            onStartup()
            //Код не подходит, так как не содержит айдишника
            // const addedUser = jsonToRow(body)
            // usersTable.append(addedUser)
            // fetchUsersToTable(usersTable)
        })
        .then(clearNewUserForms())
    // .then($("#newUserModal").tab('hide'))
    // .then($("#showAllUsers").tab('show'))

}

// Чистим поля модальное окна создания пользователя
function clearNewUserForms() {
    $("#newUserFirstName").val("");
    $("#newUserLastName").val("");
    $("#newUserAge").val("");
    $("#newUserEmail").val("");
    $("#newUserPassword").val("");
}

// Админ панель: модальное окно редактирования пользователя
function editUserModal(userId) {
    $("#editUserModal").modal('show');
    sendGet(requestURL + userId)
        .then(response => {
            return response;
        })
        .then(response => {
                $("#editId").val(response.id)
                $("#editFirstName").val(response.firstName)
                $("#editLastName").val(response.lastName)
                $("#editAge").val(response.age)
                $("#editEmail").val(response.username)
                $("#editPassword").val(response.password)
                let roleSelector = document.getElementById("editModalRoleSelector")
                roleSelector.innerHTML = roleOptions
            }
        )
}

// Редактируем пользователя
function editUser() {
    let rolesArray = []
    let body = {}
    let allOptions = document.getElementById("editModalRoleSelector").options
    for (let i = 0; i < allOptions.length; i++) {
        if (allOptions[i].selected) {
            rolesArray.push({
                id: allOptions[i].id,
                name: allOptions[i].value
            })
        }
    }
    body = {
        id: $("#editId").val(),
        firstName:  $("#editFirstName").val(),
        lastName:   $("#editLastName").val(),
        age:        $("#editAge").val(),
        username:   $("#editEmail").val(),
        password:   $("#editPassword").val(),
        roles: rolesArray
    }
        sendPut(requestURL, body)
        .then(() => {
            let userId = parseInt(document.getElementById("editId").value)
            $('#tr' + userId).replaceWith(jsonToRow(body))
        })
        .then($("#editUserModal").modal('hide'))
}

// Удаляем пользователя
function deleteUser() {
    const idOfDeletedUser = $("#deleteId").val();
    sendDelete(requestURL + idOfDeletedUser)
        .then(() => {
            let deletedUserId = parseInt(document.getElementById("deleteId").value)
            $('#tr' + deletedUserId).remove()
        })
        .then($("#deleteUserModal").modal('hide'))
}

// Админ панель: модальное окно удаления пользователя
function deleteUserModal(userId) {
    // let id = event.target.parentNode.parentNode.id;
    $("#deleteUserModal").modal('show');
    sendGet(requestURL + userId)
        .then(response => {
            $("#deleteId").val(response.id),
                $("#deleteFirstName").val(response.firstName),
                $("#deleteLastName").val(response.lastName),
                $("#deleteAge").val(response.age),
                $("#deleteEmail").val(response.username),
                $("#deletePassword").val(response.password),
                $("#deleteRoles").val(rolesToString(response.roles))
        })
}


onStartup()