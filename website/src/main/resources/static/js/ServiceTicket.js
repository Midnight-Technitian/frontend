function openNewTicketModal() {
    document.getElementById("newTicketModal").style.display = "block";
}

function closeNewTicketModal() {
    document.getElementById("newTicketModal").style.display = "none";
}

function submitNewTicket(event) {
    event.preventDefault();

    const form = event.target;
    const data = {
        customerId: form.customerId.value,
        serviceId:form.serviceId.value,
        deviceId: form.deviceId.value,
        deviceName: form.deviceName.value,
        serviceDescription:form.description.value
    };

    fetch("http://localhost:8081/api/v1/tickets", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                alert("Ticket created successfully!");
                closeNewTicketModal();
                form.reset();
            } else {
                alert("Failed to create ticket. Please try again.");
            }
        })
        .catch(() => alert("An error occurred while submitting the ticket."));
}
