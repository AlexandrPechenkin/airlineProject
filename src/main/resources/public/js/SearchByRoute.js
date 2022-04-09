let urlReq = 'http://localhost:8888/api/search'
const buttonSearch = document.getElementById('searchButton');
buttonSearch.addEventListener('click', getFlightsByRoutes);

async function getFlightsByRoutes() {

    let inp_from = document.getElementById("from").value;
    let inp_to = document.getElementById("to").value;
    let inp_departure_date = document.getElementById("departure-date").value;
    let inp_departure_date_of_return = document.getElementById("departure-date-of-return").value;
    let inp_number_of_seats = document.getElementById("number-of-seats").value;
    let inp_category = document.getElementById("category").value;

    let route = {
        "from": inp_from,
        "to": inp_to,
        "departure_date": inp_departure_date,
        "departure_date_of_return": inp_departure_date_of_return,
        "number_of_seats": inp_number_of_seats,
        "category": inp_category
    }
    await fetch(urlReq, {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(route)
    })
}