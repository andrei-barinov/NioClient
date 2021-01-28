import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioServer {
    private static final int PORT = 1234;
    private static final String FILE_NAME = "D:/Java/NioClient/direction_2/dv1.iso";

    public static void main(String args[]) throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {//Открываем сервер соккет
            serverSocketChannel.bind(new InetSocketAddress(PORT));//Устанавливаем связь соккета с портом
            try (SocketChannel socketChannel = serverSocketChannel.accept()) {//Ждем запроса на подключение от клиента
                try (FileChannel fileChannel = FileChannel.open(Paths.get(FILE_NAME), StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE)) {
                    fileChannel.transferFrom(socketChannel, 0, Long.MAX_VALUE);//Передаем байты из потока
                    // socketChannel в поток fileChannel и дальше по указаному пути создается файл
                }
            }
        }
    }
}