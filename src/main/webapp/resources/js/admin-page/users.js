let adminPageUsers = new Vue({
    el: "#manageArea",
    data: {
        rootUrl: "https://immense-springs-86257.herokuapp.com/api/manage",
        usersPage: {},
        admin: {},
        user: {},
        filter: {
            firstName: "",
            lastName: "",
            email: "",
            birthDateFrom: "",
            birthDateTo: "",
            telephone: "",
            createdAtFrom: "",
            createdAtTo: "",
            country: "",
            roles: [],
            statuses: [],
            genders: [],
            sortByField: "id",
            ascOrDesc: "asc",
            departments: []
        },
        userQty: "20",
        paginationLinks: [],
        countries: [],
        roles: [],
        statuses: [],
        genders: [],
        departments: [],
        sortFields: [
            "id", "firstName", "lastName", "email",
            "birthDate", "createdAt"
        ]
    },
    methods: {
        getAllUsers(pageNum, userQty) {
            if (this.validateFilter()) {
                let filterLink = "";
                filterLink += "&firstName=" + this.filter.firstName;
                filterLink += "&lastName=" + this.filter.lastName;
                filterLink += "&email=" + this.filter.email;
                filterLink += "&birthDateFrom=" + this.filter.birthDateFrom;
                filterLink += "&birthDateTo=" + this.filter.birthDateTo;
                filterLink += "&createdAtFrom=" + this.filter.createdAtFrom;
                filterLink += "&createdAtTo=" + this.filter.createdAtTo;
                filterLink += "&telephone=" + this.filter.telephone;
                filterLink += "&country=" + this.filter.country;
                filterLink += "&sortBy=" + this.filter.sortByField;
                filterLink += "&ascOrDesc=" + this.filter.ascOrDesc;
                filterLink += "&statuses=";
                for (let i = 0; i < this.filter.statuses.length; i++) {
                    filterLink += this.filter.statuses[i];
                    if (i !== this.filter.statuses.length - 1) {
                        filterLink += ",";
                    }
                }
                filterLink += "&roles=";
                for (let i = 0; i < this.filter.roles.length; i++) {
                    filterLink += this.filter.roles[i];
                    if (i !== this.filter.roles.length - 1) {
                        filterLink += ",";
                    }
                }
                filterLink += "&genders=";
                for (let i = 0; i < this.filter.genders.length; i++) {
                    filterLink += this.filter.genders[i];
                    if (i !== this.filter.genders.length - 1) {
                        filterLink += ",";
                    }
                }
                filterLink += "&departments=";
                for (let i = 0; i < this.filter.departments.length; i++) {
                    filterLink += this.filter.departments[i];
                    if (i !== this.filter.departments.length - 1) {
                        filterLink += ",";
                    }
                }
                axios.get(this.rootUrl + "/users?page=" + pageNum +
                    "&size=" + userQty + filterLink)
                    .then(response => {
                        this.usersPage = response.data;
                        for (let i = 0; i < this.usersPage.content.length; i++) {
                            this.usersPage.content[i].birthDate = moment(this.usersPage.content[i].birthDate).format('DD MM YYYY');
                            this.usersPage.content[i].createdAt = moment(this.usersPage.content[i].createdAt).format('DD MM YYYY');
                        }
                        this.makePaginationLinks();
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        getAdmin() {
            axios.get(this.rootUrl + "/get/admin")
                .then(response => {
                    this.admin = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        getCountries() {
            axios.get(this.rootUrl + "/get/countries")
                .then(response => {
                    this.countries = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        getRoles() {
            axios.get(this.rootUrl + "/get/roles")
                .then(response => {
                    this.roles = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        getStatuses() {
            axios.get(this.rootUrl + "/get/statuses")
                .then(response => {
                    this.statuses = response.data;
                })
                .catch(error => {
                    console.log(error)
                })
        },
        getGenders() {
            axios.get(this.rootUrl + "/get/genders")
                .then(response => {
                    this.genders = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        deleteUserById(id) {
            if (window.confirm("Are you sure?")) {
                axios.delete(this.rootUrl + "/users/" + id)
                    .then(response => {
                        this.getAllUsers(this.usersPage.number, this.userQty);
                        console.log(response);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        updateUser(index) {
            this.user = this.usersPage.content[index];
            showEditProfile();
        },
        makePaginationLinks() {
            this.paginationLinks = [];

            for (let i = this.usersPage.number - 2; i < this.usersPage.totalPages && i < this.usersPage.number + 3; i++) {
                if (i >= 0) {
                    this.paginationLinks.push(i);
                }
            }
        },
        updateUserQty() {
            this.getAllUsers(0, this.userQty);
        },
        saveUser() {
            if (window.confirm("Are you sure?") && this.validateWorker()) {
                hideEditProfile();
                let birthDate = this.user.birthDate;
                let createdAt = this.user.createdAt;

                let year = birthDate.substring(birthDate.lastIndexOf(" ") + 1);
                let month = birthDate.substring(birthDate.indexOf(" ") + 1, birthDate.lastIndexOf(" "));
                let day = birthDate.substring(0, birthDate.indexOf(" "));
                this.user.birthDate = moment(year + "-" + month + "-" + day);

                year = createdAt.substring(createdAt.lastIndexOf(" ") + 1);
                month = createdAt.substring(createdAt.indexOf(" ") + 1, createdAt.lastIndexOf(" "));
                day = createdAt.substring(0, createdAt.indexOf(" "));
                this.user.createdAt = moment(year + "-" + month + "-" + day);
                console.log("UPDATE");
                console.log("UPDATE");
                console.log(this.user);

                axios.put(this.rootUrl + "/users/", this.user)
                    .then(response => {
                        this.getAllUsers(this.usersPage.number, this.userQty);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        validateFilter() {
            if (this.filter.birthDateFrom !== "" && this.filter.birthDateTo !== "" && this.filter.birthDateFrom > this.filter.birthDateTo) {
                alert("Incorrect birth dates");
                return false;
            }
            if (isNaN(this.filter.telephone)) {
                alert("Incorrect telephone number");
                return false;
            }
            if (this.filter.createdAtFrom !== "" && this.filter.createdAtTo !== "" && this.filter.createdAtFrom > this.filter.createdAtTo) {
                alert("Incorrect created dates");
                return false;
            }
            return true;
        },
        addFilter() {
            this.getAllUsers(0, this.userQty);
        },
        resetFilter() {
            this.filter = {
                firstName: "",
                lastName: "",
                email: "",
                birthDateFrom: "",
                birthDateTo: "",
                telephone: "",
                createdAtFrom: "",
                createdAtTo: "",
                country: "",
                roles: [],
                statuses: [],
                genders: [],
                sortByField: "id",
                ascOrDesc: "asc",
                departments: []
            }
        },
        validateWorker(){
            if (this.user.userRoles.includes("ROLE_DOCTOR") || this.user.userRoles.includes("ROLE_WORKER")){
                if (this.user.department === "") {
                    alert("user should have department");
                    return false;
                } else return true;
            }
            else if (this.user.department !== ""){
                alert("user can`t have department because not worker or doctor");
                this.user.department = "";
                return false;
            } else return true;
        },
        getDepartments(){
            axios.get("https://immense-springs-86257.herokuapp.com/api/manage/get/departments")
                .then(response => {
                    this.departments = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        }
    },
    mounted() {
        this.getAllUsers(0, this.userQty);
        this.getAdmin();
        this.getCountries();
        this.getRoles();
        this.getStatuses();
        this.getGenders();
        this.getDepartments();
    }
});

function showEditProfile() {
    document.getElementById("profileArea").style.transform = "translateY(calc(" + window.innerHeight + "px + " + window.scrollY + "px + 100px))";
}

function hideEditProfile() {
    document.getElementById("profileArea").style.transform = "translateY(0%)"
}