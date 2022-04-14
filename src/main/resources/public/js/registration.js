let urlRegistration = 'http://localhost:8888/api/registration/'
let urlBooking = `http://localhost:8888/api/booking/`;

const registrationFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    createRegistration: async (holdNumber, seatId) => await fetch(
        `${urlRegistration}${holdNumber}/${seatId}`, {
        method: 'POST',
        headers: registrationFetchService.head
    }),
    getBookingByHoldNumber: async (holdNumber) => await fetch(`${urlBooking}holdNumber/${holdNumber}`)
}

let registrationModal = $('#registrationModal');
let passengerName;
let bookingId; //введённый номер брони для поиска
let booking; //json с данными из БД о местах в самолёте

//Обработчик кнопки "Зарегистрироваться"
$("#registrationButton").on('click', async () => {
    passengerName = document.getElementById("registrationPassengerSecondName").value;
    bookingId = document.getElementById("registrationBookingId").value;

    await getRegistrationInfo();
})

//Фиксированное содержимое модального окна и очистка при закрытии
registrationModal.on("show.bs.modal", () => {
    let closeButton = `<button type="button" class="btn btn-secondary" 
       data-bs-dismiss="modal">Отмена</button>`
    registrationModal.find('.modal-footer').append(closeButton);

}).on("hide.bs.modal", () => {
    document.getElementById("registrationPassengerSecondName").value = '';
    document.getElementById("registrationBookingId").value = '';
    registrationModal.find('.modal-title').html('');
    registrationModal.find('.modal-body').html('');
    registrationModal.find('.modal-footer').html('');
})

let registrationInfo = document.getElementById('registrationInfo');

async function getRegistrationInfo() {
    registrationInfo.innerText = '';

    //Запрос на поиск регистрации - если не найдена, то разрешить регистрацию
    let promiseRegistration = await fetch(`${urlRegistration}holdNumber/${bookingId}`);
    let registrationStatus = await promiseRegistration.text();

    if (promiseRegistration.status === 404) {
        await getBookingInfo();

    } else if (promiseRegistration.ok && (registrationStatus === "OK")) {
        registrationInfo.innerText = 'Регистрация уже выполнена';
        registrationInfo.setAttribute('class', 'text-primary');
    } else {
        registrationInfo.innerText = 'Регистрация не доступна';
        registrationInfo.setAttribute('class', 'text-danger');
    }
}

async function getBookingInfo() {
    //Запрос на получение букинга
    let promiseBooking = await registrationFetchService.getBookingByHoldNumber(bookingId);
    if (promiseBooking.ok) {
        booking = await promiseBooking.json();

        if (booking.departTicket.passenger.lastName === passengerName) {
            registrationModal.modal('show');
            //Заполнение содержимым модального окна
            await setModalContent();
            await showBoardInfo(booking.departTicket.flight.aircraft.model, booking.category);
            await showSeatSelectors(booking.category, booking.departTicket.flight.aircraft.model,
                booking.departTicket.flight.aircraft.categories);
        } else {
            registrationInfo.innerText = 'Отсутствует бронирование для указанного пассажира';
            registrationInfo.setAttribute('class', 'text-danger');
        }
    } else {
        registrationInfo.innerText = 'Бронирование не найдено';
        registrationInfo.setAttribute('class', 'text-danger');
    }
}

async function setModalContent() {
    registrationModal.find('.modal-title').html(`Выбор места на рейс 
        ${booking.departTicket.flight.from.city} - ${booking.departTicket.flight.to.city} 
        отправлением ${booking.departTicket.flight.departureDate} 
        ${booking.departTicket.flight.departureTime} 
        пассажира ${booking.departTicket.passenger.firstName} ${passengerName}`);

    let registerCompleteButton = `<button  class="btn btn-primary"
        id="registerCompleteButton">Подтвердить</button>`;
    registrationModal.find('.modal-footer').append(registerCompleteButton);

    let regModalBody = `
        <div class="row seat-container container-fluid">
            <div class="col-md-10" id="seats">
                <div id="boardInfo">
                </div>
            </div>    
            <div class="col-md-2">
                Selected seat:
                <div class="seat-result-container">          
                </div>
            </div>     
        </div>`;
    registrationModal.find('.modal-body').append(regModalBody);
}

