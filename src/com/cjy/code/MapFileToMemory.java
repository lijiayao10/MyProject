package com.cjy.code;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Scanner;

public class MapFileToMemory {
    public static void main(String[] args) throws IOException {
        File file = new File("d:\\policy_biz_id_2015.dat");
        MapFileToMemory mf = new MapFileToMemory();
        try {
            mf.structXML(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block 
            e.printStackTrace();
        }
    }

    /**
     * 对一个文件画块后开启不同的线程，线程中划分的块中要记录下一个块的位置。
     * 
     * @throws IOException
     */
    private List structXML(File file) throws IOException {

        //   for(int i=1;i<=8;i++){ 
        try {
            MappedByteBuffer buffer = new RandomAccessFile(file, "r").getChannel().map(FileChannel.MapMode.READ_ONLY,
            //   file.length()*7/8,file.length()/8); 
                    0, file.length());
            new Thread(new CompareThread(buffer)).start();

            System.out.println("ddd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //   } 
        return null;
    }

    //内部类实现读取文件 
    class CompareThread implements Runnable {
        private MappedByteBuffer buffer      = null;
        // 缓冲区大小为3M 
        final int                BUFFER_SIZE = 0x1000;
        // 每次读出3M的内容 
        byte[]                   dst         = new byte[BUFFER_SIZE];

        public CompareThread(MappedByteBuffer buffer) {
            this.buffer = buffer;
            //   this.length = length; 
        }

        public void run() {
            long start = System.currentTimeMillis();
            String enterStr = "\n";
            StringBuffer strBuf = new StringBuffer("");
            long total = 0;
            for (int offset = 0; offset < this.buffer.capacity(); offset += BUFFER_SIZE) {
                if (this.buffer.capacity() - offset >= BUFFER_SIZE) {
                    for (int i = 0; i < BUFFER_SIZE; i++)
                        dst[i] = this.buffer.get(offset + i);
                } else {
                    for (int i = 0; i < this.buffer.capacity() - offset; i++)
                        dst[i] = this.buffer.get(offset + i);
                }

                int length = (this.buffer.capacity() % BUFFER_SIZE == 0) ? BUFFER_SIZE : this.buffer.capacity()
                        % BUFFER_SIZE;

                System.out.println("================>");
                // 将得到的3M内容给Scanner，这里的XXX是指Scanner解析的分隔符
                Scanner scan = new Scanner(new ByteArrayInputStream(dst)).useDelimiter(" ");
                while (scan.hasNext()) {
                    // 这里为对读取文本解析的方法
                    System.out.print(scan.next() + " ");
                }
                scan.close();

                //                String tempString = new String(dst, 0, length);
                //                int fromIndex = 0;
                //                int endIndex = 0;
                //
                //                System.out.println("ddddddddddddd");
                //
                //                while ((tempString.length() >= fromIndex)) {
                //                    endIndex = tempString.indexOf(enterStr, fromIndex);
                //                    if (endIndex == -1) {
                //                        endIndex = tempString.length();
                //                    }
                //                    total++;
                //                    String line = tempString.substring(fromIndex, endIndex);
                //                    System.out.println("test " + line);
                //                    line = new String(strBuf.toString() + line);
                //                    fromIndex = endIndex + 1;
                //                    strBuf.delete(0, strBuf.length());
                //
                //                    if (total % 10000 == 0) {
                //                        System.out.println("total----->" + total + "====花费时间===="
                //                                + (System.currentTimeMillis() - start));
                //                    }
                //                }
                //                //   if (rSize > tempString.length()) { 
                //                //   strBuf.append(tempString.substring(fromIndex,tempString.length())); 
                //                //   } else { 
                //                //   strBuf.append(tempString.substring(fromIndex, rSize)); 
                //                //   } 
            }
            System.out.println("total----->" + total + "====花费时间====" + (System.currentTimeMillis() - start));

        }
    }
}
