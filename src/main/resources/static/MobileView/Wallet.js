// Load the Bank and UPI accounts list
async function loadBankAndUpiAccounts() {
    try {
      const userId = localStorage.getItem('userId');
      if (!userId) {
        console.log('User ID is missing. Please log in again.');
        return;
      }
  
      const response = await fetch(`http://localhost:8080/api/customer/bankAccounts/${userId}`);
      const data = await response.json();
  
      if (data.message === "Bank accounts/UPIs retrieved successfully.") {
        // Clear previous data
        document.getElementById('saved-bank-accounts').innerHTML = '';
        document.getElementById('saved-upi-accounts').innerHTML = '';
  
        // Loop through and add bank/UPI accounts
        data.bankAccounts.forEach(account => {
          if (account.bankType === "BankAccount") {
            const bankCard = document.createElement('div');
            bankCard.classList.add('saved-bank-card');
            const statusClass = account.status === 'Active' ? 'status-active' : 'status-inactive';
            bankCard.innerHTML = `
              <div class="saved-bank-details">
                <p>Bank Name: ${account.bankName}</p>
                <p>Bank IFSC: ${account.bankIFSC}</p>
                <p>Account Number: ${account.bankAccountNumber}</p>
                <p class="status ${statusClass}">Status: ${account.status}</p>
              </div>
              <div class="saved-bank-actions">
              <button class="btn btn-sm btn-warning edit-btn" data-id="${account.bankId}" data-type="BankAccount">Edit</button>
                <button class="btn btn-sm btn-danger edit-delete-btn" onclick="deleteBankAccount(${account.bankId})">Delete</button>
                <button class="btn btn-sm btn-warning edit-delete-btn" onclick="toggleBankStatus(${account.bankId}, '${account.status}')">
                  ${account.status === 'Active' ? 'Deactivate' : 'Activate'}
                </button>
              </div>
            `;
            document.getElementById('saved-bank-accounts').appendChild(bankCard);
          } else if (account.bankType === "UPI") {
            const upiCard = document.createElement('div');
            upiCard.classList.add('saved-upi-card');
            const statusClass = account.status === 'Active' ? 'status-active' : 'status-inactive';
            upiCard.innerHTML = `
              <div class="saved-upi-details">
                <p>UPI ID: ${account.upiId}</p>
                <p>UPI Name: ${account.upiName}</p>
                <p class="status ${statusClass}">Status: ${account.status}</p>
              </div>
              <div class="saved-upi-actions">
              <button class="btn btn-sm btn-warning edit-btn" data-id="${account.bankId}" data-type="UPI">Edit</button>
                <button class="btn btn-sm btn-danger edit-delete-btn" onclick="deleteUpiAccount(${account.bankId})">Delete</button>
                <button class="btn btn-sm btn-warning edit-delete-btn" onclick="toggleUpiStatus(${account.bankId}, '${account.status}')">
                  ${account.status === 'Active' ? 'Deactivate' : 'Activate'}
                </button>
              </div>
            `;
            document.getElementById('saved-upi-accounts').appendChild(upiCard);
          }
        });
  
        // Add buttons for adding accounts
        document.querySelectorAll('.edit-btn').forEach(button => {
            button.addEventListener('click', (e) => openEditModal(e));
          });
        addAccountButtons();
      } else {
        console.log("Failed to retrieve bank accounts or UPI accounts.");
      }
    } catch (error) {
      console.log("An error occurred while fetching bank and UPI accounts.");
      console.error(error);
    }
  }
  function addAccountButtons() {
    const addBankButton = document.createElement('button');
    addBankButton.classList.add('btn', 'btn-success', 'btn-block');
    addBankButton.setAttribute('data-bs-toggle', 'modal');
    addBankButton.setAttribute('data-bs-target', '#addBankModal');
    addBankButton.textContent = 'Add Bank Account';
    document.getElementById('saved-bank-accounts').appendChild(addBankButton);
  
    const addUpiButton = document.createElement('button');
    addUpiButton.classList.add('btn', 'btn-success', 'btn-block');
    addUpiButton.setAttribute('data-bs-toggle', 'modal');
    addUpiButton.setAttribute('data-bs-target', '#addUpiModal');
    addUpiButton.textContent = 'Add UPI Account';
    document.getElementById('saved-upi-accounts').appendChild(addUpiButton);
  }
  
  