async function showBoardInfo(aircraftModel, categoryName) {
    let boardInfo = document.getElementById('boardInfo');
    boardInfo.append(`Борт ${aircraftModel}, `);
    if (categoryName === 'Business') {
        boardInfo.append(`класс обслуживания - бизнес`);
    } else if (categoryName === 'Comfort') {
        boardInfo.append(`класс обслуживания - комфорт`);
    } else if (categoryName === 'First class') {
        boardInfo.append(`класс обслуживания - первый класс`);
    } else if (categoryName === 'Economy') {
        boardInfo.append(`класс обслуживания - эконом`);
    } else {
        boardInfo.append(`Не выбран класс обслуживания`);
    }
}

async function showSeatSelectors(categoryName, aircraftModel, listCategories) {

    let listSeats;
    for (let i in listCategories) {
        if (listCategories[i].category === categoryName) {
            listSeats = listCategories[i].seats;
        }
    }

    let arrValue = []; //массив Id Seat-ов листа из запроса
    let arrDisable = []; //массив статусов мест (недоступны)
    for (let i in listSeats) {
        arrValue.push(listSeats[i].id);
        if (listSeats[i].isRegistered) {
            arrDisable.push("disabled");
        } else {
            arrDisable.push("");
        }
    }

    let columns; //мест в ряду
    let rows; //номер последнего ряда
    let startRow; //отступ для начального ряда
    let firstColumnPassage; //значение для первого прохода
    let firstColumnPassageCategory; //значение для первого прохода категории
    let secondColumnPassage; //значение для второго прохода
    let secondColumnPassageCategory; //значение для второго прохода категории
    let arrSeatSymbols; //буквенный идентификатор места в ряду
    let incompleteRow; //номер неполного ряда
    let incompleteRowSpace; //пространство, не занятое местом

    if (aircraftModel === "МС-21-200") {
        if (categoryName === "Business") {
            startRow = 0;
            columns = 4;
            rows = 3;
            firstColumnPassageCategory = 3;
            arrSeatSymbols = ["A","B","C","D"];
            incompleteRow = [];
            incompleteRowSpace = [[]];
        } else if (categoryName === "Economy") {
            startRow = 3;
            columns = 6;
            rows = 21 + startRow;
            firstColumnPassageCategory = 4;
            arrSeatSymbols = ["A","B","C","D","E","F"];
            incompleteRow = [15, 24];
            incompleteRowSpace = [[1,5,6],[1,2,3]];
        }
    } else if (aircraftModel === "Боинг B777") {
        if (categoryName === "Business") {
            startRow = 0;
            columns = 4;
            rows = 7;
            firstColumnPassageCategory = 2;
            secondColumnPassageCategory = 4;
            arrSeatSymbols = ["AC","D", "G","HK"];
            incompleteRow = [];
            incompleteRowSpace = [[]];
        } else if (categoryName === "Comfort") {
            startRow = 7;
            columns = 8;
            rows = 3 + startRow;
            firstColumnPassageCategory = 3;
            secondColumnPassageCategory = 7;
            arrSeatSymbols = ["A", "C", "D", "E", "F", "G","H", "K"];
            incompleteRow = [];
            incompleteRowSpace = [[]];
        } else if (categoryName === "Economy") {
            startRow = 10;
            columns = 10;
            rows = 41 + startRow;
            firstColumnPassageCategory = 4;
            secondColumnPassageCategory = 8;
            arrSeatSymbols = ["A", "B", "C", "D", "E", "F", "G","H", "J", "K"];
            incompleteRow = [21, 22, 36, 37, 48, 49, 50, 51];
            incompleteRowSpace = [[1,2,3,8,9,10], [1,2,3,8,9,10],
                [1,2,3], [4,5,6,7], [2,3,8,9], [2,3,8,9], [1,2,3,8,9,10], [1,2,3,8,9,10]];
        }
    }

    let indexSeatList = 0; //счетчик, используемый для соотношения значений массивов arrValue и arrDisable с параметрами чекбоксов
    let indexSeatSymbol = 0; //счётчик для буквенных идентификаторов мест

    for (let i = startRow + 1; i <= rows; i++) {
        let divRows = document.createElement('div');
        firstColumnPassage = firstColumnPassageCategory;
        secondColumnPassage = secondColumnPassageCategory;
        for (let j = 1; j <= columns; j++) {
            let div = document.createElement('div');
            div.setAttribute('class', 'form-check-inline plane-seat');
            if (j !== firstColumnPassage && j !== secondColumnPassage) {
                let checkboxes;
                if (incompleteRow.includes(i) && incompleteRowSpace[0].includes(j)){
                    incompleteRowSpace[0].splice(0, 1);
                    if (incompleteRowSpace[0].length === 0 && incompleteRowSpace.length > 1) {
                        incompleteRow.splice(0,1);
                        incompleteRowSpace.splice(0, 1);
                    }
                    checkboxes = `<div style="padding-right: 60px"></div>`;
                } else {
                    checkboxes = `<input type="checkbox" 
                        id="check-${i}${j}" class='seat-checkbox' 
                        data-seat-row="${i}" 
                        data-seat-col="${j}"
                        data-seat-id="${arrValue[indexSeatList]}"  
                        ${arrDisable[indexSeatList]}> 
                        <label for="check-${i}${j}" 
                        class="seat-selector btn btn-outline-primary ${arrDisable[indexSeatList]}" 
                        style='min-width: 60px'>${i}${arrSeatSymbols[indexSeatSymbol]}</label>`;
                    indexSeatList++;
                }
                if (indexSeatSymbol < arrSeatSymbols.length - 1) {
                    indexSeatSymbol++;
                } else {
                    indexSeatSymbol = 0;
                }
                div.innerHTML += checkboxes;
            } else {
                div.innerHTML += `<h6 style='min-width: 50px'>ряд ${i}<h6>`;
                if (j === firstColumnPassage) {
                    firstColumnPassage = 0;
                } else if (j === secondColumnPassage) {
                    secondColumnPassage = 0;
                }
                j--;
            }
            divRows.append(div);
        }
        $("#seats").append(divRows);
    }
}

