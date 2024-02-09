package pt.ipleiria.estg.dei.lusitaniatravel.listeners;

import java.io.File;

public interface DownloadListener {
    void onDownloadComplete(File file);
    void onDownloadFailed(String errorMessage);
}
