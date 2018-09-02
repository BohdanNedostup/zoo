<div class="row" id="mainArea">
    <section id="filter" class="col-12">
        <div class="form-inline">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">created at date from</span>
                </div>
                <input type="date" v-model="filter.createdAtFrom" class="form-control">
                <div class="input-group-prepend">
                    <span class="input-group-text">created at date to</span>
                </div>
                <input type="date" v-model="filter.createdAtTo" class="form-control">
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">summary price from</span>
                </div>
                <input type="number" min="0" step="0.1" v-model="filter.summaryPriceFrom" class="form-control">
                <div class="input-group-prepend">
                    <span class="input-group-text">summary price to</span>
                </div>
                <input type="number" min="0" step="0.1" v-model="filter.summaryPriceTo" class="form-control">
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">status</span>
                </div>
                <span v-for="status in statuses">
                    <input type="checkbox" :value="status" v-model="filter.statuses">{{status}}
                </span>
                <div class="input-group-append">
                    <span class="input-group-text"></span>
                </div>
            </div>
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
        <button @click="buyOrders()" class="btn btn-success">Buy new orders</button>
    </section>

    <div class="row">
        <div class="col-12 d-flex justify-content-between">
            <div>
                <ul class="pagination">
                    <li class="page-item disabled" v-if="ordersPage.first">
                        <span class="page-link"><<</span>
                    </li>
                    <li class="page-item" v-else @click="getAllOrders(0, ordersQty)">
                        <span class="page-link"><<</span>
                    </li>
                    <li class="page-item disabled" v-if="ordersPage.first">
                        <span class="page-link"><</span>
                    </li>
                    <li class="page-item" v-else @click="getAllOrders(ordersPage.number - 1, ordersQty)">
                        <span class="page-link"><</span>
                    </li>
                    <li class="page-item" v-else><</li>
                    <li class="page-item" v-for="link in paginationLinks" v-if="link != ordersPage.number">
                        <span class="page-link" @click="getAllOrders(link, ordersQty)">{{link + 1}}</span>
                    </li>
                    <li class="page-item active" v-else>
                        <span class="page-link" v-if="link == ordersPage.number">{{link + 1}}</span>
                    </li>
                    <li class="page-item disabled" v-if="ordersPage.last">
                        <span class="page-link">></span>
                    </li>
                    <li class="page-item" v-else @click="getAllOrders(ordersPage.number + 1, ordersQty)">
                        <span class="page-link">></span>
                    </li>
                    <li class="page-item disabled" v-if="ordersPage.last">
                        <span class="page-link">>></span>
                    </li>
                    <li class="page-item" v-else @click="getAllOrders(ordersPage.totalPages - 1, ordersQty)">
                        <span class="page-link">>></span>
                    </li>
                </ul>
            </div>
            <div>
                <div>Orders on page</div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" value="10" v-model="ordersQty"
                               @change="updateOrdersQty()"> 10
                    </label>
                </div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" value="20" v-model="ordersQty"
                               @change="updateOrdersQty()"> 20
                    </label>
                </div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" value="50" v-model="ordersQty"
                               @change="updateOrdersQty()"> 50
                    </label>
                </div>
            </div>
        </div>
    </div>
    </section>
    <section id="main-section" class="col-12">
        <div id="tableWrapper" class="col-12" style="padding: 0; overflow-x: scroll">
            <table id="main-table" class="table table-striped table-sm table-hover">
                <thead class="">
                <tr>
                    <th>&numero;</th>
                    <th>Id</th>
                    <th>Product</th>
                    <th>Size</th>
                    <th>Status</th>
                    <th>Product qty</th>
                    <th>Summary price</th>
                    <th>Created at</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(order, index) in ordersPage.content">
                    <td>{{index + 1}}</td>
                    <td>{{order.id}}</td>
                    <td>{{order.product}}</td>
                    <td>{{order.productSize}}</td>
                    <td>{{order.orderStatus}}</td>
                    <td>{{order.productQty}}</td>
                    <td>{{order.summaryPrice}}</td>
                    <td>{{order.createdAt}}</td>
                    <td>
                        <div class="btn-group align-items-center" v-if="order.orderStatus == 'NOT_CONFIRMED'">
                            <button @click="deleteOrder(order.id)"
                                    class="manage-button btn btn-danger">Delete
                            </button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </section>
    <div class="col-12 d-flex justify-content-around">
        <div>
            <ul class="pagination">
                <li class="page-item disabled" v-if="ordersPage.first">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item" v-else @click="getAllOrders(0, ordersQty)">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item disabled" v-if="ordersPage.first">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else @click="getAllOrders(ordersPage.number - 1, ordersQty)">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else><</li>
                <li class="page-item" v-for="link in paginationLinks" v-if="link != ordersPage.number">
                    <span class="page-link" @click="getAllOrders(link, ordersQty)">{{link + 1}}</span>
                </li>
                <li class="page-item active" v-else>
                    <span class="page-link" v-if="link == ordersPage.number">{{link + 1}}</span>
                </li>
                <li class="page-item disabled" v-if="ordersPage.last">
                    <span class="page-link">></span>
                </li>
                <li class="page-item" v-else @click="getAllOrders(ordersPage.number + 1, ordersQty)">
                    <span class="page-link">></span>
                </li>
                <li class="page-item disabled" v-if="ordersPage.last">
                    <span class="page-link">>></span>
                </li>
                <li class="page-item" v-else @click="getAllOrders(ordersPage.totalPages - 1, ordersQty)">
                    <span class="page-link">>></span>
                </li>
            </ul>
        </div>
    </div>
</div>