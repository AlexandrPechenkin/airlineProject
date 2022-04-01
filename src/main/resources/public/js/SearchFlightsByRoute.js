let urlReq = 'http://localhost:8888/api/flight/'
const buttonSearch = document.getElementById('btnSearch');
buttonSearch.addEventListener('click', getFlightsByRoutes);

async function getFlightsByRoutes() {
    let inp_from = document.getElementById("from").value;
    let inp_to = document.getElementById("to").value;
    let inp_departure_date = document.getElementById("departure-date").value;

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
        .then(json => console.log(json))
        .catch(error => console.log(error));
}