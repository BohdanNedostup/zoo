<div class="row" id="manageArea">
    <section id="filter" class="col-12">
        <div class="form-inline">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">first name</span>
                </div>
                <input type="text" v-model="filter.firstName" class="form-control" placeholder="firstName">
                <div class="input-group-prepend">
                    <span class="input-group-text">last name</span>
                </div>
                <input type="text" v-model="filter.lastName" class="form-control" placeholder="lastName">
                <div class="input-group-prepend">
                    <span class="input-group-text">email</span>
                </div>
                <input type="text" v-model="filter.email" class="form-control" placeholder="email">
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">birth date from</span>
                </div>
                <input type="date" v-model="filter.birthDateFrom" class="form-control">
                <div class="input-group-prepend">
                    <span class="input-group-text">birth date to</span>
                </div>
                <input type="date" v-model="filter.birthDateTo" class="form-control">
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
                    <span class="input-group-text">telephone</span>
                </div>
                <input type="text" v-model="filter.telephone" class="form-control">
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">Country</span>
                </div>
                <select v-model="filter.country">
                    <option v-for="country in countries">{{country}}</option>
                </select>
                <div class="input-group-prepend">
                    <span class="input-group-text">Roles</span>
                </div>
                <span v-for="role in roles">
                    <input type="checkbox" :value="role" v-model="filter.roles">{{role}}
                </span>
                <div class="input-group-append">
                    <span class="input-group-text"></span>
                </div>
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
                Users quantity : {{usersPage.totalElements}}<br>
            </div>
            <div>
                <ul class="pagination">
                    <li class="page-item disabled" v-if="usersPage.first">
                        <span class="page-link"><<</span>
                    </li>
                    <li class="page-item" v-else @click="getAllUsers(0, userQty)">
                        <span class="page-link"><<</span>
                    </li>
                    <li class="page-item disabled" v-if="usersPage.first">
                        <span class="page-link"><</span>
                    </li>
                    <li class="page-item" v-else @click="getAllUsers(usersPage.number - 1, userQty)">
                        <span class="page-link"><</span>
                    </li>
                    <li class="page-item" v-else><</li>
                    <li class="page-item" v-for="link in paginationLinks" v-if="link != usersPage.number">
                        <span class="page-link" @click="getAllUsers(link, userQty)">{{link + 1}}</span>
                    </li>
                    <li class="page-item active" v-else>
                        <span class="page-link" v-if="link == usersPage.number">{{link + 1}}</span>
                    </li>
                    <li class="page-item disabled" v-if="usersPage.last">
                        <span class="page-link">></span>
                    </li>
                    <li class="page-item" v-else @click="getAllUsers(usersPage.number + 1, userQty)">
                        <span class="page-link">></span>
                    </li>
                    <li class="page-item disabled" v-if="usersPage.last">
                        <span class="page-link">>></span>
                    </li>
                    <li class="page-item" v-else @click="getAllUsers(usersPage.totalPages - 1, userQty)">
                        <span class="page-link">>></span>
                    </li>
                </ul>
            </div>
            <div>
                <div>Items on page</div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="one" value="20" v-model="userQty"
                               @change="updateUserQty()"> 20
                    </label>
                </div>
                <div class="form-check-inline">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="two" value="50" v-model="userQty"
                               @change="updateUserQty()"> 50
                    </label>
                </div>
                <div class="form-check-inline disabled">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input" id="three" value="100" v-model="userQty"
                               @change="updateUserQty()"> 100
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
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Email</th>
                    <th>Roles</th>
                    <th>Status</th>
                    <th>Department</th>
                    <th>Image</th>
                    <th>Gender</th>
                    <th>Birth date</th>
                    <th>Telephone</th>
                    <th>Country</th>
                    <th>Created at</th>
                    <th>Global number</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(user, index) in usersPage.content">
                    <td>{{index + 1}}</td>
                    <td>{{user.id}}</td>
                    <td>{{user.firstName    }}</td>
                    <td>{{user.lastName     }}</td>
                    <td>{{user.email        }}</td>
                    <td>{{user.userRoles    }}</td>
                    <td>{{user.status       }}</td>
                    <td>{{user.department   }}</td>
                    <td>{{user.imageUrl     }}</td>
                    <td>{{user.gender       }}</td>
                    <td>{{user.birthDate    }}</td>
                    <td>{{user.telephone    }}</td>
                    <td>{{user.country      }}</td>
                    <td>{{user.createdAt    }}</td>
                    <td>{{user.globalNumber }}</td>
                    <td v-if="user.email != admin.email">
                        <div class="btn-group align-items-center">
                            <button @click="updateUser(index)"
                                    class="manage-button btn btn-warning">Edit
                            </button>
                            <%--<button class="manage-button btn btn-info">Profile</button>--%>
                            <button @click="deleteUserById(user.id)" class="manage-button btn btn-danger">Delete
                            </button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="row justify-content-around">
            <ul class="pagination">
                <li class="page-item disabled" v-if="usersPage.first">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item" v-else @click="getAllUsers(0, userQty)">
                    <span class="page-link"><<</span>
                </li>
                <li class="page-item disabled" v-if="usersPage.first">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else @click="getAllUsers(usersPage.number - 1, userQty)">
                    <span class="page-link"><</span>
                </li>
                <li class="page-item" v-else><</li>
                <li class="page-item" v-for="link in paginationLinks" v-if="link != usersPage.number">
                    <span class="page-link" @click="getAllUsers(link, userQty)">{{link + 1}}</span>
                </li>
                <li class="page-item active" v-else>
                    <span class="page-link" v-if="link == usersPage.number">{{link + 1}}</span>
                </li>
                <li class="page-item disabled" v-if="usersPage.last">
                    <span class="page-link">></span>
                </li>
                <li class="page-item" v-else @click="getAllUsers(usersPage.number + 1, userQty)">
                    <span class="page-link">></span>
                </li>
                <li class="page-item disabled" v-if="usersPage.last">
                    <span class="page-link">>></span>
                </li>
                <li class="page-item" v-else @click="getAllUsers(usersPage.totalPages - 1, userQty)">
                    <span class="page-link">>></span>
                </li>
            </ul>
        </div>
    </section>
    <div id="profileAreaWrapper" class="container-fluid row justify-content-around">
        <div id="profileArea" class="rounded">
            <div class="row">
                <div class="col-12">
                    <div class="row justify-content-around">
                        <div>
                            <span>{{user.firstName}}</span>
                            <span>{{user.lastName}}</span>
                        </div>
                    </div>
                </div>
                <div class="col-6">
                    <span>{{user.status}}</span>
                    <img v-if="user.imageUrl != null" :src="'' + user.imageUrl" alt="profile photo" width="250px"
                         height="250px">
                    <span>created {{user.createdAt}}</span>
                </div>
                <div class="col-6" style="padding-top: 50px">
                    <div class="row justify-content-end">
                        <div class="col-12">{{user.gender}}</div>
                        <div class="col-12">{{user.birthDate}}</div>
                        <div class="col-12">{{user.email}}</div>
                        <div class="col-12">{{user.country}}</div>
                        <div class="col-12">{{user.telephone}}</div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Roles</span>
                    </div>
                    <span v-for="role in roles" v-if="role != 'ROLE_USER'">
                        <input type="checkbox" :value="role" v-model="user.userRoles">{{role}}
                    </span>
                    <span v-else>
                        <input type="checkbox" :value="role" v-model="user.userRoles" disabled>{{role}}
                    </span>
                </div>
            </div>
            <div class="row">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Department</span>
                    </div>
                    <span v-for="department in departments">
                        <input type="radio" :value="department" v-model="user.department">{{department}}
                    </span>
                </div>
            </div>
            <div class="row justify-content-around">
                <div class="btn-group">
                    <button @click="saveUser()" class="btn btn-success">Update</button>
                    <button onclick="hideEditProfile()" class="btn btn-danger">Cancel</button>
                </div>
            </div>
        </div>
    </div>
</div>