// Function to open the modal and populate form fields
function openEditModal(event) {
    const button = event.currentTarget;
    const bankId = button.getAttribute('data-id'); // Get the bankId
    const bankType = button.getAttribute('data-type'); // Get the bankType (BankAccount/UPI)
  
    // Fetch account details using bankId (simulate with mock data or API call)
    const accountDetails = getAccountDetails(bankId); // Replace with an actual API call
  
    // Populate common hidden fields
    document.getElementById('editUserId').value = accountDetails.userId;
    document.getElementById('editBankId').value = bankId;
  
    // Populate Bank Type and toggle field visibility
    document.getElementById('editBankType').value = bankType;
    toggleFields(bankType);
  
    // Populate Bank Account Fields (if applicable)
    if (bankType === 'BankAccount') {
      document.getElementById('editBankName').value = accountDetails.bankName || '';
      document.getElementById('editBankIFSC').value = accountDetails.bankIFSC || '';
      document.getElementById('editBankAccountNumber').value = accountDetails.bankAccountNumber || '';
    }
  
    // Populate UPI Account Fields (if applicable)
    if (bankType === 'UPI') {
      document.getElementById('editUpiId').value = accountDetails.upiId || '';
      document.getElementById('editUpiName').value = accountDetails.upiName || '';
      document.getElementById('editUpiNumber').value = accountDetails.upiNumber || '';
    }
  
    // Populate Status Field
    document.getElementById('editStatus').value = accountDetails.status || 'Active';
  
    // Show Modal
    const modal = new bootstrap.Modal(document.getElementById('editAccountModal'));
    modal.show();
  }
  
  // Function to toggle field visibility based on bank type
  function toggleFields(bankType) {
    const bankFields = document.querySelector('.bank-account-fields');
    const upiFields = document.querySelector('.upi-account-fields');
  
    if (bankType === 'BankAccount') {
      bankFields.style.display = 'block';
      upiFields.style.display = 'none';
    } else {
      bankFields.style.display = 'none';
      upiFields.style.display = 'block';
    }
  }
  
  // Simulate fetching account details (Replace with actual API call)
  function getAccountDetails(bankId) {
    // Mock data for demonstration
    const mockAccounts = {
      1: { userId: 101, bankName: 'ABC Bank', bankIFSC: 'ABC123456', bankAccountNumber: '1234567890', status: 'Active' },
      2: { userId: 102, upiId: 'user@upi', upiName: 'John Doe', upiNumber: '9876543210', status: 'Inactive' },
    };
  
    return mockAccounts[bankId] || {};
  }
  
  // Add event listeners to all edit buttons
  document.querySelectorAll('.edit-btn').forEach(button => {
    button.addEventListener('click', openEditModal);
  });
  
  // Handle form submission
  document.getElementById('editAccountForm').addEventListener('submit', (e) => {
    e.preventDefault(); // Prevent default form submission
  
    // Collect form data
    const formData = new FormData(e.target);
  
    // Convert formData to JSON (for API submission)
    const accountData = Object.fromEntries(formData.entries());
  
    // Submit the updated data via API (mock implementation here)
    console.log('Submitting account data:', accountData);
  
    // Close modal (simulate success)
    const modal = bootstrap.Modal.getInstance(document.getElementById('editAccountModal'));
    modal.hide();
  
    // Refresh account list or provide feedback
    alert('Account updated successfully!');
  });
  
  // Delete Bank Account
  async function deleteBankAccount(bankId) {
    try {
      const userId = localStorage.getItem('userId');
      if (!userId) {
        console.log('User ID is missing. Please log in again.');
        return;
      }
  
      const response = await fetch(`http://localhost:8080/api/customer/bankAccounts/${userId}/${bankId}`, {
        method: 'DELETE',
      });
  
      const data = await response.json();
  
      if (data.message) {
        alert(data.message); // Show success message
        loadBankAndUpiAccounts(); // Refresh accounts list
      } else {
        alert('Failed to delete bank account.');
      }
    } catch (error) {
      console.error('Error deleting bank account:', error);
      alert('An error occurred while deleting the bank account.');
    }
  }
  // Toggle Bank Account Status
