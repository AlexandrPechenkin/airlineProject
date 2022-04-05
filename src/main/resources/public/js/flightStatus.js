let urlReq = 'http://localhost:8888/api/flight/'

async function getFlightStatusByFromToDepartureDate() {
    let inp_from = document.getElementById("from").value;
    let inp_to = document.getElementById("to").value;
    let inp_departure_date = document.getElementById("departure-date").value;
    let status_info = document.getElementById("card_one")


    await fetch(urlReq + inp_from + "/" + inp_to + "/" + inp_departure_date, {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json()
            }
            throw new Error()
        })
        .then(json => {
            for (let iter in json) {
                status_info.innerHTML += `
                <div class="card-body">
                    <h5>Статус вашего рейса | ID: ${json[iter].id}</h5>
                    <p>${json[iter].flightStatus}</p>
                </div>
                `;
            }
        })
        .catch(error => console.log(error));
}

// TODO: Поиск рейса "По номеру рейса" сделан через ID, позже в Flight добавить поле "Номера рейса" и на основе его переделать.
async function getFlightStatusById() {
    let flight_number = document.getElementById("flight-number").value;
    let status_info = document.getElementById("card_two")


    await fetch(urlReq + flight_number, {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json()
            }
            throw new Error()
        })
        .then(json => {
                status_info.innerHTML += `
                <div class="card-body">
                    <h5>Статус вашего рейса | ID: ${json.id}</h5>
                    <p>${json.flightStatus}</p>
                </div>
                `;
        })
        .catch(error => console.log(error));
}