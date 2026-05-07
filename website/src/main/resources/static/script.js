const words = ["Business", "Brand", "Future", "Growth"];
let wordIndex = 0;
let charIndex = 0;
let deleting = false;

const typingElement = document.querySelector(".typing");

function typeEffect() {
    const currentWord = words[wordIndex];

    if (!deleting) {
        typingElement.textContent = currentWord.substring(0, charIndex + 1);
        charIndex++;

        if (charIndex === currentWord.length) {
            deleting = true;
            setTimeout(typeEffect, 1200);
            return;
        }
    } else {
        typingElement.textContent = currentWord.substring(0, charIndex - 1);
        charIndex--;

        if (charIndex === 0) {
            deleting = false;
            wordIndex = (wordIndex + 1) % words.length;
        }
    }

    setTimeout(typeEffect, deleting ? 70 : 120);
}

typeEffect();

document.querySelectorAll(".section, .service-card").forEach(el => {
    el.classList.add("reveal");
});

window.addEventListener("scroll", () => {
    document.querySelectorAll(".reveal").forEach(el => {
        const top = el.getBoundingClientRect().top;
        if (top < window.innerHeight - 100) {
            el.classList.add("active");
        }
    });
});

function isValidName(name) {
    return /^[A-Za-z\s]{3,}$/.test(name);
}

function isValidPhone(phone) {
    return /^[6-9][0-9]{9}$/.test(phone);
}

function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

/* ENQUIRY FORM VALIDATION */
document.getElementById("enquiryForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const message = document.getElementById("message").value.trim();

    if (!isValidName(name)) {
        alert("Please enter a valid name. Only letters are allowed.");
        return;
    }

    if (!isValidEmail(email)) {
        alert("Please enter a valid email address.");
        return;
    }

    if (!isValidPhone(phone)) {
        alert("Please enter a valid 10 digit phone number.");
        return;
    }

    if (message.length < 10) {
        alert("Please enter your requirement minimum 10 characters.");
        return;
    }

    const data = {
        name: name,
        email: email,
        phone: phone,
        message: message
    };

    const response = await fetch("/enquiry", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });

    alert(await response.text());
    this.reset();
});

/* CAREER FORM VALIDATION */
document.getElementById("careerForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const fullName = this.querySelector("input[name='fullName']").value.trim();
    const email = this.querySelector("input[name='email']").value.trim();
    const phone = this.querySelector("input[name='phone']").value.trim();
    const role = this.querySelector("input[name='role']").value.trim();
    const resume = this.querySelector("input[name='resume']").files[0];

    if (!isValidName(fullName)) {
        alert("Please enter a valid name. Only letters are allowed.");
        return;
    }

    if (!isValidEmail(email)) {
        alert("Please enter a valid email address.");
        return;
    }

    if (!isValidPhone(phone)) {
        alert("Please enter a valid 10 digit phone number.");
        return;
    }

    if (role.length < 2) {
        alert("Please enter applied role.");
        return;
    }

    if (!resume) {
        alert("Please upload your resume.");
        return;
    }

    if (resume.type !== "application/pdf" && !resume.name.toLowerCase().endsWith(".pdf")) {
        alert("Only PDF resume files are allowed.");
        return;
    }

    const formData = new FormData(this);

    const response = await fetch("/career", {
        method: "POST",
        body: formData
    });

    alert(await response.text());
    this.reset();
});
/* =========================
   MOBILE NAVBAR TOGGLE
========================= */
function toggleMenu() {
    const menu = document.getElementById("mobileMenu");
    const toggle = document.querySelector(".menu-toggle");

    menu.classList.toggle("active");
    toggle.classList.toggle("active");
}

function closeMenu() {
    const menu = document.getElementById("mobileMenu");
    const toggle = document.querySelector(".menu-toggle");

    menu.classList.remove("active");
    toggle.classList.remove("active");
}




/* AUTO CLOSE MENU ON SCROLL */

window.addEventListener("scroll", () => {
    closeMenu();
});

/* BUTTON HOVER ANIMATION MOBILE */

document.querySelectorAll(".btn").forEach(btn => {

    btn.addEventListener("touchstart", () => {
        btn.style.transform = "scale(0.96)";
    });

    btn.addEventListener("touchend", () => {
        btn.style.transform = "scale(1)";
    });

});

/* FLOAT CARD TOUCH EFFECT */

document.querySelectorAll(".float-card").forEach(card => {

    card.addEventListener("touchstart", () => {
        card.style.transform = "translateY(-8px) scale(1.04)";
    });

    card.addEventListener("touchend", () => {
        card.style.transform = "translateY(0px) scale(1)";
    });

});

/* SERVICE CARD TOUCH EFFECT */

document.querySelectorAll(".service-card").forEach(card => {

    card.addEventListener("touchstart", () => {
        card.style.transform = "translateY(-10px) scale(1.03)";
    });

    card.addEventListener("touchend", () => {
        card.style.transform = "translateY(0px) scale(1)";
    });

});