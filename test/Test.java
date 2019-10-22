import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

class Test implements PacketReceiver,Runnable{

    public NetworkInterface[] devices;
    public NetworkInterfaceAddress addr;
    public static JpcapCaptor jCaptor;
    //public static ArrayList<FlowNode> flow=new ArrayList<FlowNode>();
    public static JpcapSender sender=null;

    public static Test T = new Test();
    public StringBuffer sb1 = new StringBuffer();
    public static boolean chooseMode=true;
    public static long time;
    public static long time1;
    public static long beginTime;
    public String s=new String();

    //返回设备列表
    public NetworkInterface[] getDevices() {
        devices = JpcapCaptor.getDeviceList();
        return devices;
    }

    public void desNetworkInterface() {
        byte[]b;
        sb1.append("---------------------------------------网卡信息-------------------------------------\n");
        sb1.append("总共有 " + devices.length + " 个网络设备接口\n");

        for (int i = 0; i < devices.length; i++) {
            sb1.append("\n设备接口" + i+ ":\n");
            sb1.append("网络接口名称:" + devices[i].name+"\n");
            sb1.append("网络接口描述:" + devices[i].description+"\n");
            sb1.append("数据链路层名称:" + devices[i].datalink_name+"\n");
            sb1.append("数据链路层描述:" + devices[i].datalink_description+"\n");
            sb1.append("是否是LOOPBACK设备:" + devices[i].loopback+"\n");
            sb1.append("MAC地址:");

            b =devices[i].mac_address;
            for(int j=0;j < b.length;j++) {
                //由于toHexString输出时，遇到第一个是00000000字节时，输出16进制只有一个0，所以要添加个0。我机器是这样，不知道其他的
                if(j==0 && Integer.toHexString(b[0]&0xff).equals("0")) sb1.append(0);//字符串记得要用equals比较（忽略大小写的）
                if(j<b.length-1)sb1.append(Integer.toHexString(b[j]&0xff) .toUpperCase()+ ":"); //转换为十六进制，并且是大写
                else sb1.append(Integer.toHexString(b[j]&0xff) + " ");

            }
            sb1.append("\n");
        }

        sb1.append("-----------------------------------------------------------------------------------");
        System.out.println(sb1);
    }

    @Override
    public void run() {
        T.devices = T.getDevices();
        T.desNetworkInterface();

    }

    @Override
    public void receivePacket(Packet arg0) {
        // TODO Auto-generated method stub

    }

    // ---------------------Main-------------------------------
    public static void main(String[] args) {
        Thread t = new Thread(new Test());
        t.start();
    }

}