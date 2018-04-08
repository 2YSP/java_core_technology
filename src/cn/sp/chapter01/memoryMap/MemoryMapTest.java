package cn.sp.chapter01.memoryMap;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;

/**
 * 内存映射测试
 * 性能比较
 * Created by 2YSP on 2018/1/8.
 */
public class MemoryMapTest {
    public static void main(String[] args) throws IOException {
        System.out.println("Input stream:");
        long start = System.currentTimeMillis();
        Path fileName = Paths.get(args[0]);
        long crcValue = checkSumInputStream(fileName);
        long end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds");

        System.out.println("Buffered input stream:");
        start = System.currentTimeMillis();
        crcValue = checkSumBufferedInputStream(fileName);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds");

        System.out.println("Random Access File:");
        start = System.currentTimeMillis();
        crcValue = checkSumRandomAccessFile(fileName);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds");

        System.out.println("Mapped File:");
        start = System.currentTimeMillis();
        crcValue = checkSumMappedFile(fileName);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds");
    }

    private static long checkSumMappedFile(Path fileName) throws IOException {
        FileChannel channel = FileChannel.open(fileName);
        CRC32 crc = new CRC32();
        int length = (int) channel.size();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);
        for (int p = 0; p < length; p++) {
            int c = buffer.get(p);
            crc.update(c);
        }
        return crc.getValue();
    }

    private static long checkSumRandomAccessFile(Path fileName) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileName.toFile(), "r");
        CRC32 crc32 = new CRC32();
        long length = file.length();
        for (int p = 0; p < length; p++) {
            file.seek(p);
            int c = file.readByte();
            crc32.update(c);
        }
        return crc32.getValue();
    }

    private static long checkSumBufferedInputStream(Path fileName) throws IOException {
        InputStream in = new BufferedInputStream(Files.newInputStream(fileName));
        CRC32 crc32 = new CRC32();
        int c;
        while ((c = in.read()) != -1) {
            crc32.update(c);
        }
        return crc32.getValue();
    }

    private static long checkSumInputStream(Path fileName) throws IOException {
        InputStream in = Files.newInputStream(fileName);
        CRC32 crc = new CRC32();
        int c;
        while ((c = in.read()) != -1) {
            crc.update(c);
        }
        return crc.getValue();
    }
}
