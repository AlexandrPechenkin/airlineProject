const aircraftsFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },
    // getAllAircrafts: async () => await fetch('api/aircraft')
    getAllAircrafts: async () => await fetch('http://localhost:8888/api/aircraft')
    // ,
    // getUserByUsername: async () => await fetch(`api/name`),
    // getUserById: async (id) => await fetch(`api/users/` + id),
    // addUser: async (user) => await fetch('api/users',
    //     {method: "POST", headers: userFetch.head, body: JSON.stringify(user)}),
    // updateUser: async (user) => await fetch(`api/users/`,
    //     {method: 'PUT', headers: userFetch.head, body: JSON.stringify(user)}),
    // deleteUserByID: async (id) => await fetch(`api/users/` + id,
    //     {method: 'DELETE', headers: userFetch.head})

}

// infoUser()
getAircrafts()

// function getRoles(list) {
//     let userRoles = [];
//     for (let role of list) {
//         if (role.name == "ADMIN") {
//             userRoles.push(" ADMIN");
//         }
//         if (role.name == "USER") {
//             userRoles.push(" USER");
//         }
//
//     }
//     let stringRoles = userRoles.join("  ");
//     return stringRoles;
// }

// function addRoles(values) {
//     let roles = [];
//     for (let role of values) {
//         if (role === "ADMIN") {
//             roles.push({id: 1, name: "ADMIN", authority: "ADMIN"});
//         }
//         if (role === "USER") {
//             roles.push({id: 2, name: "USER", authority: "USER"});
//         }
//     }
//     return roles;
// }

function getAircrafts() {
    aircraftsFetch.getAllAircrafts().then(
        res => {
            res.json().then(
                aircrafts => {
                    aircrafts.forEach(aircraft => {
                        document.querySelector('#tableAircrafts').insertAdjacentHTML('beforeend',
                            `<tr>
                                    <td>${aircraft.id}</td>
                                    <td>${aircraft.brand}</td>
                                    <td>${aircraft.model}</td>
                                    <td>${aircraft.boardNumber}</td>
                                    <td>${aircraft.productionYear}</td>
                                    <td>${aircraft.flyingRange}</td>
                                    <td>
                                    <button type="submit" onclick="editAircraft(${aircraft.id})"
                                    class="btn btn-info" data-toggle="modal" data-target="#editAircraft">Edit</button>
                                    </td>
                                    <td>
                                    <button type="submit" onclick="deleteAircraft(${aircraft.id})"
                                    class="btn btn-danger" data-toggle="modal" data-target="#deleteAircraft">Delete</button>
                                    </td>
                                </tr>`);
                    })
                }
            )
        }
    )
}

// function addUserData() {
//     document.addEventListener('DOMContentLoaded', addUserData);
//     let firstName = document.getElementById('addFirstName').value;
//     let lastName = document.getElementById('addLastName').value;
//     let age = document.getElementById('addAge').value;
//     let email = document.getElementById('addEmail').value;
//     let password = document.getElementById('addPassword').value;
//
//     let elm = document.getElementById('addRoles');
//     let values = [];
//     if (elm.multiple) {
//         for (let i = 0; i < elm.options.length; i ++) {
//             if (elm.options[i].selected)
//                 values.push(elm.options[i].value);
//         }
//     } else {
//         values.push(elm.value);
//     }
//
//     let user = {
//         firstName: firstName,
//         lastName: lastName,
//         age: age,
//         email: email,
//         password: password,
//         roles: addRoles(values)
//     };
//     console.log(user)
//     userFetch.addUser(user).then(() => {
//         document.getElementById('addFirstName').value = ``;
//         document.getElementById('addLastName').value = ``;
//         document.getElementById('addAge').value = ``;
//         document.getElementById('addEmail').value = ``;
//         document.getElementById('addPassword').value = ``;
//         document.getElementById('addRoles').value = ``;
//         document.getElementById('tableUsers').innerHTML = ``;
//         getUsers();
//     })
//
//     $('#usertable-tab').tab('show');
//
// }

// function editUser(id) {
//     userFetch.getUserById(id)
//         .then(res => {
//             res.json().then(user => {
//                 $('#editId').val(user.id)
//                 $('#editFirstName').val(user.firstName)
//                 $('#editLastName').val(user.lastName)
//                 $('#editAge').val(user.age)
//                 $('#editEmail').val(user.email)
//                 $('#editPassword').val("")
//                 $('#editRoles').val(getRoles(user.roles))
//             })
//         })
// }

// function updateUser() {
//     let id = document.getElementById('editId').value;
//     let firstName = document.getElementById('editFirstName').value;
//     let lastName = document.getElementById('editLastName').value;
//     let age = document.getElementById('editAge').value;
//     let email = document.getElementById('editEmail').value;
//     let password = document.getElementById('editPassword').value;
//
//     let elm = document.getElementById('editRoles');
//     let values = [];
//     if (elm.multiple) {
//         for (let i = 0; i < elm.options.length; i ++) {
//             if (elm.options[i].selected)
//                 values.push(elm.options[i].value);
//         }
//     } else {
//         values.push(elm.value);
//     }
//
//     let user = {
//         id: id,
//         firstName: firstName,
//         lastName: lastName,
//         age: age,
//         email: email,
//         password: password,
//         roles: addRoles(values)
//     };
//     console.log(user)
//     console.log(JSON.stringify(user))
//     userFetch.updateUser(user).then(() => {
//         document.getElementById('editId').value = ``;
//         document.getElementById('editFirstName').value = ``;
//         document.getElementById('editLastName').value = ``;
//         document.getElementById('editAge').value = ``;
//         document.getElementById('editEmail').value = ``;
//         document.getElementById('editPassword').value = ``;
//         document.getElementById('editRoles').value = ``;
//         document.getElementById('tableUsers').innerHTML = ``;
//         getUsers();
//         $('#editUser').modal('hide');
//
//     })
// }

// function deleteUser(id) {
//     userFetch.getUserById(id)
//         .then(res => {
//             res.json().then(user => {
//                 $('#deleteId').val(user.id)
//                 $('#deleteFirstName').val(user.firstName)
//                 $('#deleteLastName').val(user.lastName)
//                 $('#deleteAge').val(user.age)
//                 $('#deleteEmail').val(user.email)
//                 $('#deleteRoles').val(getRoles(user.roles))
//             })
//         })
// }

// function deleteUserById() {
//     let id = document.getElementById('deleteId').value;
//     userFetch.deleteUserByID(id).then(() => {
//         document.getElementById('deleteId').value = ``;
//         document.getElementById('deleteFirstName').value = ``;
//         document.getElementById('deleteLastName').value = ``;
//         document.getElementById('deleteAge').value = ``;
//         document.getElementById('deleteEmail').value = ``;
//         document.getElementById('deleteRoles').value = ``;
//         document.getElementById('tableUsers').innerHTML = ``;
//         getUsers();
//         $('#deleteUser').modal('hide');
//     });
// }

// function infoUser() {
//     userFetch.getUserByUsername()
//         .then(res => res.json())
//         .then(user => {
//             let stringRoles = getRoles(user.roles);
//             document.querySelector('#infoUser').innerHTML = `
//                 ${user.email} with roles: ${stringRoles}
//             `;
//             document.querySelector('#userInfoPanel').innerHTML = `
//             <tr>
//                 <td>${user.id}</td>
//                 <td>${user.firstName}</td>
//                 <td>${user.lastName}</td>
//                 <td>${user.age}</td>
//                 <td>${user.email}</td>
//                 <td>${stringRoles}</td>
//                 </tr>
//             `;
//         });
// }