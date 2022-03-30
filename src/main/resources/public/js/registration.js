let urlRegistration = 'http://localhost:8888/api/registration/'

async function getRegistrationInfo(modal, passengerName, bookingId) {
    /*let promise = await fetch(urlRegistration + "/" + passengerName + "/" + bookingId, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
    if (promise.ok) {
        $('#registrationModal').modal('show');
    }
    let response = promise.json();*/

    let registerCompleteButton = `<button  class="btn btn-primary" id="registerCompleteButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" 
        data-bs-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(closeButton);
    modal.find('.modal-footer').append(registerCompleteButton);
}

document.getElementById("registrationPassengerSecondName").oninvalid = function(event) {
    alert("invalid");
    event.target.setCustomValidity('Фамилия должна состоять из латинских букв');
}

$("#registrationButton").on('click', async () => {

    let passengerName = document.getElementById("registrationPassengerSecondName").value;
    let bookingId = document.getElementById("registrationBookingId").value;

    getRegistrationModal(passengerName, bookingId);
})

async function getRegistrationModal(passengerName, bookingId) {
    $('#registrationModal').on("shown.bs.modal", (event) => {
        let modal = $(event.target);
        modal.off("shown.bs.modal");
        getRegistrationInfo(modal, passengerName, bookingId);
    }).on("hidden.bs.modal", (e) => {
        let modal = $(e.target);
        modal.off("hidden.bs.modal");
        //modal.find('.modal-title').html('');
        modal.find('.modal-body').html('');
        modal.find('.modal-footer').html('');
    })

}
