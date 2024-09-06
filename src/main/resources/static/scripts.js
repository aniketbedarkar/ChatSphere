const messageInput = document.getElementById("messageInput");
messageInput.focus();

document.addEventListener('DOMContentLoaded', () => {
    const messageList = document.getElementById('messageList');
    const scrollDownBtn = document.getElementById('scrollDownBtn');

    // Function to scroll to the bottom
    function scrollToBottom() {
        messageList.scrollTop = messageList.scrollHeight;
    }

    // Function to check scroll position and show/hide the button
    function checkScrollPosition() {
        if (messageList.scrollHeight - messageList.scrollTop > messageList.clientHeight + 50) {
            // Show button if not scrolled to the bottom
            scrollDownBtn.style.display = 'block';
        } else {
            // Hide button if scrolled to the bottom
            scrollDownBtn.style.display = 'none';
        }
    }

    // Scroll event listener
    messageList.addEventListener('scroll', checkScrollPosition);

    // Button click event to scroll to the bottom
    scrollDownBtn.addEventListener('click', () => {
        scrollToBottom();
        // Hide the button after scrolling down
        scrollDownBtn.style.display = 'none';
    });

    // Initial check to set the button state
    checkScrollPosition();

    // Initial scroll to bottom (optional)
    scrollToBottom();

});


document.addEventListener("keydown", function (event) {
    const messageInput = document.getElementById("messageInput");

    if (event.key === 'Enter') {
        if (document.activeElement === messageInput) {
            event.preventDefault(); // Prevent newline behavior
            chatForm.requestSubmit(); // Use requestSubmit for better compatibility
        } else {
            // If the input is not focused, focus on it
            event.preventDefault();
            messageInput.focus();
        }
    }

});


async function fetchMessages() {
    try {
        const currentUserId = await getHashcode(); // Await the hashcode

        if (currentUserId === null) {
            console.error('Unable to retrieve current user hashcode');
            return;
        }

        const response = await fetch('/api/v1/message/getMessages');

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const messages = await response.json();
        const messageList = document.getElementById('messageList');
        messageList.innerHTML = ''; // Clear existing messages

        messages.forEach(message => {
            const messageDiv = document.createElement('div');
            messageDiv.className = `message ${message.requestHash === currentUserId ? 'sender' : 'other'}`;

            const color = hashToColor(message.requestHash);

            // Set background color for the message
            messageDiv.style.backgroundColor = color;

            // Create message text element
            const messageText = document.createElement('p');
            messageText.className = 'message-text';
            messageText.textContent = message.text;

            // Create timestamp element
            const messageTime = document.createElement('span');
            messageTime.className = 'message-time';
            messageTime.textContent = formatTimestamp(message.timeStamp);

            // Append text and time to the message div
            messageDiv.appendChild(messageText);
            messageDiv.appendChild(messageTime);

            messageList.appendChild(messageDiv);
        });
    } catch (error) {
        console.error('Error fetching messages:', error);
    }
}

// Initial fetch
fetchMessages();

// Fetch messages every second
setInterval(fetchMessages, 1000);

let colorMap = new Map();
function saveColor(message) {
    colorMap.set(message.requestHash, message.text);
}
function hashToColor(hash) {
    if (colorMap.get(hash) != null) {
        return colorMap.get(hash);
    }
    // Ensure the hash is treated as a number
    let hashValue = parseInt(hash, 10);

    // Ensure hashValue is positive (for simplicity)
    hashValue = Math.abs(hashValue);

    // Convert the hash value to a 6-digit hexadecimal color
    let color = '#' + ('000000' + (hashValue & 0xFFFFFF).toString(16)).slice(-6);

    return color;
}


function formatTimestamp(isoString) {
    const date = new Date(isoString);
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${hours}:${minutes}`; // Format as HH:MM
}


async function getHashcode() {
    try {
        const response = await fetch('/api/v1/message/getHashcode');

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();

        if (data && data.hashcode !== undefined) {
            return data.hashcode; // Return the hashcode
        } else {
            console.error('Unexpected response format:', data);
            return null; // Return null or handle unexpected format
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        return null; // Return null or handle error
    }
}


function adjustLayout() {
    // Get the height of the viewport
    const viewportHeight = window.innerHeight;

    // Get the height of the navbar and editor container (fixed heights)
    const navbarHeight = document.querySelector('.navbar').offsetHeight;
    const editorHeight = document.querySelector('.editor-container').offsetHeight;

    // Calculate the height of the message container
    const messageContainerHeight = viewportHeight - (navbarHeight + editorHeight + 20);

    // Set the height of the message container
    document.querySelector('.message-container').style.height = `${messageContainerHeight}px`;
}

// Run on load
window.addEventListener('load', adjustLayout);

// Run on resize
window.addEventListener('resize', adjustLayout);

document.getElementById('chatForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent the form from submitting the traditional way

    const form = event.target;
    const formData = new FormData(form);

    try {
        const response = await fetch(form.action, {
            method: form.method,
            body: formData,
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const result = await response.json();
        console.log('Success:', result);

        // Optionally, you can clear the input field after successful submission
        document.getElementById('messageInput').value = '';
        // Handle success (e.g., show a message to the user)

    } catch (error) {
        console.error('Error:', error);
        // Handle error (e.g., show an error message to the user)
    }
    try {
        await fetchMessages();
    } catch (error) {
        console.error("Fetch from send error: ",error);
    }
    
    try {
        const messageList = document.getElementById('messageList');
        messageList.scrollTop = messageList.scrollHeight;
    } catch (error) {
        console.error('Scroll To Bottom Error:', error);
    }
});