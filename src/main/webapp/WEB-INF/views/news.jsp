<div id="newsArea" class="container">
    <div class="news" v-for="news in newsPage.content">
        <div>{{news.createdAt}}</div>
        <span>{{news.title}}</span>
        <span><a :href="'/news/' + news.id">more...</a></span>
    </div>
    <div class="row justify-content-around">
        <ul class="pagination">
            <li class="page-item disabled" v-if="newsPage.first">
                <span class="page-link"><<</span>
            </li>
            <li class="page-item" v-else @click="getAllNews(0)">
                <span class="page-link"><<</span>
            </li>
            <li class="page-item disabled" v-if="newsPage.first">
                <span class="page-link"><</span>
            </li>
            <li class="page-item" v-else @click="getAllNews(newsPage.number - 1)">
                <span class="page-link"><</span>
            </li>
            <li class="page-item" v-else><</li>
            <li class="page-item" v-for="link in paginationLinks" v-if="link != newsPage.number">
                <span class="page-link" @click="getAllNews(link)">{{link + 1}}</span>
            </li>
            <li class="page-item active" v-else>
                <span class="page-link" v-if="link == newsPage.number">{{link + 1}}</span>
            </li>
            <li class="page-item disabled" v-if="newsPage.last">
                <span class="page-link">></span>
            </li>
            <li class="page-item" v-else @click="getAllNews(newsPage.number + 1)">
                <span class="page-link">></span>
            </li>
            <li class="page-item disabled" v-if="newsPage.last">
                <span class="page-link">>></span>
            </li>
            <li class="page-item" v-else @click="getAllNews(newsPage.totalPages - 1)">
                <span class="page-link">>></span>
            </li>
        </ul>
    </div>
</div>