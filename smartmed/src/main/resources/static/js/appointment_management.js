// Common functions
document.addEventListener('DOMContentLoaded', function() {
    // Initialize tooltips
    $('[data-toggle="tooltip"]').tooltip();

    // Initialize popovers
    $('[data-toggle="popover"]').popover();

    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });
});

// Handle appointment booking
function bookAppointment(doctorId) {
    const appointmentDate = document.getElementById('appointmentDate').value;
    const timeSlot = document.getElementById('timeSlot').value;
    const symptoms = document.getElementById('symptoms').value;

    if (!appointmentDate || !timeSlot) {
        alert('Vui lòng chọn ngày và giờ khám');
        return;
    }

    // Submit booking form
    document.getElementById('bookingForm').submit();
}

// Handle medicine search
function searchMedicines(query) {
    const searchQuery = query.trim();
    if (searchQuery.length > 0) {
        window.location.href = `/medicines?search=${encodeURIComponent(searchQuery)}`;
    }
}

// Handle doctor search
function searchDoctors(query) {
    const searchQuery = query.trim();
    if (searchQuery.length > 0) {
        window.location.href = `/doctors?search=${encodeURIComponent(searchQuery)}`;
    }
}

// Profile page functions
function viewAppointment(appointmentId) {
    // Show appointment details in modal
    $('#appointmentModal').modal('show');
    // Load appointment details via AJAX
    fetch(`/api/appointments/${appointmentId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('appointmentDetails').innerHTML = `
                <p><strong>Ngày khám:</strong> ${data.appointmentDate}</p>
                <p><strong>Bác sĩ:</strong> ${data.doctorName}</p>
                <p><strong>Chuyên khoa:</strong> ${data.specialty}</p>
                <p><strong>Triệu chứng:</strong> ${data.symptoms}</p>
                <p><strong>Trạng thái:</strong> ${data.status}</p>
            `;
        });
}

function cancelAppointment(appointmentId) {
    if (confirm('Bạn có chắc chắn muốn hủy lịch hẹn này?')) {
        fetch(`/api/appointments/${appointmentId}/cancel`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                location.reload();
            } else {
                alert('Không thể hủy lịch hẹn. Vui lòng thử lại sau.');
            }
        });
    }
}

function viewPrescription(prescriptionId) {
    window.location.href = `/prescriptions/${prescriptionId}`;
}