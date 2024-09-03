package io.github.meixxi.zplbox.service.print;

public interface PrintService {

    /**
     * Print ZPL Data at a given TCP destination (Label printer).
     * @param zplData The ZPL Data to be printed.
     * @param ip The destinations ip.
     * @param port The destinations port.
     */
    void printLabel(String zplData, String ip, int port) throws Exception;
}
