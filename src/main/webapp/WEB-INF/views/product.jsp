<%@include file="/WEB-INF/taglib.jsp" %>
<div id="mainArea" class="row justify-content-center">
    <div>
        <img src="${product.imageUrl}" alt="product photo" width=256 height="256">
    </div>
    <div style="margin-left: 20px;">
        <div class="col-12" style="height: 50px;">
            ${product.name}
        </div>
        <div>
            ${product.category}
        </div>
        <div>
            ${product.price}
        </div>
        <div>
            Choose size :
            <span v-for="size in sizes">
                <input type="radio" :value="size" v-model="orderSize">{{size}}
            </span>
        </div>
        <div>
            Choose quantity:
            <input type="number" min="1" max="100" step="1" v-model="orderQty">
        </div>
    </div>
    <sec:authorize access="isAuthenticated()">
        <div class="row col-12 justify-content-around" style="margin-top: 20px;">
            <button class="btn btn-success" @click="addToBasket(${product.id})">Add to basket</button>
        </div>
    </sec:authorize>
    <div class="row col-12 justify-content-around">
        ${product.description}
    </div>
</div>
<div id="mc-container"></div>
<script type="text/javascript">
    cackle_widget = window.cackle_widget || [];
    cackle_widget.push({widget: 'Comment', id: 60612});
    (function () {
        var mc = document.createElement('script');
        mc.type = 'text/javascript';
        mc.async = true;
        mc.src = ('https:' == document.location.protocol ? 'https' : 'http') + '://cackle.me/widget.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(mc, s.nextSibling);
    })();
</script>