let chosenSeatId = ''; //идентификатор выбранного места согласно БД

//Обработчик нажатия на label чекбоксов
registrationModal.on('click', '.seat-selector', async (event)=> {
    let seatResultContainer = $(".seat-result-container");
    seatResultContainer.empty();

    let targetLabel = event.currentTarget;
    let checkBoxId = targetLabel.getAttribute('for');
    let targetCheckbox = document.getElementById(checkBoxId);
    chosenSeatId = targetCheckbox.dataset.seatId;

    let allCheckboxes = document.querySelectorAll('input[class="seat-checkbox"]');
    let allCheckboxesLabel = document.querySelectorAll('.seat-selector');

    //Вывод выбранных мест
    if (!targetCheckbox.getAttribute('disabled')) {
        if (!targetCheckbox.checked) {
            for (let i = 0; i < allCheckboxes.length; i++) {
                if (allCheckboxes[i] !== targetCheckbox) {
                    allCheckboxes[i].setAttribute('disabled', 'true');
                    allCheckboxesLabel[i].setAttribute('class', 'seat-selector btn btn-outline-primary disabled');
                }
            }
            seatResultContainer.append(`<div id="chosenSeat">${targetLabel.textContent} | ${chosenSeatId}</div>`);
        } else {
            chosenSeatId = '';
            document.getElementById('seats').innerHTML = '';
            await showSeatSelectors(booking.category, booking.departTicket.flight.aircraft.model,
                booking.departTicket.flight.aircraft.categories);
        }
    }
})

//обработчик кнопки "Подтвердить"
registrationModal.on('click', '#registerCompleteButton', async ()=> {

    let seatResultContainer = $(".seat-result-container");
    seatResultContainer.empty();

    if (chosenSeatId === '') {
        seatResultContainer.append(`<div>Место не выбрано!</div>`);
    } else {

        let registrationInfo = document.getElementById('registrationInfo');
        //Запрос на создание объекта Registration
        let promiseRegistration = await registrationFetchService.createRegistration(
            bookingId, chosenSeatId);

        if (promiseRegistration.ok) {
            registrationInfo.innerText = 'Успешная регистрация';
            registrationInfo.setAttribute('class', 'text-success');
        } else if (promiseRegistration.status === 404) {
            registrationInfo.innerText = 'Регистрация недоступна';
            registrationInfo.setAttribute('class', 'text-danger');
        } else {
            console.log("Ошибка запроса к RegistrationRestController");
            registrationInfo.innerText = 'Ошибка при создании регистрации';
            registrationInfo.setAttribute('class', 'text-danger');
        }
        registrationModal.modal('hide');
    }
})