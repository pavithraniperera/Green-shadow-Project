// JavaScript for Image Slider
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
function toggleEditMode() {
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