package hr.vvg.java.vjezbe.utilities;

import hr.vvg.java.vjezbe.entities.Publication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class containing methods that operate on files, reads and writes data from and into text files.
 * <p>
 * Created by marko on 5/14/15.
 */
public class FileOperator {

    private static Logger logger = LoggerFactory.getLogger(FileOperator.class);

    public static Optional<List<String>> readFile(String fileURL) {

        List<String> dataArray = new ArrayList<>();

        logger.info("Read file operations on " + fileURL, "Using FileChannel and ByteBuffer with Java "
                + System.getProperty("java.version"));

        try (RandomAccessFile raFile = new RandomAccessFile(fileURL, "rw")) {
            StringBuilder line = new StringBuilder();

            FileChannel fileChannel = raFile.getChannel();

            // TODO use RandomAccessFile methods seek, write, ... ?
            ByteBuffer buf = ByteBuffer.allocate(128);

            int bytesRead = fileChannel.read(buf);

            while (bytesRead != -1) {

                System.out.println("bytesRead " + bytesRead);

                buf.flip();

                while (buf.hasRemaining()) {
                    // TODO use bytes[] with String constructor to implement UTF-8 as default charset when translating bytes
                    char readChar = (char) buf.get();

                    if (readChar != '\n') {
                        line.append(readChar);
                    } else {
                        if (line.toString().contains("#")) {
                            // write file comments to logger
                            logger.info(fileURL + ": " + line.toString());
                        } else {
                            // add line from file to data array
                            dataArray.add(line.toString());
                        }
                        // delete content from line string
                        line.setLength(0);
                    }
                }

                buf.clear();

                bytesRead = fileChannel.read(buf);

            }

            raFile.close();

        } catch (IOException e) {
            logger.error("FileOperator", "Unable to open file.");
            e.printStackTrace();
            System.exit(0);
        }
        return Optional.of(dataArray);
    }

    public static <T extends Publication> void writeFile(T pub, String fileURL) {

        try (RandomAccessFile raFile = new RandomAccessFile(fileURL, "rw")) {

            FileChannel fileChannel = raFile.getChannel();

            ByteBuffer buf = ByteBuffer.allocate(pub.fileData().getBytes().length);

            buf.clear();

            buf.put(pub.fileData().getBytes());

            buf.flip();

            // set cursor at the end of the existing file/data
            raFile.seek(fileChannel.size());

            while (buf.hasRemaining()) {
                fileChannel.write(buf);
            }

        } catch (IOException ex) {
            logger.error("FileOperator", "Unable to write to file.");
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
