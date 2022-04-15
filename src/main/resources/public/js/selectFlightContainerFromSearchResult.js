let urlBooking = 'http://localhost:8888/api/booking/'
let urlSearch = 'http://localhost:8888/api/search/'
const selectDepartFlightButton = document.getElementById('depart-flight')
const selectReturnFlightButton = document.getElementById('return-flight')
selectDepartFlightButton.addEventListener('click', getSelectedFlightContainer);
selectReturnFlightButton.addEventListener('click', getSelectedFlightContainer);

const bookingFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json; charset=UTF-8',
        'Referer': null
    },
}

getSelectedFlightContainer()

async function getSelectedFlightContainer() {

    let category = document.getElementById("category").value
    let flight_container_id = document.getElementById("flight_container_id").value
    let flight_list_wrapper_id = document.getElementById("flight_list_wrapper_id").value

    let flight_container = {
        'flight_container_id': flight_container_id,
        'flight_list_wrapper_id': flight_list_wrapper_id,
        'category': category
    }
    await fetch(urlBooking, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(flight_container)
    })
}