import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by stefan on 02.09.2016.
 */
public class RocNetClient {

    private final JList<String> list;
    private final DefaultListModel<String> model;

    enum Group {
        Host,
        Command_Station,
        Mobile_decoders,
        Stationary_decoders,
        Programming_mobile,
        Programming_stationary,
        GP_data_transfer,
        Clock,
        Sernsor,
        Output,
        Input,
        Sound,
        Display
    }

    enum Code {
        request, event, reply
    }

    private boolean end = false;

    public static void main(String[] args) throws IOException {
        new RocNetClient();
    }

    public RocNetClient() throws IOException {
        JFrame f = new JFrame("Abfahrten");
        f.setBackground(Color.blue);
        list = new JList<String>(this.model = new DefaultListModel<String>());
//        list.setBackground(new Color(0, 0, 150));
        list.setBackground(new Color(0, 0, 111));
        list.setFont(list.getFont().deriveFont(24f));
        list.setForeground(Color.white);
        list.setVisibleRowCount(8);
        list.setPreferredSize(new Dimension(480,320));
        f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                end = true;
                e.getWindow().dispose();
            }
        });
        f.getContentPane().add(list);
        f.pack();
        f.setLocation(10, 10);
        f.setVisible(true);

        InetAddress addr = InetAddress.getByName("224.0.0.1");
        MulticastSocket ms = new MulticastSocket(4321);
        ms.setReuseAddress(true);
        ms.joinGroup(addr);

        while (!end) {
            byte[] buffer = new byte[1024 * 8];

            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            ms.receive(dp);
            dumpPacket(dp);
        }

    }

    private void dumpPacket(DatagramPacket packet) throws IOException {
        InetAddress addr = packet.getAddress();
        ByteArrayInputStream in = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
        copyStream(in, System.out);
    }

    private void copyStream(InputStream in, OutputStream out) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(in);
        BufferedOutputStream bout = new BufferedOutputStream(out);

        int netId = bin.read();
        int receipientIdH = bin.read();
        int receipientIdL = bin.read();
        int senderIdH = bin.read();
        int senderIdL = bin.read();
        Group group = Group.values()[bin.read()];
        Code code = Code.values()[bin.read()];
        int length = bin.read();

        byte[] data = new byte[length];
        bin.read(data);

        if (group == Group.Display) {
            System.out.println("TEXT = " + new String(data));
            if (new String(data).trim().isEmpty()) {
                if (model.size() > 0) {
                    model.remove(0);
                }
            } else {
                model.addElement(new String(data));
            }
        }

        bout.flush();
    }

}
