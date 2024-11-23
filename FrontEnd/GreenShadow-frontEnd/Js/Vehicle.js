document.getElementById("statusFilter").addEventListener("change", function() {
    const selectedStatus = this.value;
    const tableRows = document.querySelectorAll("#vehicleTableBody tr");

    tableRows.forEach(row => {
        const rowStatus = row.getAttribute("data-status");

        if (selectedStatus === "all" || rowStatus === selectedStatus) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
});

document.getElementById("vehicleSearch").addEventListener("input", function() {
    const searchValue = this.value.toLowerCase();
    const tableRows = document.querySelectorAll("#vehicleTableBody tr");

    tableRows.forEach(row => {
        const licensePlate = row.cells[1].textContent.toLowerCase(); // Column 2: License Plate
        const fuelType = row.cells[2].textContent.toLowerCase(); // Column 3: Fuel Type

        if (licensePlate.includes(searchValue) || fuelType.includes(searchValue)) {
            row.style.display = ""; // Show row if there's a match
        } else {
            row.style.display = "none"; // Hide row if there's no match
        }
    });
});

document.querySelectorAll('.vehicle-table tbody tr').forEach(row => {
    row.addEventListener('click', function() {

        // Show the modal
        $('#vehicleDetailModal').modal('show');
    });
});

function toggleVehicleEditMode() {
    // Close the staffDetailModal
    $('#vehicleDetailModal').modal('hide');

    // Open the addStaffModal
    $('#addVehicleModal').modal('show');

    // Change the modal header to "Update Member"
    document.getElementById('addVehicleModalLabel').innerText = 'Update Vehicle details';

    // Change the button text from "Add Staff" to "Save Changes"
    const addVehicle = document.getElementById('addVehicleBtn');
    addVehicle.innerText = 'Save Changes';


}
function addStaff() {
    const existingOptions = $('.staffForVehicle:first').html();
    const container = document.getElementById("assignedStaffContainer");
    const staffDiv = document.createElement("div");
    staffDiv.className = "d-flex align-items-center mb-2";
    staffDiv.innerHTML = `
        <select class="form-control glass-input mr-2 staffForVehicle" name="assignedStaff[]" required>
            ${existingOptions || '<option value="">Select Staff Member</option>'}
        </select>
        <button type="button" class="btn btn-sm custom-btn" onclick="removeStaff(this)">
            <i class="fa-regular fa-trash-can" style="color: green"></i>
        </button>
    `;
    container.appendChild(staffDiv);

}

function removeStaff(button) {
    button.parentElement.remove();
}

function clearVehicleForm() {
    document.getElementById("addVehicleForm").reset();
}


function fetchStaffForVehicle(){
    $.ajax({
        url: "http://localhost:8080/greenShadow/api/v1/staffs", // Update with your actual endpoint
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function (staffList) {
            // Assuming response is an array of FieldDto objects
            const staffSelect = $("#assignedStaff");
            staffSelect.empty();
            staffSelect.append('<option value="">Select Staff</option>'); // Default option

            // Populate the select element with fetched staff and pre-select assigned ones
            staffList.forEach(staff => {

                const option = `<option value="${staff.staffId}">${staff.firstName}</option>`;
                staffSelect.append(option);
            });
        },
        error: function (error) {
            console.error("Error fetching Staff:", error);
            showAlert("Failed to load staff. Please try again later.",'error');
        }
    });
}
$("#addVehicle").click(function () {
    fetchStaffForVehicle()
})


