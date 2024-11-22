$(document).ready(function () {
    // Fetch staff data on page load
    fetchLogs();

});

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
        <button type="button" class="btn btn-sm custom-btn" onclick="removeFieldCrop(this)">
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
        <button type="button" class="btn btn-sm custom-btn" onclick="removeFieldCrop(this)">
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
function addStaffForLog() {

    const existingOptions = $('.staffForLog:first').html();
    const container = document.getElementById("staffContainer");
    const newStaff = document.createElement("div");
    newStaff.className = "d-flex align-items-center mb-2";
    newStaff.innerHTML = `
        <select class="form-control mr-2 staffForLog" name="monitoringStaff[]" required>
          ${existingOptions || '<option value="">Select Staff Member</option>'}
        </select>
        <button type="button" class="btn btn-sm  custom-btn" onclick="removeStaff(this)">
            <i class="fa-regular fa-trash-can"></i>
        </button>
    `;
    container.appendChild(newStaff);
}

// Function to remove Staff combo box
function removeStaff(element) {
    element.parentNode.remove();
}

function fetchFieldsForLog() {

    $.ajax({
        url: "http://localhost:8080/greenShadow/api/v1/fields", // Update with your actual endpoint
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function (response) {
            // Assuming response is an array of FieldDto objects
            const fieldSelect = $(".fieldForLog");
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

function fetchCropForLog() {

    $.ajax({
        url: "http://localhost:8080/greenShadow/api/v1/crops", // Update with your actual endpoint
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function (response) {
            // Assuming response is an array of FieldDto objects
            const cropSelect = $(".cropForLog");
            cropSelect.empty(); // Clear existing options
            cropSelect.append('<option value="">Select Field</option>'); // Add default option

            // Populate the select element with field names and IDs
            response.forEach(crop => {
                const option = `<option value="${crop.id}">${crop.commonName}</option>`;
                cropSelect.append(option);
            });
        },
        error: function (error) {
            console.error("Error fetching crops:", error);
            showAlert("Failed to load crops. Please try again later.",'error');
        }
    });

}

function fetchStaffForLog() {

    $.ajax({
        url: "http://localhost:8080/greenShadow/api/v1/staffs", // Update with your actual endpoint
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function (response) {
            // Assuming response is an array of FieldDto objects
            const staffSelect = $("#monitoringStaff");
            staffSelect.empty(); // Clear existing options
            staffSelect.append('<option value="">Select Staff Member</option>'); // Add default option

            // Populate the select element with field names and IDs
            response.forEach(staff => {
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

$("#openAddLogModal").click(function () {
    $("#addMonitoringLogModal").modal('show')
    fetchFieldsForLog();
    fetchCropForLog();
    fetchStaffForLog();
})

function clearForm() {
    $("#addMonitoringLogForm")[0].reset();
    $("#logPreview").hide().attr("src", "");
    $("#fieldsContainer").empty();
    $("#cropsContainer").empty();
    $("#staffContainer").empty();
}

function saveLog() {
    // Collect form data
    const logDescription = $("#logDescription").val();
    const fields = $(".fieldForLog").map(function () {
        return $(this).val();
    }).get()
        .filter(val => val && val.trim() !== ""); // Filter out empty values for fields;
    const crops = $(".cropForLog").map(function () {
        return $(this).val();
    }).get()
        .filter(val => val && val.trim() !== ""); // Filter out empty values for fields;
    const staff = $(".staffForLog").map(function () {
        return $(this).val();
    }).get()
        .filter(val => val && val.trim() !== ""); // Filter out empty values for fields;
    const logStatus = $("#logStatus").val();
    const imageFile = $("#logImage")[0].files[0]; // Get the selected image file

    // Create the log data object
    const logData = {
        logDetails: logDescription,
        date:new Date(),
        status: logStatus
    };
    // Conditionally add fields if not empty
    if (fields.length > 0) {
        logData.fieldIds = fields;
    }

    // Conditionally add crops if not empty
    if (crops.length > 0) {
        logData.cropIds = crops;
    }

    // Conditionally add monitoringStaff if not empty
    if (staff.length > 0) {
        logData.staffIds = staff;
    }
   console.log(logData)
    // Create FormData to send multipart data
    const formData = new FormData();
    formData.append("logData", JSON.stringify(logData)); // Convert logData to JSON string
    if (imageFile) {
        formData.append("imageFile", imageFile); // Append the image file
    }
    // Perform AJAX request
    $.ajax({
        url: "http://localhost:8080/greenShadow/api/v1/logs", // Replace with your endpoint
        type: "POST",
        data: formData,
        processData: false, // Prevent jQuery from processing the data
        contentType: false, // Set the content type to false for FormData
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token") // Add the token header
        },
        success: function (response) {
            showAlert("Log saved successfully!",'success');
            console.log(response); // Debug: Check the response from the server
            // Optionally, clear the form or refresh the UI
            clearForm();
            $("#addMonitoringLogModal").modal("hide")
            fetchLogs()
        },
        error: function (xhr, status, error) {
            showAlert("Failed to save the log. Please try again.",'error');
            console.error(error); // Debug: Check for errors
        }
    });
}
// Call this function on the Save button click
$("#saveLogBtn").on("click", saveLog);

function fetchLogs() {
    $.ajax({
        url: 'http://localhost:8080/greenShadow/api/v1/logs',
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        success: function (logs) {
            addLogsToUI(logs);
        },
        error: function (error) {
            console.error("Error fetching fields:", error);
            $(".no-data").show();
        }
    });
}

function addLogsToUI(logs){
    const container = $(".log-card-container");
    const noDataMessage = $(".no-data");

    // Clear any existing content
    container.empty();
    if (logs && logs.length > 0) {
        noDataMessage.hide(); // Hide no-data message if logs exist

        logs.forEach(log => {

            const logCard = `
                        <div class="log-card" data-log-Id =${log.logId}>
                            <!-- Date and Time -->
                            <div class="log-date-time">
                                <span>${new Date(log.date).toLocaleDateString()}</span>
                             
                            </div>

                            <!-- Field/Crop and Status Section -->
                            <div class="log-header">
                                <div class="log-category">.....</div>
                                <div class="log-status ${log.status.toLowerCase()}">${log.status}</div>
                            </div>

                            <!-- Description and Image Section -->
                            <div class="log-middle">
                                <!-- Description on the left -->
                                <div class="log-description">
                                    ${log.logDetails || "No description available"}
                                </div>
                                <!-- Image on the right -->
                                <div class="log-image">
                                    ${log.image2
                    ? `<img src="data:image/png;base64,${log.image2}" alt="Log Image" />`
                    : `<img src="https://via.placeholder.com/80" alt="No Image Available" />`
            }
                                </div>
                            </div>

                            <!-- View Details Button -->
                            <button class="view-details-btn" data-toggle="modal" data-target="#logDetailModal" onclick="viewLogDetails(${log.id})">
                                <i class="fas fa-info-circle"></i> View Details
                            </button>
                        </div>
                    `;
            container.append(logCard);
            fetchFieldsAndCrops(log.logId)
        });
    } else {
        noDataMessage.show(); // Show no-data message if no logs exist
    }
}

function fetchFieldsAndCrops(logId) {
    $.ajax({
        url: `http://localhost:8080/greenShadow/api/v1/logs/${logId}/related-entities`,
        method: 'GET',
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token") // Add the token header
        },
        success: function (data) {
            // Select the log card using the unique logId
            const logCard = $(`.log-card[data-log-id="${logId}"]`);
            // Extract field and crop names
            const fieldNames = (data.fields && data.fields.length > 0)
                ? data.fields.map(field => field.name).join(", ")
                : null; // No Fields

            const cropNames = (data.crops && data.crops.length > 0)
                ? data.crops.map(crop => crop.commonName).join(", ")
                : null; // No Crops

            // Construct the display text
            let displayText = "";

            if (fieldNames && cropNames) {
                // Both fields and crops exist
                displayText = `${fieldNames}, ${cropNames} Crop`;
            } else if (fieldNames) {
                // Only fields exist
                displayText = fieldNames;
            } else if (cropNames) {
                // Only crops exist
                displayText = cropNames;
            } else {
                // Neither fields nor crops exist
                displayText = "No Data Available";
            }

            // Update the log card's category text
            logCard.find('.log-category').text(displayText);
        },
        error: function (xhr, status, error) {
            console.error(`Failed to fetch related entities for log ${logId}:`, error);
            logCard.find('.log-category').text("Error fetching data");
        }
    });
}










