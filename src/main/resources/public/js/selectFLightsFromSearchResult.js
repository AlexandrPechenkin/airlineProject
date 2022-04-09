let urlReq = 'http://localhost:8888/api/booking'
const selectDepartFlightButton = document.getElementById('depart-flight')
const selectReturnFlightButton = document.getElementById('return-flight')
selectDepartFlightButton.addEventListener('click', getSelectedFlight);
selectReturnFlightButton.addEventListener('click', getSelectedFlight);

async function getSelectedFlight() {

    let inp_category = document.getElementById("category").value;

    let route = {
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