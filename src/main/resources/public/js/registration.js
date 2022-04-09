let urlRegistration = 'http://localhost:8888/api/registration/'
//let urlPassenger = 'http://localhost:8888/api/passenger/'
let urlTicket = 'http://localhost:8888/api/ticket/holdNumber/'

const registrationFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    getTicketByHoldNumber: async (holdNumber) => await fetch(`${urlTicket}${holdNumber}`),
    createRegistration: async (holdNumber, seatId) => await fetch(
        `${urlRegistration}${holdNumber}/${seatId}`, {
        method: 'POST',
        headers: registrationFetchService.head
    })
}

let registrationModal = $('#registrationModal');

//Обработчик кнопки "Зарегистрироваться"
$("#registrationButton").on('click', async () => {
    let passengerName = document.getElementById("registrationPassengerSecondName").value;
    let bookingId = document.getElementById("registrationBookingId").value;

    //Запрос на получение объектов Ticket и Passenger
    await getRegistrationInfo(passengerName, bookingId);
})

//Фиксированное содержимое модального окна и очистка при закрытии
registrationModal.on("show.bs.modal", () => {
    let closeButton = `<button type="button" class="btn btn-secondary" 
       data-bs-dismiss="modal">Отмена</button>`
    registrationModal.find('.modal-footer').append(closeButton);

}).on("hidden.bs.modal", () => {
    registrationModal.find('.modal-title').html('');
    registrationModal.find('.modal-body').html('');
    registrationModal.find('.modal-footer').html('');
})

let ticketComplete;

async function getRegistrationInfo(passengerName, bookingId) {
    //Запрос на получение объекта Ticket
    let promiseTicket = await registrationFetchService.getTicketByHoldNumber(bookingId);

    if (promiseTicket.ok) {
        //let ticket = await promiseTicket.json();
        ticketComplete = await promiseTicket.json();

        //Заполнение содержимым модального окна
        await setModalContent(passengerName, bookingId);
        //await showSeatSelectors(ticket);
        await showSeatSelectors();

    } else {
        registrationModal.find('.modal-title').html(`Не найден рейс ${bookingId}`);
    }
}

async function setModalContent(passengerName, bookingId) {
    registrationModal.find('.modal-title').html(`Выбор места на рейс ${bookingId} 
        пассажира ${passengerName}`);
    let registerCompleteButton = `<button  class="btn btn-primary"
        id="registerCompleteButton">Подтвердить</button>`;
    registrationModal.find('.modal-footer').append(registerCompleteButton);

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
    registrationModal.find('.modal-body').append(regModalBody);
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
                    style='min-width: 60px'>${i}${String.fromCharCode(64 + j)}</label>`;
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

let setChosenSeats = "";

//Обработчик нажатия на label чекбоксов
registrationModal.on('click', '.seat-selector', async (event)=> {
    let seatResultContainer = $(".seat-result-container");
    seatResultContainer.empty();

    let targetLabel = event.currentTarget;
    let checkBoxId = targetLabel.getAttribute('for');
    let targetCheckbox = document.getElementById(checkBoxId);

    //Вывод выбранных мест
    let chosenCheckboxes = document.querySelectorAll('input[class="seat-checkbox"]');

    for (let i = 0; i < chosenCheckboxes.length; i++) {
        let dataSet = chosenCheckboxes[i].dataset;
        let chosenSeat = dataSet.seatRow +
            String.fromCharCode(64 + Number(dataSet.seatCol));

        if (targetCheckbox.checked &&
            chosenCheckboxes[i].checked && chosenCheckboxes[i] !== targetCheckbox ||
            !targetCheckbox.checked &&
            (chosenCheckboxes[i].checked || chosenCheckboxes[i] === targetCheckbox)) {

            setChosenSeats += chosenSeat;

            seatResultContainer.append(`<div>${chosenSeat}</div>`);
        }
    }
    ticketComplete.seat.seatNumber = setChosenSeats.substr(0,2); //после добавления возможности
                                //регистрировать несколько пассажиров заменить на массив
})

//обработчик кнопки "Подтвердить"
registrationModal.on('click', '#registerCompleteButton', async ()=> {
    let registration = {
        id: null,
        ticket: {
            id: ticketComplete.id,
            seat: {
                id: ticketComplete.seat.id,
                seatNumber: ticketComplete.seat.seatNumber,
                fare: ticketComplete.seat.fare,
                isRegistered: ticketComplete.seat.isRegistered,
                isSold: ticketComplete.seat.isSold,
                flight: ticketComplete.seat.flight
            },
            price: ticketComplete.price,
            flight: ticketComplete.flight
        },
        status: "IN_PROGRESS",
        registrationDateTime: null
    }

    //Запрос на создание объекта Registration
    let promiseRegistration = await registrationFetchService.createRegistration(
        document.getElementById("registrationBookingId").value, 3);

    registrationModal.modal('hide');
})