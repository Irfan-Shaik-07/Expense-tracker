/* Expense AI Assistant Chatbot JavaScript Module */

document.addEventListener('DOMContentLoaded', () => {
    initAIChatbot();
});

function initAIChatbot() {
    const toggleBtn = document.getElementById('aiChatbotToggleBtn');
    const chatWindow = document.getElementById('aiChatbotWindow');
    const closeBtn = document.getElementById('aiChatCloseBtn');
    const minimizeBtn = document.getElementById('aiChatMinimizeBtn');
    const sendBtn = document.getElementById('aiChatSendBtn');
    const chatInput = document.getElementById('aiChatInput');
    const messagesContainer = document.getElementById('chatMessagesContainer');
    const typingIndicator = document.getElementById('aiTypingIndicator');

    if (!toggleBtn || !chatWindow) return;

    // Toggle Chat Window Open / Close
    toggleBtn.addEventListener('click', () => {
        const isOpen = chatWindow.style.display === 'flex';
        chatWindow.style.display = isOpen ? 'none' : 'flex';
        if (!isOpen && chatInput) chatInput.focus();
    });

    if (closeBtn) {
        closeBtn.addEventListener('click', () => {
            chatWindow.style.display = 'none';
        });
    }

    if (minimizeBtn) {
        minimizeBtn.addEventListener('click', () => {
            const body = document.getElementById('chatBodyContainer');
            if (body) body.style.display = (body.style.display === 'none') ? 'flex' : 'none';
        });
    }

    // Send Message Handler
    async function handleSendMessage() {
        const text = chatInput.value.trim();
        if (!text) return;

        // Append User Message
        appendMessage('user', text);
        chatInput.value = '';

        // Show Typing Indicator
        if (typingIndicator) typingIndicator.style.display = 'flex';
        scrollToBottom();

        try {
            const response = await fetch(`${window.location.origin}/api/chat`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ message: text })
            });

            const data = await response.json();

            // Hide Typing Indicator
            if (typingIndicator) typingIndicator.style.display = 'none';

            if (data && data.reply) {
                appendMessage('ai', data.reply);
            } else {
                appendMessage('ai', "I'm having trouble retrieving your expense details right now. Please try again.");
            }
        } catch (err) {
            console.error("AI Chatbot error:", err);
            if (typingIndicator) typingIndicator.style.display = 'none';
            appendMessage('ai', "Sorry, unable to connect to server. Please try again.");
        }
        scrollToBottom();
    }

    if (sendBtn) sendBtn.addEventListener('click', handleSendMessage);

    if (chatInput) {
        chatInput.addEventListener('keydown', (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();
                handleSendMessage();
            }
        });
    }

    function appendMessage(sender, text) {
        if (!messagesContainer) return;
        const msgDiv = document.createElement('div');
        msgDiv.className = `chat-msg ${sender}-msg`;

        let formatted = text.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
        formatted = formatted.replace(/\n/g, '<br>');

        if (sender === 'ai') {
            msgDiv.innerHTML = `
                <div class="chat-avatar bg-lavender"><i class="fa-solid fa-robot"></i></div>
                <div class="msg-bubble glass-card py-2 px-3">${formatted}</div>
            `;
        } else {
            msgDiv.innerHTML = `
                <div class="msg-bubble btn-glass py-2 px-3" style="background: linear-gradient(135deg, #00CFCF, #6A0DAD); color: #fff;">${formatted}</div>
            `;
        }
        messagesContainer.appendChild(msgDiv);
    }

    function scrollToBottom() {
        if (messagesContainer) {
            messagesContainer.scrollTop = messagesContainer.scrollHeight;
        }
    }
}
