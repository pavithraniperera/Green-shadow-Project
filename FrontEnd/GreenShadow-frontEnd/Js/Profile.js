function toggleProfileEditMode() {
    const inputs = document.querySelectorAll('.profile-field input, .profile-field select');
    inputs.forEach(input => {
        if (input.id !== 'role') { // Skip the role field
            input.readOnly = !input.readOnly;
        }
    });
    document.getElementById('saveChangesBtn').style.display = 'inline';
    document.getElementById('editProfileBtn').style.display = 'none';
}


function saveProfile() {
    // Logic to save profile
    alert("Profile changes saved.");
}

function deleteAccount() {
    // Logic to delete account
    alert("Account deleted.");
}
