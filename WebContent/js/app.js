/* Expense Tracker - Core App UI Handler */

document.addEventListener('DOMContentLoaded', () => {
    initTheme();
    initSidebar();
    initRipples();
    initCounterAnimations();
});

// 1. Theme Toggle System
function initTheme() {
    const savedTheme = localStorage.getItem('appTheme') || 'light';
    document.documentElement.setAttribute('data-theme', savedTheme);
    const themeBtn = document.getElementById('themeToggleBtn');
    if (themeBtn) {
        themeBtn.innerHTML = savedTheme === 'dark' ? '<i class="fa-solid fa-sun"></i>' : '<i class="fa-solid fa-moon"></i>';
        themeBtn.addEventListener('click', () => {
            const current = document.documentElement.getAttribute('data-theme');
            const next = current === 'dark' ? 'light' : 'dark';
            document.documentElement.setAttribute('data-theme', next);
            localStorage.setItem('appTheme', next);
            themeBtn.innerHTML = next === 'dark' ? '<i class="fa-solid fa-sun"></i>' : '<i class="fa-solid fa-moon"></i>';
            showToast(`Switched to ${next} theme mode`, 'info');
        });
    }
}

// 2. Sidebar Collapse Handler
function initSidebar() {
    const toggleBtn = document.getElementById('sidebarToggleBtn');
    const sidebar = document.querySelector('.sidebar');
    if (toggleBtn && sidebar) {
        toggleBtn.addEventListener('click', () => {
            sidebar.classList.toggle('collapsed');
        });
    }
}

// 3. Ripple Effect on Buttons
function initRipples() {
    document.querySelectorAll('.btn-glass, .btn-glass-cyan, .fab-btn').forEach(button => {
        button.addEventListener('click', function (e) {
            const circle = document.createElement('span');
            const diameter = Math.max(button.clientWidth, button.clientHeight);
            const radius = diameter / 2;

            const rect = button.getBoundingClientRect();
            circle.style.width = circle.style.height = `${diameter}px`;
            circle.style.left = `${e.clientX - rect.left - radius}px`;
            circle.style.top = `${e.clientY - rect.top - radius}px`;
            circle.classList.add('ripple-span');

            const ripple = button.querySelector('.ripple-span');
            if (ripple) ripple.remove();

            button.appendChild(circle);
        });
    });
}

// 4. Counter Animation for Stat Cards
function initCounterAnimations() {
    const counters = document.querySelectorAll('.animate-counter');
    counters.forEach(counter => {
        const target = +counter.getAttribute('data-target');
        const duration = 1200; // 1.2s
        const step = target / (duration / 16);
        let current = 0;

        const updateCounter = () => {
            current += step;
            if (current < target) {
                counter.innerText = Math.ceil(current).toLocaleString();
                requestAnimationFrame(updateCounter);
            } else {
                counter.innerText = target.toLocaleString();
            }
        };
        updateCounter();
    });
}

// 5. Toast Notification Toast System
function showToast(message, type = 'info') {
    let container = document.querySelector('.toast-container');
    if (!container) {
        container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast-glass ${type}`;

    let icon = 'fa-circle-info';
    if (type === 'success') icon = 'fa-circle-check';
    if (type === 'warning') icon = 'fa-triangle-exclamation';
    if (type === 'danger') icon = 'fa-circle-xmark';

    toast.innerHTML = `
        <i class="fa-solid ${icon}" style="font-size: 20px;"></i>
        <div style="flex: 1;">${message}</div>
    `;

    container.appendChild(toast);

    setTimeout(() => {
        toast.style.animation = 'fadeOut 0.4s forwards';
        setTimeout(() => toast.remove(), 400);
    }, 3500);
}
