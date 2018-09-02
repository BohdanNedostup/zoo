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
                    <span class="input-group-text">Statuses</span>
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
                    <span class="input-group-text">Genders</span>
                </div>
                <span v-for="gender in genders">
                    <input type="checkbox" :value="gender" v-model="filter.genders">{{gender}}
                </span>
                <div class="input-group-append">
                    <span class="input-group-text"></span>
                </div>
            </div>
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
        <button onclick="showEditProfile(1)" class="btn btn-success">Add new animal</button>
    </section>
    <section id="main-section" class="col">
        <div class="col-12 d-flex justify-content-between">
            <div>
                Animals quantity : {{animalsPage.totalElements}}<br>
            </div>
            <div>
                <ul class="pagination">
                    <li class="page-item disabled" v-if="animalsPage.first">
                        <span class="page-link"><<</span>
                    </li>
                    <li class="page-item" v-else @click="getAllAnimals(0, animalsQty)">
                        <span class="page-link"><<</span>
                    </li>
                    <li class="page-item disabled" v-if="animalsPage.first">
                        <span class="page-link"><</span>
                    </li>
                    <li class="page-item" v-else @click="getAllAnimals(animalsPage.number - 1, animalsQty)">
                        <span class="page-link"><</span>
                    </li>
                    <li class="page-item" v-else><</li>
                    <li class="page-item" v-for="link in paginationLinks" v-if="link != animalsPage.number">
                        <span class="page-link" @click="getAllAnimals(link, animalsQty)">{{link + 1}}</span>
                    </li>
                    <li class="page-item active" v-else>
                        <span class="page-link" v-if="link == animalsPage.number">{{link + 1}}</span>
                    </li>
                    <li class="page-item disabled" v-if="animalsPage.last">
                        <span class="page-link">></span>
                    </li>
                    <li class="page-item" v-else @click="getAllAnimals(animalsPage.number + 1, animalsQty)">
                        <span class="page-link">></span>
                    </li>
                    <li class="page-item disabled" v-if="animalsPage.last">
                        <span class="page-link">>></span>
                    </li>
                    <li class="page-item" v-else @click="getAllAnimals(animalsPage.totalPages - 1, animalsQty)">
                        <span class="page-link">>></span>
                    </li>
                </ul>
            </div>
            <div>
                <div>Items on page</div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="one" value="20" v-model="animalsQty"
                               @change="updateAnimalsQty()"> 20
                    </label>
                </div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="two" value="50" v-model="animalsQty"
                               @change="updateAnimalsQty()"> 50
                    </label>
                </div>
                <div class="form-check-inline disabled">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="three" value="100" v-model="animalsQty"
                               @change="updateAnimalsQty()"> 100
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
                    <th>Gender</th>
                    <th>Status</th>
                    <th>Image</th>
                    <th>Wiki url</th>
                    <th>Department</th>
                    <th>Created at</th>
                    <th>Global number</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(animal, index) in animalsPage.content" class="userRow">
                    <td>{{index + 1}}</td>
                    <td>{{animal.id}}</td>
                    <td>{{animal.name}}</td>
                    <td>{{animal.gender}}</td>
                    <td>{{animal.status}}</td>
                    <td>{{animal.imageUrl}}</td>
                    <td>{{animal.wikiUrl}}</td>
                    <td>{{animal.department}}</td>
                    <td>{{animal.createdAt}}</td>
                    <td>{{animal.globalNumber}}</td>
                    <td width=100>
                        <div class="btn-group align-items-center">
                            <button @click="updateAnimal(index)"
                                    class="manage-button btn btn-warning">Edit
                            </button>
                            <button @click="deleteAnimalById(animal.id)" class="manage-button btn btn-danger">Delete
                            </button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="row justify-content-around">
            <ul class="pagination">
                <li class="page-item disabled" v-if="animalsPage.first">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item" v-else @click="getAllAnimals(0, animalsQty)">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item disabled" v-if="animalsPage.first">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else @click="getAllAnimals(animalsPage.number - 1, animalsQty)">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else><</li>
                <li class="page-item" v-for="link in paginationLinks" v-if="link != animalsPage.number">
                    <span class="page-link" @click="getAllAnimals(link, animalsQty)">{{link + 1}}</span>
                </li>
                <li class="page-item active" v-else>
                    <span class="page-link" v-if="link == animalsPage.number">{{link + 1}}</span>
                </li>
                <li class="page-item disabled" v-if="animalsPage.last">
                    <span class="page-link">></span>
                </li>
                <li class="page-item" v-else @click="getAllAnimals(animalsPage.number + 1, animalsQty)">
                    <span class="page-link">></span>
                </li>
                <li class="page-item disabled" v-if="animalsPage.last">
                    <span class="page-link">>></span>
                </li>
                <li class="page-item" v-else @click="getAllAnimals(animalsPage.totalPages - 1, animalsQty)">
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
                            <span>{{animal.name}}</span>
                        </div>
                    </div>
                </div>
                <div class="col-6">
                    <span>{{animal.status}}</span>
                    <img v-if="animal.imageUrl != null" :src="'' + animal.imageUrl" alt="profile photo" width="250px"
                         height="250px">

                </div>
                <div class="col-6" style="padding-top: 20px">
                    <div class="row justify-content-end">
                        <div class="col-12">{{animal.gender}}</div>
                        <div class="col-12">{{animal.wikiUrl}}</div>
                        <div class="col-12">created {{animal.createdAt}}</div>
                    </div>
                    <div>
                        Status :
                        <span v-for="status in statuses">
                            <input type="radio" :value="status" v-model="animal.status">{{status}}
                        </span>
                    </div>
                    <div>
                        <label for="uploadImage">Upload image:</label>
                        <input type="file" @change="uploadImage" id="uploadImage" style="width: 60px">
                    </div>
                    <div>
                        Departments :
                        <span v-for="department in departments">
                        <input type="radio" :value="department" v-model="animal.department">{{department}}
                    </span>
                    </div>
                    <div>
                        Show text input of : <br>
                        <input type="radio" value="description" v-model="textArea"> description
                        <input type="radio" value="illnessHistory" v-model="textArea"> illness history
                    </div>
                </div>
            </div>
            <div class="row" v-if="textArea == 'description'">
                <label for="description">Description</label>
                <textarea id="description" cols="100" rows="7" style="font-size: 12px"
                          v-model="animal.description"></textarea>
            </div>
            <div class="row" v-else>
                <label for="illnessHistory">IllnessHistory</label>
                <textarea id="illnessHistory" cols="100" rows="7" style="font-size: 12px"
                          v-model="animal.illnessesHistory"></textarea>
            </div>
            <div class="row justify-content-around">
                <div class="btn-group">
                    <button @click="saveAnimal()" class="btn btn-success">Update</button>
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
                        <label for="animalImage">Image:</label>
                        <input type="file" @change="uploadImage" id="animalImage">
                    </div>
                    <div class="form-group">
                        <label for="animalStatus">Status:</label>
                        <div id="animalStatus">
                            <span v-for="status in statuses">
                                <input type="radio" :value="status" v-model="animalTemplate.status">{{status}}
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="animalGender">Gender:</label>
                        <div id="animalGender">
                            <span v-for="gender in genders">
                                <input type="radio" :value="gender" v-model="animalTemplate.gender">{{gender}}
                            </span>
                        </div>
                    </div>
                    <div>
                        Show text input of : <br>
                        <input type="radio" value="description" v-model="textArea"> description
                        <input type="radio" value="illnessHistory" v-model="textArea"> illness history
                    </div>
                </div>
                <div class="col-6" style="padding-top: 20px">
                    <div class="form-group">
                        <label for="animalName">Name:</label>
                        <input id="animalName" type="text" v-model="animalTemplate.name" placeholder="name">
                    </div>
                    <div class="form-group">
                        <label for="wikiUrl">Wiki URL:</label>
                        <input id="wikiUrl" type="text" v-model="animalTemplate.wikiUrl" placeholder="wikipedia url">
                    </div>
                    <div class="form-group">
                        <label for="animalDepartment">Department:</label>
                        <div id="animalDepartment">
                            <span v-for="department in departments">
                                <input type="radio" :value="department" v-model="animalTemplate.department">{{department}}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row" v-if="textArea == 'description'">
                <label for="newDescription">Description</label>
                <textarea id="newDescription" cols="100" rows="7" style="font-size: 12px"
                          v-model="animalTemplate.description"></textarea>
            </div>
            <div class="row" v-else>
                <label for="newIllnessHistory">IllnessHistory</label>
                <textarea id="newIllnessHistory" cols="100" rows="7" style="font-size: 12px"
                          v-model="animalTemplate.illnessesHistory"></textarea>
            </div>
            <div class="row justify-content-around">
                <div class="btn-group">
                    <button @click="saveNewAnimal()" class="btn btn-success">Create</button>
                    <button onclick="hideEditProfile(1)" class="btn btn-danger">Cancel</button>
                </div>
            </div>
        </div>
    </div>
</div>