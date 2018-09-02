<%@include file="/WEB-INF/taglib.jsp" %>

<div class="row" id="mainArea">
    <section id="filter" class="col-12">
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text">name</span>
            </div>
            <input type="text" v-model="filter.name" class="form-control" placeholder="name">
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text">price from</span>
            </div>
            <input type="number" step="0.1" min="0" v-model="filter.priceFrom" class="form-control">
            <div class="input-group-prepend">
                <span class="input-group-text">price to</span>
            </div>
            <input type="number" step="0.1" min="0" v-model="filter.priceTo" class="form-control">
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Categories</span>
            </div>
            <span v-for="category in categories">
                <input type="checkbox" :value="category" v-model="filter.categories">{{category}}
            </span>
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Sizes</span>
            </div>
            <span v-for="size in sizes">
                <input type="checkbox" :value="size" v-model="filter.sizes">{{size}}
            </span>
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Sort by:</span>
            </div>
            <select v-model="filter.sortByField">
                <option v-for="sortField in sortFields" :value="sortField" >{{sortField}}</option>
            </select>
            <input type="radio" value="asc" v-model="filter.ascOrDesc">ascending
            <input type="radio" value="desc" v-model="filter.ascOrDesc">descending
            <div class="input-group-append">
                <span class="input-group-text"></span>
            </div>
        </div>
        <button @click="addFilter()" class="btn btn-primary">Add filter</button>
        <button @click="resetFilter()" class="btn btn-warning">Reset filter</button>
        <div class="row">
            <div class="col-12 d-flex justify-content-between">
                <div>
                    Products quantity : {{shopPage.totalElements}}<br>
                </div>
                <div>
                    <ul class="pagination">
                        <li class="page-item disabled" v-if="shopPage.first">
                            <span class="page-link"><<</span>
                        </li>
                        <li class="page-item" v-else @click="getAllProducts(0, itemsQty)">
                            <span class="page-link"><<</span>
                        </li>
                        <li class="page-item disabled" v-if="shopPage.first">
                            <span class="page-link"><</span>
                        </li>
                        <li class="page-item" v-else @click="getAllProducts(shopPage.number - 1, productsQty)">
                            <span class="page-link"><</span>
                        </li>
                        <li class="page-item" v-else><</li>
                        <li class="page-item" v-for="link in paginationLinks" v-if="link != shopPage.number">
                            <span class="page-link" @click="getAllProducts(link, itemsQty)">{{link + 1}}</span>
                        </li>
                        <li class="page-item active" v-else>
                            <span class="page-link" v-if="link == shopPage.number">{{link + 1}}</span>
                        </li>
                        <li class="page-item disabled" v-if="shopPage.last">
                            <span class="page-link">></span>
                        </li>
                        <li class="page-item" v-else @click="getAllProducts(shopPage.number + 1, itemsQty)">
                            <span class="page-link">></span>
                        </li>
                        <li class="page-item disabled" v-if="shopPage.last">
                            <span class="page-link">>></span>
                        </li>
                        <li class="page-item" v-else @click="getAllProducts(shopPage.totalPages - 1, itemsQty)">
                            <span class="page-link">>></span>
                        </li>
                    </ul>
                </div>
                <div>
                    <div>Items on page</div>
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            <input type="radio" class="form-check-input" value="10" v-model="itemsQty"
                                   @change="updateItemsQty()"> 10
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            <input type="radio" class="form-check-input" value="20" v-model="itemsQty"
                                   @change="updateItemsQty()"> 20
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            <input type="radio" class="form-check-input" value="50" v-model="itemsQty"
                                   @change="updateItemsQty()"> 50
                        </label>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section id="main-section" class="col-12">
        <div class="container-fluid">
            <div class="row justify-content-around">
                <div class="card" v-for="product in shopPage.content">
                    <a :href="'/shop/' + product.id">
                        <div class="card-header">{{product.name}}<br></div>
                        <img class="card-img-top" :src="'' + product.imageUrl" alt="Card image">
                        <div class="card-img-overlay">
                            <div class="card-content-wrap row justify-content-around align-content-between">
                                <div class="card-text">
                                    <p>Product category {{product.category}}<br>Product sizes <span
                                            v-for="size in product.sizes">{{size}} </span><br>Added
                                        {{product.createdAt}}</p>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">price {{product.price}}</div>
                    </a>
                </div>
            </div>
        </div>
    </section>
    <div class="col-12 d-flex justify-content-around">
        <div>
            <ul class="pagination">
                <li class="page-item disabled" v-if="shopPage.first">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item" v-else @click="getAllProducts(0, itemsQty)">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item disabled" v-if="shopPage.first">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else @click="getAllProducts(shopPage.number - 1, productsQty)">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else><</li>
                <li class="page-item" v-for="link in paginationLinks" v-if="link != shopPage.number">
                    <span class="page-link" @click="getAllProducts(link, itemsQty)">{{link + 1}}</span>
                </li>
                <li class="page-item active" v-else>
                    <span class="page-link" v-if="link == shopPage.number">{{link + 1}}</span>
                </li>
                <li class="page-item disabled" v-if="shopPage.last">
                    <span class="page-link">></span>
                </li>
                <li class="page-item" v-else @click="getAllProducts(shopPage.number + 1, itemsQty)">
                    <span class="page-link">></span>
                </li>
                <li class="page-item disabled" v-if="shopPage.last">
                    <span class="page-link">>></span>
                </li>
                <li class="page-item" v-else @click="getAllProducts(shopPage.totalPages - 1, itemsQty)">
                    <span class="page-link">>></span>
                </li>
            </ul>
        </div>
    </div>

</div>