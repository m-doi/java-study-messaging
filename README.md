# 勉強会資料
メッセージを送って、受信してみよう。

## RabbitMQ Serverを立ち上げる
時間かかるでー
```
vagrant up
```

管理画面に接続してみる。

[http://192.168.200.22:15672/](http://192.168.200.22:15672/ "RabbiMQ管理画面")

## Queueを作っとく
上記管理画面から、com.doilux.greetingというQueueを作っておく。

## メッセージを送ってみる
プログラムを起動する。
```
cd sample-app
./gradlew bootRun
```
メッセージを送る。

[http://localhost:8080/send](http://localhost:8080/send "RabbiMQ管理画面")

管理画面からメッセージを確認する。

メッセージを受信する。

[http://localhost:8080/receive](http://localhost:8080/receive "RabbiMQ管理画面")

## コードを読んでみよう

```
cat ./sample-app/src/main/java/com/doilux/Sender.java
cat ./sample-app/src/main/java/com/doilux/Receiver.java
```
