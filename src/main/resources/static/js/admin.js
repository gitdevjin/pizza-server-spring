document.addEventListener("DOMContentLoaded", () => {
    initApp();
})

const initApp = () => {
    const headerMenu = document.querySelector(".header__menu");
    headerMenu.addEventListener("click", toggleHeaderMenu);
}

const toggleHeaderMenu = () => {
    const headerBurger = document.querySelector(".header__burger");
    const menuBox = document.querySelector(".menu-box");
    headerBurger.classList.toggle("active");
    menuBox.classList.toggle("active");
}