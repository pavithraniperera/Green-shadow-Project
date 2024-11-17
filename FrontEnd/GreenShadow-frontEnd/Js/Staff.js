function toggleStaffEditMode() {
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
    // Fetch existing options from the first dropdown
    const existingOptions = $('.fieldForStaff:first').html();

    // Create a new dropdown with the same options
    const container = document.getElementById('assignedFieldsContainer');
    const fieldDiv = document.createElement('div');
    fieldDiv.className = 'd-flex align-items-center mb-2';
    fieldDiv.innerHTML = `
        <select class="form-control glass-input mr-2 fieldForStaff" name="assignedFields[]" required>
            ${existingOptions || '<option value="">Select Field</option>'}
        </select>
        <button type="button" class="btn btn-sm custom-btn" onclick="removeField(this)">
            <i class="fa-regular fa-trash-can" style="color:green"></i>
        </button>
    `;
    container.appendChild(fieldDiv);
}

// Function to remove an assigned field combo box
function removeField(button) {
    button.parentElement.remove();
}

// Function to add an assigned vehicle combo box
/*
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
*/

// Function to remove an assigned vehicle combo box
/*function removeVehicle(button) {
    button.parentElement.remove();
}*/

// Function to clear the form
function clearStaffForm() {
    document.getElementById("addStaffForm").reset();
    document.getElementById("assignedFieldsContainer").innerHTML = '';
    document.getElementById("assignedVehiclesContainer").innerHTML = '';
    addField(); // Add one initial field combo box
   // addVehicle(); // Add one initial vehicle combo box
}

function fetchAllFieldsForStaff() {
    $.ajax({
        url: "http://localhost:8080/greenShadow/api/v1/fields", // Update with your actual endpoint
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function (response) {
            // Assuming response is an array of FieldDto objects
            const fieldSelect = $(".fieldForStaff");
            fieldSelect.empty(); // Clear existing options
            fieldSelect.append('<option value="">Select Field</option>'); // Add default option

            // Populate the select element with field names and IDs
            response.forEach(field => {
                const option = `<option value="${field.fieldId}">${field.name}</option>`;
                fieldSelect.append(option);
            });
        },
        error: function (error) {
            console.error("Error fetching fields:", error);
            showAlert("Failed to load fields. Please try again later.",'error');
        }
    });
}
$("#staffSave").click(function (){
    fetchAllFieldsForStaff()
    $("#addStaffModal").modal("show")
})

$("#addStaffBtn").click(function (){
    console.log("clicked")
    // Gather form data

    const firstName = $("#firstNameModal").val();
    const lastName = $("#lastNameModal").val();
    const email = $("#emailModal").val();
    const address = $("#addressModal").val();
    const contact = $("#contactModal").val();
    const dob = $("#dobModal").val();
    const gender = $("#genderModal").val();
    const role = $("#roleModal").val();
    const designation = $("#designationModal").val();
    const joinDate = $("#joinDateModal").val();

    // Get all selected field IDs
    const assignedFields = [];
    $(".fieldForStaff").each(function () {
        const fieldId = $(this).val();
        if (fieldId) {
            assignedFields.push(fieldId);
        }
    });

    // Validate required fields
  /*  if ( !firstName || !lastName || !email || !contact || !dob || !gender || !role || !designation ||joinDate ||address){
        showAlert("Please fill all required fields.",'error');
        return;
    }

    if (assignedFields.length === 0) {
        showAlert("Please assign at least one field.",'error');
        return;
    }*/
    // Create the payload
    const staffData = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        contact: contact,
        dob: dob,
        address:address,
        gender: gender,
        role: role,
        designation: designation,
        joinDate:joinDate,
        fieldIds: assignedFields // Field IDs as a list
    };
    console.log(staffData)


    // AJAX call to save staff
    $.ajax({
        url: "http://localhost:8080/greenShadow/api/v1/staffs", // Replace with your backend URL
        type: "POST",
        contentType: "application/json",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        data: JSON.stringify(staffData),
        success: function (response) {
            showAlert("Staff added successfully!","success");
            $("#addStaffModal").modal("hide"); // Close the modal
            $("#addStaffForm")[0].reset(); // Clear the form
        },
        error: function (xhr) {
            console.error("Error while saving staff:", xhr.responseText);
            showAlert("Failed to add staff. Please try again.","error");
        }
    });


})