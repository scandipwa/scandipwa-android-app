# scandipwa-android-app

## The logic
App contains of 2 activities. Firstly, splash screen activity shows up and then attempts to launch URL as Trusted Web Activity - Chrome Custom Tab.
Chrome checks for SHA-256 key in `https://<host>/.well-known/assetlinks.json` and shows the content without toolbar if the key is valid.

## Using "trusted" feature

To make this app trusted (remove toolbar) you must add
```json
[{
  "relation": ["delegate_permission/common.handle_all_urls"],
  "target" : { "namespace": "android_app", "package_name": "com.scandipwa",
               "sha256_cert_fingerprints": ["CA:7A:FC:B9:57:43:33:CF:75:F3:78:DB:CD:FB:5A:60:B9:3A:37:84:C0:03:61:DB:3F:F8:66:0B:75:5B:BE:A9"] }
}]
```
using path `https://<host>/.well-known/assetlinks.json`

SHA-256 key is obtained from the `Google Play Console -> Release Management -> App Signing` and is checked in the location above every time user opens the app.
