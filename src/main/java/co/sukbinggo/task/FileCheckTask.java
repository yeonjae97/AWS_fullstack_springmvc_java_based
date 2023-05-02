package co.sukbinggo.task;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.sukbinggo.controller.UploadController;
import co.sukbinggo.domain.AttachVO;
import co.sukbinggo.mapper.AttachMapper;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class FileCheckTask {
	// ("초 분 시 일 월 요일 (연도))
	// ex) 2주, 4주의 목요일 02시 마다
	// "0 0 2 * * 4#2, 4#4"
	
	@Autowired
	private AttachMapper attachMapper;
	
//	@Scheduled(cron="0 * * * * *")
	public void check(){
		log.warn("file check");
	}
	
	@Scheduled(cron="0 0 2 * * *")
	public void checkFiles(){
		log.info("========== DB 목록 =================");
		List<AttachVO> fileList = attachMapper.getOldFiles();
		fileList.forEach(log::warn);
		log.info("총 갯수 : " + fileList.size());
		
		log.info("========== 어제 날짜 =================");
		log.warn(getYesterday());
		
		log.info("========== FS 목록 =================");
		List<File> files = fileList.stream().map(vo -> vo.getOriginFile()).collect(Collectors.toList());
		fileList.stream().filter(vo -> vo.isImage()).forEach(vo -> files.add(vo.getThumbFile()));
		
		files.forEach(log::info);
		
		log.info("========== FS의 폴더 내 모든 파일 목록 =================");
		List<File> realFiles =new ArrayList<File>(Arrays.asList( new File(UploadController.getPATH(), getYesterday()).listFiles()));
		realFiles.forEach(log::info);

		
		log.info("========== FS의 폴더 내 모든 파일 목록 중 DB와 일치 하지 않는 목록 ================="); // 최종적으로는 삭제되어야만 한다.
		realFiles =new ArrayList<File>(Arrays.asList( new File(UploadController.getPATH(), getYesterday()).listFiles(f -> !files.contains(f))));
		realFiles.forEach(log::info);
		realFiles.forEach(f -> f.delete());
	}
	
	public String getYesterday(){
		return new SimpleDateFormat("yyyy/MM/dd").format(new Date().getTime()- 1000 * 60 * 60 * 24);
	}
}
