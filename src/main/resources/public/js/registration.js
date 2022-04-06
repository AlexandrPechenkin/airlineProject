//let urlRegistration = 'http://localhost:8888/api/registration/'
//let urlPassenger = 'http://localhost:8888/api/passenger/'
let urlTicket = 'http://localhost:8888/api/ticket/holdNumber/'

const registrationFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    getTicketByHoldNumber: async (holdNumber) => await fetch(`${urlTicket}${holdNumber}`)
}

//Обработчик кнопки "Зарегистрирроваться"
$("#registrationButton").on('click', async () => {
    let passengerName = document.getElementById("registrationPassengerSecondName").value;
    let bookingId = document.getElementById("registrationBookingId").value;

    await getRegistrationModal(passengerName, bookingId);
})

//Управление активацией/скрытием и очисткой содержимого модального окна
async function getRegistrationModal(passengerName, bookingId) {

    $('#registrationModal').on("shown.bs.modal", (event) => {
        let modal = $(event.target);
        modal.off("shown.bs.modal");

        //Фиксированный footer модального окна
        let registerCompleteButton = `<button  class="btn btn-primary"
        id="registerCompleteButton">Подтвердить</button>`;
        let closeButton = `<button type="button" class="btn btn-secondary" 
        data-bs-dismiss="modal">Отмена</button>`
        modal.find('.modal-footer').append(closeButton);
        modal.find('.modal-footer').append(registerCompleteButton);

        //Запрос на получение объектов Registration и Passenger
        getRegistrationInfo(modal, passengerName, bookingId);

    }).on("hidden.bs.modal", (e) => {
        let modal = $(e.target);
        modal.off("hidden.bs.modal");
        modal.find('.modal-title').html('');
        modal.find('.modal-body').html('');
        modal.find('.modal-footer').html('');
    })
}

async function getRegistrationInfo(modal, passengerName, bookingId) {
    //Запрос на получение объекта Registration
    /*let promiseRegistration = await fetch(urlRegistration + bookingId, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })*/

    //Запрос на получение объекта Ticket
    let promiseTicket = await registrationFetchService.getTicketByHoldNumber(bookingId);

    if (promiseTicket.ok) {
        let ticket = await promiseTicket.json();

        //Заполнение содержимым модального окна
        await setModalContent(modal, passengerName, bookingId, ticket);

    } else {
        modal.find('.modal-title').html(`Не найден рейс ${bookingId}`);
    }
}

async function setModalContent(modal, passengerName, bookingId, ticket) {
    modal.find('.modal-title').html(`Выбор места на рейс ${bookingId} 
        пассажира ${passengerName}`);

    let regModalBody = `
        <div class="row seat-container">
            <div class="col-md-10" id="seats">
            </div>    
            <div class="col-md-2">
                Selected seat:
                <div class="seat-result-container">          
                </div>
            </div>     
        </div>
        `;
    modal.find('.modal-body').append(regModalBody);

    await showSeatSelectors(ticket);

    await showSelectedSeats();
}

async function showSeatSelectors(ticket) {
    //Переменные, отвечающие за геометрию мест
    //в дальнейшем определять исходя из типа самолёта (описание мест внутри категорий)
    //по умолчанию используется шестиместный ряд с одним проходом
    let columns = 6;
    let rows = 20;

    for (let i = 1; i <= rows; i++) {
        let divRows = document.createElement('div');
        let rowNumber = 4; //значение для прохода
        for (let j = 1; j <= columns; j++) {
            let div = document.createElement('div');
            div.setAttribute('class', 'form-check-inline plane-seat');
            if (j !== rowNumber) {
                let checkboxes = `<input type="checkbox" 
                    id="check-${i}${j}" class='seat-checkbox' 
                    data-seat-row="${i}" 
                    data-seat-col="${j}"> 
                    <label for="check-${i}${j}" 
                    class="seat-selector btn btn-outline-primary" 
                    style='min-width: 60px'>
                    ${String.fromCharCode(64 + j)}</label>`;
                div.innerHTML += checkboxes;
            } else {
                div.innerHTML += `<h6 style='min-width: 50px'>ряд ${i}<h6>`;
                j--;
                rowNumber = 0;
            }
            divRows.append(div);
        }
        $("#seats").append(divRows);
    }
}

async function showSelectedSeats() {
    //обработчик клика на label checkbox места
    $(".seat-selector").on('click', function(event) {
        let seatResultContainer = $(".seat-result-container");
        seatResultContainer.empty();

        let targetLabel = event.currentTarget;
        let checkBoxId = targetLabel.getAttribute('for');
        let targetCheckbox = document.getElementById(checkBoxId);

        //Вывод выбранных мест
        let chosenCheckboxes = document.querySelectorAll('input[class="seat-checkbox"]');
        let setChosenSeats = "";

        for (let i = 0; i < chosenCheckboxes.length; i++) {
            let dataSet = chosenCheckboxes[i].dataset;
            let chosenSeat = dataSet.seatRow +
                String.fromCharCode(64 + Number(dataSet.seatCol));

            if (targetCheckbox.checked &&
                chosenCheckboxes[i].checked && chosenCheckboxes[i] !== targetCheckbox ||
                !targetCheckbox.checked &&
                (chosenCheckboxes[i].checked || chosenCheckboxes[i] === targetCheckbox)) {

                setChosenSeats += chosenSeat;
                seatResultContainer.append('<div>' +
                    chosenSeat + '</div>');
            }
        }
    })
}