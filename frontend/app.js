const API_URL = 'http://localhost:8080/api/attendance';
const tableBody = document.getElementById('attendance-table-body');

// --- 1. Function to Load All Subjects ---
async function loadSubjects() {
    try {
        const response = await fetch(API_URL); // GET request
        const subjects = await response.json();
        renderTable(subjects);
    } catch (error) {
        console.error("Error fetching subjects:", error);
        tableBody.innerHTML = '<tr><td colspan="6">Failed to load attendance data. Is the Spring Boot server running?</td></tr>';
    }
}

// --- 2. Function to Render the Table ---
function renderTable(subjects) {
    tableBody.innerHTML = ''; // Clear existing rows
    subjects.forEach(subject => {
        const row = tableBody.insertRow();
        row.id = `row-${subject.name.replace(/\s/g, '-')}`; // Unique ID for updating
        
        row.insertCell().textContent = subject.name;
        row.insertCell().textContent = subject.totalHours;
        row.insertCell().textContent = subject.classesLeft;
        row.insertCell().textContent = subject.attendancePercentage.toFixed(2); // Display %
        row.insertCell().textContent = subject.attendanceMessage;
        
        // The '+' Button
        const buttonCell = row.insertCell();
        const button = document.createElement('button');
        button.className = 'plus-btn';
        button.textContent = '+';
        // Pass the subject name to the click handler
        button.onclick = () => takeLeave(subject.name); 
        buttonCell.appendChild(button);

        const buttonCell1 = row.insertCell();
        const button1 = document.createElement('button');
        button1.className = 'minus-btn';
        button1.textContent = '-';
        // Pass the subject name to the click handler
        button1.onclick = () => removeLeave(subject.name); 
        buttonCell1.appendChild(button1);
    });
}

// --- 3. Function to Handle '+' Button Click (PUT Request) ---
async function takeLeave(subjectName) {
    const putUrl = `${API_URL}/leave/${subjectName}`;
    try {
        // Send a PUT request to the Spring Boot API
        const response = await fetch(putUrl, {
            method: 'PUT'
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        // Get the updated subject data from the response
        const updatedSubject = await response.json();
        
        // Update only the specific row in the table
        updateTableRow(updatedSubject);

    } catch (error) {
        console.error(`Error taking leave for ${subjectName}:`, error);
        alert(`Failed to take leave for ${subjectName}. Check server logs.`);
    }
}

async function removeLeave(subjectName) {
    const putUrl = `${API_URL}/remove/${subjectName}`;
    try {
        // Send a PUT request to the Spring Boot API
        const response = await fetch(putUrl, {
            method: 'PUT'
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        // Get the updated subject data from the response
        const updatedSubject = await response.json();
        
        // Update only the specific row in the table
        updateTableRow(updatedSubject);

    } catch (error) {
        console.error(`Error taking leave for ${subjectName}:`, error);
        alert(`Failed to take leave for ${subjectName}. Check server logs.`);
    }
}


// --- 4. Function to Update a Single Row ---
function updateTableRow(subject) {
    const row = document.getElementById(`row-${subject.name.replace(/\s/g, '-')}`);
    if (row) {
        // Update the Classes Left cell (index 2)
        row.cells[2].textContent = subject.classesLeft;
        // Update the Percentage cell (index 3)
        row.cells[3].textContent = subject.attendancePercentage.toFixed(2);
        // Update the Message cell (index 4)
        row.cells[4].textContent = subject.attendanceMessage;
    }
}

// --- Initialize the application ---
document.addEventListener('DOMContentLoaded', loadSubjects);