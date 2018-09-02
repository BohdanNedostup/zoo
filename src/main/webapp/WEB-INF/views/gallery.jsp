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
                <span class="input-group-text">Departments</span>
            </div>
            <span v-for="department in departments">
                    <input type="checkbox" :value="department" v-model="filter.departments">{{department}}
                </span>
            <div class="input-group-append">
                <span class="input-group-text"></span>
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
        <div class="row">
            <div class="col-12 d-flex justify-content-between">
                <div>
                    Animals quantity : {{galleryPage.totalElements}}<br>
                </div>
                <div>
                    <ul class="pagination">
                        <li class="page-item disabled" v-if="galleryPage.first">
                            <span class="page-link"><<</span>
                        </li>
                        <li class="page-item" v-else @click="getAllAnimals(0, animalsQty)">
                            <span class="page-link"><<</span>
                        </li>
                        <li class="page-item disabled" v-if="galleryPage.first">
                            <span class="page-link"><</span>
                        </li>
                        <li class="page-item" v-else @click="getAllAnimals(galleryPage.number - 1, animalsQty)">
                            <span class="page-link"><</span>
                        </li>
                        <li class="page-item" v-else><</li>
                        <li class="page-item" v-for="link in paginationLinks" v-if="link != galleryPage.number">
                            <span class="page-link" @click="getAllAnimals(link, animalsQty)">{{link + 1}}</span>
                        </li>
                        <li class="page-item active" v-else>
                            <span class="page-link" v-if="link == galleryPage.number">{{link + 1}}</span>
                        </li>
                        <li class="page-item disabled" v-if="galleryPage.last">
                            <span class="page-link">></span>
                        </li>
                        <li class="page-item" v-else @click="getAllAnimals(galleryPage.number + 1, animalsQty)">
                            <span class="page-link">></span>
                        </li>
                        <li class="page-item disabled" v-if="galleryPage.last">
                            <span class="page-link">>></span>
                        </li>
                        <li class="page-item" v-else @click="getAllAnimals(galleryPage.totalPages - 1, animalsQty)">
                            <span class="page-link">>></span>
                        </li>
                    </ul>
                </div>
                <div>
                    <div>Items on page</div>
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            <input type="radio" class="form-check-input" value="10" v-model="animalsQty"
                                   @change="updateAnimalsQty()"> 10
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            <input type="radio" class="form-check-input" value="20" v-model="animalsQty"
                                   @change="updateAnimalsQty()"> 20
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            <input type="radio" class="form-check-input" value="50" v-model="animalsQty"
                                   @change="updateAnimalsQty()"> 50
                        </label>
                    </div>
                    <div class="form-check-inline disabled">
                        <label class="form-check-label">
                            <input type="radio" class="form-check-input" value="100" v-model="animalsQty"
                                   @change="updateAnimalsQty()"> 100
                        </label>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section id="main-section" class="col-12">
        <div class="container-fluid">
            <div class="row justify-content-around">
                <div class="card" v-for="animal in galleryPage.content">
                    <div class="card-header">{{animal.name}}<br></div>
                    <img class="card-img-top" :src="'' + animal.imageUrl" alt="Card image">
                    <div class="card-img-overlay">
                        <div class="card-content-wrap row justify-content-around align-content-between">
                            <div class="card-text">
                                <p>{{animal.description}}</p>
                            </div>
                            <a :href="animal.wikiUrl">
                                <div class="btn-success d-flex justify-content-center ">
                                    <div><i class="fab fa-wikipedia-w fa-2x"></i>
                                    </div>ikipedia
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <div class="col-12 d-flex justify-content-around">
        <div>
            <ul class="pagination">
                <li class="page-item disabled" v-if="galleryPage.first">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item" v-else @click="getAllAnimals(0, animalsQty)">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item disabled" v-if="galleryPage.first">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else @click="getAllAnimals(galleryPage.number - 1, animalsQty)">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else><</li>
                <li class="page-item" v-for="link in paginationLinks" v-if="link != galleryPage.number">
                    <span class="page-link" @click="getAllAnimals(link, animalsQty)">{{link + 1}}</span>
                </li>
                <li class="page-item active" v-else>
                    <span class="page-link" v-if="link == galleryPage.number">{{link + 1}}</span>
                </li>
                <li class="page-item disabled" v-if="galleryPage.last">
                    <span class="page-link">></span>
                </li>
                <li class="page-item" v-else @click="getAllAnimals(galleryPage.number + 1, animalsQty)">
                    <span class="page-link">></span>
                </li>
                <li class="page-item disabled" v-if="galleryPage.last">
                    <span class="page-link">>></span>
                </li>
                <li class="page-item" v-else @click="getAllAnimals(galleryPage.totalPages - 1, animalsQty)">
                    <span class="page-link">>></span>
                </li>
            </ul>
        </div>
    </div>
</div>