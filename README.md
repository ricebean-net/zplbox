# HTML 2 ZPL
**Use your favorite web framework to design ZPL Labels far more straightforward and sophisticated than native ZPL will ever can do!**

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
### 1. Start html2zpl as Docker container:
```shell
docker run -p 8080:8080  ghcr.io/meixxi/html2zpl:latest
```
### 2. Creation of a test label:
This example reference the test label 'ups-example.html', pre-installed in the docker container. More about test labels you can find below.
```shell
curl --request POST \
     --url http://localhost:8080/v1/html2zpl \
	 --header 'content-type: application/json' \
	 --data '{ "url":"http://localhost:8080/labels/ups-example.html", "widthPts":600, "heightPts":800 }'
```

### 3. Verify the output 
Copy the generated ZPL Code to https://labelary.com/viewer.html to get a visual representation.


## Test Labels
The html2zpl project comes with a set of pre-installed example labels. 

### UPS Shipping Label Example
Reference: https://www.ups.com/assets/resources/webcontent/en_GB/CustomLabelHowTo.pdf 

Target Label Size : 4 x 8 inches  
Resolution Printer: 8dpmm (203 dpi)  
Size Label in Pixes: 812 x 1624	 pixels

Project Path: [src/main/resources/static/labels/ups-example](src/main/resources/static/labels/ups-example.html)  
Container URL: http://localhost:8080/labels/ups-example.html

![Label Design UPS](./screens/label-design-ups.png)



## API Reference:
You can find the swagger.yml file in the project root. The visual representation of the file you can
find **[here](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/meiXXI/html2zpl/main/swagger.yml)**.

