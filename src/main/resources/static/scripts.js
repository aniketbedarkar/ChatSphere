const messageInput = document.getElementById("messageInput");
messageInput.focus();
let globalHashcode;

document.addEventListener("DOMContentLoaded", function () {
    // Function to scroll the message list to the bottom
    function scrollToBottom() {
        const messageList = document.querySelector('.message-list');
        messageList.scrollTop = messageList.scrollHeight;
    }

    // Scroll to the bottom on page load
    scrollToBottom();
    document.getElementById("chatForm").addEventListener("submit", function (event) {
        // Wait a bit for the DOM to update
        setTimeout(scrollToBottom, 100); // Adjust timeout as needed
    });
});
document.addEventListener("keydown", function (event) {
    const messageInput = document.getElementById("messageInput");

    if (event.key === "Enter") {
        if (document.activeElement !== messageInput) {
            // If the dialog box (textarea) is not focused, focus on it
            event.preventDefault();
            messageInput.focus();
        } else {
            // If the dialog box is already focused, submit the form
            event.preventDefault(); // Prevent newline behavior
            document.getElementById("chatForm").submit();
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
            console.log("check check: ", message.requestHash === currentUserId);
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
            console.log("hashcode from getHashcode: ", data.hashcode);
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
