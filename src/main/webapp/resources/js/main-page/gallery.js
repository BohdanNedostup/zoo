new Vue({
    el: "#mainArea",
    data: {
        rootUrl: "https://immense-springs-86257.herokuapp.com/api/gallery",
        galleryPage: {},
        filter: {
            name: "",
            sortByField: "id",
            ascOrDesc: "asc",
            departments: []
        },
        animalsQty: "20",
        paginationLinks: [],
        sortFields: [
            "id", "name"
        ],
        departments: []
    },
    methods: {
        getAllAnimals(pageNum, animalsQty) {
            let filterLink = "";
            filterLink += "&name=" + this.filter.name;
            filterLink += "&sortBy=" + this.filter.sortByField;
            filterLink += "&ascOrDesc=" + this.filter.ascOrDesc;
            filterLink += "&departments=";
            for (let i = 0; i < this.filter.departments.length; i++) {
                filterLink += this.filter.departments[i];
                if (i !== this.filter.departments.length - 1) {
                    filterLink += ",";
                }
            }
            axios.get(this.rootUrl + "?size=" + animalsQty
                + "&page=" + pageNum + filterLink)
                .then(response => {
                    this.galleryPage = response.data;
                    this.makePaginationLinks();
                })
                .catch(error => {
                    console.log(error);
                })
        },
        addFilter() {
            this.getAllAnimals(0, this.animalsQty);
        },
        updateAnimalsQty() {
            this.getAllAnimals(0, this.animalsQty);
        },
        resetFilter() {
            this.filter = {
                name: "",
                sortByField: "id",
                ascOrDesc: "asc",
                departments: []
            };
        },
        makePaginationLinks() {
            this.paginationLinks = [];

            for (let i = this.galleryPage.number - 2; i < this.galleryPage.totalPages && i < this.galleryPage.number + 3; i++) {
                if (i >= 0) {
                    this.paginationLinks.push(i);
                }
            }
        },
        getDepartments(){
            axios.get(this.rootUrl + "/departments")
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
        this.getDepartments();
    }
});