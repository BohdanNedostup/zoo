new Vue({
    el: "#mainArea",
    data: {
        rootUrl: "https://immense-springs-86257.herokuapp.com/api/shop",
        sizes: [],
        orderSize: "M",
        orderQty: 1
    },
    methods: {
        getSizes(){
            axios.get( this.rootUrl + "/sizes")
                .then(response => {
                    this.sizes = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        addToBasket(id){
            axios.put( this.rootUrl + "/buy?id=" + id + "&size=" + this.orderSize + "&qty=" + this.orderQty)
                .then(response => {
                    user().getUserProcessingOrders();
                })
                .catch(error => {
                    console.log(error);
                })
        }

    },
    mounted(){
        this.getSizes();
    }
});