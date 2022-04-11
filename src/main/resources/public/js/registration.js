let urlRegistration = 'http://localhost:8888/api/registration/'
//let urlPassenger = 'http://localhost:8888/api/passenger/'
//let urlTicket = 'http://localhost:8888/api/ticket/holdNumber/'
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

async function getRegistrationInfo() {
    let booking = { //брать из запроса к букингу (образец)
        category: "comfort",
        aircraft: "boeing_777",
        listSeats: [
            {
                id: 17,
                isRegistered: false
            },
            {
                id: 18,
                isRegistered: true
            }
        ]
    };

    //Запрос на получение мест и выбранной категории - вернуть, выше закомментить
    /*let promiseBooking = await registrationFetchService.getBookingByHoldNumber(bookingId);

    if (promiseBooking.ok) {
        //let ticket = await promiseTicket.json();
        bookingComplete = await promiseBooking.json();

        //Заполнение содержимым модального окна
        await setModalContent(passengerName, bookingId);
        await showSeatSelectors(booking);

    } else {
        registrationModal.find('.modal-title').html(`Не найден рейс ${bookingId}`);
    }*/

    //Заполнение содержимым модального окна
    await setModalContent(passengerName, bookingId);
    await showSeatSelectors(booking);
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

async function showSeatSelectors(booking) {

    let listSeats = booking.listSeats;
    let arrValue = []; //массив Id Seat-ов листа из запроса
    let arrDisable = []; //массив статусов мест (недоступны)
    for (let i in listSeats) {
        arrValue.push(listSeats[i].id);
        if (listSeats[i].isRegistered) {
            arrDisable.push("");
        } else {
            arrDisable.push("disabled");
        }
    }
    let category = booking.category;

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

    if (booking.aircraft === "MC_21_200") {
        if (category === "business") {
            startRow = 0;
            columns = 4;
            rows = 3;
            firstColumnPassageCategory = 3;
            arrSeatSymbols = ["A","B","C","D"];
            incompleteRow = [];
            incompleteRowSpace = [[]];
        } else if (category === "economy") {
            startRow = 3;
            columns = 6;
            rows = 21 + startRow;
            firstColumnPassageCategory = 4;
            arrSeatSymbols = ["A","B","C","D","E","F"];
            incompleteRow = [15, 24];
            incompleteRowSpace = [[1,5,6],[1,2,3]];
        }
    } else if (booking.aircraft === "boeing_777") {
        if (category === "business") {
            startRow = 0;
            columns = 4;
            rows = 7;
            firstColumnPassageCategory = 2;
            secondColumnPassageCategory = 4;
            arrSeatSymbols = ["AC","D", "G","HK"];
            incompleteRow = [];
            incompleteRowSpace = [[]];
        } else if (category === "comfort") {
            startRow = 7;
            columns = 8;
            rows = 3 + startRow;
            firstColumnPassageCategory = 3;
            secondColumnPassageCategory = 7;
            arrSeatSymbols = ["A", "C", "D", "E", "F", "G","H", "K"];
            incompleteRow = [];
            incompleteRowSpace = [[]];
        } else if (category === "economy") {
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

let setChosenSeats = ""; //контейнер для выбранных мест

//Обработчик нажатия на label чекбоксов
registrationModal.on('click', '.seat-selector', async (event)=> {
    let seatResultContainer = $(".seat-result-container");
    seatResultContainer.empty();

    let targetLabel = event.currentTarget;
    let checkBoxId = targetLabel.getAttribute('for');
    let targetCheckbox = document.getElementById(checkBoxId);

    //Вывод выбранных мест
    if (!targetCheckbox.getAttribute('disabled')) {
        let chosenCheckboxes = document.querySelectorAll('input[class="seat-checkbox"]');

        for (let i = 0; i < chosenCheckboxes.length; i++) {
            let dataSet = chosenCheckboxes[i].dataset;
            let chosenSeat = dataSet.seatRow +
                String.fromCharCode(64 + Number(dataSet.seatCol)) + " | " + dataSet.seatId;

            if (targetCheckbox.checked &&
                chosenCheckboxes[i].checked && chosenCheckboxes[i] !== targetCheckbox ||
                !targetCheckbox.checked &&
                (chosenCheckboxes[i].checked || chosenCheckboxes[i] === targetCheckbox)) {

                setChosenSeats = dataSet.seatId;

                seatResultContainer.append(`<div>${chosenSeat}</div>`);
            }
        }
        //после добавления возможности регистрировать несколько пассажиров
        //заменить setChosenSeats на массив и изменить запрос на отправку списка мест
    }

})

//обработчик кнопки "Подтвердить"
registrationModal.on('click', '#registerCompleteButton', async ()=> {

    //Запрос на создание объекта Registration
    let promiseRegistration = await registrationFetchService.createRegistration(
        bookingId, setChosenSeats);

    if (promiseRegistration.ok) {
        registrationModal.modal('hide');
    }
})