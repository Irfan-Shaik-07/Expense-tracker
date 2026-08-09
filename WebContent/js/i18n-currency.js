/* Multilingual i18n & Multi-Currency Engine */

const TRANSLATIONS = {
    en: {
        dashboard: "Dashboard",
        expenses: "Expenses",
        analytics: "Analytics",
        budgetPlanner: "Budget Planner",
        goalTracker: "Goal Tracker",
        insights: "Smart Insights",
        investment: "Investment Learning",
        reports: "Reports",
        settings: "Settings",
        totalExpenses: "Total Expenses",
        monthlyAverage: "Monthly Average",
        remainingBudget: "Remaining Budget",
        savings: "Total Savings",
        healthScore: "Financial Health Score",
        addExpense: "Add Expense",
        quickStats: "Quick Stats"
    },
    hi: {
        dashboard: "डैशबोर्ड",
        expenses: "खर्च",
        analytics: "विश्लेषण",
        budgetPlanner: "बजट योजनाकार",
        goalTracker: "लक्ष्य ट्रैकर",
        insights: "स्मार्ट अंतर्दृष्टि",
        investment: "निवेश सीखें",
        reports: "रिपोर्ट्स",
        settings: "सेटिंग्स",
        totalExpenses: "कुल खर्च",
        monthlyAverage: "मासिक औसत",
        remainingBudget: "शेष बजट",
        savings: "कुल बचत",
        healthScore: "वित्तीय स्वास्थ्य स्कोर",
        addExpense: "खर्च जोड़ें",
        quickStats: "त्वरित आँकड़े"
    },
    te: {
        dashboard: "డాష్‌బోర్డ్",
        expenses: "ఖర్చులు",
        analytics: "విశ్లేషణలు",
        budgetPlanner: "బడ్జెట్ ప్లానర్",
        goalTracker: "లక్ష్యాల ట్రాకర్",
        insights: "స్మార్ట్ పరిశీలనలు",
        investment: "పెట్టుబడి అవగాహన",
        reports: "నివేదికలు",
        settings: "సెట్టింగ్‌లు",
        totalExpenses: "మొత్తం ఖర్చులు",
        monthlyAverage: "నెలవారీ సగటు",
        remainingBudget: "మిగిలిన బడ్జెట్",
        savings: "మొత్తం పొదుపు",
        healthScore: "ఆర్థిక ఆరోగ్య స్కోరు",
        addExpense: "ఖర్చు జతచేయి",
        quickStats: "త్వరిత గణాంకాలు"
    },
    ta: {
        dashboard: "டாஷ்போர்டு",
        expenses: "செலவுகள்",
        analytics: "பகுப்பாய்வு",
        budgetPlanner: "பட்ஜெட் திட்டம்",
        goalTracker: "இலக்கு டிராக்கர்",
        insights: "ஸ்மார்ட் அறிவொளி",
        investment: "முதலீட்டுக் கல்வி",
        reports: "அறிக்கைகள்",
        settings: "அமைப்புகள்",
        totalExpenses: "மொத்த செலவு",
        monthlyAverage: "மாதாந்திர சராசரி",
        remainingBudget: "மீதமுள்ள பட்ஜெட்",
        savings: "மொத்த சேமிப்பு",
        healthScore: "நிதி ஆரோக்கிய மதிப்பெண்",
        addExpense: "செலவு சேர்",
        quickStats: "விரைவு புள்ளிவிவரங்கள்"
    },
    kn: {
        dashboard: "ಡ್ಯಾಶ್‌ಬೋರ್ಡ್",
        expenses: "ಖರ್ಚುಗಳು",
        analytics: "ವಿಶ್ಲೇಷಣೆ",
        budgetPlanner: "ಬಜೆಟ್ ಯೋಜಕ",
        goalTracker: "ಗುರಿ ಟ್ರ್ಯಾಕರ್",
        insights: "ಸ್ಮಾರ್ಟ್ ಒಳನೋಟಗಳು",
        investment: "ಹೂಡಿಕೆ ಕಲಿಕೆ",
        reports: "ವರದಿಗಳು",
        settings: "ಸೆಟ್ಟಿಂಗ್‌ಗಳು",
        totalExpenses: "ಒಟ್ಟು ಖರ್ಚು",
        monthlyAverage: "ಮಾಸಿಕ ಸರಾಸರಿ",
        remainingBudget: "ಉಳಿದ ಬಜೆಟ್",
        savings: "ಒಟ್ಟು ಉಳಿತಾಯ",
        healthScore: "ಹಣಕಾಸು ಆರೋಗ್ಯ ಸ್ಕೋರ್",
        addExpense: "ಖರ್ಚು ಸೇರಿಸಿ",
        quickStats: "ತ್ವರಿತ ಅಂಕಿಅಂಶಗಳು"
    },
    ml: {
        dashboard: "ഡാഷ്‌ബോർഡ്",
        expenses: "ചെലവുകൾ",
        analytics: "വിശകലനം",
        budgetPlanner: "ബജറ്റ് പ്ലാനർ",
        goalTracker: "ലക്ഷ്യ ട്രാക്കർ",
        insights: "സ്മാർട്ട് ഇൻസൈറ്റുകൾ",
        investment: "നിക്ഷേപ പഠനം",
        reports: "റിപ്പോർട്ടുകൾ",
        settings: "ക്രമീകരണങ്ങൾ",
        totalExpenses: "ആകെ ചെലവുകൾ",
        monthlyAverage: "പ്രതിമാസ ശരാശരി",
        remainingBudget: "ബാക്കി ബജറ്റ്",
        savings: "ആകെ സമ്പാദ്യം",
        healthScore: "സാമ്പത്തിക ആരോഗ്യ സ്കോർ",
        addExpense: "ചെലവ് ചേർക്കുക",
        quickStats: "ദ്രുത സ്ഥിതിവിവരക്കണക്കുകൾ"
    },
    mr: {
        dashboard: "डॅशबोर्ड",
        expenses: "खर्च",
        analytics: "विश्लेषण",
        budgetPlanner: "बजेट प्लॅनर",
        goalTracker: "ध्येय ट्रॅकर",
        insights: "स्मार्ट इनसाइट्स",
        investment: "गुंतवणूक शिका",
        reports: "अहवाल",
        settings: "सेटिंग्ज",
        totalExpenses: "एकूण खर्च",
        monthlyAverage: "मासिक सरासरी",
        remainingBudget: "उरलेले बजेट",
        savings: "एकूण बचत",
        healthScore: "आर्थिक आरोग्य स्कोअर",
        addExpense: "खर्च जोडा",
        quickStats: "जलद आकडेवारी"
    },
    bn: {
        dashboard: "ড্যাশবোর্ড",
        expenses: "খরচ",
        analytics: "বিশ্লেষণ",
        budgetPlanner: "বাজেট প্ল্যানার",
        goalTracker: "লক্ষ্য ট্র্যাকার",
        insights: "স্মার্ট ইনসাইট",
        investment: "বিনিয়োগ শিক্ষা",
        reports: "রিপোর্ট",
        settings: "সেটিংস",
        totalExpenses: "মোট খরচ",
        monthlyAverage: "মাসিক গড়",
        remainingBudget: "অবশিষ্ট বাজেট",
        savings: "মোট সঞ্চয়",
        healthScore: "আর্থিক স্বাস্থ্য স্কোর",
        addExpense: "খরচ যোগ করুন",
        quickStats: "দ্রুত পরিসংখ্যান"
    },
    es: {
        dashboard: "Panel Principal",
        expenses: "Gastos",
        analytics: "Análisis",
        budgetPlanner: "Plan de Presupuesto",
        goalTracker: "Metas Financieras",
        insights: "Ideas Inteligentes",
        investment: "Educación de Inversión",
        reports: "Informes",
        settings: "Configuración",
        totalExpenses: "Gastos Totales",
        monthlyAverage: "Promedio Mensual",
        remainingBudget: "Presupuesto Restante",
        savings: "Ahorro Total",
        healthScore: "Puntaje de Salud Financiera",
        addExpense: "Agregar Gasto",
        quickStats: "Estadísticas Rápidas"
    },
    fr: {
        dashboard: "Tableau de bord",
        expenses: "Dépenses",
        analytics: "Analytique",
        budgetPlanner: "Planificateur de budget",
        goalTracker: "Suivi des objectifs",
        insights: "Aperçus intelligents",
        investment: "Apprentissage investissement",
        reports: "Rapports",
        settings: "Paramètres",
        totalExpenses: "Dépenses totales",
        monthlyAverage: "Moyenne mensuelle",
        remainingBudget: "Budget restant",
        savings: "Épargne totale",
        healthScore: "Score de santé financière",
        addExpense: "Ajouter une dépense",
        quickStats: "Statistiques rapides"
    }
};

