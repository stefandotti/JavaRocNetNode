package at.dotti.rocnet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.Excluder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.json.stream.JsonParser;
import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by stefan on 08.09.2016.
 */
@ApplicationScoped
public class RocNetService {

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

    private List<String> queue = new ArrayList<>();

    private List<String> block = new ArrayList<>();

    private Map<String, List<String>> departuredBlock = new HashMap<>();

    @PostConstruct
    public void startup() {
        Thread th = new Thread(() -> {
            InetAddress addr = null;
            try {
                addr = InetAddress.getByName("224.0.0.1");
                MulticastSocket ms = new MulticastSocket(4321);
                ms.setReuseAddress(true);
                ms.joinGroup(addr);

                while (!end) {
                    try {
                        byte[] buffer = new byte[1024 * 8];

                        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                        ms.receive(dp);
                        processPacket(dp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        th.setDaemon(true);
        th.setName("rocnet-receiver");
        th.start();
    }

    private void processPacket(DatagramPacket packet) throws IOException {
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
        int code = bin.read();
        int length = bin.read();

        byte[] data = new byte[length];
        bin.read(data);

        if (group == Group.Display) {
            System.out.println("TEXT = " + new String(data) + "  [" + receipientIdL + "]");
            String text = new String(data).trim();
            Gson g = new Gson();
            RocNetText o = g.fromJson(text, RocNetText.class);
            if (o.getType() == TYPE.remove) {
                queue.remove(o.getId());
                departuredBlock.remove(o.getId());
            } else if (o.getType() == TYPE.enqueue) {
                String id = o.getId();
                queue.add(id);
                departuredBlock.put(id, new ArrayList<>());
            } else if (o.getType() == TYPE.enter) {
                block.add(o.getBid());
            } else if (o.getType() == TYPE.departure) {
                block.remove(o.getBid());
                if (departuredBlock.containsKey(o.getId())) {
                    departuredBlock.get(o.getId()).add(o.getBid());
                }
            } else {
                System.err.println("unknown type = " + o.getType());
            }
        } else if (group == Group.Command_Station) {
            if (code == 2) {
                System.out.println("track power: " + data[0]);
                if (data[0] == '0') {
                    this.queue.clear();
                    this.block.clear();
                }
            }
        }

        bout.flush();
    }

    public List<String> getQueue() {
        return queue;
    }

    public List<String> getBlock() {
        return block;
    }

    public Map<String, List<String>> getDeparturedBlock() {
        return departuredBlock;
    }
}
