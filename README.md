# HTML 2 ZPL
**Use your favorite web framework to design ZPL Labels far more straightforward and sophisticated than native ZPL will ever allow you!**

html2zpl is based on an embedded Chromium, rendering your web content as PNG. So, you can leverage the full web technology stack to create your labels.
You can submit your web content via a file or by referencing it using an external URL (preferred).

Once rendered as PNG, your content will be converted into a monochrome image and encoded in a ZPL native ASCII hexadecimal string. 
The final ZPL label sent to the printer consists of only the ASCII hexadecimal string embedded in a Graphic Field (^GF). 

This approach will allow you new possibilities, which are nearly impossible with native ZPL, including:
* **Images:** Embed and scale images using default web technologies
* **Fonts:** You can use any font supported by web frameworks
* **Text / Typography:** You can use any methods you use in web design.
* ...
