package com.meixxi.zplbox.service.print;

import com.meixxi.zplbox.controller.SystemController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Service
public class PrintServiceImpl implements PrintService {

    private static final Logger log = LoggerFactory.getLogger(PrintServiceImpl.class);


    @Override
    public void printLabel(String zplData, String ip, int port) throws Exception {
        try (Socket socket = new Socket(ip, port)) {
            OutputStream os = socket.getOutputStream();
            os.write(zplData.getBytes(StandardCharsets.US_ASCII));
            os.flush();

        } catch (IOException e) {
            log.error("Error printing label.", e);
            throw e;
        }
    }
}
