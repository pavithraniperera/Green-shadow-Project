function toggleEditMode() {
    // Close the staffDetailModal
    $('#staffDetailModal').modal('hide');

    // Open the addStaffModal
    $('#addStaffModal').modal('show');

    // Change the modal header to "Update Member"
    document.getElementById('addStaffModalLabel').innerText = 'Update Member';

    // Change the button text from "Add Staff" to "Save Changes"
    const addStaffBtn = document.getElementById('addStaffBtn');
    addStaffBtn.innerText = 'Save Changes';
    //addStaffBtn.onclick = saveChanges;

    // Optionally, populate the addStaffForm with existing staff details
    // populateStaffForm();
}

document.getElementById('staffSearch').addEventListener('input', function () {
    const filter = this.value.toLowerCase();
    const rows = document.querySelectorAll('.staff-table tbody tr');

    rows.forEach(row => {
        const cells = row.querySelectorAll('td');
        const match = Array.from(cells).some(cell =>
            cell.textContent.toLowerCase().includes(filter)
        );
        row.style.display = match ? '' : 'none'; // Show row if match, hide if not
    });
});

document.querySelectorAll('.staff-table tbody tr').forEach(row => {
    row.addEventListener('click', function() {

        // Show the modal
        $('#staffDetailModal').modal('show');
    });
});

// Function to add an assigned field combo box
function addField() {
    const container = document.getElementById('assignedFieldsContainer');
    const fieldDiv = document.createElement('div');
    fieldDiv.className = 'd-flex align-items-center mb-2';
    fieldDiv.innerHTML = `
            <select class="form-control glass-input mr-2" name="assignedFields[]" required>
                <option value="">Select Field</option>
                <option value="Field A">Field A</option>
                <option value="Field B">Field B</option>
                <!-- Add more fields dynamically if needed -->
            </select>
            <button type="button" class="btn btn-sm custom-btn" onclick="removeField(this)"><i class="fa-regular fa-trash-can" style="color:green"></i></button>
        `;
    container.appendChild(fieldDiv);
}

// Function to remove an assigned field combo box
function removeField(button) {
    button.parentElement.remove();
}

// Function to add an assigned vehicle combo box
function addVehicle() {
    const container = document.getElementById('assignedVehiclesContainer');
    const vehicleDiv = document.createElement('div');
    vehicleDiv.className = 'd-flex align-items-center mb-2';
    vehicleDiv.innerHTML = `
            <select class="form-control glass-input mr-2" name="assignedVehicles[]" required>
                <option value="">Select Vehicle</option>
                <option value="Vehicle 1">Vehicle 1</option>
                <option value="Vehicle 2">Vehicle 2</option>
                <!-- Add more vehicles dynamically if needed -->
            </select>
          <button type="button" class="btn btn-sm custom-btn" onclick="removeField(this)"><i class="fa-regular fa-trash-can" style="color:green"></i></button>
        `;
    container.appendChild(vehicleDiv);
}

// Function to remove an assigned vehicle combo box
function removeVehicle(button) {
    button.parentElement.remove();
}

// Function to clear the form
function clearForm() {
    document.getElementById("addStaffForm").reset();
    document.getElementById("assignedFieldsContainer").innerHTML = '';
    document.getElementById("assignedVehiclesContainer").innerHTML = '';
    addField(); // Add one initial field combo box
    addVehicle(); // Add one initial vehicle combo box
}
