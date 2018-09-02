new Vue({
    el: "#manageArea",
    data: {
        rootUrl: "https://immense-springs-86257.herokuapp.com/api/manage/orders",
        order: {},
        ordersPage: {},
        filter: {
            userIdFrom: 0,
            userIdTo: 0,
            productIdFrom: 0,
            productIdTo: 0,
            statuses: [],
            productQtyFrom: 0,
            productQtyTo: 0,
            summaryPriceFrom: 0,
            summaryPriceTo: 0,
            sizes: [],
            createdAtFrom: "",
            createdAtTo: "",
            sortByField: "id",
            ascOrDesc: "asc"
        },
        statuses: [],
        sizes: [],
        ordersQty: "20",
        paginationLinks: [],
        sortFields: [
            "id", "userId", "productId", "productQty",
            "summaryPrice", "createdAt"
        ]
    },
    methods: {
        getAllOrders(pageNum, ordersQty) {
            if (this.validateFilter()) {
                let filterLink = "";
                filterLink += "&userIdFrom=" + this.filter.userIdFrom;
                filterLink += "&userIdTo=" + this.filter.userIdTo;
                filterLink += "&productIdFrom=" + this.filter.productIdFrom;
                filterLink += "&productIdTo=" + this.filter.productIdTo;
                filterLink += "&productQtyFrom=" + this.filter.productQtyFrom;
                filterLink += "&productQtyTo=" + this.filter.productQtyTo;
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
                filterLink += "&sizes=";
                for (let i = 0; i < this.filter.sizes.length; i++) {
                    filterLink += this.filter.sizes[i];
                    if (i !== this.filter.sizes.length - 1) {
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
                userIdFrom: 0,
                userIdTo: 0,
                productIdFrom: 0,
                productIdTo: 0,
                statuses: [],
                productQtyFrom: 0,
                productQtyTo: 0,
                summaryPriceFrom: 0,
                summaryPriceTo: 0,
                sizes: [],
                createdAtFrom: "",
                createdAtTo: "",
                sortByField: "id",
                ascOrDesc: "asc"
            }
        },
        addFilter(){
            this.getAllOrders(0, this.ordersQty);
        },
        updateOrdersQty() {
            this.getAllOrders(0, this.ordersQty);
        },
        updateOrder(index){
            this.order = this.ordersPage.content[index];
            showEditOrder();
        },
        getOrderStatuses() {
            axios.get("https://immense-springs-86257.herokuapp.com/api/user/orders/statuses")
                .then(response => {
                    this.statuses = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        getProductSizes() {
            axios.get("https://immense-springs-86257.herokuapp.com/api/shop/sizes")
                .then(response => {
                    this.sizes = response.data;
                })
                .catch(error => {
                    console.log(error)
                })
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
            if (this.filter.userIdFrom > this.filter.userIdTo && this.filter.userIdTo !== 0) {
                alert("User id from is greater then to");
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
            if (this.filter.summaryPriceFrom > this.filter.summaryPriceTo && this.filter.summaryPriceTo !== 0){
                alert("Summary price from is greater then to");
                return false;
            }
            return true;
        },
        updateOrderStatus(){
            if (window.confirm("Are you sure?")) {
                hideEditOrder();

                let createdAt = this.order.createdAt;

                let year = createdAt.substring(createdAt.lastIndexOf(" ") + 1);
                let month = createdAt.substring(createdAt.indexOf(" ") + 1, createdAt.lastIndexOf(" "));
                let day = createdAt.substring(0, createdAt.indexOf(" "));
                this.order.createdAt = moment(year + "-" + month + "-" + day);
                axios.put(this.rootUrl, this.order)
                    .then(response => {
                        this.getAllOrders(this.ordersPage.number, this.ordersQty);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
        }
    },
    mounted() {
        this.getAllOrders(0, this.ordersQty);
        this.getOrderStatuses();
        this.getProductSizes();
    }
});

function showEditOrder() {
    document.getElementsByClassName("orderArea")[0].style.transform = "translateY(calc(" + window.innerHeight + "px + " + window.scrollY + "px + 100px))";
}

function hideEditOrder() {
    document.getElementsByClassName("orderArea")[0].style.transform = "translateY(0%)"
}