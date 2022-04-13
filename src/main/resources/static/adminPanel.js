const adminsFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },

    getAllAdmins: async () => await fetch('api/admin'),
    getAdminById: async (id) => await fetch(`api/admin/` + id),
    addAdmin: async (admin) => await fetch('api/admin',
        {method: "POST", headers: adminsFetch.head, body: JSON.stringify(admin)}),
    updateAdmin: async (admin) => await fetch(`api/admin`,
        {method: 'PUT', headers: adminsFetch.head, body: JSON.stringify(admin)}),
    deleteAdminByID: async (id) => await fetch(`api/admin/` + id,
        {method: 'DELETE', headers: adminsFetch.head})
}


getAdmins()

function getAdmins() {
    adminsFetch.getAllAdmins()
        .then(res => res.json())
        .then(admins => {
                admins.forEach(admin => {

                    document.querySelector('#tableAdmins').insertAdjacentHTML('beforeend',
                        `<tr>

                            <td>${admin.id}</td>
                            <td>${admin.nickname}</td>
                            <td>${admin.email}</td>
                            <td>${admin.roles.map((role) => role.name).join(', ')}</td>
                            <td>
                            <button type="submit" onclick="editAdmin(${admin.id})"
                            class="btn btn-info" data-toggle="modal" data-target="#editAdmin">Edit</button>
                            </td>
                            <td>
                            <button type="submit" onclick="deleteAdmin(${admin.id})"
                            class="btn btn-danger" data-toggle="modal" data-target="#deleteAdmin">Delete</button>
                            </td>
                        </tr>`
                    )
                })
            }
        )
}


function addAdminData() {
    document.addEventListener('DOMContentLoaded', addAdminData)
    let nickname = document.getElementById('addNicknameAdmin').value
    let email = document.getElementById('addEmailAdmin').value
    let password = document.getElementById('addPasswordAdmin').value

    let roleId = $('#selector').val()

    roleId = roleId.map((role) => JSON.parse(role))

    let admin = {
        nickname: nickname,
        email: email,
        password: password,
        roles: roleId

    }

    adminsFetch.addAdmin(admin).then(() => {
        document.getElementById('addNicknameAdmin').value = ``
        document.getElementById('addEmailAdmin').value = ``
        document.getElementById('addPasswordAdmin').value = ``
        document.getElementById('selector').value = ``

        document.getElementById('tableAdmins').innerHTML = ``

        getAdmins()
    })

    $('#adminstable-tab').tab('show')

}


function editAdmin(id) {
    adminsFetch.getAdminById(id)
        .then(res => res.json())
        .then(admin => {
            $('#editId').val(admin.id)
            $('#editNickname').val(admin.nickname)
            $('#editEmail').val(admin.email)
        })
}

function updateAdmin() {
    let id = document.getElementById('editId').value
    let nickname = document.getElementById('editNickname').value
    let email = document.getElementById('editEmail').value
    let password = document.getElementById('editPassword').value
    let roleId = $('#editSelector').val()

    roleId = roleId.map((role) => JSON.parse(role))

    let admin = {
        id: id,
        nickname: nickname,
        email: email,
        password: password,
        roles: roleId
    }

    adminsFetch.updateAdmin(admin).then(() => {

        document.getElementById('editId').value = ``
        document.getElementById('editNickname').value = ``
        document.getElementById('editEmail').value = ``
        document.getElementById('editPassword').value = ``
        document.getElementById('editSelector').value = ``

        document.getElementById('tableAdmins').innerHTML = ``

        getAdmins()

        $('#editAdmin').modal('hide')
    })
}

function deleteAdmin(id) {
    adminsFetch.getAdminById(id)
        .then(res => res.json())
        .then(admin => {
            $('#deleteId').val(admin.id)
            $('#deleteNickname').val(admin.nickname)
            $('#deleteEmail').val(admin.email)
        })
}

function deleteAdminById() {
    let id = document.getElementById('deleteId').value

    adminsFetch.deleteAdminByID(id).then(() => {
        document.getElementById('deleteId').value = ``
        document.getElementById('deleteNickname').value = ``
        document.getElementById('deleteEmail').value = ``

        document.getElementById('tableAdmins').innerHTML = ``

        getAdmins()

        $('#deleteAdmin').modal('hide')
    })
}

const managersFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },

    getAllManagers: async () => await fetch('/api/airlineManager'),
    getManagerById: async (id) => await fetch(`/api/airlineManager/` + id),
    addManager: async (manager) => await fetch('/api/airlineManager',
        {method: "POST", headers: managersFetch.head, body: JSON.stringify(manager)}),
    updateManager: async (manager) => await fetch(`/api/airlineManager`,
        {method: 'PUT', headers: managersFetch.head, body: JSON.stringify(manager)}),
    deleteManagerByID: async (id) => await fetch(`/api/airlineManager/` + id,
        {method: 'DELETE', headers: managersFetch.head})
}

getManagers()

function getManagers() {
    managersFetch.getAllManagers()
        .then(res => res.json())
        .then(manager => {
                manager.forEach(manager => {

                    document.querySelector('#tableManagers').insertAdjacentHTML('beforeend',
                        `<tr>
                            <td>${manager.id}</td>
                            <td>${manager.email}</td>
                            <td>${manager.parkName}</td>
                            <td>${manager.roles.map((role) => role.name).join(', ')}</td>
                            <td>
                            <button type="submit" onclick="editManager(${manager.id})"
                            class="btn btn-info" data-toggle="modal" data-target="#editManager">Edit</button>
                            </td>
                            <td>
                            <button type="submit" onclick="deleteManager(${manager.id})"
                            class="btn btn-danger" data-toggle="modal" data-target="#deleteManager">Delete</button>
                            </td>
                        </tr>`
                    )
                })
            }
        )
}

function addManagerData() {
    document.addEventListener('DOMContentLoaded', addManagerData)
    let email = document.getElementById('addEmailManager').value
    let parkName = document.getElementById('addParkName').value
    let password = document.getElementById('addPasswordManager').value
    let roleId = $('#selectorManager').val()
    roleId = roleId.map((role) => JSON.parse(role))

    let manager = {
        email: email,
        parkName: parkName,
        password: password,
        roles: roleId
    }

    managersFetch.addManager(manager).then(() => {
        document.getElementById('addEmailManager').value = ``
        document.getElementById('addParkName').value = ``
        document.getElementById('addPasswordManager').value = ``

        document.getElementById('tableManagers').innerHTML = ``

        getManagers()
    })

    $('#managerstable-tab').tab('show')

}

function editManager(id) {
    managersFetch.getManagerById(id)
        .then(res => res.json())
        .then(manager => {
            $('#editManagerId').val(manager.id)
            $('#editEmailManager').val(manager.email)
            $('#editParkName').val(manager.parkName)
        })
}

function updateManager() {
    let id = document.getElementById('editManagerId').value
    let email = document.getElementById('editEmailManager').value
    let parkName = document.getElementById('editParkName').value
    let password = document.getElementById('editPasswordManager').value
    let roleId = $('#editSelectorManager').val()

    roleId = roleId.map((role) => JSON.parse(role))

    let manager = {
        id: id,
        email: email,
        parkName: parkName,
        password: password,
        roles: roleId

    }

    managersFetch.updateManager(manager).then(() => {
        document.getElementById('editManagerId').value = ``
        document.getElementById('editEmailManager').value = ``
        document.getElementById('editParkName').value = ``
        document.getElementById('editPasswordManager').value = ``

        document.getElementById('tableManagers').innerHTML = ``

        getManagers()

        $('#editManager').modal('hide')

    })
}

function deleteManager(id) {
    managersFetch.getManagerById(id)
        .then(res => res.json())
        .then(manager => {
            $('#deleteIdManager').val(manager.id)
            $('#deleteEmailManager').val(manager.email)
            $('#deleteParkName').val(manager.parkName)
        })
}

function deleteManagerById() {
    let id = document.getElementById('deleteIdManager').value

    managersFetch.deleteManagerByID(id).then(() => {
        document.getElementById('deleteIdManager').value = ``
        document.getElementById('deleteEmailManager').value = ``
        document.getElementById('deleteParkName').value = ``

        document.getElementById('tableManagers').innerHTML = ``

        getManagers()

        $('#deleteManager').modal('hide')
    })
}

const passengersFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },

    getAllPassengers: async () => await fetch('api/passenger'),
    getPassengerById: async (id) => await fetch(`api/passenger/` + id),
    addPassenger: async (passenger) => await fetch('api/passenger',
        {method: "POST", headers: passengersFetch.head, body: JSON.stringify(passenger)}),
    updatePassenger: async (passenger) => await fetch(`/api/passenger`,
        {method: 'PUT', headers: passengersFetch.head, body: JSON.stringify(passenger)}),
    deletePassengerById: async (id) => await fetch(`api/passenger/` + id,
        {method: 'DELETE', headers: passengersFetch.head})
}

getPassengers()

function getPassengers() {
    passengersFetch.getAllPassengers()
        .then(res => res.json())
        .then(passengers => {
                passengers.forEach(passenger => {


                    document.querySelector('#tablePassengers').insertAdjacentHTML('beforeend',
                        `<tr>
                            <td>${passenger.id}</td>
                            <td>${passenger.email}</td>
                            <td>${passenger.passport.firstName}</td>
                            <td>${passenger.passport.lastName}</td>
                            <td>${passenger.passport.middleName}</td>
                            <td>${passenger.passport.dateOfBirth}</td>
                            <td>${passenger.passport.gender}</td>
                            <td>${passenger.passport.birthplace}</td>
                            <td>${passenger.passport.residenceRegistration}</td>
                            <td>${passenger.passport.seriesAndNumber}</td>
                            <td>${passenger.roles.map((role) => role.name).join(', ')}</td>
                            <td>
                            <button type="submit" onclick="editPassenger(${passenger.id})"
                            class="btn btn-info" data-toggle="modal" data-target="#editPassenger">Edit</button>
                            </td>
                            <td>
                            <button type="submit" onclick="deletePassenger(${passenger.id})"
                            class="btn btn-danger" data-toggle="modal" data-target="#deletePassenger">Delete</button>
                            </td>
                        </tr>`
                    )
                })
            }
        )
}

function addPassengerData() {
    document.addEventListener('DOMContentLoaded', addPassengerData)
    let email = document.getElementById('addEmailPassenger').value
    let password = document.getElementById('addPasswordPassenger').value
    let firstName = document.getElementById('firstName').value
    let lastName = document.getElementById('lastName').value
    let middleName = document.getElementById('middleName').value
    let dateOfBirth = document.getElementById('dateOfBirth').value
    let gender = document.getElementById('gender').value
    let birthplace = document.getElementById('birthplace').value
    let residenceRegistration = document.getElementById('residenceRegistration').value
    let seriesAndNumber = document.getElementById('seriesAndNumber').value
    let roleId = $('#selectorPassenger').val()

    roleId = roleId.map((role) => JSON.parse(role))

    let passenger = {
        email: email,
        password: password,
        firstName: firstName,
        lastName: lastName,
        middleName: middleName,
        dateOfBirth: dateOfBirth,
        roles: roleId,
        passport: {
            firstName: firstName,
            lastName: lastName,
            middleName: middleName,
            gender: gender,
            birthplace: birthplace,
            residenceRegistration: residenceRegistration,
            dateOfBirth: dateOfBirth,
            seriesAndNumber: seriesAndNumber
        }

    }

    passengersFetch.addPassenger(passenger).then(() => {
        document.getElementById('addEmailPassenger').value = ``
        document.getElementById('addPasswordPassenger').value = ``
        document.getElementById('firstName').value = ``
        document.getElementById('lastName').value = ``
        document.getElementById('middleName').value = ``
        document.getElementById('dateOfBirth').value = ``
        document.getElementById('gender').value = ``
        document.getElementById('birthplace').value = ``
        document.getElementById('residenceRegistration').value = ``
        document.getElementById('seriesAndNumber').value = ``

        document.getElementById('tablePassengers').innerHTML = ``

        getPassengers()
    })

    $('#passengerstable-tab').tab('show')
}


