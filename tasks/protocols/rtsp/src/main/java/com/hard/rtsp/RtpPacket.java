package com.hard.rtsp;

public class RtpPacket {
    // size of the RTP header:
    private static int HEADER_SIZE = 12;

    // Fields that compose the RTP header
    public int Version;
    public int Padding;
    public int Extension;
    public int CC;
    public int Marker;
    public int PayloadType;
    public int SequenceNumber;
    public int TimeStamp;
    public int Ssrc;

    // Bitstream of the RTP header
    public byte[] headers;

    // size of the RTP payload
    public int payload_size;

    // Bitstream of the RTP payload
    public byte[] payload;

    // --------------------------
    // Constructor of an RtpPacket object from header fields and payload
    // bitstream
    // --------------------------
    public RtpPacket(int PType, int Framenb, int Time, byte[] data, int data_length) {
        // fill by default header fields:
        Version = 2;
        Padding = 0;
        Extension = 0;
        CC = 0;
        Marker = 0;
        Ssrc = 0;

        // fill changing header fields:
        SequenceNumber = Framenb;
        TimeStamp = Time;
        PayloadType = PType;

        // build the header bistream:
        // --------------------------
        headers = new byte[HEADER_SIZE];

        headers[1] = (byte) ((Marker << 7) | PayloadType);
        headers[2] = (byte) (SequenceNumber >> 8);
        headers[3] = (byte) (SequenceNumber);

        for (int i = 0; i < 4; i++)
            headers[7 - i] = (byte) (TimeStamp >> (8 * i));

        for (int i = 0; i < 4; i++)
            headers[11 - i] = (byte) (Ssrc >> (8 * i));

        payload_size = data_length;
        payload = new byte[data_length];
        payload = data;
    }

    // --------------------------
    // Constructor of an RtpPacket object from the packet bistream
    // --------------------------
    public RtpPacket(byte[] packet, int packet_size) {
        // fill default fields:
        Version = 2;
        Padding = 0;
        Extension = 0;
        CC = 0;
        Marker = 0;
        Ssrc = 0;

        // check if total packet size is lower than the header size
        if (packet_size >= HEADER_SIZE) {
            // get the header bitsream:
            headers = new byte[HEADER_SIZE];
            for (int i = 0; i < HEADER_SIZE; i++)
                headers[i] = packet[i];

            // get the payload bitstream:
            payload_size = packet_size - HEADER_SIZE;
            payload = new byte[payload_size];
            for (int i = HEADER_SIZE; i < packet_size; i++)
                payload[i - HEADER_SIZE] = packet[i];

            // interpret the changing fields of the header:
            PayloadType = headers[1] & 127;
            SequenceNumber = unsigned_int(headers[3])
                    + 256 * unsigned_int(headers[2]);

            TimeStamp = unsigned_int(headers[7])
                    + 256 * unsigned_int(headers[6])
                    + 65536 * unsigned_int(headers[5])
                    + 16777216 * unsigned_int(headers[4]);
        }
    }

    // --------------------------
    // getPayload: return the payload bistream of the RtpPacket and its size
    // --------------------------
    public int getPayload(byte[] data) {
        for (int i = 0; i < payload_size; i++)
            data[i] = payload[i];

        return payload_size;
    }

    // --------------------------
    // getPayload_length: return the length of the payload
    // --------------------------
    public int getPayload_length() {
        return payload_size;
    }

    // --------------------------
    // getLength: return the total length of the RTP packet
    // --------------------------
    public int getLength() {
        return payload_size + HEADER_SIZE;
    }

    // --------------------------
    // getpacket: returns the packet bitstream and its length
    // --------------------------
    public int getPacket(byte[] packet) {
        // construct the packet = header + payload
        for (int i = 0; i < HEADER_SIZE; i++)
            packet[i] = headers[i];

        for (int i = 0; i < payload_size; i++)
            packet[i + HEADER_SIZE] = payload[i];

        // return total size of the packet
        return payload_size + HEADER_SIZE;
    }

    // --------------------------
    // getTimestamp
    // --------------------------
    public int getTimestamp() {
        return TimeStamp;
    }

    // --------------------------
    // getSequenceNumber
    // --------------------------
    public int getSequenceNumber() {
        return SequenceNumber;
    }

    // --------------------------
    // getPayloadType
    // --------------------------
    public int getPayloadType() {
        return PayloadType;
    }

    // --------------------------
    // print headers without the SSRC
    // --------------------------
    public void printHeader() {
        for (int i = 0; i < (HEADER_SIZE - 4); i++) {
            for (int j = 7; j >= 0; j--)
                if (((1 << j) & headers[i]) != 0)
                    System.out.print("1");
                else
                    System.out.print("0");
            System.out.print(" ");
        }

        System.out.println();
    }

    // return the unsigned value of 8-bit integer nb
    public static int unsigned_int(int nb) {
        if (nb >= 0)
            return nb;
        else
            return 256 + nb;
    }
}
