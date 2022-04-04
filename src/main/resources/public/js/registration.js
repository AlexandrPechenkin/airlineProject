let urlRegistration = 'http://localhost:8888/api/registration/'

//Обработчик кнопки "зарегистрирроваться"
$("#registrationButton").on('click', async () => {

    let passengerName = document.getElementById("registrationPassengerSecondName").value;
    let bookingId = document.getElementById("registrationBookingId").value;

    getRegistrationModal(passengerName, bookingId);
})

//Управление активацией/скрытием и очисткой содержимого модального окна
async function getRegistrationModal(passengerName, bookingId) {
    $('#registrationModal').on("shown.bs.modal", (event) => {
        let modal = $(event.target);
        modal.off("shown.bs.modal");
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
    //Запрос на получение объекта Registration c пыстым seat
    let promise = await fetch(urlRegistration + passengerName + "/" + bookingId, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
    let registration = promise.json(); //извлечь данные

    //Заполнение содержимым модального окна
    modal.find('.modal-title').html('Выбор места на рейс № ' + bookingId +
        ' пассажира ' + passengerName);
    let registerCompleteButton = `<button  class="btn btn-primary" 
        id="registerCompleteButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" 
        data-bs-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(closeButton);
    modal.find('.modal-footer').append(registerCompleteButton);


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

    //Переменные, отвечающие за геометрию мест
    //в дальнейшем определять исходя из типа самолёта
    //по умолчанию используется шестиместный ряд с одним проходом
    let rows = 20;
    let columns = 6;
    let seatCheck = "check-";

    for (let i = 1; i <= rows; i++) {
        let divRows = document.createElement('div');
        let rowNumber = 4; //значение для прохода
        for (let j = 1; j <= columns; j++) {
            let div = document.createElement('div');
            div.setAttribute('class', 'form-check-inline plane-seat');
            if (j !== rowNumber) {
                let checkboxes = "<input type=\"checkbox\" " +
                    "id=\"" + seatCheck + i + j + "\" class='seat-checkbox'" +
                    " data-seat-row=\"" + i + "\"" +
                    " data-seat-col=\"" + j + "\">" +
                    " <label for=\"" + seatCheck + i + j + "\"" +
                    " class=\"seat-selector btn btn-outline-primary\"" +
                    " style='min-width: 60px'" +
                    " data-seat-row=\"" + i + "\"" +
                    " data-seat-col=\"" + j + "\">" + i +
                    String.fromCharCode(64 + j) + "</label>";
                div.innerHTML += checkboxes;
            } else {
                div.innerHTML += "<h6 style='min-width: 50px'>ряд " + i + "<h6>";
                j--;
                rowNumber = 0;
            }
            divRows.append(div);
        }
        $("#seats").append(divRows);
    }

    //обработчик клика на label checkbox места
    $(".seat-selector").on('click',
        function(event) {
            $(".seat-result-container").empty();
            let targetElement = event.currentTarget;
            let checkBoxId = targetElement.getAttribute('for')
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
                        $(".seat-result-container").append('<div>' +
                            chosenSeat + '</div>');
                }
            }
        })
}