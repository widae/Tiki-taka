package api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import entity.Ground;
import entity.GroundImage;
import service.IGroundService;


@RestController
@RequestMapping("api/ground")
public class GroundController{
	
	@Autowired
	IGroundService groundService;
	
	@GetMapping("{pageNum}")
	public ResponseEntity<List<Ground>> getGrounds(@PathVariable int pageNum, @RequestParam String text){
		List<Ground> list = groundService.getGrounds(pageNum, text);
		/*for(int i = 0; i< list.size(); i++) {
			List<GroundImage> images = list.get(i).getGroundImages();
			if(images.size() > 0)
				System.out.println(images.get(0).getPictureUrl());
		}*/
		return new ResponseEntity<List<Ground>>(list , HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public void handleAllException(Exception ex) {
		ex.printStackTrace();
		
	}

}