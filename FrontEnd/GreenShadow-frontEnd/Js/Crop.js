$(document).ready(function () {
  fetchCrops()
});

// Function to preview the uploaded image
function previewCropImage(event, previewId) {
    const output = document.getElementById(previewId);
    output.src = URL.createObjectURL(event.target.files[0]);
    output.style.display = 'block';
}

// Function to open the Add Field Modal
function toggleEditCropMode() {
    $("#cropDetailModal").modal('hide')

    $('#addCropModal').modal('show');
    document.getElementById('addCropModalLabel').innerText = 'Update Crop Details';

    // Change the button text from "Add Staff" to "Save Changes"
    const addCropBtn = document.getElementById('addCrop');
    addCropBtn.innerText = 'Save Changes'
}

//save Crop function
// Attach click event listener to the Add Crop button
$('#openAddModal').click(function () {
    fetchAndPopulateFields()
});
$('#addCrop').click(function () {

    // Gather form data
    const formData = new FormData();
   const cropData={
        commonName: $('#CropCommonName').val(),
        specificName:$('#CropSpecialName').val(),
        category: $('#CropCategory').val(),
        season:$('#CropSeason').val(),
        fieldId:$('#fieldSelect').val()
    }
    console.log(cropData)
    formData.append('cropData',JSON.stringify(cropData))
    // Handle image upload
    const cropImage = $('#cropImage')[0].files[0];
    if (cropImage) {
        formData.append('imageFile', cropImage);
    }

    // AJAX POST request to save the crop
    $.ajax({
        url: 'http://localhost:8080/greenShadow/api/v1/crops',
        type: 'POST',
        data: formData,
        processData: false, // Prevent jQuery from automatically transforming the data
        contentType: false,
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function (response) {
            // Close the modal
            $('#addCropModal').modal('hide');

            // Clear the form fields
            $('#addCropForm')[0].reset();
            $('#cropPreview').hide().attr('src', '');

            // update the  UI
            fetchCrops()
            // Optional: Show a success message
            showAlert('Crop added successfully!','success');
        },
        error: function (error) {
            // Optional: Show an error message
            console.error('Error adding crop:', error);
            showAlert('Failed to add crop. Please try again.','error');
        },
    });

});

// Function to fetch fields and populate the select element
function fetchAndPopulateFields() {
    $.ajax({
        url: "http://localhost:8080/greenShadow/api/v1/fields", // Update with your actual endpoint
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        success: function (response) {
            // Assuming response is an array of FieldDto objects
            const fieldSelect = $("#fieldSelect");
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


function fetchCrops() {
    $.ajax({
        url: 'http://localhost:8080/greenShadow/api/v1/crops',
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        success: function (crops) {
            addCropToUI(crops);
        },
        error: function (error) {
            console.error("Error fetching fields:", error);
            $(".no-data").show();
        }
    });
}
// Function to add the new crop card to the UI dynamically
function addCropToUI(crops) {
    const container = $('#crop-container');
    container.empty(); // Clear existing content

    if (crops.length === 0) {
        $(".no-data").show();
        return;
    }

    crops.forEach(crop => {
        //set field name that related to crop
        fetchFieldById(crop, function (fieldName) {
            $(`#field-info-${crop.id} p`).text(fieldName);

        });

        const image = crop.image1 ? `data:image/jpeg;base64,${crop.image1}` : 'https://via.placeholder.com/600x200?text=No+Image';
        const cropCard = `
        <div class="card-custom"  data-id="${crop.id}">
            <!-- Crop Image -->
            <div class="image-container">
                <img src="${image}" alt="Crop Image">
            </div>

            <!-- Card Content -->
            <div class="card-content">
                <!-- Common Name -->
                <div class="field-info">
                    <h4>Common Name</h4>
                    <p>${crop.commonName}</p>
                </div>

                <!-- Special Name -->
                <div class="field-info">
                    <h4>Special Name</h4>
                    <p>${crop.specificName}</p>
                </div>

                <!-- Category -->
                <div class="field-info">
                    <h4>Category</h4>
                    <p>${crop.category}</p>
                </div>

                <!-- Field -->
                <div class="field-info" id="field-info-${crop.id}">
                    <h4>Field</h4>
                    <p>.... </p>
                </div>

                <!-- Centered View More Button -->
               <div class="view-more-container">
                    <button id="viewCrop" class="btn view-btn viewCropData" 
                        data-crop='${JSON.stringify(crop)}' 
                        onclick="viewCropData(this)">
                        View more
                    </button>
                </div>
            </div>
        </div>
    `;
        container.append(cropCard);
    });

}
//fetch field data of related field
function fetchFieldById(crop, callback) {
    console.log("Crop Data:", crop);
    console.log("Field ID:", crop.fieldId);

    $.ajax({
        url: `http://localhost:8080/greenShadow/api/v1/fields/${crop.fieldId}`, // Replace with your actual endpoint
        type: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token") // Include token if required
        },
        success: function (response) {
            console.log("Fetched Field Data:", response);
            callback(response.name); // Pass the field name to the callback
        },
        error: function (error) {
            console.error("Error fetching field by ID:", error);
        }
    });
}
function viewCropData(button){

    // Parse crop data from the button's data attribute
    const cropDetail = JSON.parse($(button).attr('data-crop'));
    console.log(cropDetail)
    console.log(cropDetail.id,cropDetail.specificName)

    // Populate modal fields
    $('#cropId').val(cropDetail.id);
    $('#specialName').val(cropDetail.specificName);
    $('#commonName').val(cropDetail.commonName);
    $('#category').val(cropDetail.category);
    $('#season').val(cropDetail.season);
    // Update the modal image
    const imageSrc = cropDetail.image1
        ? `data:image/jpeg;base64,${cropDetail.image1}`
        : 'https://via.placeholder.com/600x200?text=No+Image';
    $('#cropDetailModal .modal-header img').attr('src', imageSrc);

    // Fetch field name for the modal
    fetchFieldById(cropDetail, function (fieldName) {
        $('#field').val(fieldName);
    });
    $("#cropDetailModal").modal("show");
};


