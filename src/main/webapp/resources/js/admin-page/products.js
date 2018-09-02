new Vue({
    el: "#manageArea",
    data: {
        rootUrl: "https://immense-springs-86257.herokuapp.com/api/manage/products",
        productsPage: {},
        product: {},
        filter: {
            name: "",
            priceFrom: 0,
            priceTo: 0,
            createdAtFrom: "",
            createdAtTo: "",
            categories: [],
            sizes: [],
            sortByField: "id",
            ascOrDesc: "asc"
        },
        productsQty: "20",
        categories: [],
        paginationLinks: [],
        sizes: {},
        selectedFile: null,
        productTemplate: {
            name: "",
            price: 0,
            category: "Cap",
            sizes: [],
            description: ""
        },
        sortFields: [
            "id", "name", "price", "createdAt"
        ]
    },
    methods: {
        getAllProducts(pageNum, productsQty) {
            if (this.validateFilter()) {
                let filterLink = "";
                filterLink += "&name=" + this.filter.name;
                filterLink += "&priceFrom=" + this.filter.priceFrom;
                filterLink += "&priceTo=" + this.filter.priceTo;
                filterLink += "&createdAtFrom=" + this.filter.createdAtFrom;
                filterLink += "&createdAtTo=" + this.filter.createdAtTo;
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
                axios.get(this.rootUrl + "?size=" + productsQty
                    + "&page=" + pageNum + filterLink)
                    .then(response => {
                        this.productsPage = response.data;
                        this.selectedFile = null;

                        for (let i = 0; i < this.productsPage.content.length; i++) {
                            this.productsPage.content[i].createdAt = moment(this.productsPage.content[i].createdAt).format('DD MM YYYY');
                        }
                        this.makePaginationLinks();
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        }
        ,
        resetFilter() {
            this.filter = {
                name: "",
                categories: [],
                priceFrom: 0,
                priceTo: 0,
                sizes: [],
                createdAtFrom: "",
                createdAtTo: "",
            }
        }
        ,
        addFilter() {
            this.getAllProducts(0, this.productsQty);
        }
        ,
        makePaginationLinks() {
            this.paginationLinks = [];

            for (let i = this.productsPage.number - 2; i < this.productsPage.totalPages && i < this.productsPage.number + 3; i++) {
                if (i >= 0) {
                    this.paginationLinks.push(i);
                }
            }
        }
        ,
        uploadImage(event) {
            this.selectedFile = event.target.files[0];
        }
        ,
        updateProduct(index) {
            this.product = this.productsPage.content[index];
            showEditProfile(0);
        }
        ,
        updateProductsQty() {
            this.getAllProducts(0, this.productsQty)
        }
        ,
        deleteProductById(id) {
            if (window.confirm("Are you sure?")) {
                console.log(id);
                axios.delete(this.rootUrl + "/" + id)
                    .then(response => {
                        this.getAllProducts(this.productsPage.number, this.productsQty);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        }
        ,
        getProductSizes() {
            axios.get("https://immense-springs-86257.herokuapp.com/api/shop/sizes")
                .then(response => {
                    this.sizes = response.data;
                })
                .catch(error => {
                    console.log(error)
                })
        }
        ,
        getProductCategories() {
            axios.get("https://immense-springs-86257.herokuapp.com/api/shop/categories")
                .then(response => {
                    this.categories = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        }
        ,
        saveProduct() {
            if (window.confirm("Are you sure?") && this.validateUpdateProfile()) {
                hideEditProfile(0);
                let createdAt = this.product.createdAt;

                let year = createdAt.substring(createdAt.lastIndexOf(" ") + 1);
                let month = createdAt.substring(createdAt.indexOf(" ") + 1, createdAt.lastIndexOf(" "));
                let day = createdAt.substring(0, createdAt.indexOf(" "));
                this.product.createdAt = moment(year + "-" + month + "-" + day);
                axios.put(this.rootUrl, this.product)
                    .then(response => {
                        if (this.selectedFile != null) {
                            this.uploadImageToProductEntity(this.product.id)
                        }
                        this.getAllProducts(this.productsPage.number, this.productsQty);
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        }
        ,
        saveNewProduct() {
            if (window.confirm("Are you sure?") && this.validateCreateForm()) {
                hideEditProfile(1);
                axios.post(this.rootUrl, this.productTemplate)
                    .then(response => {
                        if (this.selectedFile != null) {
                            this.uploadImageToProductEntity(response.data);
                        }
                        this.getAllProducts(this.productsPage.number, this.productsQty);
                        this.productTemplate = {
                            name: "",
                            price: 0,
                            category: "Cap",
                            sizes: [],
                            description: ""
                        };
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        }
        ,
        uploadImageToProductEntity(id) {
            if (id < 0) {
                alert("There is product with such name");
            } else {
                let fd = new FormData();
                if (this.selectedFile != null) {
                    fd.append("image", this.selectedFile, "product" + id + this.selectedFile.name.substring(this.selectedFile.name.lastIndexOf(".")));
                    axios.post(this.rootUrl + "/image/" + id, fd)
                        .then(response => {
                            this.getAllProducts(this.productsPage.number, this.productsQty);
                        })
                        .catch(error => {
                            console.log(error);
                        });
                }
            }
        }
        ,
        validateUpdateProfile() {
            if (this.product.description === "") {
                alert("Description can`t be empty!")
                return false;
            }
            return true;
        }
        ,
        validateFilter() {
            if (this.filter.createdAtFrom !== "" && this.filter.createdAtTo !== "" && this.filter.createdAtFrom > this.filter.createdAtTo) {
                alert("Incorrect created dates!");
                return false;
            }
            if (this.filter.priceFrom > this.filter.priceTo && this.filter.priceTo != 0) {
                alert("Price from is greater then to");
                return false;
            }
            return true;
        }
        ,
        validateCreateForm() {
            if (this.productTemplate.name === "") {
                alert("Name can`t be empty!");
                return false;
            }
            return true;
        }
    },
    mounted() {
        this.getAllProducts(0, this.productsQty);
        this.getProductSizes();
        this.getProductCategories();
    }
});


function showEditProfile(area) {
    document.getElementsByClassName("profileArea")[area].style.transform = "translateY(calc(" + window.innerHeight + "px + " + window.scrollY + "px + 100px))";
}

function hideEditProfile(area) {
    document.getElementsByClassName("profileArea")[area].style.transform = "translateY(0%)"
}