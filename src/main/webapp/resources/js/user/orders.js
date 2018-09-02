new Vue({
    el: "#mainArea",
    data: {
        rootUrl: "https://immense-springs-86257.herokuapp.com/api/user/orders",
        filter: {
            statuses: [],
            summaryPriceFrom: 0,
            summaryPriceTo: 0,
            createdAtFrom: "",
            createdAtTo: "",
            sortByField: "id",
            ascOrDesc: "asc"
        },
        ordersPage: {},
        ordersQty: "20",
        paginationLinks: [],
        statuses: [],
        sortFields: [
            "id", "productQty", "summaryPrice", "createdAt"
        ]
    },
    methods: {
        getAllOrders(pageNum, ordersQty) {
            if (this.validateFilter()) {
                let filterLink = "";
                filterLink += "&createdAtFrom=" + this.filter.createdAtFrom;
                filterLink += "&createdAtTo=" + this.filter.createdAtTo;
                filterLink += "&summaryPriceFrom=" + this.filter.summaryPriceFrom;
                filterLink += "&summaryPriceTo=" + this.filter.summaryPriceTo;
                filterLink += "&sortBy=" + this.filter.sortByField;
                filterLink += "&ascOrDesc=" + this.filter.ascOrDesc;
                filterLink += "&statuses=";
                for (let i = 0; i < this.filter.statuses.length; i++) {
                    filterLink += this.filter.statuses[i];
                    if (i !== this.filter.statuses.length - 1) {
                        filterLink += ",";
                    }
                }
                axios.get(this.rootUrl + "?size=" + ordersQty + "&page=" + pageNum + filterLink)
                    .then(response => {
                        this.ordersPage = response.data;

                        for (let i = 0; i < this.ordersPage.content.length; i++) {
                            this.ordersPage.content[i].createdAt = moment(this.ordersPage.content[i].createdAt).format('DD MM YYYY');
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
                statuses: [],
                summaryPriceFrom: 0,
                summaryPriceTo: 0,
                createdAtFrom: "",
                createdAtTo: "",
                sortByField: "id",
                ascOrDesc: "asc"
            }
        },
        buyOrders() {
            if (window.confirm("Are you sure?\n Your all not confirmed orders will be confirmed")) {
                axios.get(this.rootUrl + "/confirm")
                    .then(response => {
                        user().getUserProcessingOrders();
                        this.getAllOrders(0, this.ordersQty);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        deleteOrder(id) {
            if (window.confirm("Are you sure?")) {
                axios.delete(this.rootUrl + "/" + id)
                    .then(response => {
                        user().getUserProcessingOrders();
                        this.getAllOrders(0, this.ordersQty);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        addFilter() {
            this.getAllOrders(0, this.ordersQty);
        },
        updateOrdersQty() {
            this.getAllOrders(0, this.ordersQty);
        },
        makePaginationLinks() {
            this.paginationLinks = [];

            for (let i = this.ordersPage.number - 2; i < this.ordersPage.totalPages && i < this.ordersPage.number + 3; i++) {
                if (i >= 0) {
                    this.paginationLinks.push(i);
                }
            }
        },
        validateFilter() {
            if (this.filter.createdAtFrom !== "" && this.filter.createdAtTo !== "" && this.filter.createdAtFrom > this.filter.createdAtTo) {
                alert("Incorrect created dates!");
                return false;
            }
            if (this.filter.productIdFrom > this.filter.productIdTo && this.filter.productIdTo !== 0) {
                alert("Product id from is greater then to");
                return false;
            }
            if (this.filter.productQtyFrom > this.filter.productQtyTo && this.filter.productQtyTo !== 0) {
                alert("Product quantity from is greater then to");
                return false;
            }
            if (this.filter.summaryPriceFrom > this.filter.summaryPriceTo && this.filter.summaryPriceTo !== 0) {
                alert("Summary price from is greater then to");
                return false;
            }
            return true;
        },
        getOrderStatuses() {
            axios.get(this.rootUrl + "/statuses")
                .then(response => {
                    this.statuses = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
    },
    mounted() {
        this.getAllOrders(0, this.ordersQty);
        this.getOrderStatuses();
    }
});
