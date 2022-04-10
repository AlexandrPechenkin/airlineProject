let urlRegistration = 'http://localhost:8888/api/registration/'
//let urlPassenger = 'http://localhost:8888/api/passenger/'
let urlTicket = 'http://localhost:8888/api/ticket/holdNumber/'
let urlBooking = `http://localhost:8888/api/booking/`;

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
    }),
    getBookingByHoldNumber: async (holdNumber) => await fetch(`${urlBooking}${holdNumber}`)
}

let registrationModal = $('#registrationModal');
let passengerName;
let bookingId;

//Обработчик кнопки "Зарегистрироваться"
$("#registrationButton").on('click', async () => {
    passengerName = document.getElementById("registrationPassengerSecondName").value;
    bookingId = document.getElementById("registrationBookingId").value;

    //Запрос на получение объектов Ticket и Passenger
    await getRegistrationInfo();
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

async function getRegistrationInfo() {
    //Запрос на получение объекта Ticket
    let promiseTicket = await registrationFetchService.getTicketByHoldNumber(bookingId);

    if (promiseTicket.ok) {
        //let ticket = await promiseTicket.json();
        ticketComplete = await promiseTicket.json();

        //Заполнение содержимым модального окна
        await setModalContent(passengerName, bookingId);
        await showSeatSelectors();

    } else {
        registrationModal.find('.modal-title').html(`Не найден рейс ${bookingId}`);
    }

    //Запрос на получение мест и выбранной категории - вернуть
    /*let promiseBooking = await registrationFetchService.getBookingByHoldNumber(bookingId);

    if (promiseBooking.ok) {
        //let ticket = await promiseTicket.json();
        bookingComplete = await promiseBooking.json();

        //Заполнение содержимым модального окна
        await setModalContent(passengerName, bookingId);
        //await showSeatSelectors(ticket);
        await showSeatSelectors();

    } else {
        registrationModal.find('.modal-title').html(`Не найден рейс ${bookingId}`);
    }*/
}

async function setModalContent() {
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
        </div>`;
    registrationModal.find('.modal-body').append(regModalBody);
}

async function showSeatSelectors() {

    let MC_21_200 = true;
    let boeing_777 = false;
    let category = "economy";
    //let category = "business";


    if (MC_21_200) {
        await showSeatSelectorsMC_21_200(category);
    } else if (boeing_777) {
        await showSeatSelectorsBoeing_B777(category);
    }

}

//Схема МС-21-200
async function showSeatSelectorsMC_21_200(category) {
    let columns;
    let rows;
    let startRow; //отступ для начального ряда
    let columnPassage; //значение для прохода
    let columnPassageCategory; //значение для прохода категории
    let firstIncompleteRow; //первый неполный ряд
    let secondIncompleteRow; // второй неполный ряд
    let arrSeatSymbols; //буквенный идентификатор места в ряду
    let arrSeatSymbolsCategory; //буквенный идентификатор места в ряду для категории
    let startSeatId; //идентификатор первого места в категории

    if (category === "business") {
        startRow = 0;
        columns = 4;
        rows = 3;
        columnPassageCategory = 3;
        firstIncompleteRow = 0;
        secondIncompleteRow = 0;
        arrSeatSymbolsCategory = ["A","B","C","D"];
        startSeatId = 1;
    } else if (category === "economy") {
        startRow = 3;
        columns = 6;
        rows = 21 + startRow;
        columnPassageCategory = 4;
        firstIncompleteRow = 12 + startRow;
        secondIncompleteRow = 21 + startRow;
        arrSeatSymbolsCategory = ["A","B","C","D","E","F"];
        startSeatId = 13;
    }

    let arrValue = Array.from({length: 132}, (e, i)=> i); //массив Id Seat-ов листа из запроса

    for (let i = startRow + 1; i <= rows; i++) {
        let divRows = document.createElement('div');
        columnPassage = columnPassageCategory;
        arrSeatSymbols = Array.from(arrSeatSymbolsCategory);
        for (let j = 1; j <= columns; j++) {
            let div = document.createElement('div');
            div.setAttribute('class', 'form-check-inline plane-seat');
            if (j !== columnPassage) {
                let checkboxes;
                if ((i === firstIncompleteRow) && ((j > 4) || (j < 2)) ||
                    (i === secondIncompleteRow) && (j < 4)){
                    checkboxes = `<div style="padding-right: 60px"></div>`;
                    arrSeatSymbols.splice(0,1);
                } else {
                    checkboxes = `<input type="checkbox" 
                        id="check-${i}${j}" class='seat-checkbox' 
                        data-seat-row="${i}" 
                        data-seat-col="${j}"
                        data-seat-id="${arrValue.splice(startSeatId,1)}"> 
                        <label for="check-${i}${j}" 
                        class="seat-selector btn btn-outline-primary" 
                        style='min-width: 60px'>${i}${arrSeatSymbols.splice(0,1)}</label>`;
                }
                div.innerHTML += checkboxes;
            } else {
                div.innerHTML += `<h6 style='min-width: 50px'>ряд ${i}<h6>`;
                j--;
                columnPassage = 0;
            }
            divRows.append(div);
        }
        $("#seats").append(divRows);
    }
}

//Схема Боинг-В777 на 427 мест
async function showSeatSelectorsBoeing_B777(category) {

}

let setChosenSeats = ""; //контейнер для выбранных мест

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
            String.fromCharCode(64 + Number(dataSet.seatCol)) + " | " + dataSet.seatId;

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

    //Запрос на создание объекта Registration
    let promiseRegistration = await registrationFetchService.createRegistration(
        bookingId, 3);

    if (promiseRegistration.ok) {
        registrationModal.modal('hide');
    }

})