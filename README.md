ZplBox revolutionizes ZPL label creation by allowing you to use the full web technology stack. Design your labels with 
**HTML, CSS, and JavaScript**, and let ZplBox handle the conversion. Your web content is rendered as a PNG and then transformed 
into a ZPL graphic, giving you the freedom to incorporate **images, custom fonts, rich typography, and special characters** with ease.

Beyond web content, ZplBox also features robust **PDF support**. It can convert any PDF document into a high-quality PNG using 
Apache PDFBox, which is then seamlessly integrated into your ZPL label. This allows you to leverage existing PDF designs and 
documents for your labels, simplifying your workflow and expanding your design possibilities.

Whether you submit your content as a web file, a URL, or a PDF, ZplBox provides a straightforward way to generate ZPL code 
as a string or send it directly to your printer. This unique approach leverages modern web and document technologies to create
dynamic and complex labels that are nearly impossible to achieve with native ZPL alone.

ZplBox offers a flexible and powerful solution for generating ZPL labels, whether you use our [**Cloud-Hosted Service**](https://rapidapi.com/ricebean-ricebean-default/api/zplbox2) or our convenient [**Self-Hosted ZPL Print Server**](https://zplbox.org/#zpl-print-server).

## Cloud-Hosted Service (*hosted*)
Our cloud-hosted ZplBox API, available on RapidAPI, provides a seamless, serverless experience for your label generation needs.

[**ZplBox at RapidAPI >>**](https://rapidapi.com/ricebean-ricebean-default/api/zplbox2)

### Quick Start
Create a ZPL label of test label https://zplbox.org/test-labels/test-label-2.pdf:
```shell
$ curl --request POST 
    --url https://zplbox2.p.rapidapi.com/v1/pdf2zpl 
    --header 'Accept: text/plain' 
    --header 'Content-Type: application/json' 
    --header 'x-rapidapi-host: zplbox2.p.rapidapi.com' 
    --header 'x-rapidapi-key: [YOUR_API_KEY]' 
    --data '{"url":"https://zplbox.org/test-labels/test-label-2.pdf","dotsPerInch":203,"orientation":"Rotate0"}'
    --output my-zpl-label.txt
```

To print a file containing ZPL code (_e. g. my-zpl-label.txt_), use the `netcat` (`nc`) command-line utility, available on **macOS** and **Linux**. Zebra printers are listening on port 9100 by default:
```shell
$ nc -N 192.168.100.42 9100 < my-zpl-label.txt
```
If you are using **Windows**, the official replacement for Netcat is Ncat, which is included with the popular Nmap network utility.

## Community Edition *(self-hosted)*
ZplBox is designed as a Docker container what can be started using this command:
```shell
$ docker run -p 8080:8080 ghcr.io/ricebean-net/zplbox:latest
```
ZplBox comes with an intuitive user interface (UI) designed to help you get started with ZplBox and its API 
effortlessly. The UI allows you to manually convert HTML and PDF files to ZPL (Zebra Programming Language) and
send the generated ZPL labels directly to a printer. 

Once the docker container is started, you can access the interface at http://localhost:8080.

## Documentation and Examples 
For a complete guide, technical documentation, and additional code examples, please visit **https://zplbox.org**.

* **Extended Quick Start:** Get up and running fast with our step-by-step guide.
* **API Reference:** Explore the full API with our visual Swagger documentation.
* **Test Labels:** View and run examples for both HTML and PDF labels.
* **Code Examples:** Find code snippets for common use cases.