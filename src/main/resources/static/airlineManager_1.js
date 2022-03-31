const aircraftsTableTbody = document.querySelector('#aircraftsTable tbody')
const aircraftUrl = 'http://localhost:8888/api/aircraft'
let aircraftOutput = '';

fetch(aircraftUrl)
    .then(res => res.json())
    .then(aircrafts => {
        aircrafts.forEach(aircraft => {
            aircraftOutput += `
                <tr>
                    <td>${aircraft.id}</td>
                    <td>${aircraft.brand}</td>
                </tr>
            `
        })
        aircraftsTableTbody.innerHTML = aircraftOutput
    })