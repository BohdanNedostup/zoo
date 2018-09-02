new Vue({
    el: "#newsArea",
    data: {
        rootUrl: "https://immense-springs-86257.herokuapp.com/api/news",
        newsPage: {},
        paginationLinks: []
    },
    methods: {
        getAllNews(pageNum){
            axios.get(this.rootUrl + "?size=20&page=" + pageNum)
                .then(response => {
                    this.newsPage = response.data;
                    for (let i = 0; i < this.newsPage.content.length; i++) {
                        this.newsPage.content[i].createdAt = moment(this.newsPage.content[i].createdAt).format('DD MM YYYY');
                        this.newsPage.content[i].title = this.newsPage.content[i].title.substring(0, 100);
                    }
                    this.makePaginationLinks();
                })
                .catch(error => {
                    console.log(error);
                })
        },
        makePaginationLinks() {
            this.paginationLinks = [];

            for (let i = this.newsPage.number - 2; i < this.newsPage.totalPages && i < this.newsPage.number + 3; i++) {
                if (i >= 0) {
                    this.paginationLinks.push(i);
                }
            }
        },
    },
    mounted() {
        this.getAllNews(0);
    }
});