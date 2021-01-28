import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NioClient {
    private static final int PORT = 1234;
    private static final String HOST = "localhost";
    private static final String FILE_NAME = "D:/2020/Загрузки/Math Lab/Mathworks Matlab R2017b x64/dv1.iso";

    public static void main(String args[]) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(HOST, PORT);//Реализуем IP-адрес сокета
        try (SocketChannel socketChannel = SocketChannel.open(serverAddress)) {//Отькрываем канал для указанного
            // удаленного адреса и так же подключаемся к нему
            /*try (FileChannel fileChannel = FileChannel.open(Paths.get(FILE_NAME))) { //Открываем файловый канал и
                // указываем путь к файлу
                fileChannel.transferTo(0, fileChannel.size(), socketChannel);//Передаем байты из канала
                // fileChannel в канал socketChannel*/

            RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw");
            long fileSize = file.length();
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int)fileSize);//Создаем буфер и задаем ему размер

            int bytesRead = channel.read(buffer);//Вычисляем сколько байт в буфере
            while (bytesRead > -1) {
                buffer.flip();//переключает режим буфера с режима записи на режим чтения. Он также устанавливает позицию
                // обратно в 0 и устанавливает предел, в котором позиция была во время записи.
                while (buffer.hasRemaining()) {
                    socketChannel.write(buffer);//Записываем данные из буфера в соккет канал
                }
                buffer.clear();//Устанавливаем позицию в ноль
                bytesRead = channel.read(buffer);//Вычисляем сколько байт в буфере
            }
            file.close();
            }
        }
    }