const CURRENCY_SYMBOLS = {
    INR: '₹', USD: '$', EUR: '€', GBP: '£', JPY: '¥', CAD: 'CA$', AUD: 'A$', SGD: 'S$', AED: 'AED '
};

document.addEventListener('DOMContentLoaded', () => {
    initLangAndCurrencySelectors();
});

function initLangAndCurrencySelectors() {
    const langSelect = document.getElementById('globalLanguageSelect');
    const currSelect = document.getElementById('globalCurrencySelect');

    if (langSelect) {
        langSelect.addEventListener('change', (e) => {
            const lang = e.target.value;
            applyTranslations(lang);
            localStorage.setItem('appLang', lang);
        });
    }

    if (currSelect) {
        currSelect.addEventListener('change', (e) => {
            const curr = e.target.value;
            applyCurrency(curr);
            localStorage.setItem('appCurr', curr);
        });
    }
}

function applyTranslations(lang) {
    const dict = TRANSLATIONS[lang] || TRANSLATIONS.en;
    document.querySelectorAll('[data-i18n]').forEach(el => {
        const key = el.getAttribute('data-i18n');
        if (dict[key]) el.textContent = dict[key];
    });
}

function applyCurrency(curr) {
    const symbol = CURRENCY_SYMBOLS[curr] || '₹';
    document.querySelectorAll('.curr-symbol').forEach(el => {
        el.textContent = symbol;
    });
}
