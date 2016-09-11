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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
        Code code = Code.values()[bin.read()];
        int length = bin.read();

        byte[] data = new byte[length];
        bin.read(data);

        if (group == Group.Display) {
            System.out.println("TEXT = " + new String(data) + "  [" + receipientIdL + "]");
            if (new String(data).trim().isEmpty()) {
                if (queue.size() > 0) {
                    queue.remove(0);
                }
            } else {
                String text = new String(data).trim();
                Gson g = new Gson();
                JsonObject o = g.fromJson(text, JsonObject.class);

//                String next = o.get("text").getAsString();
//                String train = "";
//                if (next.indexOf('|') != -1) {
//                    train = next.substring(0, next.indexOf('|'));
//                    next = next.substring(train.length() + 1);
//                }
//                Pattern p = Pattern.compile("(?i)([a-z]+)([0-9]+)");
//                Matcher m = p.matcher(train);
//                Line line = null;
//                if (m.find()) {
//                    line = new Line(m.group(1).toLowerCase(), Integer.parseInt(m.group(2)));
//                }
                String id = o.get("id").getAsString();
                queue.add(id);
            }
        }

        bout.flush();
    }

    @PreDestroy
    public void stop() {
        end = true;
    }

    public List<String> getQueue() {
        return queue;
    }
}
