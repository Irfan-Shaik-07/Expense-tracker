<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<footer style="padding: 20px 32px; border-top: 1px solid var(--card-glass-border); text-align: center; color: var(--text-muted); font-size: 13px;">
    <p>© 2026 Expense Tracker Inc. Enterprise Personal Finance Management. Educational content provided for financial literacy.</p>
</footer>

<!-- Floating AI Chatbot Button -->
<button id="aiChatbotToggleBtn" class="ai-fab-btn" title="Ask AI Expense Assistant">
    <i class="fa-solid fa-robot"></i>
</button>

<!-- AI Chatbot Window -->
<div id="aiChatbotWindow" class="ai-chat-window">
    <!-- Header -->
    <div class="ai-chat-header">
        <div class="d-flex align-items-center gap-2">
            <i class="fa-solid fa-robot" style="font-size: 20px;"></i>
            <div>
                <strong style="font-size: 14px; display: block;">Expense AI Assistant</strong>
                <span class="badge bg-success" style="font-size: 9px;">Online</span>
            </div>
        </div>
        <div class="d-flex gap-2">
            <button id="aiChatMinimizeBtn" class="btn p-0 text-white" style="font-size: 14px;"><i class="fa-solid fa-minus"></i></button>
            <button id="aiChatCloseBtn" class="btn p-0 text-white" style="font-size: 14px;"><i class="fa-solid fa-xmark"></i></button>
        </div>
    </div>

    <!-- Messages Area -->
    <div id="chatBodyContainer" style="display: flex; flex-direction: column; flex: 1; overflow: hidden;">
        <div id="chatMessagesContainer" class="chat-messages-stream">
            <div class="chat-msg ai-msg">
                <div class="chat-avatar bg-lavender"><i class="fa-solid fa-robot"></i></div>
                <div class="msg-bubble glass-card py-2 px-3">
                    Hello! I'm your AI Expense Assistant. Ask me questions like <strong>"How much did I spend this month?"</strong>, <strong>"Where did I spend the most?"</strong>, or <strong>"Am I over budget?"</strong>!
                </div>
            </div>
        </div>

        <!-- Typing Indicator -->
        <div id="aiTypingIndicator" class="typing-indicator">
            <i class="fa-solid fa-robot me-1"></i> AI is typing
            <div class="typing-dots"><span></span><span></span><span></span></div>
        </div>

        <!-- Input Area -->
        <div class="p-3 border-top d-flex gap-2" style="background: rgba(0,0,0,0.02);">
            <input type="text" id="aiChatInput" class="form-control-glass" placeholder="Ask about your expenses..." style="font-size: 13px;">
            <button id="aiChatSendBtn" class="btn-glass px-3" style="border-radius: 12px;"><i class="fa-solid fa-paper-plane"></i></button>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/chatbot.js"></script>
