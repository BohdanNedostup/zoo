window.onload = function () {
    document.getElementById("menu-btn").onclick = show;
    initializeScreen();
    initializeMenuLinks();
    user().getUserProcessingOrders();
}
window.onresize = initializeScreen;

function initializeScreen() {
    let menuButton = document.getElementById("menu-btn");
    document.getElementById("body-wrapper").setAttribute("style", "padding-top: " + document.getElementById("header-navbar").offsetHeight + "px;");

    if (window.innerWidth <= 671) {
        if (menuButton.onclick === show || menuButton.onclick === hide) {
            if (menuButton.onclick === hide) {
                hide();
            }
            for (i = 1; i < 7; i++) {
                let item = document.getElementById("menu" + i);
                item.setAttribute("class", "col-12 menuItem");
            }
            document.getElementById("menu1").style.marginTop = "100px";
            if (menuButton.onclick === show) {
                menuButton.onclick = showX;
            }
        }
        if (window.innerWidth <= 650) {
            document.getElementById("header-navbar").setAttribute("class", "navbar navbar-expand-sm fixed-top justify-content-around");
        }
    } else {
        document.getElementById("header-navbar").setAttribute("class", "navbar navbar-expand-sm fixed-top justify-content-between");
        if (menuButton.onclick === showX || menuButton.onclick === hideX) {
            if (menuButton.onclick === hideX) {
                hideX();
            }
            document.getElementsByClassName("menu-row")[0].style.top = "-100%";
            for (i = 1; i < 7; i++) {
                let item = document.getElementById("menu" + i);
                item.setAttribute("class", "col menuItem");
            }
            document.getElementById("menu1").style.marginTop = "0px";

            if (menuButton.onclick === showX) {
                menuButton.onclick = show;
            }
        }
    }
}

function show() {
    for (i = 1; i < 7; i++) {
        // "translateY(calc(" + window.innerHeight + "px + " + window.scrollY + "px + 100px))"
        document.getElementById("menu" + i).style.transform = "translateY(calc(100% + " + window.scrollY + "px))";
    }
    document.getElementById("menu-icon").setAttribute("src", "/resources/img/icons/cancel-music.png");
    document.getElementById("menu-btn").onclick = hide;
}

function hide() {
    for (i = 1; i < 7; i++) {
        document.getElementById("menu" + i).style.transform = "translateY(0%)";
    }
    document.getElementById("menu-icon").setAttribute("src", "/resources/img/icons/three-parallel-lines.png");
    document.getElementById("menu-btn").onclick = show;
}

function showX() {
    document.getElementsByClassName("menu-row")[0].style.top = window.scrollY + "px";
    for (i = 1; i < 7; i++) {
        document.getElementById("menu" + i).style.transform = "translateX(calc(100% + " + window.scrollX + "px))";
    }
    document.getElementById("menu-icon").setAttribute("src", "/resources/img/icons/cancel-music.png");
    document.getElementById("menu-btn").onclick = hideX;
}

function hideX() {
    for (i = 1; i < 7; i++) {
        document.getElementById("menu" + i).style.transform = "translateX(0%)";
    }
    document.getElementById("menu-icon").setAttribute("src", "/resources/img/icons/three-parallel-lines.png");
    document.getElementById("menu-btn").onclick = showX;
}

function initializeMenuLinks() {
    document.getElementById("logo").onclick = function () {
        if (document.getElementById("menu-btn").onclick === hide) {
            hide();
        } else if (document.getElementById("menu-btn").onclick === hideX) {
            hideX();
        }
    };
    let links = document.getElementsByClassName("menu-links");
    for (var i = 0; i < links.length; i++) {
        links[i].onclick = function () {
            if (document.getElementById("menu-btn").onclick === hide) {
                hide();
            } else {
                hideX();
            }
        };
    }
}

function user(){
    return new Vue({
        data: {
            userProcessingOrders: 0
        },
        methods: {
            getUserProcessingOrders(){
                axios.get("https://immense-springs-86257.herokuapp.com/api/shop/orders")
                    .then(response => {
                        this.userProcessingOrders = response.data;
                        changingCartIcon(this.userProcessingOrders);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        mounted(){
            this.getUserProcessingOrders();
        }
    });
}

function changingCartIcon(items){
    if (items !== -1) {
        if (items !== 0) {
            document.getElementById("shopping-cart").setAttribute("src", "/resources/img/icons/shopping-cart-full.png");
        } else {
            document.getElementById("shopping-cart").setAttribute("src", "/resources/img/icons/shopping-cart.png");
        }
    }
}