document.addEventListener('DOMContentLoaded', () => {
    const container = document.querySelector('.container');
    const signupButton = document.querySelector('.signup-section header');
    const loginButton = document.querySelector('.login-section header');
    const signupForm = document.getElementById('signup-form');
    const loginForm = document.getElementById('login-form');

    signupButton.addEventListener('click', () => {
        container.classList.remove('active');
    });

    loginButton.addEventListener('click', () => {
        container.classList.add('active');
    });

    // Login form validation
    let captchaAnswer = '';

    loginForm.addEventListener('submit', (event) => {
        event.preventDefault();
        const email = document.getElementById('login-email').value.trim();
        const password = document.getElementById('login-password').value.trim();
        const captcha = document.getElementById('login-captcha').value.trim();
        let valid = true;

        if (email === '') {
            showError('login-email-error', 'Please fill in your email address.');
            valid = false;
        } else if (!validateEmail(email)) {
            showError('login-email-error', 'Please enter a valid email address.');
            valid = false;
        } else {
            clearError('login-email-error');
        }

        if (password === '') {
            showError('login-password-error', 'Please fill in your password.');
            valid = false;
        } else {
            clearError('login-password-error');
        }

        if (captcha !== captchaAnswer) {
            showError('login-captcha-error', 'Invalid CAPTCHA. Please try again.');
            valid = false;
        } else {
            clearError('login-captcha-error');
        }

        if (valid) {
            // If all validations pass, submit the form
            loginForm.submit();
        }
    });

    // Add a CAPTCHA input field and error message element
    const captchaInput = document.createElement('input');
    captchaInput.type = 'text';
    captchaInput.id = 'login-captcha';
    captchaInput.placeholder = 'Enter CAPTCHA';

    const captchaError = document.createElement('div');
    captchaError.id = 'login-captcha-error';

    loginForm.appendChild(captchaInput);
    loginForm.appendChild(captchaError);

    // Add a CAPTCHA answer display element
    const captchaAnswerElement = document.createElement('div');
    captchaAnswerElement.id = 'login-captcha-answer';
    loginForm.appendChild(captchaAnswerElement);

    // Generate a random CAPTCHA answer
    function generateCaptcha() {
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        captchaAnswer = '';
        for (let i = 0; i < 6; i++) {
            captchaAnswer += chars.charAt(Math.floor(Math.random() * chars.length));
        }

        // Create obfuscated CAPTCHA
        const captchaDisplay = captchaAnswer.split('').map(char => `<span>${char}</span>`).join('');
        captchaAnswerElement.innerHTML = `Enter this CAPTCHA: <span style="user-select: none;">${captchaDisplay}</span>`;
    }

    generateCaptcha();

    // Show error message
    function showError(elementId, message) {
        document.getElementById(elementId).textContent = message;
    }

    // Clear error message
    function clearError(elementId) {
        document.getElementById(elementId).textContent = '';
    }

    // Email validation function
    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
});
