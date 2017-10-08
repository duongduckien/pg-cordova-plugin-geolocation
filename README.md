# Cordova Plugin Geolocation

Get current location in android & ios mobile app

## Example

Installing the Cordova CLI
```
npm install -g cordova
```

Create the App
```
cordova create hello com.example.hello HelloWorld
cd hello
```

Add Platforms
```
cordova platform add ios
cordova platform add android
```

Add plugin
```
cordova plugin add pg-geolocation
cordova plugin add cordova-plugin-add-swift-support --save
```

## For IOS

* Open Xcode & choose team in signing
<<<<<<< HEAD
* Build Phases > Link Binary With Libraries
=======
* Build Phases > Link Binary With Libraries
>>>>>>> 4457318df7b3a3b98f170747457b6caad2ba04d7
==> Add CoreLocation.framework

##
<img src="https://i.imgur.com/0ZPS6gF.png" width="600">

* Edit HelloWorld-Info.plist
* Add privacy location permision
##
<img src="https://i.imgur.com/CcsV0Xn.png" width="600">

##
* Change hello\www\js\index.js
* Replace onDeviceReady function

```
onDeviceReady: function() {

  app.receivedEvent('deviceready');

  geolocation.getgeolocation(
    'Geolocation',
    function(msg) {
      document.getElementById('lat').innerHTML = msg[0].lat;
      document.getElementById('lng').innerHTML = msg[1].lng;
    },
    function(err) {
      document.getElementById('lat').innerHTML = err;
    }
  );

  geolocation.getstatus(
    'Plugin Ready',
    function(msg) {
      document.getElementById('deviceready').querySelector('.received').innerHTML = msg;
    },
    function(err) {
      document.getElementById('deviceready').innerHTML = '<p class="event received">' + err + '</p>';
    }
  );

  geolocation.getname(
    'Geolocation plugin',
    function(msg) {
      document.getElementsByTagName('h1')[0].innerHTML = msg;
    },
    function(err) {
      document.getElementsByTagName('h1')[0].innerHTML = err;
    }
  );
},
```

* Change hello\www\index.html
```
<div class="app">
    <h1>Apache Cordova</h1>
    <div id="deviceready" class="blink">
        <p class="event listening">Connecting to Device</p>
        <p class="event received">Device is Ready</p>
    </div>
    <div class="">
      <p>Latitude: <span id="lat"></span></p>
      <p>Longitude: <span id="lng"></span></p>
    </div>
</div>
```
Build app
```
cordova build ios
```
Run app in Xcode

##
<img src="https://i.imgur.com/tAtnF9I.png" width="300">

<img src="https://i.imgur.com/heIXwE1.png" width="300">
<<<<<<< HEAD

=======

>>>>>>> 4457318df7b3a3b98f170747457b6caad2ba04d7

## For Android

...
