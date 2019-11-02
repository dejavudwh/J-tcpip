package linklayer;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;

import java.io.IOException;
import java.net.Inet4Address;

public class ProtocolsEntry {

    public static void main(String[] args) throws IOException {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        NetworkInterface device = null;

        System.out.println("there are " + devices.length + " devices");

        for (int i = 0; i < devices.length; i++) {
            boolean findDevice = false;

            for (NetworkInterfaceAddress addr : devices[i].addresses) {
                if (!(addr.address instanceof Inet4Address)) {
                    continue;
                }

                findDevice = true;
                break;
            }

            if (findDevice) {
                device = devices[i];
                break;
            }

        }

        System.out.println("open divice: " + device.name);

        JpcapCaptor jpcap = JpcapCaptor.openDevice(device, 2000, true, 20);

        jpcap.loopPacket(-1, new DataLinkLayer());
    }
}
