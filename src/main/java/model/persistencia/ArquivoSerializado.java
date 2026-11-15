package model.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.*;

/** Escreve primeiro um temporário para não destruir o arquivo anterior numa falha. */
final class ArquivoSerializado {
    private ArquivoSerializado() { }

    static void gravar(File arquivo, Object dados) throws IOException {
        Path destino = arquivo.toPath().toAbsolutePath();
        Files.createDirectories(destino.getParent());
        Path temporario = Files.createTempFile(destino.getParent(), "save-", ".tmp");
        try {
            try (ObjectOutputStream saida = new ObjectOutputStream(Files.newOutputStream(temporario))) {
                saida.writeObject(dados);
            }
            try {
                Files.move(temporario, destino, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporario, destino, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporario);
        }
    }
}
