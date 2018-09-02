new Vue({
   el: "#buyTicketArea",
   data: {
       tickets: 1,
       summary: 57
   },
    methods: {
        calculateSummary(){
            this.summary = this.tickets * 57;
        }
    }
});