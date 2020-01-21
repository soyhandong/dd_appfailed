package kr.co.ddapp.api.v1.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 *  뉴스관련 API 콘트롤러
 */
@RestController
@RequestMapping("/api/v1")
public class NewsRestController {

    @RequestMapping(value = "/public/news/{gubun}/latest", method = RequestMethod.GET)
    public ResponseEntity getLatestNews(@RequestParam Map<String, Object> params, @PathVariable("gubun") String gubun ){
        return ResponseEntity.ok(new HashMap<>());
    }


}
