const aircraftsFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },

    getAllAircrafts: async () => await fetch('api/aircraft'),
    getAircraftById: async (id) => await fetch(`api/aircraft/` + id),
    addAircraft: async (aircraft) => await fetch('api/aircraft',
        {method: "POST", headers: aircraftsFetch.head, body: JSON.stringify(aircraft)}),
    updateAircraft: async (aircraft) => await fetch(`api/aircraft`,
        {method: 'PUT', headers: aircraftsFetch.head, body: JSON.stringify(aircraft)}),
    deleteAircraftByID: async (id) => await fetch(`api/aircraft/` + id,
        {method: 'DELETE', headers: aircraftsFetch.head})
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

function editAircraft(id) {
    aircraftsFetch.getAircraftById(id)
        .then(res => res.json())
        .then(aircraft => {
            $('#editId').val(aircraft.id)
            $('#editBrand').val(aircraft.brand)
            $('#editModel').val(aircraft.model)
            $('#editBoardNumber').val(aircraft.boardNumber)
            $('#editProductionYear').val(aircraft.productionYear)
            $('#editFlyingRange').val(aircraft.flyingRange)
        })
}

function updateAircraft() {
    let id = document.getElementById('editId').value
    let brand = document.getElementById('editBrand').value
    let model = document.getElementById('editModel').value
    let boardNumber = document.getElementById('editBoardNumber').value
    let productionYear = document.getElementById('editProductionYear').value
    let flyingRange = document.getElementById('editFlyingRange').value

    let aircraft = {
        id: id,
        brand: brand,
        model: model,
        boardNumber: boardNumber,
        productionYear: productionYear,
        flyingRange: flyingRange
    }

    aircraftsFetch.updateAircraft(aircraft).then(() => {
        document.getElementById('editId').value = ``
        document.getElementById('editBrand').value = ``
        document.getElementById('editModel').value = ``
        document.getElementById('editBoardNumber').value = ``
        document.getElementById('editProductionYear').value = ``
        document.getElementById('editFlyingRange').value = ``

        document.getElementById('tableAircrafts').innerHTML = ``

        getAircrafts()

        $('#editAircraft').modal('hide')
    })
}

function deleteAircraft(id) {
    aircraftsFetch.getAircraftById(id)
        .then(res => res.json())
        .then(aircraft => {
            $('#deleteId').val(aircraft.id)
            $('#deleteBrand').val(aircraft.brand)
            $('#deleteModel').val(aircraft.model)
            $('#deleteBoardNumber').val(aircraft.boardNumber)
            $('#deleteProductionYear').val(aircraft.productionYear)
            $('#deleteFlyingRange').val(aircraft.flyingRange)
        })
}

function deleteAircraftById() {
    let id = document.getElementById('deleteId').value

    aircraftsFetch.deleteAircraftByID(id).then(() => {
        document.getElementById('deleteId').value = ``
        document.getElementById('deleteBrand').value = ``
        document.getElementById('deleteModel').value = ``
        document.getElementById('deleteBoardNumber').value = ``
        document.getElementById('deleteProductionYear').value = ``
        document.getElementById('deleteFlyingRange').value = ``

        document.getElementById('tableAircrafts').innerHTML = ``

        getAircrafts()

        $('#deleteAircraft').modal('hide')
    })
}

const flightsFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },

    getAllFlights: async () => await fetch('api/flight'),
    getFlightById: async (id) => await fetch(`api/flight/` + id),
    addFlight: async (flight) => await fetch('api/flight',
        {method: "POST", headers: flightsFetch.head, body: JSON.stringify(flight)}),
    updateFlight: async (flight) => await fetch(`api/flight`,
        {method: 'PUT', headers: flightsFetch.head, body: JSON.stringify(flight)}),
    deleteFlightByID: async (id) => await fetch(`api/flight/` + id,
        {method: 'DELETE', headers: flightsFetch.head})
}

getFlights()

function getFlights() {
    flightsFetch.getAllFlights()
        .then(res => res.json())
        .then(flights => {
                flights.forEach(flight => {
                    document.querySelector('#tableFlights').insertAdjacentHTML('beforeend',
                        `<tr>
                            <td>${flight.id}</td>
                            <td>${flight.departureDate}</td>
                            <td>${flight.departureTime}</td>
                            <td>${flight.arrivalDateTime}</td>
                            <td>${flight.destinationFrom}</td>
                            <td>${flight.destinationTo}</td>
                            <td>${flight.flightStatus}</td>
                            <td>
                            <button type="submit" onclick="editFlight(${flight.id})"
                            class="btn btn-info" data-toggle="modal" data-target="#editFlight">Edit</button>
                            </td>
                            <td>
                            <button type="submit" onclick="deleteFlight(${flight.id})"
                            class="btn btn-danger" data-toggle="modal" data-target="#deleteFlight">Delete</button>
                            </td>
                        </tr>`
                    )
                })
            }
        )
}