function editPassenger(id) {
    passengersFetch.getPassengerById(id)
        .then(res => res.json())
        .then(passenger => {
            $('#editPassengerId').val(passenger.id)
            $('#editEmailPassenger').val(passenger.email)
            $('#editFirstName').val(passenger.passport.firstName)
            $('#editLastName').val(passenger.passport.lastName)
            $('#editMiddleName').val(passenger.passport.middleName)
            $('#editDateOfBirth').val(passenger.passport.dateOfBirth)
            $('#editGender').val(passenger.passport.gender)
            $('#editBirthplace').val(passenger.passport.birthplace)
            $('#editResidenceRegistration').val(passenger.passport.residenceRegistration)
            $('#editSeriesAndNumber').val(passenger.passport.seriesAndNumber)
            $('#editPassportId').val(passenger.passport.id)
        })
}

function updatePassenger() {
    let id = document.getElementById('editPassengerId').value
    let email = document.getElementById('editEmailPassenger').value
    let password = document.getElementById('editPasswordPassenger').value
    let firstName = document.getElementById('editFirstName').value
    let lastName = document.getElementById('editLastName').value
    let middleName = document.getElementById('editMiddleName').value
    let dateOfBirth = document.getElementById('editDateOfBirth').value
    let gender = document.getElementById('editGender').value
    let birthplace = document.getElementById('editBirthplace').value
    let residenceRegistration = document.getElementById('editResidenceRegistration').value
    let seriesAndNumber = document.getElementById('editSeriesAndNumber').value
    let passportId = document.getElementById('editPassportId').value

    let roleId = $('#editSelectorPassenger').val()
    roleId = roleId.map((role) => JSON.parse(role))

    let passenger = {
        id: id,
        email: email,
        password: password,
        firstName: firstName,
        lastName: lastName,
        middleName: middleName,
        dateOfBirth: dateOfBirth,
        roles: roleId,
        passport: {
            id: passportId,
            firstName: firstName,
            lastName: lastName,
            middleName: middleName,
            gender: gender,
            birthplace: birthplace,
            residenceRegistration: residenceRegistration,
            dateOfBirth: dateOfBirth,
            seriesAndNumber: seriesAndNumber
        }
    }

    passengersFetch.updatePassenger(passenger).then(() => {
        document.getElementById('editPassengerId').value = ``
        document.getElementById('editEmailPassenger').value = ``
        document.getElementById('editPasswordPassenger').value = ``
        document.getElementById('editFirstName').value = ``
        document.getElementById('editLastName').value = ``
        document.getElementById('editMiddleName').value = ``
        document.getElementById('editDateOfBirth').value = ``
        document.getElementById('editGender').value = ``
        document.getElementById('editBirthplace').value = ``
        document.getElementById('editResidenceRegistration').value = ``
        document.getElementById('editSeriesAndNumber').value = ``

        document.getElementById('tablePassengers').innerHTML = ``

        getPassengers()

        $('#editPassenger').modal('hide')

    })
}


function deletePassenger(id) {
    passengersFetch.getPassengerById(id)
        .then(res => res.json())
        .then(passenger => {
            $('#deleteIdPassenger').val(passenger.id)
            $('#deleteEmailPassenger').val(passenger.email)
            $('#deleteFirstName').val(passenger.passport.firstName)
            $('#deleteLastName').val(passenger.passport.lastName)
            $('#deleteMiddleName').val(passenger.passport.middleName)
            $('#deleteDateOfBirth').val(passenger.passport.dateOfBirth)
            $('#deleteGender').val(passenger.passport.gender)
            $('#deleteBirthplace').val(passenger.passport.birthplace)
            $('#deleteResidenceRegistration').val(passenger.passport.residenceRegistration)
            $('#deleteSeriesAndNumber').val(passenger.passport.seriesAndNumber)
        })
}

