package com.hard.rtsp.client;

import com.hard.rtsp.RtpPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class Client {
    /**
     * GUI
     */

    private JFrame frame = new JFrame("Client");
    private JButton setupButton = new JButton("Setup");
    private JButton playButton = new JButton("Play");
    private JButton pauseButton = new JButton("Pause");
    private JButton tearButton = new JButton("Teardown");
    private JPanel mainPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JLabel iconLabel = new JLabel();
    private ImageIcon icon;

    /**
     * RTP variables
     */

    private DatagramPacket rcvdp; //UDP packet received from the server
    private DatagramSocket RTPsocket; //socket to be used to send and receive UDP packets
    private static int RTP_RCV_PORT = 25000; //port where the client will receive the RTP packets

    private Timer timer; //timer used to receive data from the UDP socket
    private byte[] buf; //buffer used to store data received from the server

    /**
     * RTSP variables
     */

    //rtsp states
    private final static int INIT = 0;
    private final static int READY = 1;
    private final static int PLAYING = 2;
    private static int state; //RTSP state == INIT or READY or PLAYING
    private Socket rtspSocket; //socket used to send/receive RTSP messages

    //input and output stream filters
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private static String VideoFileName; //video file to request to the server
    private int RTSPSeqNb = 0; //Sequence number of RTSP messages within the session
    private int RTSPid = 0; //ID of the RTSP session (given by the RTSP Server)

    private final static String CRLF = "\r\n";

    /**
     * Video constants
     */

    private static int MJPEG_TYPE = 26; //RTP payload type for MJPEG video

    public Client() {
        /**
         * build GUI
         */

        //Frame
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //Buttons
        buttonPanel.setLayout(new GridLayout(1, 0));
        buttonPanel.add(setupButton);
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(tearButton);
        setupButton.addActionListener(new setupButtonListener());
        playButton.addActionListener(new playButtonListener());
        pauseButton.addActionListener(new pauseButtonListener());
        tearButton.addActionListener(new tearButtonListener());

        //Image display label
        iconLabel.setIcon(null);

        //frame layout
        mainPanel.setLayout(null);
        mainPanel.add(iconLabel);
        mainPanel.add(buttonPanel);
        iconLabel.setBounds(0, 0, 380, 280);
        buttonPanel.setBounds(0, 280, 380, 50);

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.setSize(new Dimension(390, 370));
        frame.setVisible(true);

        //init timer
        //--------------------------
        timer = new Timer(20, new timerListener());
        timer.setInitialDelay(0);
        timer.setCoalesce(true);

        //allocate enough memory for the buffer used to receive data from the server
        buf = new byte[15000];
    }

    //------------------------------------
    //main
    //------------------------------------
    public static void main(String[] args) throws Exception {
        //Create a Client object
        Client client = new Client();

        //get server RTSP port and IP address from the command line
        //------------------
        int RTSP_server_port = 8554; //Integer.parseInt(argv[1]);
        String ServerHost = "127.0.0.1"; //argv[0];
        InetAddress ServerIPAddr = InetAddress.getByName(ServerHost);

        //get video filename to request:
        VideoFileName = "c:/0/dev-library/tasks/protocols/rtsp/src/main/resources/server/movie.mjpeg"; //argv[2];

        //Establish a TCP connection with the server to exchange RTSP messages
        //------------------
        // theClient.rtspSocket = new Socket(ServerIPAddr, RTSP_server_port);

        client.rtspSocket = new Socket("127.0.0.1", 8554);

        //Set input and output stream filters:
        bufferedReader = new BufferedReader(new InputStreamReader(client.rtspSocket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.rtspSocket.getOutputStream()));

        //init RTSP state:
        state = INIT;
    }

    //------------------------------------
    //Handler for buttons
    //------------------------------------

    //.............
    //TO COMPLETE
    //.............

    //Handler for Setup button
    //-----------------------
    class setupButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //System.out.println("Setup Button pressed !");

            if (state == INIT) {
                //Init non-blocking RTPsocket that will be used to receive data
                try {
                    //construct a new DatagramSocket to receive RTP packets from the server, on port RTP_RCV_PORT
                    //RTPsocket = ...
                    RTPsocket = new DatagramSocket(RTP_RCV_PORT);


                    //   set TimeOut value of the socket to 5msec.


                    RTPsocket.setSoTimeout(5);
                    //set TimeOut value of the socket to 5msec.
                    //....

                } catch (SocketException se) {
                    System.out.println("Socket exception: " + se);
                    System.exit(0);
                }

                //init RTSP sequence number
                RTSPSeqNb = 1;

                //Send SETUP message to the server
                send_RTSP_request("SETUP");

                //Wait for the response
                if (parse_server_response() != 200)
                    System.out.println("Invalid Server Response");
                else {
                    //change RTSP state and print new state
                    state = READY;
                    System.out.println("New RTSP state: READY");
                    //System.out.println("New RTSP state: ....");
                }
            }//else if state != INIT then do nothing
        }
    }

    //Handler for Play button
    //-----------------------
    class playButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Play Button pressed !");

            if (state == READY) {
                //increase RTSP sequence number
                RTSPSeqNb++;

                //send PLAY message to the server
                send_RTSP_request("PLAY");

                // wait for the response
                if (parse_server_response() != 200)
                    System.out.println("Invalid Server Response");
                else {
                    // change RTSP state and print out new state
                    state = PLAYING;
                    System.out.println("New RTSP state: PLAYING");

                    // start the timer
                    timer.start();
                }
            }//else if state != READY then do nothing
        }
    }

    //Handler for Pause button
    //-----------------------
    class pauseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //System.out.println("Pause Button pressed !");

            if (state == PLAYING) {
                //increase RTSP sequence number

                //........

                //Send PAUSE message to the server
                send_RTSP_request("PAUSE");

                //Wait for the response
                if (parse_server_response() != 200)
                    System.out.println("Invalid Server Response");
                else {
                    //change RTSP state and print out new state
                    //........
                    //System.out.println("New RTSP state: ...");

                    //stop the timer
                    timer.stop();
                }
            }
            //else if state != PLAYING then do nothing
        }
    }

    //Handler for Teardown button
    //-----------------------
    class tearButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //System.out.println("Teardown Button pressed !");

            //increase RTSP sequence number
            // ..........

            //Send TEARDOWN message to the server
            send_RTSP_request("TEARDOWN");

            //Wait for the response
            if (parse_server_response() != 200)
                System.out.println("Invalid Server Response");
            else {
                //change RTSP state and print out new state
                //........
                //System.out.println("New RTSP state: ...");

                //stop the timer
                timer.stop();

                //exit
                System.exit(0);
            }
        }
    }

    //------------------------------------
    //Handler for timer
    //------------------------------------
    class timerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Construct a DatagramPacket to receive data from the UDP socket
            rcvdp = new DatagramPacket(buf, buf.length);

            try {
                //receive the DP from the socket:
                RTPsocket.receive(rcvdp);

                //create an RtpPacket object from the DP
                RtpPacket rtp_packet = new RtpPacket(rcvdp.getData(), rcvdp.getLength());

                //print important header fields of the RTP packet received:
                System.out.println("Got RTP packet with SeqNum # " + rtp_packet.getsequencenumber() + " TimeStamp " + rtp_packet.gettimestamp() + " ms, of type " + rtp_packet.getpayloadtype());

                //print header bitstream:
                rtp_packet.printheader();

                //get the payload bitstream from the RtpPacket object
                int payload_length = rtp_packet.getpayload_length();
                byte[] payload = new byte[payload_length];
                rtp_packet.getpayload(payload);

                //get an Image object from the payload bitstream
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = toolkit.createImage(payload, 0, payload_length);

                //display the image as an ImageIcon object
                icon = new ImageIcon(image);
                iconLabel.setIcon(icon);
            } catch (InterruptedIOException iioe) {
                //System.out.println("Nothing to read");
            } catch (IOException ioe) {
                System.out.println("Exception caught: " + ioe);
            }
        }
    }

    //------------------------------------
    //Parse Server Response
    //------------------------------------
    private int parse_server_response() {
        int reply_code = 0;

        try {
            //parse status line and extract the reply_code:
            String StatusLine = bufferedReader.readLine();
            //System.out.println("RTSP Client - Received from Server:");
            System.out.println(StatusLine);

            StringTokenizer tokens = new StringTokenizer(StatusLine);
            tokens.nextToken(); //skip over the RTSP version
            reply_code = Integer.parseInt(tokens.nextToken());

            //if reply code is OK get and print the 2 other lines
            if (reply_code == 200) {
                String SeqNumLine = bufferedReader.readLine();
                System.out.println(SeqNumLine);

                String SessionLine = bufferedReader.readLine();
                System.out.println(SessionLine);

                //if state == INIT gets the Session Id from the SessionLine
                tokens = new StringTokenizer(SessionLine);
                tokens.nextToken(); //skip over the Session:
                RTSPid = Integer.parseInt(tokens.nextToken());
            }
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex);
            System.exit(0);
        }

        return (reply_code);
    }

    //------------------------------------
    //Send RTSP Request
    //------------------------------------

    //.............
    //TO COMPLETE
    //.............

    private void send_RTSP_request(String request_type) {
        try {
            bufferedWriter.write(request_type + " " + VideoFileName + " " + "RTSP/1.0" + CRLF);

            bufferedWriter.write("CSeq: " + RTSPSeqNb + CRLF);

            if (request_type.equals("SETUP")) {
                bufferedWriter.write("Transport: RTP/UDP; client_port= " + RTP_RCV_PORT + CRLF);
            } else {
                bufferedWriter.write("Session: " + RTSPid + CRLF);
            }

            bufferedWriter.flush();
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex);
        }
    }
}
