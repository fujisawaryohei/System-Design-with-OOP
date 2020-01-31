object Sample {
    def main(args: Array[String]): Unit = {
        val price = computePrice(10, 200);
        println(price);
    }

    def computePrice(quantity: Int, unitPrice: Int): Double = {
        var basePrice = quantity + unitPrice;
        
        var shippingPrice = 0;
        if(basePrice < 3000){
            shippingPrice += 500;
        }

        val totalPrice = (basePrice + shippingPrice).toDouble * 1.10
        
        return totalPrice;
    }
}