async function toggleBankStatus(bankId, currentStatus) {
    try {
      const userId = localStorage.getItem('userId');
      if (!userId) {
        console.log('User ID is missing. Please log in again.');
        return;
      }
  
      const response = await fetch(`http://localhost:8080/api/customer/bankAccounts/status/${userId}/${bankId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        }
      });
  
      const data = await response.json();
  
      if (data.message) {
        alert(data.message);
        loadBankAndUpiAccounts(); // Refresh the accounts list
      } else {
        alert('Failed to update bank account status.');
      }
    } catch (error) {
      console.error('Error toggling bank account status:', error);
      alert('An error occurred while updating the bank account status.');
    }
  }
  
  // Toggle UPI Account Status
  async function toggleUpiStatus(bankId, currentStatus) {
    try {
      const userId = localStorage.getItem('userId');
      if (!userId) {
        console.log('User ID is missing. Please log in again.');
        return;
      }
  
      const response = await fetch(`http://localhost:8080/api/customer/bankAccounts/status/${userId}/${bankId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        }
      });
  
      const data = await response.json();
  
      if (data.message) {
        alert(data.message);
        loadBankAndUpiAccounts(); // Refresh the accounts list
      } else {
        alert('Failed to update UPI account status.');
      }
    } catch (error) {
      console.error('Error toggling UPI account status:', error);
      alert('An error occurred while updating the UPI account status.');
    }
  }
  // Delete UPI Account
  async function deleteUpiAccount(bankId) {
    try {
      const userId = localStorage.getItem('userId');
      if (!userId) {
        console.log('User ID is missing. Please log in again.');
        return;
      }
  
      const response = await fetch(`http://localhost:8080/api/customer/bankAccounts/${userId}/${bankId}`, {
        method: 'DELETE',
      });
  
      const data = await response.json();
  
      if (data.message) {
        alert(data.message); // Show success message
        loadBankAndUpiAccounts(); // Refresh accounts list
      } else {
        alert('Failed to delete UPI account.');
      }
    } catch (error) {
      console.error('Error deleting UPI account:', error);
      alert('An error occurred while deleting the UPI account.');
    }
  }
  
  // Add Bank Account
  document.querySelector('#addBankModal .btn-primary').addEventListener('click', async () => {
    const bankName = document.getElementById('bankName').value;
    const accountNumber = document.getElementById('accountNumber').value;
    const ifscCode = document.getElementById('ifscCode').value;
  
    if (!bankName || !accountNumber || !ifscCode) {
      alert('Please fill out all fields.');
      return;
    }
  
    const userId = localStorage.getItem('userId');
    if (!userId) {
      console.error('User ID is missing. Please log in again.');
      return;
    }
  
    const bankAccountData = {
      bankType: "BankAccount",
      bankName: bankName,
      bankIFSC: ifscCode,
      bankAccountNumber: accountNumber,
      status: "Active"
    };
  
    try {
      const response = await fetch(`http://localhost:8080/api/customer/bankAccounts/${userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(bankAccountData)
      });
  
      const data = await response.json();
      if (data.message === "Bank account/UPI added successfully.") {
        alert('Bank Account added successfully');
        loadBankAndUpiAccounts(); // Refresh the bank accounts list
        const modal = new bootstrap.Modal(document.getElementById('addBankModal'));
        modal.hide(); // Close the modal
      } else {
        alert('Failed to add bank account');
      }
    } catch (error) {
      console.error('Error adding bank account:', error);
      alert('An error occurred while adding the bank account.');
    }
  });
  
  // Add UPI Account
  document.querySelector('#addUpiModal .btn-primary').addEventListener('click', async () => {
    const upiId = document.getElementById('upiId').value;
    const upiName = document.getElementById('upiName').value;
    const upiNumber = document.getElementById('upiNumber').value;
  
    if (!upiId || !upiName || !upiNumber) {
      alert('Please fill out all fields.');
      return;
    }
  
    const userId = localStorage.getItem('userId');
    if (!userId) {
      console.error('User ID is missing. Please log in again.');
      return;
    }
  
    const upiAccountData = {
      bankType: "UPI",
      upiId: upiId,
      upiName: upiName,
      upiNumber: upiNumber,
      status: "Active"
    };
  
    try {
      const response = await fetch(`http://localhost:8080/api/customer/bankAccounts/${userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(upiAccountData)
      });
  
      const data = await response.json();
      if (data.message === "Bank account/UPI added successfully.") {
        alert('UPI Account added successfully');
        loadBankAndUpiAccounts(); // Refresh the UPI accounts list
        const modal = new bootstrap.Modal(document.getElementById('addUpiModal'));
        modal.hide(); // Close the modal
      } else {
        alert('Failed to add UPI account');
      }
    } catch (error) {
      console.error('Error adding UPI account:', error);
      alert('An error occurred while adding the UPI account.');
    }
  });
  
  window.addEventListener('load', loadBankAndUpiAccounts);