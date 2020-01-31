今回からオブジェクト指向設計のブログを書いていきたいと思います。  
本日は、変更に強いプログラムという点に重きを置いて書いていきたいと思います。  

## 変更が大変なプログラムの特徴
変更が大変なプログラムの特徴は３つあります。   

・メソッドが長い  
・クラスが大きい  
・引数が多い  

仮に簡単なロジックと言え、長いメソッドはそれだけで理解するのに時間がかかります。  
（if-else文が入り組んでいるや、繰り返し文のネストなど）  
また、大きなクラスは責務超過で有ることが多いです。関心事を詰め込みすぎているため、  
変更箇所を変更する事でクラスのどの部分に影響が出るのか等の特定をするまでに時間がかかります。  
引数が多い場合、関心事を詰め込みすぎている事が多いです。  
変更する時に、どの引数が関係し、どの引数が関係しないかを特定するのが大変です。  

引数が長いと必然的にメソッドが長くなり、if-else文などが増えます。またメソッドが長くなりがちだと、  
クラスが肥大化していきます。引数の数やメソッド内での記述量がクラスの肥大化へと繋がります。  

## プログラムの変更が楽になる書き方
### 1.わかりやすい名前を使う。
名前のわかりやすさはプログラムの読みやすさ/読みにくさに繋がります。  
当たり前ではありますが、わかりやすい変数名を命名することや、メソッド名でもわかりやすいメソッド名を命名することが重要です。  
例えば、配送システムを作っている場合、数量をquantity、価格をprice、単価をunitPriceなど、そのシステムでの用語をそのまま  
命名してあげると、数量の関心事が急遽変更された場合、数量として命名されている変数や、クラス等の記述がある箇所を特定しやすくなります。

### 2. 長いメソッドは「段落」にわけて読みやすくする。
だらだらと切れ目なく書かれたプログラムは可読性が下がり、切れ目があるプログラムと比較して理解するまでに時間がかかります。  
そういうプログラムを読みやすくする簡単な方法が、前後の行と意味が異なる場所を「段落」で分ける事です。

```scala
var price = quantity * unitPrice;
if(price > 3000){
    price += 500;
}
price = price * taxRate();
```
上記のプログラムは、**ベース価格の計算**、**送料の加算**、**税額の加算**の３つを行っています。ですので、

```scala
var price = quantity * unitPrice;

if(price < 3000){
    price += 500;
}

price = price * taxRate();
```

といった形でそれぞれ意味の異なる箇所で段落分けを行って上げる事で一気に見やすさが向上します。  
**プログラムを読みながら意味が異なる場所を見つけた場合は、積極的に空白行を追加して段落分けを行いましょう。**

### 3.目的ごとに変数を用意する。
段落分けを行いましたが、まだ理解しづらい部分があります。それはローカル変数のpriceを使いまわしているという点です。  
上記のプログラムが何をしているか改めて整理してみると、    

・購入数と単価から合計金額を計算する。  
・その金額が3000円未満だった場合送料が500円加算される。  
・その送料も含めた合計金額を税を加算して最終金額を割り出す。

つまり、ただpriceと言っても合計金額、送料込みの合計金額、税込みの最終金額と3つの種類の価格がこのプログラムには登場します。  
ですが、これをローカル変数のpriceだけ使用してプログラムを書いてしまう事で、今は何を求めているのかがわからなくなってしまいます。

```scala
var basePrice = quantity * unitPrice;

var shippingPrice = 0;
if(basePrice < 3000){
    shippingPrice += 500;
}

val totalPrice = (basePrice + shippingPrice) * taxRate();
```

それぞれの計算ステップごとに専用のローカル変数を用意したので、計算の目的が明確になりました。　　
こうすることで変更の影響範囲が局所化し、またコードのメンテナンスが行いやすくなりました。  
このように専用のローカル変数を用意して、コードの意図を変数名で説明するやり方を**説明用の変数の導入**と呼びます。  
既存コードの設計を改善するリファクタリングの基本テクニックです。  
最初に示したprice変数の例のように、1つの変数を使い回す事を**破壊的代入**と呼びます。