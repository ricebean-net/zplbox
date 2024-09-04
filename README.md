ZplBox utilizes an embedded [Chromium](https://www.chromium.org/Home/) engine to render your web content as PNG, enabling you to use the full web
technology stack to create your labels. PDF documents are converted to PNG using [Apache PDFBox](https://pdfbox.apache.org/). You can submit your
content either as a file or by referencing an external URL.

Once your content is rendered as a PNG, it is transformed into a monochrome image and encoded as a ZPL-native ASCII
hexadecimal string. The resulting ZPL label consists solely of this ASCII hexadecimal string, embedded in a Graphic
Field (^GF), and is returned in the response or directly forwarded to the label printer (ZPL Print Server).

This approach opens up new possibilities that are nearly impossible with native ZPL. For more details please
refer to https://zplbox.org.

# Quick Start
For a quick start, visit our Quick Start Guide at https://zplbox.org/#quick-start to get up and running with ZplBox in no time. This guide provides step-by-step instructions to help you begin your journey quickly and efficiently.

# Technical Documentation
## API Reference
You can find the swagger.yml file (OpenAPI specification) in the project root directory. A visual representation 
of this file is available at **https://zplbox.org/#api-reference**.

## Test Labels
The ZplBox project comes with a set of pre-installed example labels.

### UPS Shipping Label Example (HTML)

Reference: https://www.ups.com/assets/resources/webcontent/en_GB/CustomLabelHowTo.pdf

Target Label Size : 4 x 8 inches  
Resolution Printer: 8dpmm (203 dpi)  
Size Label in Pixes: 812 x 1624 pixels

Project Path: [src/main/resources/static/labels/ups-example.html](src/main/resources/static/labels/ups-example.html)  
Container URL: http://localhost:8080/labels/ups-example.html

```bash
curl --request POST \
     --url http://localhost:8080/v1/html2zpl \
     --header 'content-type: application/json' \
     --data '{ "url":"http://localhost:8080/labels/ups-example.html", "widthPts":812, "heightPts":1624 }'
```

**NOTE: Adjust label size to 4 x 8 inches for this label on https://labelary.com/viewer.html**

![Label Design UPS](./screens/label-design-ups.png)
*Google Chrome: Developer tools -> Custom Viewport Size (here: 812 x 1624 pixels - scale: 50 %)*

### Example Shipping Label (PDF)
Source: https://doc.phomemo.com/Labels-Sample.pdf

Target Label Size : 4 x 6 inches  
Resolution Printer: 8dpmm (203 dpi)

Project Path: [src/main/resources/static/labels/test-label-2.pdf](src/main/resources/static/labels/test-label-2.pdf)  
Container URL: http://localhost:8080/labels/test-label-2.pdf

```bash
curl --request POST \
     --url http://localhost:8080/v1/pdf2zpl \
     --header 'content-type: application/json' \
     --data '{ "url":"http://localhost:8080/labels/test-label-2.pdf", "dotsPerInch":203 }'
```

**NOTE: Adjust label size to 4 x 6 inches for this label on https://labelary.com/viewer.html**





## Code Examples
### Post base64 encoded HTML files
You can submit a file to ZplBox by encoding it to base64 as below:

```bash
curl --request POST \
     --url http://localhost:8080/v1/html2zpl \
     --header 'content-type: application/json' \
     --data '{ "dataBase64":"'$(base64 -w 0 my-file.html)'", "widthPts":812, "heightPts":1624 }'
```

