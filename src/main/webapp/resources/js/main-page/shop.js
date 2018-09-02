new Vue({
    el: "#mainArea",
    data: {
        rootUrl: "https://immense-springs-86257.herokuapp.com/api/shop",
        shopPage: {},
        filter: {
            name: "",
            categories: [],
            priceFrom: 0,
            priceTo: 0,
            sizes: [],
            sortByField: "id",
            ascOrDesc: "asc"
        },
        itemsQty: "20",
        paginationLinks: [],
        categories: [],
        sizes: [],
        sortFields: [
            "id", "name", "price", "createdAt"
        ]
    },
    methods: {
        getAllProducts(pageNum, itemsQty) {
            if (this.validateFilter()) {
                let filterLink = "";
                filterLink += "&name=" + this.filter.name;
                filterLink += "&priceFrom=" + this.filter.priceFrom;
                filterLink += "&priceTo=" + this.filter.priceTo;
                filterLink += "&sortBy=" + this.filter.sortByField;
                filterLink += "&ascOrDesc=" + this.filter.ascOrDesc;
                filterLink += "&categories=";
                for (let i = 0; i < this.filter.categories.length; i++) {
                    filterLink += this.filter.categories[i];
                    if (i !== this.filter.categories.length - 1) {
                        filterLink += ",";
                    }
                }
                filterLink += "&sizes=";
                for (let i = 0; i < this.filter.sizes.length; i++) {
                    filterLink += this.filter.sizes[i];
                    if (i !== this.filter.sizes.length - 1) {
                        filterLink += ",";
                    }
                }
                axios.get(this.rootUrl + "?size=" + itemsQty
                    + "&page=" + pageNum + filterLink)
                    .then(response => {
                        this.shopPage = response.data;
                        for (i = 0; i < this.shopPage.content.length; i++) {
                            this.shopPage.content[i].createdAt = moment(this.shopPage.content[i].createdAt).format('DD MM YYYY');
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
                name: "",
                categories: [],
                priceFrom: 0,
                priceTo: 0,
                sizes: [],
                sortByField: "id",
                ascOrDesc: "asc"
            }
        },
        updateItemsQty() {
            this.getAllProducts(0, this.itemsQty);
        },
        makePaginationLinks() {
            this.paginationLinks = [];

            for (let i = this.shopPage.number - 2; i < this.shopPage.totalPages && i < this.shopPage.number + 3; i++) {
                if (i >= 0) {
                    this.paginationLinks.push(i);
                }
            }
        },
        validateFilter() {
            if (this.filter.priceFrom > this.filter.priceTo && this.filter.priceTo !== 0) {
                alert("Price from is greater then to");
                return false;
            }
            return true;
        },
        getCategories() {
            axios.get(this.rootUrl + "/categories")
                .then(response => {
                    this.categories = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        getSizes() {
            axios.get(this.rootUrl + "/sizes")
                .then(response => {
                    this.sizes = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        addFilter() {
            this.getAllProducts(0, this.itemsQty);
        }

    },
    mounted() {
        this.getAllProducts(0, this.itemsQty);
        this.getCategories();
        this.getSizes();
    }
});