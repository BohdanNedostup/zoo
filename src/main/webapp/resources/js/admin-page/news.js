new Vue({
    el: "#manageArea",
    data: {
        rootUrl: "https://immense-springs-86257.herokuapp.com/api/manage/news",
        newsPage: {},
        news: {},
        filter: {
            title: "",
            createdAtFrom: "",
            createdAtTo: "",
            sortByField: "id",
            ascOrDesc: "asc"
        },
        sortFields: [
            "id", "title", "createdAt"
        ],
        paginationLinks: [],
        newsQty: "20",
        selectedFile: null,
        newsTemplate: {
            title: "",
            text: ""
        }
    },
    methods: {
        getAllNews(pageNum, newsQty) {
            if (this.validateFilter()) {
                let filterLink = "";
                filterLink += "&title=" + this.filter.title;
                filterLink += "&createdAtFrom=" + this.filter.createdAtFrom;
                filterLink += "&createdAtTo=" + this.filter.createdAtTo;
                filterLink += "&sortBy=" + this.filter.sortByField;
                filterLink += "&ascOrDesc=" + this.filter.ascOrDesc;
                axios.get(this.rootUrl + "?pageSize=" + newsQty +
                    "pageNum" + pageNum + filterLink)
                    .then(response => {
                        this.newsPage = response.data;
                        for (let i = 0; i < this.newsPage.content.length; i++) {
                            this.newsPage.content[i].createdAt = moment(this.newsPage.content[i].createdAt).format('DD MM YYYY');
                        }
                        this.makePaginationLinks();
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        resetFilter() {
            this.filter = {
                title: "",
                createdAtFrom: "",
                createdAtTo: "",
                sortByField: "id",
                ascOrDesc: "asc"
            }
        },
        addFilter(){
            this.getAllNews(0, this.newsQty);
        },
        updateNewsQty(){
            this.getAllNews(0, this.newsQty);
        },
        validateFilter() {
            if (this.filter.createdAtFrom !== "" && this.filter.createdAtTo !== "" && this.filter.createdAtFrom > this.filter.createdAtTo) {
                alert("Incorrect created dates!");
                return false;
            }
            return true;
        },
        makePaginationLinks() {
            this.paginationLinks = [];

            for (let i = this.newsPage.number - 2; i < this.newsPage.totalPages && i < this.newsPage.number + 3; i++) {
                if (i >= 0) {
                    this.paginationLinks.push(i);
                }
            }
        },
        deleteNewsById(id) {
            if (window.confirm("Are you sure?")) {
                axios.delete(this.rootUrl + "?id=" + id)
                    .then(response => {
                        this.getAllNews(this.newsPage.number, this.newsQty);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        saveNews() {
            if (window.confirm("Are you sure?") && this.validateUpdateNews()) {
                hideEditProfile(0);
                let createdAt = this.news.createdAt;

                let year = createdAt.substring(createdAt.lastIndexOf(" ") + 1);
                let month = createdAt.substring(createdAt.indexOf(" ") + 1, createdAt.lastIndexOf(" "));
                let day = createdAt.substring(0, createdAt.indexOf(" "));
                this.news.createdAt = moment(year + "-" + month + "-" + day);
                axios.put(this.rootUrl, this.news)
                    .then(response => {
                        if (this.selectedFile != null) {
                            this.uploadImageToNewsEntity(this.news.id)
                        }
                        this.getAllNews(this.newsPage.number, this.newsQty);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        saveNewNews(){
            if (window.confirm("Are you sure?")) {
                if (this.newsTemplate.title === "" ||
                    this.newsTemplate.text === "") {
                    alert("There are empty fields");
                } else {
                    hideEditProfile(1);
                    axios.post(this.rootUrl, this.newsTemplate)
                        .then(response => {
                            this.uploadImageToNewsEntity(response.data);
                            this.getAllNews(this.news.number, this.newsQty);
                        })
                        .catch(error => {
                            console.log(error);
                        })
                }
            }
        },
        validateUpdateNews(){
            if (this.news.text === "" || this.news.title === ""){
                return false;
            }
            return true;
        },
        uploadImageToNewsEntity(id) {
            if (id < 0) {
                alert("There is news with such title");
            } else {
                let fd = new FormData();
                if (this.selectedFile != null) {
                    fd.append("image", this.selectedFile, "news" + id + this.selectedFile.name.substring(this.selectedFile.name.lastIndexOf(".")));
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
        uploadImage(event) {
            this.selectedFile = event.target.files[0];
        },
        updateNews(index) {
            this.news = this.newsPage.content[index];
            showEditProfile(0);
        },
    },
    mounted() {
        this.getAllNews(0, this.newsQty);
    }
});

function showEditProfile(area) {
    document.getElementsByClassName("newsArea")[area].style.transform = "translateY(calc(" + window.innerHeight + "px + " + window.scrollY + "px + 100px))";
}

function hideEditProfile(area) {
    document.getElementsByClassName("newsArea")[area].style.transform = "translateY(0%)"
}