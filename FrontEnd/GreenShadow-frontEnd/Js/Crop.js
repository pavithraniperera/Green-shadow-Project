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