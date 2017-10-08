
import CoreLocation

@objc(geolocation) class geolocation : CDVPlugin {


    override func pluginInitialize() {

    }


    // Function show status plugin
    @objc(getstatus:)
    func getstatus(command: CDVInvokedUrlCommand) {

        var pluginResult = CDVPluginResult(
            status: CDVCommandStatus_ERROR
        )

        let msg = command.arguments[0] as? String ?? ""

        if msg.characters.count > 0 {
            pluginResult = CDVPluginResult(
                status: CDVCommandStatus_OK,
                messageAs: msg
            )
        }

        self.commandDelegate!.send(
            pluginResult,
            callbackId: command.callbackId
        )

    }


    // Function get current location
    @objc(getgeolocation:)
    func getgeolocation(command: CDVInvokedUrlCommand) {

        var pluginResult = CDVPluginResult(
            status: CDVCommandStatus_ERROR
        )

        // Get current location
        let locationManager = CLLocationManager()
        locationManager.delegate = self as? CLLocationManagerDelegate;
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestAlwaysAuthorization()
        locationManager.startUpdatingLocation()

        var currentLocation: CLLocation!
        currentLocation = locationManager.location
        print(currentLocation.coordinate.latitude)
        print(currentLocation.coordinate.longitude)

        func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
            let locValue:CLLocationCoordinate2D = manager.location!.coordinate
            print("locations = \(locValue.latitude) \(locValue.longitude)")
        }

        // Pass latitude & longitude to view
        let lat = ["lat": currentLocation.coordinate.latitude] as [AnyHashable : Any]
        let lng = ["lng": currentLocation.coordinate.longitude] as [AnyHashable : Any]

        let array = [lat, lng]

        pluginResult = CDVPluginResult(
            status: CDVCommandStatus_OK,
            messageAs: array
        )

        self.commandDelegate!.send(
            pluginResult,
            callbackId: command.callbackId
        )

    }

}
