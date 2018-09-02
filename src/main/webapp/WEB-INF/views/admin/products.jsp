<div class="row" id="manageArea">
    <section id="filter" class="col-12">
        <div class="form-inline">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">name</span>
                </div>
                <input type="text" v-model="filter.name" class="form-control" placeholder="name">
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
                <div class="input-group-append">
                    <span class="input-group-text"></span>
                </div>
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">Sizes</span>
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
        <button onclick="showEditProfile(1)" class="btn btn-success">Add new product</button>

    </section>
    <section id="main-section" class="col">
        <div class="col-12 d-flex justify-content-between">
            <div>
                Products quantity : {{productsPage.totalElements}}<br>
            </div>
            <div>
                <ul class="pagination">
                    <li class="page-item disabled" v-if="productsPage.first">
                        <span class="page-link"><<</span>
                    </li>
                    <li class="page-item" v-else @click="getAllProducts(0, productsQty)">
                        <span class="page-link"><<</span>
                    </li>
                    <li class="page-item disabled" v-if="productsPage.first">
                        <span class="page-link"><</span>
                    </li>
                    <li class="page-item" v-else @click="getAllProducts(productsPage.number - 1, productsQty)">
                        <span class="page-link"><</span>
                    </li>
                    <li class="page-item" v-else><</li>
                    <li class="page-item" v-for="link in paginationLinks" v-if="link != productsPage.number">
                        <span class="page-link" @click="getAllProducts(link, productsQty)">{{link + 1}}</span>
                    </li>
                    <li class="page-item active" v-else>
                        <span class="page-link" v-if="link == productsPage.number">{{link + 1}}</span>
                    </li>
                    <li class="page-item disabled" v-if="productsPage.last">
                        <span class="page-link">></span>
                    </li>
                    <li class="page-item" v-else @click="getAllProducts(productsPage.number + 1, productsQty)">
                        <span class="page-link">></span>
                    </li>
                    <li class="page-item disabled" v-if="productsPage.last">
                        <span class="page-link">>></span>
                    </li>
                    <li class="page-item" v-else @click="getAllProducts(productsPage.totalPages - 1, productsQty)">
                        <span class="page-link">>></span>
                    </li>
                </ul>
            </div>
            <div>
                <div>Items on page</div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="one" value="20" v-model="productsQty"
                               @change="updateProductsQty()"> 20
                    </label>
                </div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="two" value="50" v-model="productsQty"
                               @change="updateProductsQty()"> 50
                    </label>
                </div>
                <div class="form-check-inline disabled">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="three" value="100" v-model="productsQty"
                               @change="updateProductsQty()"> 100
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
                    <th>Name</th>
                    <th>Category</th>
                    <th>Image</th>
                    <th>Price</th>
                    <th>Size</th>
                    <th>Created at</th>
                    <th>Global number</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(product, index) in productsPage.content">
                    <td>{{index + 1}}</td>
                    <td>{{product.id}}</td>
                    <td>{{product.name}}</td>
                    <td>{{product.category}}</td>
                    <td>{{product.imageUrl}}</td>
                    <td>{{product.price}}</td>
                    <td>{{product.sizes}}</td>
                    <td>{{product.createdAt}}</td>
                    <td>{{product.globalNumber}}</td>
                    <td>
                        <div class="btn-group align-items-center">
                            <button @click="updateProduct(index)"
                                    class="manage-button btn btn-warning">Edit
                            </button>
                            <button @click="deleteProductById(product.id)" class="manage-button btn btn-danger">Delete
                            </button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="row justify-content-around">
            <ul class="pagination">
                <li class="page-item disabled" v-if="productsPage.first">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item" v-else @click="getAllProducts(0, productsQty)">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item disabled" v-if="productsPage.first">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else @click="getAllProducts(productsPage.number - 1, productsQty)">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else><</li>
                <li class="page-item" v-for="link in paginationLinks" v-if="link != productsPage.number">
                    <span class="page-link" @click="getAllProducts(link, productsQty)">{{link + 1}}</span>
                </li>
                <li class="page-item active" v-else>
                    <span class="page-link" v-if="link == productsPage.number">{{link + 1}}</span>
                </li>
                <li class="page-item disabled" v-if="productsPage.last">
                    <span class="page-link">></span>
                </li>
                <li class="page-item" v-else @click="getAllProducts(productsPage.number + 1, productsQty)">
                    <span class="page-link">></span>
                </li>
                <li class="page-item disabled" v-if="productsPage.last">
                    <span class="page-link">>></span>
                </li>
                <li class="page-item" v-else @click="getAllProducts(productsPage.totalPages - 1, productsQty)">
                    <span class="page-link">>></span>
                </li>
            </ul>
        </div>
    </section>
    <div class="profileAreaWrapper container-fluid row justify-content-around">
        <div class="rounded profileArea">
            <div class="row">
                <div class="col-12">
                    <div class="row justify-content-around">
                        <div>
                            <span>{{product.name}}</span>
                        </div>
                    </div>
                </div>
                <div class="col-6">
                    <img v-if="product.imageUrl != null" :src="'' + product.imageUrl" alt="profile photo"
                         width="250px"
                         height="250px">
                    <div class="row justify-content-end">
                        <div class="col-12">created {{product.createdAt}}</div>
                    </div>
                </div>
                <div class="col-6" style="padding-top: 30px">
                    <div>
                        <label for="categories">Category:</label>
                        <span v-for="category in categories">
                                <input type="radio" :value="category" v-model="product.category" id="categories">{{category}}
                            </span>
                    </div>
                    <div>
                        <label for="sizes">Size:</label>
                        <span v-for="size in sizes" id="sizes">
                                <input type="checkbox" :value="size" v-model="product.sizes">{{size}}
                            </span>
                    </div>
                    <div>
                        <label for="uploadImage">Upload image:</label>
                        <input type="file" @change="uploadImage" id="uploadImage" style="width: 60px">
                    </div>
                    <div>
                        <label for="price">Price:</label>
                        <input type="number" step="0.1" min=0 id="price" v-model="product.price"
                               :placeholder="'' + product.price">
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="description">Description</label>
                <textarea id="description" cols="100" rows="7" style="font-size: 12px"
                          v-model="product.description"></textarea>
            </div>
            <div class="row justify-content-around">
                <div class="btn-group">
                    <button @click="saveProduct()" class="btn btn-success">Update</button>
                    <button onclick="hideEditProfile(0)" class="btn btn-danger">Cancel</button>
                </div>
            </div>
        </div>
    </div>
    <div class="profileAreaWrapper container-fluid row justify-content-around">
        <div class="rounded profileArea">
            <div class="row">
                <div class="col-6">
                    <div class="form-group">
                        <label for="productImage">Image:</label>
                        <input type="file" @change="uploadImage" id="productImage">
                    </div>
                    <div class="form-group">
                        <label for="size">Size:</label>
                        <span v-for="size in sizes">
                            <input type="checkbox" :value="size" id="size" v-model="productTemplate.sizes">{{size}}
                        </span>
                    </div>
                    <div class="form-group">
                        <label for="category">Category:</label>
                        <span v-for="category in categories">
                            <input type="radio" :value="category" id="category" v-model="productTemplate.category">{{category}}
                        </span>
                    </div>
                </div>
                <div class="col-6" style="padding-top: 50px">
                    <div class="form-group">
                        <label for="name">Name:</label>
                        <input id="name" type="text" v-model="productTemplate.name" placeholder="name">
                    </div>
                    <div class="form-group">
                        <label for="newPrice">Price:</label>
                        <input id="newPrice" type="number" step="0.1" min="0" v-model="productTemplate.price">
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="newDescription">Description</label>
                <textarea id="newDescription" cols="100" rows="7" style="font-size: 12px"
                          v-model="productTemplate.description"></textarea>
            </div>
            <div class="row justify-content-around">
                <div class="btn-group">
                    <button @click="saveNewProduct()" class="btn btn-success">Create</button>
                    <button onclick="hideEditProfile(1)" class="btn btn-danger">Cancel</button>
                </div>
            </div>
        </div>
    </div>
</div>

