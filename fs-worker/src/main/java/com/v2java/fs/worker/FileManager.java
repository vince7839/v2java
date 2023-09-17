package com.v2java.fs.worker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.BitSet;
import javax.annotation.PostConstruct;
import javax.swing.filechooser.FileSystemView;

import com.v2java.fs.worker.netty.slave.FilePacket;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class FileManager {

    @Autowired
    WorkerConfig workerConfig;

    File dataDir;

    @Getter
    BitSet bitSet = new BitSet();

    @PostConstruct
    public void init(){
        findDataDir();
        scanFile();
    }

    public void findDataDir(){
        String dataDirPath = workerConfig.getDataDir();
        if (StringUtils.isEmpty(dataDirPath)){
            dataDir = FileSystemView.getFileSystemView().getHomeDirectory();
        }else{
            dataDir = new File(dataDirPath);
            if (dataDir.exists()){
                if (dataDir.isFile()){
                    throw new RuntimeException();
                }else{
                    //TODO 检查读写权限、文件个数等
                }
            }else{
                boolean success = dataDir.mkdirs();
                if (!success){
                    throw new RuntimeException();
                }
            }
        }
    }

    public void scanFile(){
        log.info("data dir:{},files:{}",dataDir.getAbsolutePath(),dataDir.listFiles());
        for(File file:dataDir.listFiles()){
            update(file.getName(),true);
        }
    }

    public void update(String filename,boolean exists){
        try{
            Integer postion = Integer.valueOf(filename);
            bitSet.set(postion,exists);
        }catch (Exception e){
            log.error("不可识别的水位：{}",filename);
        }
    }

    public String toBase64(){
        byte[] bytes = Base64.getEncoder().encode(bitSet.toByteArray());
        return new String(bytes);
    }

    public File getFileByWatermark(Long watermark){
        String path = dataDir.getAbsolutePath()+"/"+watermark;
        return new File(path);
    }

    public void saveWatermarkFile(FilePacket filePacket){
        File file = getFileByWatermark(filePacket.getWatermark());
        try (
                OutputStream out = new FileOutputStream(file)
        ) {
            out.write(filePacket.getBytes());
            bitSet.set(filePacket.getWatermark().intValue(),true);
            //TODO tmp文件防止重复同步 增加队列有序同步 同步前检查文件是否存在或者正在同步
        } catch (Exception e) {
            log.error("write watermark file exception", e);
        }
    }
}
