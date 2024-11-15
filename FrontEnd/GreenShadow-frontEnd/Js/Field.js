// JavaScript for Image Slider

function toggleFieldEditMode() {
    $('#fieldDetailModal').modal('hide');

    // Open the addStaffModal
    $('#addFieldModal').modal('show');

    // Change the modal header to "Update Member"
    document.getElementById('addFieldModalLabel').innerText = 'Update Field';

    // Change the button text from "Add Staff" to "Save Changes"
    const addFieldBtn = document.getElementById('addField');
    addFieldBtn.innerText = 'Save Changes';
}

function saveChanges() {
    // Logic for saving changes (e.g., sending updated data to the server)
    console.log("Saving changes...");


}
// JavaScript to handle image preview
function previewImage(event, previewId) {
    const reader = new FileReader();
    reader.onload = function () {
        const output = document.getElementById(previewId);
        output.src = reader.result;
        output.style.display = 'block';
    };
    reader.readAsDataURL(event.target.files[0]);
}

document.addEventListener('DOMContentLoaded', function() {
    // Center coordinates for Panadura, Sri Lanka
    var panaduraCoordinates = [6.7114, 79.9072];

// Define broader bounds to cover a larger area
    var bounds = [
        [6.45, 79.75], // Southwest corner
        [7.10, 80.05]  // Northeast corner
    ];

    // Initialize map centered on Panadura with zoom level suitable for the area
    var map = L.map('map', {
        center: panaduraCoordinates,
        zoom: 13,
        maxBounds: bounds,
        maxBoundsViscosity: 1.0 // Prevents panning outside bounds
    });

    // Add OpenStreetMap tiles
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 20,
        attribution: '© OpenStreetMap'
    }).addTo(map);

    // Marker to display chosen location
    var marker;

    // Map click event to place marker and get coordinates
    map.on('click', function(e) {
        const lat = e.latlng.lat.toFixed(6);  // Latitude
        const lng = e.latlng.lng.toFixed(6);  // Longitude



        var formattedLocation = formatCoordinates(lat, lng);
        // Update input fields
        document.getElementById('location').value = formattedLocation;
        console.log(formattedLocation)

        // Update marker position
        if (marker) {
            marker.setLatLng(e.latlng);
        } else {
            marker = L.marker(e.latlng).addTo(map);
        }
    });
    // Add this to refresh the map when the modal is opened
    $('#addFieldModal').on('shown.bs.modal', function () {
        map.invalidateSize(); // Refresh map to fit the container
    });

    // Function to format latitude and longitude
    function formatCoordinates(lat, lng) {
        var latDirection = lat >= 0 ? 'N' : 'S';
        var lngDirection = lng >= 0 ? 'E' : 'W';

        var formattedLat = Math.abs(lat).toFixed(4) + '° ' + latDirection;
        var formattedLng = Math.abs(lng).toFixed(4) + '° ' + lngDirection;

        return formattedLat + ', ' + formattedLng;
    }

});

//added field
$("#addField").click(function () {

    const fieldName = $("#Name").val();
    const fieldLocation = $("#location").val();
    const fieldSize = $("#Size").val();
    const fieldImage1 = $("#fieldImage1")[0].files[0];
    const fieldImage2 = $("#fieldImage2")[0].files[0];

    // Create a JavaScript object representing field data
    const fieldData = {
        name: fieldName,
        location: fieldLocation,
        size: fieldSize,
    };

    // Create FormData and append data and images
    const formData = new FormData();
    formData.append("fieldData", JSON.stringify(fieldData));
    if (fieldImage1) formData.append("image1", fieldImage1);
    if (fieldImage2) formData.append("image2", fieldImage2);

    // Get JWT token from localStorage
    const token = localStorage.getItem("token");
    console.log(token)

    // Perform the AJAX request
    $.ajax({
        url: "http://localhost:8080/greenShadow/api/v1/fields",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        headers: {
            Authorization: "Bearer " + token // Include JWT in Authorization header
        },
        success: function (response) {
            console.log(response)
            // Clear the input fields in the modal
            clearFieldForm();
            // Hide the modal
            $("#addFieldModal").modal('hide');
            showAlert("Field created successfully", 'success');

        },
        error: function (xhr, status, error) {
            console.log(status,error)
           showAlert("Failed to create field:", 'error');

        }
    });
});

function clearFieldForm(){
    // Clear the form fields
    $("#addFieldModal").find("input, textarea, select").val("");

    // If you're previewing images in <img> tags, clear the preview as well
    $("#preview1").attr("src", ""); // Clear first image preview
    $("#preview2").attr("src", ""); // Clear second image preview
}
$(document).ready(function () {
    fetchFields();

    function fetchFields() {
        $.ajax({
            url: 'http://localhost:8080/greenShadow/api/v1/fields',
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            success: function (fields) {
                populateFields(fields);
            },
            error: function (error) {
                console.error("Error fetching fields:", error);
                $(".no-data").show();
            }
        });
    }

    function populateFields(fields) {
        const container = $('.item-container');
        container.empty(); // Clear existing content

        if (fields.length === 0) {
            $(".no-data").show();
            return;
        }

        $(".no-data").hide();

        fields.forEach(field => {
            // Use the Base64 string as the source for each image
            const image1Src = field.image1 ? `data:image/jpeg;base64,${field.image1}` : 'https://via.placeholder.com/600x200?text=Field+Image+1';
            const image2Src = field.image2 ? `data:image/jpeg;base64,${field.image2}` : 'https://via.placeholder.com/600x200?text=Field+Image+2';
            const card = `
                <div class="card-custom data-field-id="${field.fieldId}">
                    <div class="slider" data-slider-index="0">
                      <img src="${image1Src}" class="active" alt="Field Image 1">
                       <img src="${image2Src}" alt="Field Image 2">
                        <div class="slider-buttons">
                            <button class="slider-button prev-button">&#10094;</button>
                            <button class="slider-button next-button">&#10095;</button>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="field-info">
                            <h4>${field.name}</h4>
                            <p>A beautiful field for growing crops.</p>
                        </div>
                        <div class="field-info">
                            <h4>Size</h4>
                            <p>${field.size} Sq. meters</p>
                        </div>
                        <div class="field-info">
                            <h4>Location</h4>
                            <p>GPS Coordinates: ${field.location}</p>
                        </div>
                        <div class="view-more-container">
                            <button type="button" class="btn view-btn" onclick="openFieldModal(${field.fieldId})">View more</button>
                        </div>
                    </div>
                </div>
            `;
            container.append(card);
        });

       // initializeSliders();
        document.querySelectorAll('.slider').forEach((slider, sliderIndex) => {
            const images = slider.querySelectorAll('img');
            let currentImageIndex = 0;

            function showImage(index) {
                images.forEach((img, i) => {
                    img.classList.remove('active');
                    if (i === index) {
                        img.classList.add('active');
                    }
                });
            }

            // Event listener for next button
            slider.querySelector('.next-button').addEventListener('click', () => {
                currentImageIndex = (currentImageIndex + 1) % images.length;
                showImage(currentImageIndex);
            });

            // Event listener for previous button
            slider.querySelector('.prev-button').addEventListener('click', () => {
                currentImageIndex = (currentImageIndex - 1 + images.length) % images.length;
                showImage(currentImageIndex);
            });
        });
    }

    function openFieldModal(fieldId) {
        // Fetch and display additional details for the field in a modal
        $('#fieldDetailModal').modal('show');
    }
});

