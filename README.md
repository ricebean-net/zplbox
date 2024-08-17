# HTML 2 ZPL
**Use your favorite web framework to design ZPL Labels far more straightforward and sophisticated than native ZPL will ever allow you!**

html2zpl is based on an embedded Chromium, rendering your web content as PNG. So, you can leverage the full web technology stack to create your labels.
You can submit your web content via a file or by referencing it using an external URL (preferred).

Once rendered as PNG, your content will be converted into a monochrome image and encoded in a ZPL native ASCII hexadecimal string. 
The final ZPL label consists of only the ASCII hexadecimal string embedded in a Graphic Field (^GF) and will sent back in the response. 

This approach will allow you new possibilities, which are nearly impossible with native ZPL, including:
* **Images:** Embed and scale images flexibly using default web technologies
* **Fonts:** You can use any fonts supported by web frameworks
* **Text / Typography:** You can use any methods you use in web design.
* ...


## Quick Start
Start html2zpl as Docker container:
```shell
docker run -p 8080:8080  ghcr.io/meixxi/html2zpl:main
```

### HTTP Endpoint:
```shell
POST: /v1/html2zpl/
```

### Payload:
```json
{
    "url": "http://localhost:8080/labels/test-label-1.html",
    "widthPts": 600,
    "heightPts": 800
}
```
 ### Response (ZPL Data):
```text
^XA
^FO0,0^GFA,60000,60000,75,[...]
^XZ
```

## Example
Here is an example using the curl command.
```shell
curl --request POST \
     --url http://localhost:8080/v1/html2zpl/ \
	 --header 'content-type: application/json' \
	 --data '{ "url":"http://localhost:8080/labels/test-label-1.html", "widthPts":600, "heightPts":800 }'
```

## Test Labels
You can find test labels in static resource folder of the project:
* **test-label-1.html:** [src/main/resources/static/labels/test-label-1.html](src/main/resources/static/labels/test-label-1.html) (URL: http://localhost:8080/labels/test-label-1.html)
