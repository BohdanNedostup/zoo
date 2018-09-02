<div class="row" id="manageArea">
    <section id="filter" class="col-12">
        <div class="form-inline">

            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">title</span>
                </div>
                <input type="text" v-model="filter.title" class="form-control">
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
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Sort by:</span>
            </div>
            <select v-model="filter.sortByField">
                <option v-for="sortField in sortFields" :value="sortField">{{sortField}}</option>
            </select>
            <input type="radio" value="asc" v-model="filter.ascOrDesc">ascending
            <input type="radio" value="desc" v-model="filter.ascOrDesc">descending
            <div class="input-group-append">
                <span class="input-group-text"></span>
            </div>
        </div>
        <button @click="addFilter()" class="btn btn-primary">Add filter</button>
        <button @click="resetFilter()" class="btn btn-warning">Reset filter</button>
        <button onclick="showEditProfile(1)" class="btn btn-success">Add news</button>
    </section>
    <section id="main-section" class="col">
        <div class="col-12 d-flex justify-content-between">
            <div>
                News quantity : {{newsPage.totalElements}}<br>
            </div>
            <div>
                <ul class="pagination">
                    <li class="page-item disabled" v-if="newsPage.first">
                        <span class="page-link"><<</span>
                    </li>
                    <li class="page-item" v-else @click="getAllNews(0, newsQty)">
                        <span class="page-link"><<</span>
                    </li>
                    <li class="page-item disabled" v-if="newsPage.first">
                        <span class="page-link"><</span>
                    </li>
                    <li class="page-item" v-else @click="getAllNews(newsPage.number - 1, newsQty)">
                        <span class="page-link"><</span>
                    </li>
                    <li class="page-item" v-else><</li>
                    <li class="page-item" v-for="link in paginationLinks" v-if="link != newsPage.number">
                        <span class="page-link" @click="getAllNews(link, newsQty)">{{link + 1}}</span>
                    </li>
                    <li class="page-item active" v-else>
                        <span class="page-link" v-if="link == newsPage.number">{{link + 1}}</span>
                    </li>
                    <li class="page-item disabled" v-if="newsPage.last">
                        <span class="page-link">></span>
                    </li>
                    <li class="page-item" v-else @click="getAllNews(newsPage.number + 1, newsQty)">
                        <span class="page-link">></span>
                    </li>
                    <li class="page-item disabled" v-if="newsPage.last">
                        <span class="page-link">>></span>
                    </li>
                    <li class="page-item" v-else @click="getAllNews(newsPage.totalPages - 1, newsQty)">
                        <span class="page-link">>></span>
                    </li>
                </ul>
            </div>
            <div>
                <div>News on page</div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="one" value="20" v-model="newsQty"
                               @change="updateNewsQty()"> 20
                    </label>
                </div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="two" value="50" v-model="newsQty"
                               @change="updateNewsQty()"> 50
                    </label>
                </div>
                <div class="form-check-inline disabled">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="three" value="100" v-model="newsQty"
                               @change="updateNewsQty()"> 100
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
                    <th>Title</th>
                    <th>Image URL</th>
                    <th>Created at</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(news, index) in newsPage.content">
                    <td>{{index + 1}}</td>
                    <td>{{news.id}}</td>
                    <td>{{news.title}}</td>
                    <td>{{news.imageUrl}}</td>
                    <td>{{news.createdAt}}</td>
                    <td>
                        <div class="btn-group align-items-center">
                            <button @click="updateNews(index)" class="manage-button btn btn-warning">Edit</button>
                            <button @click="deleteNewsById(news.id)" class="manage-button btn btn-danger">Delete
                            </button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="row justify-content-around">
            <ul class="pagination">
                <li class="page-item disabled" v-if="newsPage.first">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item" v-else @click="getAllNews(0, newsQty)">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item disabled" v-if="newsPage.first">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else @click="getAllNews(newsPage.number - 1, newsQty)">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else><</li>
                <li class="page-item" v-for="link in paginationLinks" v-if="link != newsPage.number">
                    <span class="page-link" @click="getAllNews(link, newsQty)">{{link + 1}}</span>
                </li>
                <li class="page-item active" v-else>
                    <span class="page-link" v-if="link == newsPage.number">{{link + 1}}</span>
                </li>
                <li class="page-item disabled" v-if="newsPage.last">
                    <span class="page-link">></span>
                </li>
                <li class="page-item" v-else @click="getAllNews(newsPage.number + 1, newsQty)">
                    <span class="page-link">></span>
                </li>
                <li class="page-item disabled" v-if="newsPage.last">
                    <span class="page-link">>></span>
                </li>
                <li class="page-item" v-else @click="getAllNews(newsPage.totalPages - 1, newsQty)">
                    <span class="page-link">>></span>
                </li>
            </ul>
        </div>
    </section>
    <div class="newsAreaWrapper container-fluid row justify-content-around">
        <div class="rounded newsArea">
            <div class="row">
                <div class="col-6">
                    <img v-if="news.imageUrl != null" :src="'' + news.imageUrl" alt="news photo" width="250px"
                         height="250px">
                </div>
                <div class="col-6" style="padding-top: 20px">
                    <div class="row justify-content-end">
                        <div class="col-12">created {{news.createdAt}}</div>
                    </div>
                    <div>
                        <label for="uploadImage">Upload image:</label>
                        <input type="file" @change="uploadImage" id="uploadImage" style="width: 60px">
                    </div>
                    <div>
                        <label for="title">Title : </label>
                        <textarea id="title" cols="30" rows="3" style="font-size: 12px"
                                  v-model="news.title"></textarea>
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="text">Text : </label>
                <textarea id="text" cols="100" rows="7" style="font-size: 12px"
                          v-model="news.text"></textarea>
            </div>
            <div class="row justify-content-around">
                <div class="btn-group">
                    <button @click="saveNews()" class="btn btn-success">Update</button>
                    <button onclick="hideEditProfile(0)" class="btn btn-danger">Cancel</button>
                </div>
            </div>
        </div>
    </div>
    <div class="newsAreaWrapper container-fluid row justify-content-around">
        <div class="rounded newsArea">
            <div class="row">
                <div class="col-6">
                    <div class="form-group">
                        <label for="newsImage">Image:</label>
                        <input type="file" @change="uploadImage" id="newsImage">
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="newTitle">Title</label>
                <textarea id="newTitle" cols="100" rows="7" style="font-size: 12px"
                          v-model="newsTemplate.title"></textarea>
            </div>
            <div class="row">
                <label for="newText">Text</label>
                <textarea id="newText" cols="100" rows="7" style="font-size: 12px"
                          v-model="newsTemplate.text"></textarea>
            </div>

            <div class="row justify-content-around">
                <div class="btn-group">
                    <button @click="saveNewNews()" class="btn btn-success">Create news</button>
                    <button onclick="hideEditProfile(1)" class="btn btn-danger">Cancel</button>
                </div>
            </div>
        </div>
    </div>
</div>