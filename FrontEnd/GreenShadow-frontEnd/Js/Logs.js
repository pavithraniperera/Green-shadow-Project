function enableEditMode() {
    $('#logDetailModal').modal('hide');

    // Open the addStaffModal
    $('#addMonitoringLogModal').modal('show');

    // Change the modal header to "Update Member"
    document.getElementById('addMonitoringLogModalLabel').innerText = 'Update Lod Details';

    // Change the button text from "Add Staff" to "Save Changes"
    const addStaffBtn = document.getElementById('saveLogBtn');
    addStaffBtn.innerText = 'Save Changes';

}
// Function to update the date and time in real-time
function updateDateTime() {
    const now = new Date();
    const formattedDateTime = now.toLocaleString();
    document.getElementById("currentDateTime").textContent = formattedDateTime;
}
setInterval(updateDateTime, 1000); // Update every second

// Function to add a new Field/Crop combo box
function addFieldToLog() {
    // Fetch existing options from the first dropdown
    const existingOptions = $('.fieldForLog:first').html();
    const container = document.getElementById("fieldsContainer");
    const newFieldCrop = document.createElement("div");
    newFieldCrop.className = "d-flex align-items-center mb-2";
    newFieldCrop.innerHTML = `
        <select class="form-control mr-2 fieldForLog" name="fields[]" >
          ${existingOptions || '<option value="">Select Field</option>'}
        </select>
        <button type="button" class="btn btn-sm btn-danger" onclick="removeFieldCrop(this)">
            <i class="fa-regular fa-trash-can"></i>
        </button>
    `;
    container.appendChild(newFieldCrop);
}
function addCropToLog() {
    const existingOptions = $('.cropForLog:first').html();
    const container = document.getElementById("cropsContainer");
    const newFieldCrop = document.createElement("div");
    newFieldCrop.className = "d-flex align-items-center mb-2";
    newFieldCrop.innerHTML = `
        <select class="form-control mr-2 cropForLog" name="Crops[]" >
           ${existingOptions || '<option value="">Select Crop</option>'}
        </select>
        <button type="button" class="btn btn-sm btn-danger" onclick="removeFieldCrop(this)">
            <i class="fa-regular fa-trash-can"></i>
        </button>
    `;
    container.appendChild(newFieldCrop);
}
// Function to preview the uploaded image
function previewLogImage(event, previewId) {
    const output = document.getElementById(previewId);
    output.src = URL.createObjectURL(event.target.files[0]);
    output.style.display = 'block';
}


// Function to remove Field/Crop combo box
function removeFieldCrop(element) {
    element.parentNode.remove();
}

// Function to add a new Staff combo box
function addStaff() {
    const existingOptions = $('.staffForLog:first').html();
    const container = document.getElementById("staffContainer");
    const newStaff = document.createElement("div");
    newStaff.className = "d-flex align-items-center mb-2";
    newStaff.innerHTML = `
        <select class="form-control mr-2 staffForLog" name="monitoringStaff[]" required>
          ${existingOptions || '<option value="">Select Staff</option>'}
        </select>
        <button type="button" class="btn btn-sm btn-danger" onclick="removeStaff(this)">
            <i class="fa-regular fa-trash-can"></i>
        </button>
    `;
    container.appendChild(newStaff);
}

// Function to remove Staff combo box
function removeStaff(element) {
    element.parentNode.remove();
}



