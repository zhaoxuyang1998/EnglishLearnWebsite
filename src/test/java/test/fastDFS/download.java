package test.fastDFS;

import com.github.tobato.fastdfs.domain.proto.storage.DownloadFileWriter;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.neuedu.util.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@DependsOn("springContextHolder")
public class download {
    private FastFileStorageClient fastFileStorageClient= SpringContextHolder.getBean(FastFileStorageClient.class);
    public static void main(String[] args) throws IOException {

        String path="group1/M00/00/00/tlxkUl6IP2uAACOVAAOFnOIZA_Q958.pdf";

    }
}
