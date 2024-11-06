document.getElementById('equipmentStatusFilter').addEventListener('change', function() {
    const filter = this.value;
    const rows = document.querySelectorAll('#equipmentTableBody tr');

    rows.forEach(row => {
        const status = row.getAttribute('data-status');
        row.style.display = (filter === 'all' || filter === status) ? '' : 'none';
    });
});

document.getElementById('equipmentSearch').addEventListener('input', function() {
    const searchQuery = this.value.toLowerCase();
    const rows = document.querySelectorAll('#equipmentTableBody tr');

    rows.forEach(row => {
        const name = row.cells[0].textContent.toLowerCase();  // Equipment Name column
        const type = row.cells[1].textContent.toLowerCase();  // Equipment Type column

        row.style.display = (name.includes(searchQuery) || type.includes(searchQuery)) ? '' : 'none';
    });
});
document.querySelectorAll('#equipmentTableBody tr').forEach(row => {
    row.addEventListener('click', () => {
        // Get data from row (this could be retrieved from data attributes or an object)
        /*  const equipmentData = {
              id: row.querySelector('.equipment-id').innerText,
              name: row.querySelector('.equipment-name').innerText,
              type: row.querySelector('.equipment-type').innerText,
              status: row.querySelector('.equipment-status').innerText,
              assignedStaff: row.querySelector('.assigned-staff').innerText,
              assignedField: row.querySelector('.assigned-field').innerText,
              remarks: row.querySelector('.equipment-remarks').innerText,
          };*/

        $('#equipmentDetailModal').modal('show');
    });
});

function toggleEquipmentEditMode() {
    // Close the staffDetailModal
    $('#equipmentDetailModal').modal('hide');

    // Open the addStaffModal
    $('#addEquipmentModal').modal('show');

    // Change the modal header to "Update Member"
    document.getElementById('addEquipmentModalLabel').innerText = 'Update Equipment';

    // Change the button text from "Add Staff" to "Save Changes"
    const addEquipment = document.getElementById('saveEquipmentBtn');
    addEquipment.innerText = 'Save Changes';


}

