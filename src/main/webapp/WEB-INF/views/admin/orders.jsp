<div class="row" id="manageArea">
    <section id="filter" class="col-12">
        <div class="form-inline">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">user id from</span>
                </div>
                <input type="number" min="0" step="1" v-model="filter.userIdFrom" class="form-control">
                <div class="input-group-prepend">
                    <span class="input-group-text">user id to</span>
                </div>
                <input type="number" min="0" step="1" v-model="filter.userIdTo" class="form-control">
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">product id from</span>
                </div>
                <input type="number" min="0" step="1" v-model="filter.productIdFrom" class="form-control">
                <div class="input-group-prepend">
                    <span class="input-group-text">product id to</span>
                </div>
                <input type="number" min="0" step="1" v-model="filter.productIdTo" class="form-control">
            </div>
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
                    <span class="input-group-text">status</span>
                </div>
                <span v-for="status in statuses">
                    <input type="checkbox" :value="status" v-model="filter.statuses">{{status}}
                </span>
                <div class="input-group-append">
                    <span class="input-group-text"></span>
                </div>
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">product qty from</span>
                </div>
                <input type="number" min="0" step="1" v-model="filter.productQtyFrom" class="form-control">
                <div class="input-group-prepend">
                    <span class="input-group-text">product qty to</span>
                </div>
                <input type="number" min="0" step="1" v-model="filter.productQtyTo" class="form-control">
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
                    <span class="input-group-text">sizes</span>
                </div>
                <span v-for="size in sizes">
                    <input type="checkbox" :value="size" v-model="filter.sizes">{{size}}
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
    </section>
    <section id="main-section" class="col">
        <div class="col-12 d-flex justify-content-between">
            <div>
                Orders quantity : {{ordersPage.totalElements}}<br>
            </div>
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
                <div>Items on page</div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="one" value="20" v-model="ordersQty"
                               @change="updateOrdersQty()"> 20
                    </label>
                </div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="two" value="50" v-model="ordersQty"
                               @change="updateOrdersQty()"> 50
                    </label>
                </div>
                <div class="form-check-inline disabled">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="three" value="100" v-model="ordersQty"
                               @change="updateOrdersQty()"> 100
                    </label>
                </div>
            </div>
        </div>


        <div id="tableWrapper" class="col-12" style="padding: 0; overflow-x: scroll">
            <table id="main-table" class="table table-striped table-sm table-hover table-bordered">
                <thead class="">
                <tr>
                    <th>&numero;</th>
                    <th>Id</th>
                    <th>User id</th>
                    <th>Product id</th>
                    <th>Status</th>
                    <th>Product qty</th>
                    <th>Summary price</th>
                    <th>Size</th>
                    <th>Created at</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(order, index) in ordersPage.content">
                    <td>{{index + 1}}</td>
                    <td>{{order.id}}</td>
                    <td>{{order.userId}}</td>
                    <td>{{order.productId}}</td>
                    <td>{{order.orderStatus}}</td>
                    <td>{{order.productQty}}</td>
                    <td>{{order.summaryPrice}}</td>
                    <td>{{order.productSize}}</td>
                    <td>{{order.createdAt}}</td>
                    <td>
                        <div class="btn-group align-items-center">
                            <button @click="updateOrder(index)"
                                    class="manage-button btn btn-warning">Edit
                            </button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="row justify-content-around">
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
    </section>
    <div class="orderAreaWrapper container-fluid row justify-content-around">
        <div class="rounded orderArea">
            <div class="row justify-content-around">
                <div class="col-7">
                    User id : {{order.userId}}
                </div>
                <div class="col-7">
                    Product id : {{order.productId}}
                </div>
                <div class="col-7">
                    Size : {{order.productSize}}
                </div>
                <div class="col-7">
                    Qty : {{order.productQty}}
                </div>
                <div class="col-7">
                    Summary price : {{order.summaryPrice}}
                </div>
                <div class="col-7">
                    Created at : {{order.createdAt}}
                </div>
                <div class="col-7">
                    Choose status :
                    <span v-for="status in statuses">
                        <input type="radio" :value="status" v-model="order.orderStatus">{{status}}
                    </span>
                </div>
            </div>

            <div class="row justify-content-around">
                <div class="btn-group">
                    <button @click="updateOrderStatus()" class="btn btn-success">Update</button>
                    <button onclick="hideEditOrder()" class="btn btn-danger">Cancel</button>
                </div>
            </div>
        </div>
    </div>
</div>