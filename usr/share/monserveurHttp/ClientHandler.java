package serverconfig;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) {
                out.println("HTTP/1.1 400 Bad Request");
                out.println("Content-Type: text/html");
                out.println();
                out.println("<html><body><h1>400 Bad Request</h1></body></html>");
                return;
            }
            System.out.println("Requête reçue: " + requestLine);

            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String filePath = requestParts[1].equals("/") ? "/index.html" : requestParts[1];

            String parameters = "";
            if (method.equals("GET") && filePath.contains("?")) {
                String[] parts = filePath.split("\\?");
                filePath = parts[0];
                parameters = parts.length > 1 ? parts[1] : "";
            } else if (method.equals("POST")) {
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null && !line.isEmpty()) {
                }
                char[] buffer = new char[1024];
                int charsRead;
                while (in.ready() && (charsRead = in.read(buffer)) != -1) {
                    body.append(buffer, 0, charsRead);
                }
                parameters = body.toString();
                System.out.println("Données POST : " + parameters);
            }

            Path path = Paths.get(SimpleHttpServer.getDocRoot() + filePath);
            File file = path.toFile();

            if (file.exists() && !file.isDirectory()) {
                if (filePath.endsWith(".php") && SimpleHttpServer.isPhpEnabled()) {
                    servePhp(out, filePath, method, parameters);
                } else {
                    serveFile(clientSocket.getOutputStream(), file);
                }
            } else {
                out.println("HTTP/1.1 404 Not Found");
                out.println("Content-Type: text/html");
                out.println();
                out.println("<html><body><h1>404 Not Found</h1></body></html>");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void serveFile(OutputStream outputStream, File file) throws IOException {
        String contentType = Files.probeContentType(file.toPath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        PrintWriter out = new PrintWriter(outputStream, true);
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: " + contentType);
        out.println("Content-Length: " + file.length());
        out.println();
        out.flush();

        try (InputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        outputStream.flush();
    }

    private void servePhp(PrintWriter out, String filePath, String method, String parameters) {
        try {
            File phpFile = new File(SimpleHttpServer.getDocRoot() + filePath);
            if (!phpFile.exists()) {
                out.println("HTTP/1.1 404 Not Found");
                out.println("Content-Type: text/html");
                out.println();
                out.println("<html><body><h1>Fichier introuvable : " + filePath + "</h1></body></html>");
                return;
            }

            String phpOutput = PhpReader.contentPhp(phpFile, method, parameters);

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html; charset=UTF-8");
            out.println("Content-Length: " + phpOutput.getBytes().length);
            out.println();
            out.print(phpOutput);

        } catch (IOException e) {
            e.printStackTrace();
            out.println("HTTP/1.1 500 Internal Server Error");
            out.println("Content-Type: text/html; charset=UTF-8");
            out.println();
            out.println("<html><body><h1>Erreur serveur : " + e.getMessage() + "</h1></body></html>");
        }
    }
}
