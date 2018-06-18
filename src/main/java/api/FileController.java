package api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import entity.Team;
import repository.TeamRepository;

@RestController
@RequestMapping("api/file")
public class FileController {
	
	@Value("#{config['uploaded.folder']}")
	String uploadedFolder;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@PostMapping
	public ResponseEntity<Void> write(@RequestParam("file") MultipartFile mFile, @RequestParam String type, @RequestParam int id) {
		
		try {
			
			String requestUrl = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURL().toString();
			
			String path = uploadedFolder + File.separator + type + File.separator + id;
			
			byte[] bytes = mFile.getBytes();
			File dir = new File(path);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(path + File.separator + mFile.getOriginalFilename());
			if(!file.exists()) {
				file.createNewFile();
			}
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			stream.close();
			Team team = teamRepository.findById(id).get();
			team.setPictureUrl(requestUrl+"/" + type + "/" + id + "/" + mFile.getOriginalFilename());
			teamRepository.save(team);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
	}
	
	@GetMapping("/{type}/{id}/{fileName:.+}")
	public ResponseEntity<?> read(@PathVariable String type, @PathVariable String id, @PathVariable String fileName){
		
		String path = uploadedFolder + File.separator+ type + File.separator + id;
		try{
			File file = new File(path + File.separator + fileName);
			if(file.exists() && file.canRead()) {
				Resource resource = new UrlResource(file.toURI());
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition", "attachment; fileName=\"" + resource.getFilename() + "\"");
				return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
	}
  
	
	
}