function deletePassengerById() {
    let id = document.getElementById('deleteIdPassenger').value

    passengersFetch.deletePassengerById(id).then(() => {
        document.getElementById('deleteIdPassenger').value = ``
        document.getElementById('deleteEmailPassenger').value = ``
        document.getElementById('deletePasswordPassenger').value = ``
        document.getElementById('deleteFirstName').value = ``
        document.getElementById('deleteLastName').value = ``
        document.getElementById('deleteMiddleName').value = ``
        document.getElementById('deleteDateOfBirth').value = ``
        document.getElementById('deleteGender').value = ``
        document.getElementById('deleteBirthplace').value = ``
        document.getElementById('deleteResidenceRegistration').value = ``
        document.getElementById('deleteSeriesAndNumber').value = ``

        document.getElementById('tablePassengers').innerHTML = ``

        getPassengers()

        $('#deletePassenger').modal('hide')
    })
}

const rolesFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },

    getAllRoles: async () => await fetch('/api/role'),
    getRoleById: async (id) => await fetch(`/api/role/` + id),
    addRole: async (role) => await fetch('/api/role',
        {method: "POST", headers: rolesFetch.head, body: JSON.stringify(role)}),
    updateRole: async (role) => await fetch(`/api/role`,
        {method: 'PUT', headers: rolesFetch.head, body: JSON.stringify(role)}),
    deleteRoleByID: async (id) => await fetch(`/api/role/` + id,
        {method: 'DELETE', headers: rolesFetch.head}),
    postRoles: async (role) => await fetch(`/api/role/all`,
        {method: 'POST', headers: rolesFetch.head, body: JSON.stringify(role)})
}

rolesFetch.postRoles()
    .then((resp) => resp.json())
    .then((data) => {
        $.each(data, function (i, role) {
            $('.roles').append($('<option>').text(role.name).attr({
                    "value": JSON.stringify(role)
                })
            )
        })
    })


getRoles()

function getRoles() {
    rolesFetch.getAllRoles()
        .then(res => res.json())
        .then(role => {
                role.forEach(role => {
                    document.querySelector('#tableRoles').insertAdjacentHTML('beforeend',
                        `<tr>
                            <td>${role.id}</td>
                            <td>${role.name}</td>
                            <td>
                            <button type="submit" onclick="editRole(${role.id})"
                            class="btn btn-info" data-toggle="modal" data-target="#editRole">Edit</button>
                            </td>
                            <td>
                            <button type="submit" onclick="deleteRole(${role.id})"
                            class="btn btn-danger" data-toggle="modal" data-target="#deleteRole">Delete</button>
                            </td>
                        </tr>`
                    )
                })
            }
        )
}

function addRoleData() {
    document.addEventListener('DOMContentLoaded', addRoleData)
    let name = document.getElementById('roleName').value

    let role = {
        name: name
    }

    rolesFetch.addRole(role).then(() => {
        document.getElementById('roleName').value = ``

        document.getElementById('tableRoles').innerHTML = ``

        getRoles()
    })

    $('#rolestable-tab').tab('show')

}

function editRole(id) {
    rolesFetch.getRoleById(id)
        .then(res => res.json())
        .then(role => {
            $('#editRoleId').val(role.id)
            $('#editRoleName').val(role.name)
        })
}

function updateRole() {
    let id = document.getElementById('editRoleId').value
    let name = document.getElementById('editRoleName').value

    let role = {
        id: id,
        name: name
    }

    rolesFetch.updateRole(role).then(() => {
        document.getElementById('editRoleId').value = ``
        document.getElementById('editRoleName').value = ``

        document.getElementById('tableRoles').innerHTML = ``

        getRoles()

        $('#editRole').modal('hide')

    })
}

function deleteRole(id) {
    rolesFetch.getRoleById(id)
        .then(res => res.json())
        .then(role => {
            $('#deleteIdRole').val(role.id)
            $('#deleteRoleName').val(role.name)
        })
}

function deleteRoleById() {
    let id = document.getElementById('deleteIdRole').value

    rolesFetch.deleteRoleByID(id).then(() => {
        document.getElementById('deleteIdRole').value = ``
        document.getElementById('deleteRoleName').value = ``

        document.getElementById('tableRoles').innerHTML = ``

        getRoles()

        $('#deleteRole').modal('hide')
    })
}

