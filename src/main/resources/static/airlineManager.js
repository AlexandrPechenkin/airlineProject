const aircraftsFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },
    getAllAircrafts: async () => await fetch('api/aircraft'),
    // getUserByUsername: async () => await fetch(`api/name`),
    // getUserById: async (id) => await fetch(`api/users/` + id),
    addAircraft: async (aircraft) => await fetch('api/aircraft',
        {method: "POST", headers: aircraftsFetch.head, body: JSON.stringify(aircraft)})
    // updateUser: async (user) => await fetch(`api/users/`,
    //     {method: 'PUT', headers: userFetch.head, body: JSON.stringify(user)}),
    // deleteUserByID: async (id) => await fetch(`api/users/` + id,
    //     {method: 'DELETE', headers: userFetch.head})

}

getAircrafts()

function getAircrafts() {
    aircraftsFetch.getAllAircrafts()
        .then(res => res.json())
        .then(aircrafts => {
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
                        </tr>`
                )
            })
        }
    )
}

function addAircraftData() {
    document.addEventListener('DOMContentLoaded', addAircraftData)
    let brand = document.getElementById('addBrand').value
    let model = document.getElementById('addModel').value
    let boardNumber = document.getElementById('addBoardNumber').value
    let productionYear = document.getElementById('addProductionYear').value
    let flyingRange = document.getElementById('addFlyingRange').value

    let aircraft = {
        brand: brand,
        model: model,
        boardNumber: boardNumber,
        productionYear: productionYear,
        flyingRange: flyingRange
    }

    aircraftsFetch.addAircraft(aircraft).then(() => {
        document.getElementById('addBrand').value = ``
        document.getElementById('addModel').value = ``
        document.getElementById('addBoardNumber').value = ``
        document.getElementById('addProductionYear').value = ``
        document.getElementById('addFlyingRange').value = ``

        document.getElementById('tableAircrafts').innerHTML = ``

        getAircrafts()
    })

    $('#aircraftstable-tab').tab('show')

}

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