// function addFlightData() {
//     document.addEventListener('DOMContentLoaded', addFlightData)
//     let brand = document.getElementById('addBrand').value
//     let model = document.getElementById('addModel').value
//     let boardNumber = document.getElementById('addBoardNumber').value
//     let productionYear = document.getElementById('addProductionYear').value
//     let flyingRange = document.getElementById('addFlyingRange').value
//
//     let flight = {
//         brand: brand,
//         model: model,
//         boardNumber: boardNumber,
//         productionYear: productionYear,
//         flyingRange: flyingRange
//     }
//
//     flightsFetch.addFlight(flight).then(() => {
//         document.getElementById('addBrand').value = ``
//         document.getElementById('addModel').value = ``
//         document.getElementById('addBoardNumber').value = ``
//         document.getElementById('addProductionYear').value = ``
//         document.getElementById('addFlyingRange').value = ``
//
//         document.getElementById('tableFlights').innerHTML = ``
//
//         getFlights()
//     })
//
//     $('#flightstable-tab').tab('show')
//
// }
//
// function editFlight(id) {
//     flightsFetch.getFlightById(id)
//         .then(res => res.json())
//         .then(flight => {
//             $('#editId').val(flight.id)
//             $('#editBrand').val(flight.brand)
//             $('#editModel').val(flight.model)
//             $('#editBoardNumber').val(flight.boardNumber)
//             $('#editProductionYear').val(flight.productionYear)
//             $('#editFlyingRange').val(flight.flyingRange)
//         })
// }
//
// function updateFlight() {
//     let id = document.getElementById('editId').value
//     let brand = document.getElementById('editBrand').value
//     let model = document.getElementById('editModel').value
//     let boardNumber = document.getElementById('editBoardNumber').value
//     let productionYear = document.getElementById('editProductionYear').value
//     let flyingRange = document.getElementById('editFlyingRange').value
//
//     let flight = {
//         id: id,
//         brand: brand,
//         model: model,
//         boardNumber: boardNumber,
//         productionYear: productionYear,
//         flyingRange: flyingRange
//     }
//
//     flightsFetch.updateFlight(flight).then(() => {
//         document.getElementById('editId').value = ``
//         document.getElementById('editBrand').value = ``
//         document.getElementById('editModel').value = ``
//         document.getElementById('editBoardNumber').value = ``
//         document.getElementById('editProductionYear').value = ``
//         document.getElementById('editFlyingRange').value = ``
//
//         document.getElementById('tableFlights').innerHTML = ``
//
//         getFlights()
//
//         $('#editFlight').modal('hide')
//     })
// }
//
// function deleteFlight(id) {
//     flightsFetch.getFlightById(id)
//         .then(res => res.json())
//         .then(flight => {
//             $('#deleteId').val(flight.id)
//             $('#deleteBrand').val(flight.brand)
//             $('#deleteModel').val(flight.model)
//             $('#deleteBoardNumber').val(flight.boardNumber)
//             $('#deleteProductionYear').val(flight.productionYear)
//             $('#deleteFlyingRange').val(flight.flyingRange)
//         })
// }
//
// function deleteFlightById() {
//     let id = document.getElementById('deleteId').value
//
//     flightsFetch.deleteFlightByID(id).then(() => {
//         document.getElementById('deleteId').value = ``
//         document.getElementById('deleteBrand').value = ``
//         document.getElementById('deleteModel').value = ``
//         document.getElementById('deleteBoardNumber').value = ``
//         document.getElementById('deleteProductionYear').value = ``
//         document.getElementById('deleteFlyingRange').value = ``
//
//         document.getElementById('tableFlights').innerHTML = ``
//
//         getFlights()
//
//         $('#deleteFlight').modal('hide')
//     })
// }
//
// const ticketsFetch = {
//     head: {
//         'Accept': 'application/json',
//         'Content-Type': 'application/json; charset=UTF-8',
//         'Referer': null
//     },
//
//     getAllTickets: async () => await fetch('api/ticket')
// }
//
// getTickets()
//
// function getTickets() {
//     ticketsFetch.getAllTickets()
//         .then(res => res.json())
//         .then(tickets => {
//                 tickets.forEach(ticket => {
//                     document.querySelector('#tableTickets').insertAdjacentHTML('beforeend',
//                         `<tr>
//                             <td>${ticket.id}</td>
//                             <td>${ticket.brand}</td>
//                             <td>${ticket.model}</td>
//                             <td>${ticket.boardNumber}</td>
//                             <td>${ticket.productionYear}</td>
//                             <td>${ticket.flyingRange}</td>
//                             <td>
//                             <button type="submit" onclick="editTicket(${ticket.id})"
//                             class="btn btn-info" data-toggle="modal" data-target="#editTicket">Edit</button>
//                             </td>
//                             <td>
//                             <button type="submit" onclick="deleteTicket(${ticket.id})"
//                             class="btn btn-danger" data-toggle="modal" data-target="#deleteTicket">Delete</button>
//                             </td>
//                         </tr>`
//                     )
//                 })
//             }
//         )
// }

