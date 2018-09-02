new Vue({
    el: "#manageArea",
    data: {
        rootUrl: "https://immense-springs-86257.herokuapp.com/api/animals",
        animalsPage: {},
        animal: {},
        filter: {
            name: "",
            statuses: [],
            genders: [],
            createdAtFrom: "",
            createdAtTo: "",
            sortByField: "id",
            ascOrDesc: "asc",
            departments: []
        },
        animalsQty: "20",
        paginationLinks: [],
        statuses: [],
        genders: [],
        textArea: "description",
        animalTemplate: {
            name: "",
            status: "HEALTHY",
            gender: "MALE",
            department: "Arachnida",
            description: "",
            illnessesHistory: "",
            wikiUrl: ""
        },
        selectedFile: null,
        sortFields: [
            "id", "name", "createdAt"
        ],
        departments: []
    },
    methods: {
        getAllAnimals(pageNum, animalsQty) {
            if (this.validateFilter()) {
                let filterLink = "";
                filterLink += "&name=" + this.filter.name;
                filterLink += "&createdAtFrom=" + this.filter.createdAtFrom;
                filterLink += "&createdAtTo=" + this.filter.createdAtTo;
                filterLink += "&sortBy=" + this.filter.sortByField;
                filterLink += "&ascOrDesc=" + this.filter.ascOrDesc;
                filterLink += "&statuses=";
                for (let i = 0; i < this.filter.statuses.length; i++) {
                    filterLink += this.filter.statuses[i];
                    if (i !== this.filter.statuses.length - 1) {
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
                axios.get(this.rootUrl + "?page=" + pageNum +
                    "&size=" + animalsQty + filterLink)
                    .then(response => {
                        this.animalsPage = response.data;
                        for (i = 0; i < this.animalsPage.content.length; i++) {
                            this.animalsPage.content[i].createdAt = moment(this.animalsPage.content[i].createdAt).format('DD MM YYYY');
                        }
                        this.makePaginationLinks();
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        getStatuses() {
            axios.get(this.rootUrl + "/statuses")
                .then(response => {
                    this.statuses = response.data;
                })
                .catch(error => {
                    console.log(error)
                })
        },
        getGenders() {
            axios.get(this.rootUrl + "/genders")
                .then(response => {
                    this.genders = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        deleteAnimalById(id) {
            if (window.confirm("Are you sure?")) {
                axios.delete(this.rootUrl + "/" + id)
                    .then(response => {
                        this.getAllAnimals(this.animalsPage.number, this.animalsQty);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        updateAnimal(index) {
            this.animal = this.animalsPage.content[index];
            showEditProfile(0);
        },
        makePaginationLinks() {
            this.paginationLinks = [];

            for (let i = this.animalsPage.number - 2; i < this.animalsPage.totalPages && i < this.animalsPage.number + 3; i++) {
                if (i >= 0) {
                    this.paginationLinks.push(i);
                }
            }
        },
        updateAnimalsQty() {
            this.getAllAnimals(0, this.animalsQty);
        },
        saveAnimal() {
            if (window.confirm("Are you sure?") && this.validateUpdateProfile()) {
                hideEditProfile(0);
                let createdAt = this.animal.createdAt;

                let year = createdAt.substring(createdAt.lastIndexOf(" ") + 1);
                let month = createdAt.substring(createdAt.indexOf(" ") + 1, createdAt.lastIndexOf(" "));
                let day = createdAt.substring(0, createdAt.indexOf(" "));
                this.animal.createdAt = moment(year + "-" + month + "-" + day);
                axios.put(this.rootUrl, this.animal)
                    .then(response => {
                        if (this.selectedFile != null) {
                            this.uploadImageToAnimalEntity(this.animal.id)
                        }
                        this.getAllAnimals(this.animalsPage.number, this.animalsQty);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        uploadImage(event) {
            this.selectedFile = event.target.files[0];
        },
        saveNewAnimal() {
            if (window.confirm("Are you sure?")) {
                if (this.animalTemplate.description === "" ||
                    this.animalTemplate.name === "" || this.animalTemplate.wikiUrl === "") {
                    alert("There are empty fields (illnesses history not required)");
                } else {
                    axios.post(this.rootUrl, this.animalTemplate)
                        .then(response => {
                            this.uploadImageToAnimalEntity(response.data);
                            this.getAllAnimals(this.animalsPage.number, this.animalsQty);
                        })
                        .catch(error => {
                            console.log(error);
                        })
                }
            }
        },
        uploadImageToAnimalEntity(id) {
            if (id < 0) {
                alert("There is animal with such name");
            } else {
                hideEditProfile(1);
                let fd = new FormData();
                if (this.selectedFile != null) {
                    fd.append("image", this.selectedFile, "animal" + id + this.selectedFile.name.substring(this.selectedFile.name.lastIndexOf(".")));
                    axios.post(this.rootUrl + "/image/" + id, fd)
                        .then(response => {

                        })
                        .catch(error => {
                            console.log(error);
                        });
                }
            }
            this.selectedFile = null;
        },
        validateFilter() {
            if (this.filter.createdAtFrom !== "" && this.filter.createdAtTo !== "" && this.filter.createdAtFrom > this.filter.createdAtTo) {
                alert("Incorrect created dates");
                return false;
            }
            return true;
        },
        validateUpdateProfile() {
            if (this.animal.description === "") {
                return false;
            } else {
                return true;
            }
        },
        addFilter() {
            this.getAllAnimals(0, this.animalsQty);
        },
        resetFilter() {
            this.filter = {
                name: "",
                statuses: [],
                genders: [],
                createdAtFrom: "",
                createdAtTo: "",
                sortByField: "id",
                ascOrDesc: "asc",
                departments: []
            }
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
        this.getAllAnimals(0, this.animalsQty);
        this.getStatuses();
        this.getGenders();
        this.getDepartments();
    }
});

function showEditProfile(area) {
    document.getElementsByClassName("profileArea")[area].style.transform = "translateY(calc(" + window.innerHeight + "px + " + window.scrollY + "px + 100px))";
}

function hideEditProfile(area) {
    document.getElementsByClassName("profileArea")[area].style.transform = "translateY(0%)"
}