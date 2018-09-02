<div class="row justify-content-around" id="buyTicketArea">
    <form action="">
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Tickets</span>
            </div>
            <input type="number" @change="calculateSummary" min="1" step="1" v-model="tickets">
            <div class="input-group-append">
                <span class="input-group-text">summary : {{summary}}</span>
            </div>
        </div>
        <button class="btn btn-success">Donate</button>
    </form>
</div>
