<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar-glass">
    <div style="display: flex; align-items: center; gap: 16px;">
        <button id="sidebarToggleBtn" class="btn-outline-glass" style="padding: 8px 12px; border-radius: 8px;">
            <i class="fa-solid fa-bars"></i>
        </button>
        <h4 class="text-gradient" style="margin: 0; font-weight: 600;">Expense Tracker</h4>
    </div>

    <div class="nav-actions">
        <!-- Currency Selector -->
        <select id="globalCurrencySelect" class="form-control-glass" style="width: auto; padding: 6px 12px; font-size: 13px;">
            <option value="INR">🇮🇳 INR (₹)</option>
            <option value="USD">🇺🇸 USD ($)</option>
            <option value="EUR">🇪🇺 EUR (€)</option>
            <option value="GBP">🇬🇧 GBP (£)</option>
            <option value="JPY">🇯🇵 JPY (¥)</option>
            <option value="CAD">🇨🇦 CAD (CA$)</option>
            <option value="AUD">🇦🇺 AUD (A$)</option>
            <option value="SGD">🇸🇬 SGD (S$)</option>
            <option value="AED">🇦🇪 AED (AED)</option>
        </select>

        <!-- Language Selector -->
        <select id="globalLanguageSelect" class="form-control-glass" style="width: auto; padding: 6px 12px; font-size: 13px;">
            <option value="en">English</option>
            <option value="hi">हिंदी (Hindi)</option>
            <option value="te">తెలుగు (Telugu)</option>
            <option value="ta">தமிழ் (Tamil)</option>
            <option value="kn">ಕನ್ನಡ (Kannada)</option>
            <option value="ml">മലയാളം (Malayalam)</option>
            <option value="mr">मराठी (Marathi)</option>
            <option value="bn">বাংলা (Bengali)</option>
            <option value="es">Español</option>
            <option value="fr">Français</option>
        </select>

        <!-- Dark / Light Theme Toggle -->
        <button id="themeToggleBtn" class="btn-outline-glass" style="padding: 8px 12px; border-radius: 50%;">
            <i class="fa-solid fa-moon"></i>
        </button>

        <!-- User Profile Pill -->
        <div class="user-profile-pill">
            <img src="${pageContext.request.contextPath}/assets/images/default-avatar.png" onerror="this.src='https://ui-avatars.com/api/?name=Alex+Morgan&background=B57EDC&color=fff'" class="user-avatar" alt="Avatar">
            <span style="font-weight: 500; font-size: 14px;">Alex Morgan</span>
        </div>
    </div>
</nav>
